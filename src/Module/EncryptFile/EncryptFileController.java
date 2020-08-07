package Module.EncryptFile;

import Module.EncryptKey.CryptoEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Path("/encrypt-file")
public class EncryptFileController {
    @Inject
    private EncryptFileService encryptFileService;

    public EncryptFileController() {
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void test(@QueryParam("file") String file) {
        encryptFileService.test(file);
    }

    @POST
    @Path("encrypt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response encrypt(@FormDataParam("data") FormDataBodyPart content,
                            @FormDataParam("data") FormDataContentDisposition contentDisposition,
                            @FormDataParam("data") final InputStream input) {
        byte[] key = EncryptFileService.getRandomKey();
        StreamingOutput streamingOutput = output -> {
            output.write(key);
            encryptFileService.encryptAndHash(input, key, output);
        };
        return Response.ok(streamingOutput).header("Content-Disposition",
                "attachment; filename=" + contentDisposition.getFileName()).build();
    }

    @POST
    @Path("decrypt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response decrypt(@FormDataParam("key") String key,
                            @FormDataParam("data") FormDataBodyPart content,
                            @FormDataParam("data") FormDataContentDisposition contentDisposition,
                            @FormDataParam("data") final InputStream input) throws IOException {
        byte[] keyByte = Base64.getDecoder().decode(key);
        if (keyByte.length != 32) return Response.serverError().build();
        StreamingOutput streamingOutput = output -> {
            encryptFileService.decrypt(input, keyByte, output);
        };
        return Response.ok(streamingOutput).header("Content-Disposition",
                "attachment; filename=" + contentDisposition.getFileName()).build();
    }
}