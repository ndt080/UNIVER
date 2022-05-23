package controller.commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AboutCommand extends Command {
    final String urlPattern = "about";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

        logger.info("AboutCommand doGet run...");
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/about.jsp");
        dispatcher.forward(request, response);
        logger.info("AboutCommand doGet send response...");
    }
}
