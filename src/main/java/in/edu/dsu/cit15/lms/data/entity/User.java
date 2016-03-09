package in.edu.dsu.cit15.lms.data.entity;

import com.google.appengine.repackaged.com.google.common.base.MoreObjects;
import com.googlecode.objectify.annotation.*;
import in.edu.dsu.cit15.lms.jgravatar.Gravatar;
import in.edu.dsu.cit15.lms.jgravatar.GravatarDefaultImage;
import in.edu.dsu.cit15.lms.security.UserAuthority;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by abhishekmunie on 26/01/16.
 */
@Entity
@Cache
@XmlRootElement(name = "user")
public class User implements Serializable {

  private static final long serialVersionUID = -8192759137617814481L;

  @Id
  @org.springframework.data.annotation.Id
  private String id = null;
  //  @RestResource(exported = false)
  private String nickname;
  @Index
  private String email;
  private String givenName;
  private String familyName;
  @Unindex
  private Set<UserAuthority> authorities;
  @Unindex
  private boolean enabled;
  @Ignore
  private AtomicReference<String> profileImageURL = new AtomicReference<>();

  public User() {
  }

  /**
   * Constructor for Guest Users
   * <p/>
   * Assigns the user the "GUEST" authority only.
   *
   * @param id
   * @param nickname
   * @param email
   */
  public User(String id, String nickname, String email) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
    this.givenName = null;
    this.familyName = null;
    this.authorities = EnumSet.of(UserAuthority.NEW_USER);
    this.enabled = true;
  }

  /**
   * Constructor for Authenticated Users
   *
   * @param id
   * @param nickname
   * @param email
   * @param givenName
   * @param familyName
   * @param authorities
   * @param enabled
   */
  public User(String id, String nickname, String email, String givenName, String familyName, Set<UserAuthority> authorities, boolean enabled) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
    this.givenName = givenName;
    this.familyName = familyName;
    this.authorities = authorities;
    this.enabled = enabled;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public Set<UserAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<UserAuthority> authorities) {
    this.authorities = authorities;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getProfileImageURL() {
    String value = profileImageURL.get();
    if (value == null) {
      synchronized (profileImageURL) {
        profileImageURL.compareAndSet(null, generateProfileImageURL());
      }
    }
    return profileImageURL.get();
  }

  public void setProfileImageURL(AtomicReference<String> profileImageURL) {
    this.profileImageURL = profileImageURL;
  }

  public String generateProfileImageURL() {
    String profileImageURL = null;
    if (email != null && !email.isEmpty()) {
      Gravatar gravatar = new Gravatar();
      gravatar.setSize(32);
      gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
      profileImageURL = gravatar.getUrl(this.email);
      //    profileImageURL = Singleton.getCloudinary().url().type("gravatar")
      //      .format("jpg")
      //      .transformation(new Transformation().width(16).height(16).crop("fit"))
      //      .secure(true)
      //      .generate(this.email);
    }
    //    System.out.println("profileImageURL = [" + profileImageURL + "]");
    return profileImageURL;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("id", id)
      .add("nickname", nickname)
      .add("email", email)
      .add("givenName", givenName)
      .add("familyName", familyName)
      .add("authorities", authorities)
      .add("enabled", enabled)
      .add("profileImageURL", profileImageURL)
      .toString();
  }
}
