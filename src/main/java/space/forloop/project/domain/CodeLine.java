package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeLine {

  private String body;

  @Builder.Default private ArrayList<Comment> comments = new ArrayList<>();
}
