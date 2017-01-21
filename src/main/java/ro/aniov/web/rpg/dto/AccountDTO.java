/**
 * Created by Marius on 12/9/2016.
 */
package ro.aniov.web.rpg.dto;

import org.hibernate.validator.constraints.NotBlank;
import ro.aniov.web.rpg.dto.validators.EmailIsUnique;
import ro.aniov.web.rpg.dto.validators.EmailIsValid;
import ro.aniov.web.rpg.dto.validators.MatchFields;

import javax.validation.constraints.Size;

/**
 * A DTO representing an Account.
 */

@MatchFields(first = "plainPassword", second = "repeatPlainPassword", message = "Passwords don't match")
public class AccountDTO {

    @NotBlank(message = "")
    @Size(min = 5, max=50, message = "Email must be between 5 and 50 characters long")
    @EmailIsUnique
    @EmailIsValid
    private String email;

    @NotBlank(message = "")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String plainPassword;

    @NotBlank(message = "")
    private String repeatPlainPassword;

    public AccountDTO() {
    }

    /** Testing purpose */
    public AccountDTO(String email, String plainPassword, String repeatPlainPassword) {
        this.email = email;
        this.plainPassword = plainPassword;
        this.repeatPlainPassword = repeatPlainPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getRepeatPlainPassword() {
        return repeatPlainPassword;
    }

    public void setRepeatPlainPassword(String repeatPlainPassword) {
        this.repeatPlainPassword = repeatPlainPassword;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + email + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
                ", repeatPlainPassword='" + repeatPlainPassword + '\'' +
                '}';
    }
}
