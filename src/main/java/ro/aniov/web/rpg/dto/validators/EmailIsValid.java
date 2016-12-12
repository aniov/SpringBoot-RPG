package ro.aniov.web.rpg.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Marius on 12/10/2016.
 */
@Target(value = ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = EmailIsValidValidator.class)
@Documented
public @interface EmailIsValid {

    String message() default "Please enter a valid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};


}
