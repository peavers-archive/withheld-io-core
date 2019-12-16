package space.forloop.project.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import space.forloop.project.security.domain.FirebaseUserDetails;

/** @author Chris Turner (chris@forloop.space) */
public class UsernamePasswordAuthenticationBearer {

  public static Mono<Authentication> create(final FirebaseUserDetails userDetails) {
    return Mono.justOrEmpty(
        new UsernamePasswordAuthenticationToken(
            userDetails.getEmail(), null, userDetails.getAuthorities()));
  }
}
