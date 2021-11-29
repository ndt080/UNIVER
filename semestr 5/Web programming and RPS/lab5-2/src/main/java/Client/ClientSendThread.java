package Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientSendThread extends Thread {
    /**
     * Client instance
     */
    private final Client client;

    /**
     * ClientSendThread constructor
     * @param client Client instance
     */
    public ClientSendThread(Client client) {
        this.client = client;
    }

    /**
     * Run ClientSendThread
     */
    @Override
    public void run() {
        send(client.getChannel());
    }

    /**
     * Send message
     * @param socketChannel SocketChannel instance
     */
    private static void send(SocketChannel socketChannel) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                String in = sc.nextLine();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(in.getBytes());
                buffer.flip();
                int bytesWritten = socketChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
