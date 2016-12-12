package ro.aniov.web.rpg.dto.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ro.aniov.web.rpg.service.AccountService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Marius on 12/10/2016.
 */

/**
 * Create a Validator to check if the email String field provided is unique
 */
public class EmailIsUniqueValidator implements ConstraintValidator<EmailIsUnique, String>{

    @Autowired
    AccountService accountService;

    @Override
    public void initialize(EmailIsUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (accountService.findAccountByEmail(email) != null)
            return false;

        return true;
    }
}
