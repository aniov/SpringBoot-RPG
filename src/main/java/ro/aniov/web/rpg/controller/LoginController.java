package ro.aniov.web.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Marius on 12/8/2016.
 */
@Controller
public class LoginController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping(value = "/login")
    public String letLogin(@RequestParam(value = "error", required = false) String loginError, Model model, HttpServletRequest request){

        if (loginError != null){
            model.addAttribute("error", getErrorMessage(request));
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){

            request.getSession().invalidate();
            List<SessionInformation> userSessions = sessionRegistry.getAllSessions(auth, true);

            for (SessionInformation session: userSessions) {
                sessionRegistry.removeSessionInformation(session.getSessionId());
            }

            //new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    /** Customize error message from login page*/
    private String getErrorMessage(HttpServletRequest request){
        String errorMessage = "";

        Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        if (exception instanceof BadCredentialsException){
            errorMessage = "Wrong email or password";
        } else if (exception instanceof LockedException){/***/
            errorMessage = "Account is locked";
        } else if (exception instanceof DisabledException){ /***/
            errorMessage = "Account is disabled";
        } else if (exception instanceof AccountExpiredException){/***/
            errorMessage = "Account is expired";
        } else if (exception instanceof CredentialsExpiredException){
            errorMessage = "Credentials have expired";
        }
        return errorMessage;
    }
}
