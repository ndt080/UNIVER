package application.console;

import configuration.ConnectionPool;
import exceptions.ConnectionPoolException;
import exceptions.DAOException;
import models.entities.*;
import models.daos.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Console application class
 */
public class ConsoleApplication {
    /**
     * Logger object
     */
    private static final Logger logger = LogManager.getLogger(ConsoleApplication.class);
    static AdminDAO adminDAO;
    static ClientDAO clientDAO;
    static HorseDAO horseDAO;
    static RaceDAO raceDAO;
    static BetDAO betDAO;

    /**
     * Initialize DAOs instances
     */
    private static void initDAOs() {
        adminDAO = new AdminDAO();
        clientDAO = new ClientDAO();
        horseDAO = new HorseDAO();
        raceDAO = new RaceDAO();
        betDAO = new BetDAO();
    }

    /**
     * Create database tables if not exists
     * @throws DAOException
     */
    private static void createTables() throws DAOException {
//        adminDAO.createTable();
//        clientDAO.createTable();
//        horseDAO.createTable();
//        raceDAO.createTable();
//        betDAO.createTable();
    }

    /**
     * Create new client
     * @throws DAOException
     */
    private static void registerNewClient() throws DAOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter client last name: ");
        String clientLastName = scanner.nextLine();

        System.out.println("Enter client name: ");
        String clientName = scanner.nextLine();

        clientDAO.insert(new Client(0, clientLastName, clientName));
    }

    /**
     * Print Races by date
     * @throws DAOException
     * @throws ParseException
     */
    private static void printRacesByDate() throws DAOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date (dd-MM-yyyy): ");
        printList(raceDAO.getByDate(formatter.parse(scanner.nextLine())));
    }

    /**
     * Print horses by race ID
     * @throws DAOException
     * @throws ParseException
     */
    private static void printHorsesByRaceId() throws DAOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter race ID: ");
        printList(raceDAO.getAll());
        printList(horseDAO.getByRaceId(Integer.parseInt(scanner.nextLine())));
    }

    /**
     * Print winning race clients
     * @throws DAOException
     * @throws ParseException
     */
    private static void printWinningRaceClients() throws DAOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter race ID: ");
        printList(raceDAO.getAll());
        printList(clientDAO.getWinnerClientsByRaceId(Integer.parseInt(scanner.nextLine())));
    }

    /**
     * Print list
     * @param list
     */
    private static void printList(Collection list) {
        for (Object row: list) {
            System.out.println(row);
        }
    }

    private static void createBet() throws DAOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID: ");
        int adminId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter client ID: ");
        List<Client> clients = clientDAO.getAll();
        printList(clients);
        int clientId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter race ID: ");
        printList(raceDAO.getAll());
        int raceId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter horse ID: ");
        printList(horseDAO.getAll());
        int horseId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter coefficient: ");
        double coefficient = Double.parseDouble(scanner.nextLine());

        betDAO.insert(new Bet(
            0,
            new Client(clientId, null, null),
            new Race(raceId, null, null, null),
            new Horse(horseId, null, null),
            amount,
            coefficient,
            new Date(),
            new Admin(adminId, null, null)
        ));
    }

    private static void showMenuOptions() {
        System.out.println("""
            1 - Register new client
            2 - Create bet
            3 - Get All races
            4 - Get races by date
            5 - Get horses by race id
            6 - Get a list of winning race clients
            0 - EXIT\s
        """) ;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            logger.info("Init DAOs...");
            initDAOs();

            logger.info("Create tables if not exists...");
            createTables();

            showMenuOptions();
            while(scanner.hasNextLine()) {
                switch (scanner.nextLine()) {
                    case "1" -> registerNewClient();
                    case "2" -> createBet();
                    case "3" -> printList(raceDAO.getAll());
                    case "4" -> printRacesByDate();
                    case "5" -> printHorsesByRaceId();
                    case "6" -> printWinningRaceClients();
                    case "0" -> {
                        ConnectionPool.closeConnections();
                        System.exit(0);
                    }
                }
                showMenuOptions();
            }

            ConnectionPool.closeConnections();
        } catch (DAOException | ConnectionPoolException | ParseException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
