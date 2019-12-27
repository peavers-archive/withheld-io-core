/* Licensed under Apache-2.0 */
package io.withheld.core.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/** @author Chris Turner (chris@forloop.space) */
@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseUser {

  @Builder.Default private List<String> reviewGroup = new ArrayList<>();

  private String displayName;

  private String email;

  private String photoURL;

  private String role;

  private String uid;

  @Override
  public boolean equals(final Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final FirebaseUser that = (FirebaseUser) o;

    return uid != null ? uid.equals(that.uid) : that.uid == null;
  }

  @Override
  public int hashCode() {

    return uid != null ? uid.hashCode() : 0;
  }
}
