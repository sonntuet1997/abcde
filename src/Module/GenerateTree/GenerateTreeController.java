package Module.GenerateTree;

import Module.Log.LogEntity;
import Module.Log.LogService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

@Path("/generate-tree")
public class GenerateTreeController {
    @Inject
    private GenerateTreeService generateTreeService;

    public GenerateTreeController() {
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
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public GenerateTreeEntity getAll() {
        GenerateTreeEntity generateTreeEntity = new GenerateTreeEntity();
        generateTreeEntity.geneticCode = "sadasd";
        return generateTreeEntity;
    }
//
//    @GET
//    @Path("kill/{file}")
////    @Produces(MediaType.APPLICATION_JSON)
//    public String kill(@PathParam("file") String file) {
//        Message message = new Message();
//        message.status = "Canceled";
//        try {
//            LogService.LogServices.get(file).close(message);
//            LogService.LogServices.remove(file);
//        } catch (IOException | EncodeException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    @POST
    @Path("start-generate")
//    @Produces({MediaType.APPLICATION_OCTET_STREAM})
//    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public LogEntity startGenerate(
            @FormDataParam("data") FormDataBodyPart data,
            @FormDataParam("alignment") FormDataBodyPart content,
            @FormDataParam("alignment") FormDataContentDisposition contentDisposition,
            @FormDataParam("alignment") final InputStream input) {
        data.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        GenerateTreeRequestEntity generateTreeRequestEntity = data.getValueAs(GenerateTreeRequestEntity.class);
        LogEntity result = generateTreeService.startGenerate(generateTreeRequestEntity,content, contentDisposition, input);
        return result;

//        return Response.ok(streamingOutput).header("Content-Disposition",
//                "attachment; filename=test.txt").build();
    }


    @POST
    @Path("force-generate")
//    @Produces({MediaType.APPLICATION_OCTET_STREAM})
//    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public LogEntity forceGenerate(
            @FormDataParam("data") FormDataBodyPart data,
            @FormDataParam("alignment") FormDataBodyPart content,
            @FormDataParam("alignment") FormDataContentDisposition contentDisposition,
            @FormDataParam("alignment") final InputStream input) {
        data.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        GenerateTreeRequestEntity generateTreeRequestEntity = data.getValueAs(GenerateTreeRequestEntity.class);
        LogEntity result = generateTreeService.startGenerate(generateTreeRequestEntity,content, contentDisposition, input);
        return result;

//        return Response.ok(streamingOutput).header("Content-Disposition",
//                "attachment; filename=test.txt").build();
    }
//
//    @POST
//    @Path("start-generateeee")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces({MediaType.APPLICATION_OCTET_STREAM})
//    public Response startGenerate(
//            @FormDataParam("json") FormDataBodyPart json,
//            @FormDataParam("data") FormDataBodyPart content,
//            @FormDataParam("data") FormDataContentDisposition contentDisposition,
//            @FormDataParam("data") final InputStream input) {
//        byte[] key = GenerateTreeService.getRandomKey();
//        StreamingOutput streamingOutput = output -> {
//            output.write(key);
//            generateTreeService.startGenerate(input, key, output);
//        };
//        return Response.ok(streamingOutput).header("Content-Disposition",
//                "attachment; filename=" + contentDisposition.getFileName()).build();
//    }
}