/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

  private String applicant;

  private String downloadUrl;

  private String gitBaseDirectory;

  private String level;

  private String position;

  private String source;

  private String workingDirectory;

  @Builder.Default private boolean underReview = true;

  @Builder.Default private long created = Instant.now().toEpochMilli();

  @Builder.Default private List<FirebaseUser> reviewers = new ArrayList<>();

  @Builder.Default private List<String> reviewGroups = new ArrayList<>();

  @Builder.Default private List<Feedback> feedback = new ArrayList<>();
}
