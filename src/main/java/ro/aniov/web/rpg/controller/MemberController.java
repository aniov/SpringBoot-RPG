package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.service.HeroService;
import ro.aniov.web.rpg.service.UserService;

import java.util.List;

/**
 * Created by Marius on 12/13/2016.
 */
@Controller
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
                              @RequestParam(name = "r", defaultValue = "10") int results_p_page, Model model, Authentication authentication){

        Page<User> users = userService.findUserByName(userName, pageNr, results_p_page);

        model.addAttribute("users", users);
        model.addAttribute("userName", userName);
        model.addAttribute("pageNr", pageNr);
        model.addAttribute("results_p_page", results_p_page);

        return "search_results";
    }

}
