package controller.endpoints;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    @OnMessage
    public void onMessage(Session session, String msg) {
        String[] messageInfo = msg.split(":=:");

        session.getUserProperties().put("name", messageInfo[0]);

        for (Session sessionReceiver : session.getOpenSessions()) {
            if (sessionReceiver.isOpen()) {
                if (sessionReceiver.getUserProperties().get("name") != messageInfo[0]) {
                    try {
                        sessionReceiver.getBasicRemote().sendText(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}