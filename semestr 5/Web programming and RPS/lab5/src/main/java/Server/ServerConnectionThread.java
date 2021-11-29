package Server;

import core.services.MessageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Server thread instance for each client connection
 */
class ServerConnectionThread extends Thread {
    /**
     * Is connected client
     */
    boolean connected = true;

    /**
     * Socket instance
     */
    Socket socket;

    /**
     * Head server thread
     */
    ServerInitThread server;

    /**
     * ServerConnectionThread constructor
     *
     * @param socket socket
     * @param server head server thread
     */
    ServerConnectionThread(Socket socket, ServerInitThread server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Run ServerConnectionThread
     */
    @Override
    public void run() {
        while (this.server.alive) {
            try {
                BufferedReader dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = dis.readLine();
                if (msg == null) {
                    server.clients.remove(this);
                    this.connected = false;
                    this.server.onClientChange(this);
                    return;
                } else {
                    server.onClientMessage(this, msg);
                }
            } catch (IOException e) {
                server.clients.remove(this);
                this.connected = false;
                this.server.onClientChange(this);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                MessageService.getInstance().getString("server_connection_thread"),
                this.socket.getInetAddress().toString().replace("/", ""),
                this.socket.getPort(),
                this.connected);
    }

    /**
     * Send message to client
     *
     * @param message message text
     */
    public void sendMessage(String message) {
        try {
            PrintStream ps = new PrintStream(this.socket.getOutputStream());
            ps.println(message);
            ps.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}