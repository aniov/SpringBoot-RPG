package ro.aniov.web.rpg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Marius on 12/10/2016.
 */

/**
 * Configure Http Security
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DatabaseAuthenticationProvider databaseAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(databaseAuthenticationProvider).eraseCredentials(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        // @formatter:off
        web.ignoring()
                .antMatchers("/js/**",
                            "/css/**",
                            "/*.css",
                            "/*.png",
                            "/*.gif");
        // @formatter:on

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
           .formLogin()
                .loginPage("/login")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/")
           .and()
                .logout()
                    .logoutSuccessUrl("/login")
           .and()
                .authorizeRequests()
                    .antMatchers("/login",
                                            "/register",
                                            "/")
                    .permitAll()
           .and()
                .authorizeRequests()
                    .antMatchers("/members",
                                            "/profile",
                                            "/editname",
                                            "/logout")
                    .authenticated()
                    .antMatchers("/admin")
                        .hasRole("ADMIN")
           .anyRequest()
                .denyAll();

        // @formatter:on

    }

}
