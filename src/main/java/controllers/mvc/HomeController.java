package controllers.mvc;

import models.entity.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.security.SecurityUser;

/**
 * Created by Mordr on 07.03.2017.
 * Домашний контроллер
 */
@Controller
public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class);
    private static final String GREETING_ATTRIBUTE_NAME = "greeting_message";

    @RequestMapping(value = {"/","/index","/home"}, method = RequestMethod.GET)
    public String getHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        model.addAttribute(GREETING_ATTRIBUTE_NAME, user.getFirstName());
        return "home";
    }
}
