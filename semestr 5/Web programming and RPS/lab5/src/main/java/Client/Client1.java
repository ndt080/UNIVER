package Client;

import core.models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.services.Utils;

/**
 * Client class
 */
class Client1 {
    private static final Logger Logger = LogManager.getLogger(Client1.class);

    public static void main(String[] args) {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);

        new Client( Config.getInstance().server_address, Config.getInstance().server_port);
    }
}