package controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeCommand extends Command {
    static final Logger logger = LogManager.getLogger(HomeCommand.class);
    final String urlPattern = "home";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        logger.info("HomeCommand doGet run...");

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/index.jsp");
        dispatcher.forward(request, response);
        logger.info("HomeCommand doGet send response...");
    }
}
