package Client;

import core.models.config.Config;
import core.services.Utils;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class Client3 {
    /**
     * Logger
     */
    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(Client3.class);

    public static void main(String[] args) throws IOException {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);
        new Client();
    }
}
