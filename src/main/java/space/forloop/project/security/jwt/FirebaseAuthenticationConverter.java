/* Licensed under Apache-2.0 */
package space.forloop.project.security.jwt;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import space.forloop.project.security.domain.FirebaseUserDetails;

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







  
  private Mono<FirebaseToken> verifyToken(final String unverifiedToken) {
    try {
      return Mono.justOrEmpty(firebaseAuth.verifyIdTokenAsync(unverifiedToken).get());
    } catch (final Exception e) {
      return Mono.error(e);
    }
  }

  private Mono<FirebaseUserDetails> buildUserDetails(final FirebaseToken firebaseToken) {
    return Mono.justOrEmpty(
        FirebaseUserDetails.builder()
            .email(firebaseToken.getEmail())
            .picture(firebaseToken.getPicture())
            .userId(firebaseToken.getUid())
            .username(firebaseToken.getName())
            .build());
  }

  private Mono<Authentication> buildAuthentication(final FirebaseUserDetails userDetails) {
    return Mono.justOrEmpty(
        new UsernamePasswordAuthenticationToken(
            userDetails.getEmail(), null, userDetails.getAuthorities()));
  }

  @Override
  public Mono<Authentication> convert(final ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange)
        .flatMap(AuthorizationHeaderPayload::extract)
        .filter(matchBearerLength)
        .flatMap(isolateBearerValue)
        .flatMap(this::verifyToken)
        .flatMap(this::buildUserDetails)
        .flatMap(this::buildAuthentication);
  }
}
