/**
 * Created by Marius on 12/16/2016.
 */
package ro.aniov.web.rpg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.Role;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.repository.AccountRepository;

/**
 * Pre Run Initial check if the DB has no ROLE_ADMIN || ROLE_USER
 * And creates one with given credentials
 */

@Component
public class SpringBootStartUp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        createInitialAdmin();
        createInitialUser();
    }

    private void createInitialDataBaseUser(String email, String password, Role role){

        if (accountRepository.findByRole(role).isEmpty() && accountRepository.findByEmail(email) == null) {

            Account account = new Account();
            account.setEmail(email);
            account.setPasswordHash(new BCryptPasswordEncoder().encode(password));
            account.setRole(role);
            User user = new User();
            account.setUser(user);
            accountRepository.save(account);
            System.out.println("\n#######################\nNew account Created: " + role);
        }
        System.out.println("\n##############\nUser: " + email + " Password: " + password + " ROLE: " + role);
    }

    private void createInitialAdmin() {
        createInitialDataBaseUser("admin@admin.com", "11111", Role.ROLE_ADMIN);
    }

    private void createInitialUser() {
        createInitialDataBaseUser("q@q.com", "11111", Role.ROLE_USER);
    }
}