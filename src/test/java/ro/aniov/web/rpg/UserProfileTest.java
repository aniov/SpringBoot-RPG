package ro.aniov.web.rpg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.service.AccountService;
import ro.aniov.web.rpg.service.UserService;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Marius on 1/29/2017.
 */

/** Testing "/profile/edit" Controller*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserProfileTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    private MockMvc mockMvc;
    /** Already exist in DB*/
    private final String testAccount = "q@q.com";

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                            .webAppContextSetup(webApplicationContext)
                            .apply(springSecurity())
                            .build();
    }

    @Test
    @WithMockUser(username = testAccount)
    public void testModifyUserProfile() throws Exception {

        Account account = accountService.findAccountByEmail(testAccount);
        Long id = account.getId();
        String firstName = "changedFirstName";
        String lastName = "changedLastName";
        String sex = "MALE";

        mockMvc.perform(put("/profile/edit")
                .param("id", String.valueOf(id))
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("sex", sex)
                .with(csrf()))
                .andExpect(status().isOk());

        assertTrue("first Name should be: " + firstName, userService.findUserById(id).getFirstName().equals(firstName));
        assertTrue("last Name should be: " + lastName, userService.findUserById(id).getLastName().equals(lastName));
        assertTrue("sex should be: " + sex, userService.findUserById(id).getSex().getName().equals(sex));


        /** Testing wrong param input value*/
        mockMvc.perform(put("/profile/edit")
                .param("id", String.valueOf(id))
                .param("firstName", "Wrong?Name*Ch@rs")
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

}
