package controllers.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 * Фильтр по сессиям
 */
@Deprecated
//@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);
    private static final String AUTH_ATTRIBUTE_NAME = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);
        Object user = null;
        if(session != null) {
            user = session.getAttribute(AUTH_ATTRIBUTE_NAME);
        }
        if(
            (session == null || user == null)
            && !uri.endsWith("login")
            && !uri.contains("registration")
            && !uri.contains("/css/")
            && !uri.contains("error")
            && !uri.contains("/webjars/")
            && !uri.contains("/js/")
        ){
            logger.trace("denied access request " + uri);
            res.sendRedirect(req.getContextPath() + "/login");
        }else{
            logger.trace("allowed access request " + uri);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.trace("filter destroyed");
    }
}
