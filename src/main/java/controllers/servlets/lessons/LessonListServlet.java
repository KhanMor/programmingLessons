package controllers.servlets.lessons;

import controllers.servlets.SuperServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mordr on 03.03.2017.
 */
@Deprecated
public class LessonListServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(LessonListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
