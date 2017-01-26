package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.repository.HeroRepository;
import ro.aniov.web.rpg.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marius on 12/14/2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private SessionRegistry sessionRegistry;

    public User getUserFromContext() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByAccountEmail(email);
    }

    public User findUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> findAll() {

        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public Page<User> findUserByName(String userName, int pageNr, int results_per_page){

        if (pageNr < 1)
            pageNr = 1;
        Pageable request = new PageRequest(pageNr - 1, results_per_page, Sort.Direction.ASC, "firstName", "lastName", "id");

        return userRepository.findByFirstNameContaining(userName, request);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setLoggedAccounts(List<User> users){

        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<String> emails =  new ArrayList<>();
        for (Object principal : principals){
            if (principal instanceof org.springframework.security.core.userdetails.User){
                emails.add(((org.springframework.security.core.userdetails.User) principal).getUsername());
            }
        }
        for (User user : users){
            if (emails.contains(user.getAccount().getEmail())) {
                user.getAccount().setLogged(true);
                System.out.println("Logged user: " + user.getId());
            }
            else{
                user.getAccount().setLogged(false);
            }

        }

    }
}
