package Client;

import core.models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.services.Utils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Client class
 */
class Client {
    private static final Logger Logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);

        new Client( Config.getInstance().server_address, Config.getInstance().server_port);
    }

    /**
     * Is running client
     */
    public boolean alive = true;

    /**
     * Server address
     */
    public String server_address;

    /**
     * Server port
     */
    public int server_port;

    /**
     * Client constructor
     * @param server_address server address
     * @param server_port server port
     */
    Client(String server_address, int server_port) {
        this.server_address = server_address;
        this.server_port = server_port;
        ClientConnectionThread cct = new ClientConnectionThread(this);
        cct.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String cmd = scanner.nextLine();
            try {
                PrintStream ps = new PrintStream(cct.socket.getOutputStream());
                ps.println(cmd);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}