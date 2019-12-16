package space.forloop.project.security.jwt;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import space.forloop.project.security.domain.FirebaseUserDetails;

import java.util.concurrent.ExecutionException;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTCustomVerifier {

  private final FirebaseAuth firebaseAuth;

  public Mono<FirebaseUserDetails> check(final String unverifiedToken) {

    final ApiFuture<FirebaseToken> task = firebaseAuth.verifyIdTokenAsync(unverifiedToken);

    try {
      final FirebaseToken token = task.get();

      final FirebaseUserDetails firebaseUserDetails =
          FirebaseUserDetails.builder()
              .email(token.getEmail())
              .picture(token.getPicture())
              .userId(token.getUid())
              .username(token.getName())
              .build();

      return Mono.justOrEmpty(firebaseUserDetails);
    } catch (final InterruptedException | ExecutionException e) {
      throw new SessionAuthenticationException(e.getMessage());
    }
  }
}
