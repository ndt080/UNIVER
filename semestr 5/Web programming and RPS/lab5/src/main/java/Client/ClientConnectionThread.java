package Client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.services.MessageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Client thread.
 */
class ClientConnectionThread extends Thread {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(ClientConnectionThread.class);

    /**
     * Client
     */
    Client client;

    /**
     * Socket
     */
    Socket socket;

    /**
     * ClientConnectionThread constructor
     * @param client client
     */
    ClientConnectionThread(Client client) {
        this.client = client;
    }

    /**
     * Running ClientConnectionThread
     */
    @Override
    public void run() {
        try {
            Logger.info(String.format(
                    MessageService.getInstance().getString("connecting_to_server"),
                    this.client.server_address,
                    this.client.server_port)
            );

            socket = new Socket(this.client.server_address, this.client.server_port);
            BufferedReader dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (this.client.alive) {
                String message = dis.readLine();
                if (message == null) return;

                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error(e);
        }
    }
}