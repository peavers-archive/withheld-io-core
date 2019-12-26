/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.util.ArrayList;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeLine {

  /**
   * Code lines don't exist outside the concept of {@link CodeFile} so they don't have their own ID.
   * This will generate a fake one to lookup the comments based on it.
   */
  @Id @Builder.Default private String id = UUID.randomUUID().toString();

  private String body;

  @Builder.Default private ArrayList<Comment> comments = new ArrayList<>();
}
