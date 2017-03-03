package controllers.servlets.registration;

import controllers.servlets.SuperServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 *
 */
@WebServlet(urlPatterns = "/registrationSuccess")
public class RegistrationSuccess extends SuperServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.success.jsp").forward(req, resp);
    }
}
