package space.forloop.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sum {

  @JsonProperty("blank")
  private int blank;

  @JsonProperty("comment")
  private int comment;

  @JsonProperty("code")
  private int code;

  @JsonProperty("nFiles")
  private int nFiles;
}
