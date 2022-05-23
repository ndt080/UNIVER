package controller.commands;

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
public class ChatCommand extends Command {
    static final Logger logger = LogManager.getLogger(ChatCommand.class);
    final String urlPattern = "chat";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      ServletContext servletContext
    ) throws ServletException, IOException {
        logger.info("Open chat...");
        Auth user = (Auth) request.getSession().getAttribute("user");
        request.setAttribute("senderId", user.getLogin());

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/chat.jsp");
        dispatcher.forward(request, response);
        logger.info("Success open chat");
    }
}
