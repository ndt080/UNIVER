package controller.command;


import model.dao.ClientDAO;
import model.dao.OrderDAO;
import model.entities.Client;
import model.entities.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MakeOrder extends Command {

    final String urlPattern = "makeOrder";


    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/makeOrderPage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            int dish_id = Integer.parseInt(request.getParameter("dish_id"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            try{
                ClientDAO clientAdmin = new ClientDAO();
                List<Client> clients = clientAdmin.getAllClients();
                Client client =  clients.get(clients.size() - 1);
                List<Order> orders = clientAdmin.getAllOrders();
                clientAdmin.makeOrder(0, client.getId());
                int orderId = orders.get(orders.size() - 1).getId() + 1;
                clientAdmin.addDishToOrder(orderId,dish_id,amount);

                new OrderDAO().SetTotalPrice(orderId);
                clientAdmin.orderApproved(orderId);
                clientAdmin.orderPaid(orderId);
            }catch (Exception e){
                e.printStackTrace();
            }
            request.getRequestDispatcher("/view/mainPage.jsp").forward(request, response);

    }
}
