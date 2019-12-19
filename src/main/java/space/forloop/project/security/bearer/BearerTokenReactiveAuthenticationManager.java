/* Licensed under Apache-2.0 */
package space.forloop.project.security.bearer;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public class BearerTokenReactiveAuthenticationManager implements ReactiveAuthenticationManager {

  @Override
  public Mono<Authentication> authenticate(final Authentication authentication) {
    return Mono.just(authentication);
  }
}
