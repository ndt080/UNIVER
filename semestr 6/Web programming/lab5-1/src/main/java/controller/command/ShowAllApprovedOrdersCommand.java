package controller.command;


import model.dao.ClientDAO;
import model.entities.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllApprovedOrdersCommand extends Command {

    final String urlPattern = "showAllOrders";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        String errorString = null;

        List<Order> list = null;
        try {
            list = (new ClientDAO()).getAllApprovedOrders();
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("ordersList", list);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/showAllApprovedOrdersPage.jsp");
        dispatcher.forward(request, response);
    }
}
