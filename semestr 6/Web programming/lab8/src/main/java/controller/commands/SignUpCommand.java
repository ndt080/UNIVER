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
public class SignUpCommand extends Command {
    static final Logger logger = LogManager.getLogger(SignUpCommand.class);
    final String urlPattern = "registration";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      ServletContext servletContext
    ) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/signUp.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response,
                       ServletContext servletContext
    ) throws ServletException, IOException {
        if (request.getParameter("command").equals("registration")) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            String errorString = "";

            try {
                AuthDAO daoUser = new AuthDAO();

                if (daoUser.authHandler(login)) {
                    errorString = "User with this login already exists.";

                    logger.info(errorString);
                    request.setAttribute("errorString", errorString);

                    request.getRequestDispatcher("/view/signUp.jsp").forward(request, response);
                } else {
                    Auth user = new Auth(login, password, "user");
                    daoUser.signUp(user);

                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);

                    request.setAttribute("errorString", errorString);
                    session.setAttribute("error", errorString);
                    logger.info(errorString);

                    request.getRequestDispatcher("/view/index.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
