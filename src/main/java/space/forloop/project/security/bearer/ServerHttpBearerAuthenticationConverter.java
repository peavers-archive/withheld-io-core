package space.forloop.project.security.bearer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import space.forloop.project.security.jwt.AuthorizationHeaderPayload;
import space.forloop.project.security.jwt.JWTCustomVerifier;
import space.forloop.project.security.jwt.UsernamePasswordAuthenticationBearer;

import java.util.function.Function;
import java.util.function.Predicate;

/** @author Chris Turner (chris@forloop.space) */
@Component
@RequiredArgsConstructor
public class ServerHttpBearerAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

  private static final String BEARER = "Bearer ";

  private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();

  private static final Function<String, Mono<String>> isolateBearerValue = authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length()));

  private final JWTCustomVerifier jwtVerifier ;

  @Override
  public Mono<Authentication> apply(final ServerWebExchange serverWebExchange) {
    return Mono.justOrEmpty(serverWebExchange)
        .flatMap(AuthorizationHeaderPayload::extract)
        .filter(matchBearerLength)
        .flatMap(isolateBearerValue)
        .flatMap(jwtVerifier::check)
        .flatMap(UsernamePasswordAuthenticationBearer::create);
  }
}
