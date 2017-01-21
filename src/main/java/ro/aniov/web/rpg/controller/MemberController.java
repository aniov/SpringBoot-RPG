package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
