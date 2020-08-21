package Module.File;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/file")
public class FileController {
    @Inject
    private FileService fileService;

    public FileController() {

    }

    @GET
    @Path("zip/{file}")
//    @Produces(MediaType.APPLICATION_JSON)

    public Response zip(@PathParam("file") String file) {
        StreamingOutput streamingOutput = outputStream -> {
            try {
                this.fileService.zip(file, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return Response.ok(streamingOutput).header("Content-Disposition",
                "attachment; filename=" + file + ".zip").build();
    }

//
//    @POST
//    @Path("cleanFolder")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void cleanFolder() {
//        this.fileService.cleanFolder();
//        //TODO: Check version
//        //TODO: ......
//    }
//
//    @POST
//    @Path("upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public EncryptFileEntity upload(
//            @FormDataParam("json") FormDataBodyPart json,
//            @FormDataParam("data") FormDataBodyPart content,
//            @FormDataParam("data") FormDataContentDisposition contentDisposition,
//            @FormDataParam("data") final InputStream input) throws Exception {
//        //TODO: Check version
//        //TODO: Check type
//        json.setMediaType(MediaType.APPLICATION_JSON_TYPE);
//        FileEntity fileEntity = json.getValueAs(FileEntity.class);
////        this.fileService.upload(fileEntity, content, contentDisposition, input);
////    public List<KeyShareEntity> get(@BeanParam SearchFileModel searchFileModel) {
//        return new EncryptFileEntity();
////        return fileService.get(searchFileModel);
//    }
//
//    @POST
//    @Path("download")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces({MediaType.APPLICATION_OCTET_STREAM})
//    public Response download(
//            @FormDataParam("json") FormDataBodyPart json,
//            @FormDataParam("data") FormDataBodyPart content,
//                                      @FormDataParam("data") FormDataContentDisposition contentDisposition,
//                                      @FormDataParam("data") final InputStream input) {
//        json.setMediaType(MediaType.APPLICATION_JSON_TYPE);
//        FileEntity fileEntity = json.getValueAs(FileEntity.class);
//        StreamingOutput streamingOutput = outputStream -> {
//            try {
////                this.fileService.download(fileEntity,outputStream);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//        return Response.ok(streamingOutput).header("Content-Disposition",
//                "attachment; filename=1").build();
//        //TODO: Send signature = Increased Number,
//        //TODO: Check permission
//        //TODO: Get File
//        //TODO: Decrypt If needed


//    public List<KeyShareEntity> get(@BeanParam SearchFileModel searchFileModel) {
//        return fileService.get(searchFileModel);
//    }
}