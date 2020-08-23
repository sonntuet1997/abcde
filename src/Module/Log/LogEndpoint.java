package Module.Log;

import Module.GenerateTree.GenerateTreeService;
import Module.GenerateTree.Message;
import Module.GenerateTree.MessageDecoder;
import Module.GenerateTree.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value = "/websocket/log/{filename}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class LogEndpoint {
    public static final HashMap<LogEntity, Set<LogEndpoint>> fileSub = new HashMap<>();
    private GenerateTreeService generateTreeService = new GenerateTreeService();
    private LogEntity logEntity;
    private Session session;

    public static void broadcast(LogEntity logEntity, Message message) {
        if (fileSub.containsKey(logEntity)) {
            fileSub.get(logEntity).forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        endpoint.session.getBasicRemote().
                                sendObject(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void updateLogEntity(LogEntity logEntity) {
        var x = fileSub.remove(logEntity);
        x = (x == null) ? new CopyOnWriteArraySet<>() : x;
        fileSub.put(logEntity, x);
    }

    public static void close(LogEntity logEntity, Message message) {
        if (fileSub.containsKey(logEntity)) {
            fileSub.get(logEntity).forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        endpoint.session.getBasicRemote().
                                sendObject(message);
//                        endpoint.session.close();
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            });
            fileSub.remove(logEntity);
        }
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("filename") String filename) throws IOException, EncodeException {
        this.session = session;
        this.logEntity = new LogEntity();
        this.logEntity.url = filename;
        Message message = new Message();
        if (!fileSub.containsKey(logEntity)) {
            fileSub.put(logEntity, new CopyOnWriteArraySet<>());
            message.status = "Success";
        } else {
            var y = fileSub.keySet().stream().filter(log -> {
                System.out.println(log.equals(logEntity));
                return log.equals(logEntity);
            }).findFirst().get();
            message.status = y.status;
            try {
                message.content = y.logs != null ? y.logs : generateTreeService.getLogContent(filename);
            } catch (Exception e) {
                message.content = e.getMessage();
                e.printStackTrace();
            }
        }
        fileSub.get(logEntity).add(this);

        session.getBasicRemote().sendObject(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, EncodeException {
//        message.from = users.get(session.getId());
//        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
//        Message message = new Message();
//        message.status = "Disconnect";
//        message.content = "Disconnected!";
//        LogEndpoint.broadcast(logEntity, message);
        Set<LogEndpoint> logEndpoints = fileSub.get(logEntity);
        logEndpoints.remove(this);
//        try {
//            session.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("ERRRRRRRRRRRRRRRRRRRRRR");
        fileSub.get(logEntity).remove(this);
        // Do error handling here
    }
}

