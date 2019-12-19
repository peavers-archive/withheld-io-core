/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

  private String level;

  private String nextStage;

  private String comments;

  private Reviewer reviewer;
}
