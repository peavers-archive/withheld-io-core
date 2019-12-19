/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

  private Reviewer reviewer;

  private String body;

  @Builder.Default private long created = Instant.now().toEpochMilli();
}
