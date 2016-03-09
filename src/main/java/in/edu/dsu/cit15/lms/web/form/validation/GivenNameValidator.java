package in.edu.dsu.cit15.lms.web.form.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by abhishekmunie on 24/01/16.
 */
public class GivenNameValidator implements ConstraintValidator<GivenName, String> {
  private static final Pattern VALID = Pattern.compile("[\\p{L}'\\-,.]+");

  public void initialize(GivenName constraintAnnotation) {
  }

  public boolean isValid(String value, ConstraintValidatorContext context) {
    return VALID.matcher(value).matches();
  }

}
