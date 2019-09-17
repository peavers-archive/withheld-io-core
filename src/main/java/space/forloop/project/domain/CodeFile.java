package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeFile {

  @Id private String id;

  private String location;

  private String projectId;

  @Builder.Default private ArrayList<CodeLine> codeLines = new ArrayList<>();
}
