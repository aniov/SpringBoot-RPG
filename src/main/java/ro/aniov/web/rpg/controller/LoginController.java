package ro.aniov.web.rpg.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Marius on 12/8/2016.
 */
@Controller
public class LoginController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping(value = "/login")
    public String letLogin(@RequestParam(value = "error", required = false) String loginError, Model model){

        if (loginError != null){
            model.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping ("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
