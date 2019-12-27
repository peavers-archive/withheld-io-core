/* Licensed under Apache-2.0 */
package io.withheld.core.domain;

import java.time.Instant;
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
public class Comment {

  @Builder.Default private long created = Instant.now().toEpochMilli();

  @Id @Builder.Default private String id = UUID.randomUUID().toString();

  private FirebaseUser firebaseUser;

  private String body;

  private String codeLineId;
}
