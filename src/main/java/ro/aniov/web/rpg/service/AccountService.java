package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.dto.AccountDTO;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.Role;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.repository.AccountRepository;
import ro.aniov.web.rpg.repository.UserRepository;

import java.util.List;

/**
 * Created by Marius on 12/9/2016.
 */
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public void registerAccount(AccountDTO accountDTO){

        Account account = new Account();

        account.setEmail(accountDTO.getEmail());
        account.setPasswordHash(new BCryptPasswordEncoder().encode(accountDTO.getPlainPassword()));
        account.setRole(Role.ROLE_USER);

        User user = new User();
        account.setUser(user);
        accountRepository.save(account);

    }

    /** @PreAuthorize("isAuthenticated()") */
    public Account findAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public List<Account> findAccountByEmailContaining(String email_part) {
        return accountRepository.findByEmailContaining(email_part);
    }
}
