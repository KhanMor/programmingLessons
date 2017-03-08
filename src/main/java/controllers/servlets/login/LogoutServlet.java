package controllers.servlets.login;

import controllers.servlets.SuperServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 * Выход пользователя из системы
 */
//@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class);
    private static final String AUTH_ATTRIBUTE_NAME = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        logger.trace("user " + session.getAttribute(AUTH_ATTRIBUTE_NAME) + " logged out.");
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
