package filters;

import models.entities.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User session filter
 */
public class UserSessionFilter implements Filter {
    static final Logger logger = LogManager.getLogger(UserSessionFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();

        Auth user = (Auth) session.getAttribute("user");
        String command = httpServletRequest.getParameter("command");

        if (user == null && !(command == null ||
                                command.equals("home") ||
                                command.equals("authorization") ||
                                command.equals("registration") ||
                                command.equals("about")
                             )
        ) {
            logger.info("UserSession");
            httpServletResponse.sendRedirect("/?command=home");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
