package Server;

import core.services.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A class is a thread that waits for a connection from a new client and then creates a Server instance
 * for each connection.ServerConnectionThread
 */
class ServerInitThread extends Thread {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(ServerInitThread.class);
    /**
     * List of ServerConnectionThreads
     */
    public ArrayList<ServerConnectionThread> clients = new ArrayList<ServerConnectionThread>();
    /**
     * Is running server
     */
    public boolean alive = true;
    /**
     * Server port
     */
    public int port;

    /**
     * Constructor ServerInitThread
     *
     * @param port server port
     */
    ServerInitThread(int port) {
        this.port = port;
    }

    /**
     * Run ServerInitThread
     */
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (this.alive) {
                Socket socket = serverSocket.accept();
                ServerConnectionThread sst = new ServerConnectionThread(socket, this);
                this.clients.add(sst);
                this.onClientChange(sst);
                sst.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the client connects or disconnects
     *
     * @param clientThread client
     */
    public void onClientChange(ServerConnectionThread clientThread) {
        String messageKey = clientThread.connected ? "client_connected" : "client_disconnected";
        String message = String.format(
                MessageService.getInstance().getString(messageKey),
                clientThread.socket.getInetAddress().toString().replace("/", ""),
                clientThread.socket.getPort());
        Logger.info(message);

        String activeClients = MessageService.getInstance().getString("client_list") + "\n";
        for (int i = 0; i < clients.size(); i++) {
            activeClients += "\t" + i + ". " + clients.get(i) + "\r\n";
        }

        for (ServerConnectionThread c : clients) {
            try {
                PrintStream ps = new PrintStream(c.socket.getOutputStream());
                c.sendMessage(message);
                c.sendMessage(activeClients);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Logger.info(activeClients);
    }

    /**
     * Send message to client
     *
     * @param clientThread client
     * @param message      message text
     */
    public void onClientMessage(ServerConnectionThread clientThread, String message) {
        for (ServerConnectionThread client : clients) {
            if (client.socket != clientThread.socket) {
                client.sendMessage(message);
            }
        }
    }
}