package space.forloop.project.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public class AuthorizationHeaderPayload {

  public static Mono<String> extract(final ServerWebExchange serverWebExchange) {
    return Mono.justOrEmpty(
        serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
  }
}
