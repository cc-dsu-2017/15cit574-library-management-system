package in.edu.dsu.cit15.lms.web.form.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by abhishekmunie on 24/01/16.
 */
public class FamilyNameValidator implements ConstraintValidator<FamilyName, String> {
  private static final Pattern VALID = Pattern.compile("[\\p{L}'\\-,.]+");

  public void initialize(FamilyName constraintAnnotation) {
  }

  public boolean isValid(String value, ConstraintValidatorContext context) {
    return VALID.matcher(value).matches();
  }
}
