package in.edu.dsu.cit15.lms.web.form.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by abhishekmunie on 24/01/16.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GivenNameValidator.class)
public @interface GivenName {

  String message() default "{com.abhishekmunie.epls.web.form.validation.givenname}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
