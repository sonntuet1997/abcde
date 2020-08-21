package Module.Tree;

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
        value = "/websocket/tree/{filename}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class TreeEndpoint {
    public static final HashMap<TreeEntity, Set<TreeEndpoint>> fileSub = new HashMap<>();
    private GenerateTreeService generateTreeService = new GenerateTreeService();
    private TreeEntity treeEntity;
    private Session session;

    public static void broadcast(TreeEntity treeEntity, Message message) {
        if (fileSub.containsKey(treeEntity)) {
            fileSub.get(treeEntity).forEach(endpoint -> {
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

    public static void updateLogEntity(TreeEntity treeEntity) {
        var x = fileSub.remove(treeEntity);
        x = (x == null) ? new CopyOnWriteArraySet<>() : x;
        fileSub.put(treeEntity, x);
    }

    public static void close(TreeEntity treeEntity, Message message) {
        if (fileSub.containsKey(treeEntity)) {
            fileSub.get(treeEntity).forEach(endpoint -> {
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
            fileSub.remove(treeEntity);
        }
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("filename") String filename) throws IOException, EncodeException {
        this.session = session;
        this.treeEntity = new TreeEntity();
        this.treeEntity.url = filename;
        Message message = new Message();
        if (!fileSub.containsKey(treeEntity)) {
            fileSub.put(treeEntity, new CopyOnWriteArraySet<>());
            message.status = "Success";
        } else {
            var y = fileSub.keySet().stream().filter(log -> {
                System.out.println(log.equals(treeEntity));
                return log.equals(treeEntity);
            }).findFirst().get();
            message.status = y.status;
        }
        fileSub.get(treeEntity).add(this);
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
    public void onClose(Session session) throws IOException {
//        Message message = new Message();
//        message.status = "Disconnect";
//        message.content = "Disconnected!";
//        LogEndpoint.broadcast(logEntity, message);
        Set<TreeEndpoint> treeEndpoints = fileSub.get(treeEntity);
        treeEndpoints.remove(this);
//        try {
//            session.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("ERRRRRRRRRRRRRRRRRRRRRR");
        fileSub.get(treeEntity).remove(this);
        // Do error handling here
    }
}

