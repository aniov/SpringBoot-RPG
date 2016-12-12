package ro.aniov.web.rpg.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.service.AccountService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius on 12/10/2016.
 */

/**
 * Check if the account name / password provided by the user are found in DB
 */

@Service
public class DatabaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountService accountService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        boolean valid = true;

        final String plainPassword = usernamePasswordAuthenticationToken.getCredentials().toString();

        // Check if the "plainPassowrd" String is null || contain only white spaces
        if (!StringUtils.hasText(plainPassword)) {
            this.logger.warn("AccountName {}: no password provided", email);
            valid = false;
        }
        Account account = accountService.findAccountByEmail(email);

        if (account == null){
            this.logger.warn("AccountName {}: account not found", email);
            valid = false;
        }else {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (!bCryptPasswordEncoder.matches(plainPassword, account.getPasswordHash())) {
                this.logger.warn("AccountName {}: bad password for account", email);
                valid = false;
            }
        }

        if (!valid){
            throw new BadCredentialsException("Invalid Account name / Password for user: " + email);
        }

        final List<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, plainPassword,
                account.isEnabled(), account.isAccountNonExpired(), account.isCredentialsNonExpired(), account.isAccountNonLocked(), authorities);

        return userDetails;
    }
}
