package in.edu.dsu.cit15.lms.web.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by abhishekmunie on 24/01/16.
 */
public class SignupForm {

  //  @GivenName
  @NotBlank
  private String givenName;

  //  @FamilyName
  @NotBlank
  private String familyName;

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
}
