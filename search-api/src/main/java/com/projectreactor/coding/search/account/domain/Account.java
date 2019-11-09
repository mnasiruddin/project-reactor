package com.projectreactor.coding.search.account.domain;

import com.projectreactor.coding.search.constant.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("account")
public class Account extends BaseEntity implements UserDetails {

  private String firstname;
  private String surname;
  private String username;
  private String password;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;

  @Getter
  @Setter
  private List<UserRole> roles;

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(
        Collectors.toList());
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Returns the username used to authenticate the user. Cannot return <code>null</code>.
   *
   * @return the username (never <code>null</code>)
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired),
   * <code>false</code> if no longer valid (ie expired)
   */
  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  /**
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie non-expired),
   * <code>false</code> if no longer valid (ie expired)
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  @Override
  public boolean isNew() {
    return null == getId();
  }
}
