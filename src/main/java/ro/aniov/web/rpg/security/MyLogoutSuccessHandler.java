package ro.aniov.web.rpg.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marius on 1/25/2017.
 */
/** Not used*/
@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        super.onLogoutSuccess(request, response, authentication);

        System.out.println("\nYou logged out: " + authentication.getName());
        /** Do stuff*/
        request.getSession().invalidate();
        setDefaultTargetUrl("/login");
    }
}
