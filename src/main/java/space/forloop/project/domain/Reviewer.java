/* Licensed under Apache-2.0 */
package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Reviewer {

  @Id private String id;

  private String displayName;

  @Field("email")
  private String email;

  private String photoUrl;

  private String role;

  @Override
  public boolean equals(final Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Reviewer reviewer = (Reviewer) o;

    return email != null ? email.equals(reviewer.email) : reviewer.email == null;
  }

  @Override
  public int hashCode() {

    return email != null ? email.hashCode() : 0;
  }
}
