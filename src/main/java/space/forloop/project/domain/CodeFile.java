/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import java.util.ArrayList;
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
public class CodeFile {

  @Id private String id;

  private String location;

  private String projectId;

  private long size;

  @Builder.Default private ArrayList<CodeLine> codeLines = new ArrayList<>();
}
