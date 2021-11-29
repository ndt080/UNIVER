package Server;

import core.models.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.services.MessageService;
import core.services.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 17.	Чат. Сервер рассылает всем клиентам информацию о клиентах, вошедших в чат и покинувших его.
 */

/**
 * The main class for the server, stores a list of connections and performs message forwarding
 */
public class Server {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(Server.class);
    /**
     * ServerSocketChannel
     */
    private final ServerSocketChannel serverSocketChannel;
    /**
     * Selector
     */
    private final Selector selector;

    public Server(int port) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));
        this.serverSocketChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        new ServerThread(this, selector).start();
    }

    public static void main(String[] args) throws IOException {
        Config.load("src/main/resources/config.json");
        Utils.initLocale(Logger);
        int port = Config.getInstance().server_port;

        Logger.info(String.format(MessageService.getInstance().getString("start_server"), port));
        new Server(9080);
    }

    /**
     * Get ServerSocketChannel
     *
     * @return ServerSocketChannel
     */
    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    /**
     * Get Selector
     *
     * @return Selector
     */
    public Selector getSelector() {
        return selector;
    }
}