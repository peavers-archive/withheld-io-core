/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

  @Builder.Default private long created = Instant.now().toEpochMilli();

  /**
   * Comments don't exist outside the concept of {@link CodeLine} so they don't have their own ID.
   * This will generate a fake one to lookup the comments based on it.
   */
  @Id @Builder.Default private String id = UUID.randomUUID().toString();

  private FirebaseUser firebaseUser;

  private String body;

  private String codeLineId;
}
