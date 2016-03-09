package in.edu.dsu.cit15.lms.security;

import in.edu.dsu.cit15.lms.data.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by abhishekmunie on 26/01/16.
 */
public class GoogleUserAuthentication implements Authentication {

  private final User principal;
  private final Object details;
  private boolean authenticated;

  public GoogleUserAuthentication(User principal, Object details) {
    this.principal = principal;
    this.details = details;
    authenticated = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new HashSet<GrantedAuthority>(principal.getAuthorities());
  }

  @Override
  public Object getCredentials() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return principal.getId();
  }

  @Override
  public String toString() {
    return "GoogleUserAuthentication{" +
      "principal=" + principal +
      ", details=" + details +
      ", authenticated=" + authenticated +
      '}';
  }
}

