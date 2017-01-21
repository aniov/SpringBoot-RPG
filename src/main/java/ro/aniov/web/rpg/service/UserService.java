package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.repository.HeroRepository;
import ro.aniov.web.rpg.repository.UserRepository;

import java.util.List;


/**
 * Created by Marius on 12/14/2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    HeroRepository heroRepository;

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
}
