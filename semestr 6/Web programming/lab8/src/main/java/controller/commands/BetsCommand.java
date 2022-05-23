package controller.commands;

import exceptions.DAOException;
import models.daos.BetDAO;
import models.entities.Bet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BetsCommand extends Command {
    static final Logger logger = LogManager.getLogger(BetsCommand.class);
    final String urlPattern = "bets";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

        logger.info("BetsCommand doGet start...");
        String errorString = null;
        List<Bet> bets = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String queryDate = request.getParameter("date");
            String clientId = request.getParameter("client");

            if(queryDate != null) {
                Date date = formatter.parse(queryDate);
                bets = (new BetDAO()).getByClientAndDate(clientId, date);
            } else {
                bets = (new BetDAO()).getAll();
            }

        } catch (ParseException | DAOException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("betsList", bets);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/bets.jsp");
        dispatcher.forward(request, response);
        logger.info("BetsCommand doGet send response...");
    }
}
