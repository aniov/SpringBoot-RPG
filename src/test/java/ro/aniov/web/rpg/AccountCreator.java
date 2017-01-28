package ro.aniov.web.rpg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.aniov.web.rpg.dto.AccountDTO;
import ro.aniov.web.rpg.model.Account;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.model.characters.hero.HeroType;
import ro.aniov.web.rpg.service.AccountService;
import ro.aniov.web.rpg.service.HeroService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Marius on 1/19/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountCreator {

    private AccountDTO accountDTO;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HeroService heroService;

    private final int size = 8;

    private final static String base_address= "@mockAddress.com";

    //@Ignore
    @Test
    public void createAccountsTest(){

        for (int i = 0; i < 260; i++){

            String email = generateRandomString().append(base_address).toString();
            String password = email;

            if (accountService.findAccountByEmail(email) == null){
                    accountDTO =  new AccountDTO(email, password, password);
                    accountService.registerAccount(accountDTO);
            }
        }
    }
    //@Ignore
    @Test
    public void createHeroes(){

        /** Find our mock test accounts*/
        List<Account> accounts = accountService.findAccountByEmailContaining(base_address);
        List<User> users = new ArrayList<>();
        for (Account account : accounts){
            System.out.println("\n***************Size of Accounts List:" + accounts.size());
            System.out.println("\n" + account.getUser());
                users.add(account.getUser());
        }
        /** Add random heroes for our test users*/
        for (User user : users){
            setRandomHeroes(user);
            assertNotNull(user.getHeroes());
        }
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

    private void setRandomHeroes(User user){

        Random random = new Random();
        int nrOfHeroes = random.nextInt(4) + 1;
        while (nrOfHeroes > 0){

            HeroType heroType = HeroType.values()[random.nextInt(HeroType.values().length)];
            Hero hero = new Hero(generateRandomString().toString(), heroType, user);
            heroService.saveHero(hero);
            nrOfHeroes--;
        }
    }
}
