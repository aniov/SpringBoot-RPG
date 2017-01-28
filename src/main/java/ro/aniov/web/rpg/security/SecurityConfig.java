package ro.aniov.web.rpg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

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
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(databaseAuthenticationProvider).eraseCredentials(true);
    }
    /** Set up SessionRegistry used to access logged accounts*/
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        // @formatter:off
        web.ignoring()
                .antMatchers("/js/*",
                                        "/css/*",
                                        "/png/*",
                                        "/pics/*",
                                        "/app/*",
                                        "/fonts/*"
                            );
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
           .formLogin()
                .loginPage("/login")
                    .failureUrl("/login?error=true")//.defaultSuccessUrl("/member")
                    .successHandler(myAuthenticationSuccessHandler)
           /** We use Custom 'logout'*/
           /*.and()
                .logout()
                    .logoutSuccessUrl("/login")*/
           .and()
                .authorizeRequests()
                    .antMatchers("/login",
                                            "/register",
                                            "/")
                    .permitAll()
           .and()
                .authorizeRequests()
                    .antMatchers("/member",
                                            "/search",
                                            "/profile",
                                            "/profile/edit",
                                            "/delete_hero/*",
                                            "/edit_heroname",
                                            "/game_play",
                                            "/editname",
                                            "/new_hero",
                                            "/ini_game",
                                            "/logout")
                    .authenticated()
                    .antMatchers("/admin",
                                            "/disable_account/*",
                                            "/expire_account/*",
                                            "/lock_account/*",
                                            "/delete_account/*",
                                            "/change_account_role")
                        .hasRole("ADMIN")
           .anyRequest()
                .denyAll()
                /** Configure so we have 1 session used in SessionRegistry*/
           .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .sessionRegistry(sessionRegistry());

        // @formatter:on
    }

}
