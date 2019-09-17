package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.egit.github.core.Repository;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

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

  private String notes;

  private CLOC cloc;

  private Author author;

  private Repository repository;

  @Builder.Default private long created = Instant.now().toEpochMilli();

  @Builder.Default private ArrayList<String> reviewers = new ArrayList<>();
}
