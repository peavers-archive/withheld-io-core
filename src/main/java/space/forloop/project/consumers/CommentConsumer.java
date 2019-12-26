package space.forloop.project.consumers;

import org.springframework.security.core.Authentication;
import space.forloop.project.domain.CodeLine;
import space.forloop.project.domain.Comment;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CommentConsumer implements Consumer<CodeLine> {

  private final Authentication authentication;

  public CommentConsumer(Authentication authentication) {
    this.authentication = authentication;
  }

  @Override
  public void accept(CodeLine codeLine) {
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
