package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

  @Builder.Default private String id = UUID.randomUUID().toString();

  private Reviewer reviewer;

  private String body;

  @Builder.Default private long created = Instant.now().toEpochMilli();
}
