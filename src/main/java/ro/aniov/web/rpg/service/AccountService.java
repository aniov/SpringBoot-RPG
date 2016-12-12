package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.dto.AccountDTO;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.repository.AccountRepository;

/**
 * Created by Marius on 12/9/2016.
 */
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account registerAccount(AccountDTO accountDTO){

        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPasswordHash(new BCryptPasswordEncoder().encode(accountDTO.getPlainPassword()));
        accountRepository.save(account);

        return account;
    }

    public Account findAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }
}
