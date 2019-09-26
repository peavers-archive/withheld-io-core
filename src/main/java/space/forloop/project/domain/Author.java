package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

  private String avatarUrl;

  private String name;

  private String email;

  @Override
  public boolean equals(final Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Author author = (Author) o;

    return email != null ? email.equals(author.email) : author.email == null;
  }

  @Override
  public int hashCode() {

    return email != null ? email.hashCode() : 0;
  }

}
