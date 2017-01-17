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
import ro.aniov.web.rpg.repository.AccountRepository;
import ro.aniov.web.rpg.service.UserService;

/**
 * Pre Run Initial check if the DB has no ROLE_ADMIN user
 * And creates one with given credentials
 */

@Component
public class SpringBootStartUp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        //createInitialAdmin();
       // createInitialUser();
    }

    public void createInitialAdmin() {
        if (accountRepository.findByRole(Role.ROLE_ADMIN) == null) {
            Account account = new Account();
            account.setEmail("admin@admin.com");
            account.setPasswordHash(new BCryptPasswordEncoder().encode("11111"));
            account.setRole(Role.ROLE_ADMIN);
            accountRepository.save(account);
            userService.createNewBlankUser(account);
            System.out.println("Created Admin user");
        }
            System.out.println("User: admin@admin.com, password: 11111 , ROLE_ADMIN");

    }

    /*public void createInitialUser() {
        if (accountRepository.findByRole(Role.ROLE_USER) == null) {
            Account account = new Account();
            account.setEmail("q@q.com");
            account.setPasswordHash(new BCryptPasswordEncoder().encode("11111"));
            account.setRole(Role.ROLE_USER);
            accountRepository.save(account);
            userService.createNewBlankUser(account);
            System.out.println("Created User");
        }
            System.out.println("User: q@q.com, password: 11111 , ROLE_USER");

    }*/
}