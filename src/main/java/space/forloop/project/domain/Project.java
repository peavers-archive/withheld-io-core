/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.time.Instant;
import java.util.HashSet;
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
public class Project {

  @Id @Builder.Default private String id = UUID.randomUUID().toString();

  private String source;

  private String gitBaseDirectory;

  private String workingDirectory;

  private String position;

  private String level;

  private String applicant;

  @Builder.Default private boolean underReview = true;

  @Builder.Default private long created = Instant.now().toEpochMilli();

  @Builder.Default private HashSet<Reviewer> reviewers = new HashSet<>();

  @Builder.Default private HashSet<Feedback> feedback = new HashSet<>();
}
