package controller.commands;

import exceptions.DAOException;
import models.daos.RaceDAO;
import models.entities.Race;
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
import java.util.*;

public class RacesCommand extends Command {
    static final Logger logger = LogManager.getLogger(RacesCommand.class);
    final String urlPattern = "races";

    private static void printList(Collection list) {
        for (Object row: list) {
            System.out.println(row);
        }
    }

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        logger.info("RacesCommand doGet run...");
        String errorString = null;
        List<Race> races = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String queryDate = request.getParameter("date");

            if(queryDate != null) {
                Date date = formatter.parse(queryDate);
                races = (new RaceDAO()).getByDate(date);
            } else {
                races = (new RaceDAO()).getAll();
            }
        } catch (ParseException | DAOException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("raceList", races);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/races.jsp");
        dispatcher.forward(request, response);
        logger.info("RacesCommand doGet send response...");
    }
}
