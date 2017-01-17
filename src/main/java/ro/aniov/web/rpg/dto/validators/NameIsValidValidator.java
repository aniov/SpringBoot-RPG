/**
 * Created by Marius on 12/19/2016.
 */
package ro.aniov.web.rpg.dto.validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create a Validator for name String field using regex syntax
 */
public class NameIsValidValidator implements ConstraintValidator<NameIsValid, String>{

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]*$";

    @Override
    public void initialize(NameIsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
