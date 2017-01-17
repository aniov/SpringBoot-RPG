/**
 * Created by Marius on 12/19/2016.
 */
package ro.aniov.web.rpg.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A name Validator interface
 */


@Target(value = ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NameIsValidValidator.class)
@Documented
public @interface NameIsValid {

    String message() default "Please enter a valid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
