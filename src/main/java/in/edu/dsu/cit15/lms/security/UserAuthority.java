package in.edu.dsu.cit15.lms.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by abhishekmunie on 23/01/16.
 */
public enum UserAuthority implements GrantedAuthority {

  ADMIN(1), GUEST(1 << 1), NEW_USER(1 << 2), USER(1 << 3), MODERATOR(1 << 4);

  /**
   * This bit is used to represent the Authority of User in DataStore.
   * Thus, it's important that this doesn't change
   */
  private final int bit;

  /**
   * Creates an authority with a specific bit representation.
   *
   * @param bit the permission bit which will represent this authority in the datastore.
   */
  UserAuthority(int bit) {
    this.bit = bit;
  }

  public int getBit() {
    return bit;
  }

  @Override
  public String getAuthority() {
    return "ROLE_" + toString();
  }
}
