/* Licensed under Apache-2.0 */
package space.forloop.project.security.domain;

import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@RequiredArgsConstructor
public class FirebaseUserDetails implements UserDetails {

  private final String userId;
  private final String picture;
  private final String username;
  private final String email;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return null;
  }

  @Override
  public String getPassword() {

    return null;
  }

  @Override
  public boolean isAccountNonExpired() {

    return false;
  }

  @Override
  public boolean isAccountNonLocked() {

    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return false;
  }

  @Override
  public boolean isEnabled() {

    return false;
  }
}
