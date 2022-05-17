package controller.command;



import model.dao.ClientDAO;
import model.entities.Dish;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowMenuCommand extends Command {

    final String urlPattern = "showMenu";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        String errorString = null;

        List<Dish> list = null;
        try {
            list = (new ClientDAO()).showMenu();
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("menuList", list);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/showMenuPage.jsp");
        dispatcher.forward(request, response);
    }
}
