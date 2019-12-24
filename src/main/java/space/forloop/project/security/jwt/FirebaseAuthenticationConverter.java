/* Licensed under Apache-2.0 */
package space.forloop.project.security.jwt;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.FirebaseUser;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAuthenticationConverter implements ServerAuthenticationConverter {

  private static final String BEARER = "Bearer ";

  private final Predicate<String> matchBearerLength =
      authValue -> authValue.length() > BEARER.length();

  private final Function<String, Mono<String>> isolateBearerValue =
      authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length()));

  private final FirebaseAuth firebaseAuth;

  private Mono<String> extractHeader(final ServerWebExchange serverWebExchange) {
    return Mono.justOrEmpty(
        serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
  }

  private Mono<FirebaseToken> verifyToken(final String unverifiedToken) {
    try {
      return Mono.justOrEmpty(firebaseAuth.verifyIdTokenAsync(unverifiedToken).get());
    } catch (final Exception e) {
      return Mono.error(e);
    }
  }

  private Mono<FirebaseUser> buildUserDetails(final FirebaseToken firebaseToken) {
    return Mono.justOrEmpty(
        FirebaseUser.builder()
            .email(firebaseToken.getEmail())
            .photoURL(firebaseToken.getPicture())
            .uid(firebaseToken.getUid())
            .displayName(firebaseToken.getName())
            .build());
  }

  private Mono<Authentication> buildAuthentication(final FirebaseUser userDetails) {
    return Mono.justOrEmpty(
        new UsernamePasswordAuthenticationToken(userDetails.getUid(), null, null));
  }

  @Override
  public Mono<Authentication> convert(final ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange)
        .flatMap(this::extractHeader)
        .filter(matchBearerLength)
        .flatMap(isolateBearerValue)
        .flatMap(this::verifyToken)
        .flatMap(this::buildUserDetails)
        .flatMap(this::buildAuthentication);
  }
}
