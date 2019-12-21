/* Licensed under Apache-2.0 */
package space.forloop.project.security.jwt;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import space.forloop.project.security.domain.FirebaseUserDetails;

import java.util.function.Function;
import java.util.function.Predicate;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthenticationConverter implements ServerAuthenticationConverter {

  private static final String BEARER = "Bearer ";

  private static final Predicate<String> matchBearerLength =
      authValue -> authValue.length() > BEARER.length();

  private static final Function<String, Mono<String>> isolateBearerValue =
      authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length()));

  private final FirebaseAuth firebaseAuth;

  private Mono<FirebaseUserDetails> check(final String unverifiedToken) {
    try {
      final ApiFuture<FirebaseToken> task = firebaseAuth.verifyIdTokenAsync(unverifiedToken);

      final FirebaseToken token = task.get();

      final FirebaseUserDetails firebaseUserDetails =
          FirebaseUserDetails.builder()
              .email(token.getEmail())
              .picture(token.getPicture())
              .userId(token.getUid())
              .username(token.getName())
              .build();

      return Mono.justOrEmpty(firebaseUserDetails);
    } catch (final Exception e) {
      throw new SessionAuthenticationException(e.getMessage());
    }
  }

  @Override
  public Mono<Authentication> convert(final ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange)
        .flatMap(AuthorizationHeaderPayload::extract)
        .filter(matchBearerLength)
        .flatMap(isolateBearerValue)
        .flatMap(this::check)
        .flatMap(UsernamePasswordAuthenticationBearer::create);
  }
}
