package controllers.filter;

import controllers.LoginServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 */
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("filter init");
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        logger.trace("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);
        Object user = null;
        if(session != null) {
            user = session.getAttribute("user");
            logger.trace("session = " + session);
        }
        if(
            (session == null || user == null)
            && !uri.endsWith("login")
            && !uri.contains("registration")
            && !uri.contains("css")
            && !uri.contains("error")
        ){
            logger.trace("Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/login");
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.trace("filter destroyed");
    }
}
