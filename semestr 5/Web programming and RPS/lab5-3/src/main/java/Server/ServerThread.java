package Server;

import core.services.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class ServerThread extends Thread {
    /**
     * Logger
     */
    private static final Logger Logger = LogManager.getLogger(ServerThread.class);
    /**
     * Server instance
     */
    private final Server server;
    /**
     * Message buffer
     */
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    /**
     * Selector instance
     */
    private final Selector selector;

    /**
     * ServerThread constructor
     * @param server server instance
     * @param selector selector instance
     */
    ServerThread(Server server, Selector selector) {
        this.server = server;
        this.selector = selector;
    }

    /**
     * Listen socket
     */
    private void listen() {
        try {
            Iterator<SelectionKey> iterator;
            SelectionKey key;
            while (server.getServerSocketChannel().isOpen()) {
                selector.select();
                iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        acceptHandler(key);
                    } else if (key.isReadable()) {
                        readHandler(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read handler
     * @param key SelectionKey
     * @throws IOException exception
     */
    private void readHandler(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((SocketChannel) key.channel());
        StringBuilder stringBuilder = new StringBuilder();
        String message;

        buffer.clear();
        int read = 0;
        try {
            while ((read = socketChannel.read(buffer)) > 0) {
                // for reading
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                stringBuilder.append(new String(bytes));
                buffer.clear();
            }
        } catch (Exception e) {
            key.cancel();
            read = -1;
        }

        if (read < 0) {
            message = String.format(
                    MessageService.getInstance().getString("client_disconnected"),
                    socketChannel.socket().getInetAddress().toString().replace("/", ""),
                    socketChannel.socket().getPort());
            socketChannel.close();
            Logger.info(message);
        } else {
            message = stringBuilder.toString();
        }

        sendMessages(message + "\n", key);
    }

    /**
     * Accept handler
     * @param key SelectionKey
     * @throws IOException exception
     */
    private void acceptHandler(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        String address = socketChannel.socket().getInetAddress().toString() + ":" + socketChannel.socket().getPort();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ, address);


        String message = String.format(
                MessageService.getInstance().getString("client_connected"),
                socketChannel.socket().getInetAddress().toString().replace("/", ""),
                socketChannel.socket().getPort());
        Logger.info(message);

        sendMessages(message + "\n", socketChannel.keyFor(selector));
    }

    /**
     * Send message to client
     *
     * @param message message text
     */
    private void sendMessages(String message, SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        for (SelectionKey item : selector.keys()) {
            if (item.isValid() && item.channel() instanceof SocketChannel socketChannel && !item.equals(key)) {
                socketChannel.write(buffer);
                buffer.rewind();
            }
        }
    }

    /**
     * Run ServerThread
     */
    @Override
    public void run() {
        listen();
    }
}
