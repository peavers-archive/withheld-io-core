/* Licensed under Apache-2.0 */
package space.forloop.project.consumers;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import space.forloop.project.domain.CodeLine;
import space.forloop.project.domain.Comment;

@Slf4j
public class CommentConsumer implements Consumer<CodeLine> {

  private final Authentication authentication;

  public CommentConsumer(final Authentication authentication) {
    this.authentication = authentication;
  }

  @Override
  public void accept(final CodeLine codeLine) {

    final ArrayList<Comment> comments =
        codeLine.getComments().stream()
            .filter(
                comment ->
                    comment
                        .getFirebaseUser()
                        .getUid()
                        .equalsIgnoreCase(authentication.getPrincipal().toString()))
            .collect(Collectors.toCollection(ArrayList::new));
    codeLine.setComments(comments);
  }
}
