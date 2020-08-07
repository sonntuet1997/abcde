package Module.GenerateTree;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value = "/LogEndpoint/{filename}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class LogEndpoint {
    public static final HashMap<String, Set<LogEndpoint>> fileSub = new HashMap<>();
    private GenerateTreeService generateTreeService = new GenerateTreeService();
    private String filename;
    private Session session;

    public static void broadcast(String filename, Message message) throws IOException, EncodeException {
        if (fileSub.containsKey(filename)) {
            fileSub.get(filename).forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        endpoint.session.getBasicRemote().
                                sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void close(String filename,Message message) throws IOException, EncodeException {
        if (fileSub.containsKey(filename)) {
            fileSub.get(filename).forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        endpoint.session.getBasicRemote().
                                sendObject(message);
                        endpoint.session.close();
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            });
            fileSub.remove(filename);
        }
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("filename") String filename) throws IOException, EncodeException {
        this.session = session;
        this.filename = filename;
        if (!fileSub.containsKey(filename)) fileSub.put(filename, new CopyOnWriteArraySet<>());
        fileSub.get(filename).add(this);
        Message message = new Message();
        message.status = "Processing";
        try {
            message.content = generateTreeService.getLogContent(filename);
        } catch (Exception e) {
            message.content = e.getMessage();
            e.printStackTrace();
        }
        session.getBasicRemote().sendObject(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, EncodeException {
//        message.from = users.get(session.getId());
//        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        Set<LogEndpoint> logEndpoints = fileSub.get(filename);
        logEndpoints.remove(this);
//        Message message = new Message();
//        message.from = users.get(session.getId());
//        message.content = "Disconnected!";
//        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}

