package Module.Log;

import Module.GenerateTree.GenerateTreeEntity;
import Module.GenerateTree.GenerateTreeService;
import Module.GenerateTree.Message;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Set;

@Path("/log")
public class LogController {
    @Inject
    private GenerateTreeService generateTreeService;

    public LogController() {
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public GenerateTreeEntity e() {
        GenerateTreeEntity generateTreeEntity = new GenerateTreeEntity();
        generateTreeEntity.geneticCode = "sadasd";
        return generateTreeEntity;
    }


    @GET
    @Path("get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<LogEntity> getAll() {
        return LogService.LogServices.keySet();
    }

    @GET
    @Path("kill/{file}")
//    @Produces(MediaType.APPLICATION_JSON)
    public String kill(@PathParam("file") String file) {
        Message message = new Message();
        message.status = "Canceled";
        try {
            LogEntity logEntity = new LogEntity();
            logEntity.url = file;
            LogService.LogServices.get(logEntity).close(message);
            LogService.LogServices.remove(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}