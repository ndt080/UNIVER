package controller.commands;

import exceptions.DAOException;
import models.daos.AuthDAO;
import models.daos.HorseDAO;
import models.daos.RaceDAO;
import models.entities.Auth;
import models.entities.Horse;
import models.entities.Race;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HorsesCommand extends Command {
    static final Logger logger = LogManager.getLogger(HorsesCommand.class);
    final String urlPattern = "horses";

    @Override
    public String getPattern() {
        return urlPattern;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {
        logger.info("HorsesCommand doGet run...");
        String errorString = null;
        List<Horse> horses = null;

        try {
            String query = request.getParameter("raceId");
            if(query != null) {
                horses = (new HorseDAO()).getByRaceId(Integer.parseInt(query));
            } else {
                horses = (new HorseDAO()).getAll();
            }
        } catch (DAOException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("horsesList", horses);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/view/horses.jsp");
        dispatcher.forward(request, response);
        logger.info("HorsesCommand doGet send response...");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response,
                       ServletContext servletContext
    ) throws ServletException, IOException {
        if (request.getParameter("command").equals("horses")) {
            String horseId = request.getParameter("horseId");
            String horseName = request.getParameter("horseName");
            List<Horse> horses = null;

            String errorString = "";

            try {
                HorseDAO horseDAO = new HorseDAO();

                if (horseId == null || horseName == null) {
                    errorString = "Invalid data";

                    logger.info(errorString);
                    request.setAttribute("errorString", errorString);

                    request.getRequestDispatcher("/view/horses.jsp").forward(request, response);
                    return;
                }

                (new HorseDAO()).updateName(horseName, Integer.parseInt(horseId));

                horses = (new HorseDAO()).getAll();

                request.setAttribute("errorString", errorString);
                request.setAttribute("horsesList", horses);
                request.getRequestDispatcher("/view/horses.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
