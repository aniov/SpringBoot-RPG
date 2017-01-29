/**
 * Created by Marius on 12/16/2016.
 */
package ro.aniov.web.rpg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ro.aniov.web.rpg.dto.AccountDTO;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.Role;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.repository.AccountRepository;
import ro.aniov.web.rpg.service.AccountService;
import ro.aniov.web.rpg.service.UserService;

import java.util.Random;

/**
 * Pre Run Initial check if the DB has no ROLE_ADMIN || ROLE_USER
 * And creates one with given credentials
 */

@Component
public class SpringBootStartUp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private AccountDTO accountDTO;

    private final int size = 8;

    private final static String base_address= "@mockAddress.com";


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        createInitialAdmin();
        createInitialUser();
        /** Populate DB*/
        Long nr = accountService.findHowMany();
        if (nr < 250) {
            createMockUsers(250 - nr);
        }
    }

    private void createMockUsers(Long nrOfTestUsers) {

        for (int i = 0; i < nrOfTestUsers; i++){

            String firstName = generateRandomString().toString();
            String lastName = generateRandomString().toString();

            String email = generateRandomString().append(base_address).toString();
            String password = email;

            if (accountService.findAccountByEmail(email) == null){
                accountDTO =  new AccountDTO(email, password, password);
                accountService.registerAccount(accountDTO);
                Account account = accountRepository.findByEmail(email);
                User user = userService.findUserById(account.getId());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setSex(randomSexType());
                userService.saveUser(user);
            }
        }
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

    private StringBuilder generateRandomString(){

        StringBuilder email = new StringBuilder();
        char ch;
        Random random = new Random();
        for (int i = 0; i < size; i++){
            ch = (char)(random.nextInt(26) + 'a');
            email.append(ch);
        }
        System.out.println(email);
        return email;
    }

    private User.sexType randomSexType(){
        Random random = new Random();
        return User.sexType.values()[random.nextInt(User.sexType.values().length)];
    }
}