package controllers.mvc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * Created by Mordr on 07.03.2017.
 */
@Controller
public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class);
    private static final String USER_NAME_ATTRIBUTE_NAME = "user_name";
    private static final String GREETING_ATTRIBUTE_NAME = "greeting_message";

    @RequestMapping(value = {"/","/index","/home"}, method = RequestMethod.GET)
    public String getHomePage(@SessionAttribute(USER_NAME_ATTRIBUTE_NAME) String userName, Model model) {
        model.addAttribute(GREETING_ATTRIBUTE_NAME, userName);
        return "index";
    }
}
