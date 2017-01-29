package ro.aniov.web.rpg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.aniov.web.rpg.dto.validators.NameIsValid;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.service.HeroService;
import ro.aniov.web.rpg.service.UserService;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Marius on 12/13/2016.
 */
@Controller
@Validated
public class MemberController {

    @Autowired
    UserService userService;

    @Autowired
    HeroService heroService;

    @GetMapping(value = "/member")
    public String getMember(Model model) {

        User user = userService.getUserFromContext();
        List<Hero> heroes = heroService.getHeroesByUserId(user.getId());
        model.addAttribute("heroes", heroes);
        return "member";
    }

    @GetMapping(value = "/search")
    public String searchUsers(@RequestParam(name = "userName") String userName,
                              @RequestParam(name = "p", defaultValue = "1") int pageNr,
                              @RequestParam(name = "r", defaultValue = "10") int results_p_page, Model model){

        Page<User> users = userService.findUserByName(userName, pageNr, results_p_page);

        model.addAttribute("users", users);
        model.addAttribute("userName", userName);
        model.addAttribute("pageNr", pageNr);
        model.addAttribute("results_p_page", results_p_page);

        return "search_results";
    }

    @GetMapping(value = "/profile")
    public String profile(Model model){

        User user = userService.getUserFromContext();
        model.addAttribute("user", user);

        return "profile";
    }

    @PutMapping(value = "/profile/edit")
    @ResponseBody
    public ResponseEntity editProfile(@RequestParam(name = "id") Long id,
                                                    @Size @NameIsValid @RequestParam(name = "firstName", defaultValue = "Unset") String firstName,
                                                    @Size @NameIsValid @RequestParam(name = "lastName", defaultValue = "Unset") String lastName,
                                                    @RequestParam(name = "sex", defaultValue = "UNKNOWN") String sex){

        try {
            userService.editProfile(id, firstName, lastName, sex);
        } catch (AccessDeniedException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (AuthenticationCredentialsNotFoundException e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
