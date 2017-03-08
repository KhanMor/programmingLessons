package spring;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mordr on 08.03.2017.
 */
public class CustomExceptionResolver extends SimpleMappingExceptionResolver {
    private static final Logger logger = Logger.getLogger(CustomExceptionResolver.class);

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        logger.error(buildLogMessage(ex, request), ex);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
}
