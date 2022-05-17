package controller.command;

import exception.DAOException;
import model.dao.ClientDAO;

import model.entities.Client;
import model.entities.Order;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllClientOrders extends Command {

    final String urlPattern = "getAllOrders";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        String errorString = null;

        List<Order> list = null;
        List<Client> listC = null;
        try {
            listC = new ClientDAO().getAllClients();
            int id = listC.get(listC.size() - 1).getId();
            list = (new ClientDAO()).getAllClientOrders(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("ordersList", list);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/getClientsOrdersPage.jsp");
        dispatcher.forward(request, response);
    }
}
