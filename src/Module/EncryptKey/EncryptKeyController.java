//package Module.EncryptKey;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//
//@Path("/encrypt-key")
//public class EncryptKeyController {
//    @Inject
//    private EncryptKeyService encryptKeyService;
//
//    public EncryptKeyController() {
//    }
//
//    @GET
//    @Path("test")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CryptoEntity test(@QueryParam("file") String file) {
//        return encryptKeyService.test(file);
//    }
//
//    @POST
//    @Path("encrypt")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CryptoEntity encrypt(CryptoEntity cryptoEntity) {
//        return encryptKeyService.encrypt(cryptoEntity);
//    }
//
//    @POST
//    @Path("sign")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CryptoEntity sign(CryptoEntity cryptoEntity) {
//        return encryptKeyService.sign(cryptoEntity);
//    }
//
//    @POST
//    @Path("encrypt-all")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CryptoEntity[] encryptAll(CryptoEntity[] cryptoEntities) {
//        return encryptKeyService.encryptAll(cryptoEntities);
//    }
//
//    @POST
//    @Path("decrypt")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public CryptoEntity decrypt(CryptoEntity cryptoEntity) {
//        return encryptKeyService.decrypt(cryptoEntity);
//    }
//
//    @POST
//    @Path("verify")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public boolean verify(CryptoEntity cryptoEntity) {
//        return encryptKeyService.verify(cryptoEntity);
//    }
//}