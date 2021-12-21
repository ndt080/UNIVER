package Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientListenThread extends Thread{
    /**
     * Client instance
     */
    private final Client client;

    /**
     * ClientListenThread constructor
     * @param client Client instance
     */
    public ClientListenThread(Client client) {
        this.client = client;
    }

    /**
     * Run ClientListenThread
     */
    @Override
    public void run() {
        listen(client.getChannel());
    }

    /**
     * Listen SocketChannel
     */
    private static void listen(SocketChannel socketChannel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                StringBuilder stringBuilder = new StringBuilder();
                buffer.clear();
                int read = 0;

                read = socketChannel.read(buffer);
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                stringBuilder.append(new String(bytes));

                if (stringBuilder.toString().length() > 0) {
                    System.out.print(stringBuilder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
