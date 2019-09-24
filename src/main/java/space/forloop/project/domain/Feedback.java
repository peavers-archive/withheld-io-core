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
public class Feedback {

  @Builder.Default private String id = UUID.randomUUID().toString();

  private Author author;

  private String positive;

  private String negative;

  private int rating;

  @Builder.Default private long created = Instant.now().toEpochMilli();
}
