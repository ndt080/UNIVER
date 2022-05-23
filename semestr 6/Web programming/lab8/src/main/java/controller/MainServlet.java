package controller;

import controller.commands.*;
import org.hibernate.annotations.NotFound;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/"}, name = "MainServlet")
public class MainServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public Map<String, Command> commands;

    public MainServlet() {
        super();
        this.commands = new HashMap<>();
    }

    @Override
    public void init() {
        Command[] commands = {
                new HomeCommand(),
                new AboutCommand(),
                new RacesCommand(),
                new BetsCommand(),
                new SigInCommand(),
                new SignUpCommand(),
                new LogoutCommand(),
                new ChatCommand(),
                new HorsesCommand()
        };

        for (Command c : commands) {
            this.commands.put(c.getPattern(), c);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");

        if (command == null) {
            commands.get("home").doGet(request, response, this.getServletContext());
        } else if (commands.containsKey(command)) {
            commands.get(command).doGet(request, response, this.getServletContext());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");

        if (command == null) {
            commands.get("home").doGet(request, response, this.getServletContext());
        } else if (commands.containsKey(command)) {
            commands.get(command).doPost(request, response, this.getServletContext());
        }
    }
}
