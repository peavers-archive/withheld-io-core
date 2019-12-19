/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeLine {

  private String body;

  @Builder.Default private ArrayList<Comment> comments = new ArrayList<>();
}
