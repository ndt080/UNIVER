package Client;

import core.models.config.Config;
import core.services.Utils;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Class represents nio client
 */
public class Client {
    /**
     * Logger
     */
    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(Client.class);
    /**
     * SocketChannel instance
     */
    SocketChannel client;

    /**
     * Client constructor
     * @throws IOException exception
     */
    public Client() throws IOException {
        client = SocketChannel.open(new InetSocketAddress(
                Config.getInstance().client_address,
                Config.getInstance().client_port
        ));
        new ClientListenThread(this).start();
        new ClientSendThread(this).start();
    }

    public static void main(String[] args) throws IOException {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);
        new Client();
    }

    /**
     * Get SocketChannel
     * @return SocketChannel
     */
    public SocketChannel getChannel() {
        return client;
    }
}