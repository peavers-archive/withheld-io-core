/* Licensed under Apache-2.0 */
package io.withheld.core.consumers;

import io.withheld.core.domain.CodeLine;
import io.withheld.core.domain.Comment;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

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
