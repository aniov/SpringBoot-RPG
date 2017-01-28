package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setEnableAccount(Long id) throws PermissionDeniedDataAccessException{
        Account account = accountRepository.findById(id);
        accountRepository.setEnabled(id, !account.isEnabled());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setExipredAccount(Long id) throws PermissionDeniedDataAccessException{
        Account account = accountRepository.findById(id);
        accountRepository.setExpired(id, !account.isAccountNonExpired());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setLockAccount(Long id) throws PermissionDeniedDataAccessException{
        Account account = accountRepository.findById(id);
        accountRepository.setLock(id, !account.isAccountNonLocked());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccount(Long id) throws PermissionDeniedDataAccessException{
        Account account = accountRepository.findById(id);
        User user = userService.getUserFromContext();
        if (account.getId() == user.getAccount().getId()){
            throw new DataIntegrityViolationException("Not allowed delete your own account");
        }
        accountRepository.delete(account);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void roleChange(Long id, String role) throws PermissionDeniedDataAccessException{

        Role roleType = Role.valueOf(role);
        Account account = accountRepository.findById(id);
        if (roleType != null && account != null) {
            accountRepository.changeRole(id, roleType);
        }
    }
}
