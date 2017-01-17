package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.aniov.web.rpg.model.User;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.service.HeroService;
import ro.aniov.web.rpg.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Marius on 12/13/2016.
 */
@Controller
public class MemberController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    UserService userService;

    @Autowired
    HeroService heroService;

    @GetMapping(value = "/member")
    public String getMember(Model model) {

        /*GameMap gameMap;


        if ((gameMap = (GameMap) httpSession.getAttribute("gameMap")) != null) {
            User user = userService.getUserFromContext();
            Long heroId = gameMap.getHeroId();
            GameMap.hId.remove(heroId);
            GameMap.gameMapsInstances.remove(gameMap.getClass());


            httpSession.removeAttribute("gameMap");
            httpSession.removeAttribute("heroPlayDTO");
            httpSession.removeAttribute("heroIdDTO");
            //httpSession.invalidate();
        }*/

        User user = userService.getUserFromContext();
        List<Hero> heroes = heroService.getHeroesByUserId(user.getId());
        model.addAttribute("heroes", heroes);
        return "member";
    }

}
