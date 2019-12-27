/* Licensed under Apache-2.0 */
package io.withheld.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@UtilityClass
public class AuthUtils {

  public Mono<Authentication> getAuthentication() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .flatMap(Mono::just);
  }
}
