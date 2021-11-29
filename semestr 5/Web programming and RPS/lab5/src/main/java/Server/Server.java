package Server;

import core.models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.services.MessageService;
import core.services.Utils;

/*
 * 17.	Чат. Сервер рассылает всем клиентам информацию о клиентах, вошедших в чат и покинувших его.
 */

/**
 * The main class for the server, stores a list of connections and performs message forwarding
 */
class Server {
    private static final Logger Logger = LogManager.getLogger(Server.class);

    public static void main(String[] args) {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);
        int port = Config.getInstance().server_port;

        Logger.info(String.format(MessageService.getInstance().getString("start_server"), port));
        new ServerInitThread(port).start();
    }
}