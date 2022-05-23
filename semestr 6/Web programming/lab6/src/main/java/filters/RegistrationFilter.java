package filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationFilter implements Filter {
    static final Logger logger = LogManager.getLogger(RegistrationFilter.class);

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String command = httpServletRequest.getParameter("command");
        String password = servletRequest.getParameter("password");
        String confirmed_password = servletRequest.getParameter("confirm_password");

        if (httpServletRequest.getMethod().equals("POST") && command.equals("registration")) {
            if (!password.equals(confirmed_password)) {
                String errorString = "Passwords do not match";

                logger.info(errorString);
                httpServletResponse.sendRedirect("/?command=registration");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}