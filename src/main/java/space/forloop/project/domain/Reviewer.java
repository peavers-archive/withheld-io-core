package space.forloop.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reviewer {

    @Id
    private String id;

    private String displayName;

    private String email;

    private String photoUrl;

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Reviewer reviewer = (Reviewer) o;

        return id.equals(reviewer.id);
    }

    @Override
  public int hashCode() {

        return id.hashCode();
  }

}
