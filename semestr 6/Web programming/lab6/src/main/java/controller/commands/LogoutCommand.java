package controller.commands;

import models.daos.AuthDAO;
import models.entities.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Authorization command
 */
public class LogoutCommand extends Command {
    static final Logger logger = LogManager.getLogger(LogoutCommand.class);
    final String urlPattern = "logout";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      ServletContext servletContext
    ) throws ServletException, IOException {
        logger.info("Logout...");
        HttpSession session = request.getSession(true);
        session.setAttribute("user",null);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/index.jsp");
        dispatcher.forward(request, response);
        logger.info("Success logout");
    }
}
