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
public class SigInCommand extends Command {
    static final Logger logger = LogManager.getLogger(SigInCommand.class);
    final String urlPattern = "authorization";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      ServletContext servletContext
    ) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/signIn.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response,
                       ServletContext servletContext
    ) throws ServletException, IOException {
        if (request.getParameter("command") == null || request.getParameter("command").equals("authorization")) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String errorString = "";

            try {
                AuthDAO authDAO = new AuthDAO();

                if (!authDAO.authHandler(login)) {
                    errorString = "Invalid login";
                    HttpSession session = request.getSession(true);

                    session.setAttribute("error", errorString);
                    request.setAttribute("errorString", errorString);
                    logger.info(errorString);

                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/signIn.jsp");
                    dispatcher.forward(request, response);
                } else {
                    Auth user = authDAO.signIn(login, password);

                    if (user != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user", user);

                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/index.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        errorString = "Invalid password";
                        logger.info(errorString);

                        request.setAttribute("errorString", errorString);

                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/signIn.jsp");
                        dispatcher.forward(request, response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
            }
        }
    }
}
