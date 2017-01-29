package ro.aniov.web.rpg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.Role;
import ro.aniov.web.rpg.service.AccountService;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Marius on 12/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountControllerTest {

    private final String email = "test@some.com";
    private final String password = "testPassword";
    private final String repeatPassword = "testPassword";
    private final String mockPassword = "mockPassword";

    @Autowired
    AccountService accountService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                                .webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();
    }

    @Test
    public void testRegisterAccountValid() throws Exception {

        mockMvc.perform(post("/register")
                .param("email", email)
                .param("plainPassword", password)
                .param("repeatPlainPassword", repeatPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Account account = accountService.findAccountByEmail(email);

        assertEquals("Account should exist: ", account.getEmail(), email);
        assertTrue("Passwords should match: ", new BCryptPasswordEncoder().matches(password, account.getPasswordHash()));
        assertTrue(account.isEnabled());
        assertTrue(account.isAccountNonExpired());
        assertTrue(account.isCredentialsNonExpired());
        assertTrue(account.isAccountNonLocked());
        assertEquals(Role.ROLE_USER, account.getRole());
        assertNotNull(account.getCreated());
    }

    @Test
    public void testRegisterAccountInvalidEmail() throws Exception {

        mockMvc.perform(post("/register")
                .param("email", "NotAValid!*@email")
                .param("plainPassword", password)
                .param("repeatPlainPassword", repeatPassword)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        Account account = accountService.findAccountByEmail("NotAValid!*@email");

        assertNull("Account should not exists",account);
    }

    @Test
    public void testRegisterAccountDuplicate() throws Exception {

        mockMvc.perform(post("/register")
                .param("email", email)
                .param("plainPassword", mockPassword)
                .param("repeatPlainPassword", mockPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        /**Create new account with same email */
        mockMvc.perform(post("/register")
                .param("email", email)
                .param("plainPassword", "newAccountpass")
                .param("repeatPlainPassword", "newAccountpass")
                .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        Account account = accountService.findAccountByEmail(email);

        assertTrue("Mock user should have same password 'mockPassword:'",
                new BCryptPasswordEncoder().matches(mockPassword, account.getPasswordHash()));

    }
}
