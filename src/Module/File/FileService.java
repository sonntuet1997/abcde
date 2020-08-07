package Module.File;

import Module.EncryptKey.EncryptKeyService;

/**
 * Created by Son on 6/15/2017.
 */
public class FileService {
    public String restUrl;
    public String filePath;
    public EncryptKeyService encryptKeyService;

    public FileService() {
        this.restUrl = System.getenv("REST_URL");
        this.restUrl = this.restUrl == null ? "http://localhost:3000" : this.restUrl;
        this.filePath = System.getenv("FILE_PATH");
        this.filePath = this.filePath == null ? "files" : this.filePath;
        encryptKeyService = new EncryptKeyService();
    }
//
//    public EncryptFileEntity upload(FileEntity fileEntity,
//                                    FormDataBodyPart content,
//                                    FormDataContentDisposition contentDisposition,
//                                    final InputStream input) throws Exception {
////        for (final File fileEntry : files.listFiles()) {
////            if (fileEntry.isDirectory()) {
////                addJavaFiles(fileEntry);
////            } else{
////                System.out.println(fileEntry.getPath());
////              fileEntry.getPath()
////            }
////        }
//        CryptoEntity cryptoEntity = new CryptoEntity();
//        cryptoEntity.certificate = fileEntity.certificate;
//        cryptoEntity.sign = fileEntity.sign;
//        cryptoEntity.data = fileEntity.message;
//        if (!this.encryptKeyService.verify(cryptoEntity)) throw new CustomException(400,"error 2");
//        ObjectMapper mapper = new ObjectMapper();
//        FileEntity readValue = mapper.readValue(cryptoEntity.data, FileEntity.class);
//        String[] s = fileEntity.src.split("/");
//        s[s.length - 1] = s[s.length - 1] + fileEntity.hash;
//        String[] path = readValue.src.split("/");
//        for (int i = 0; i < path.length; i++){
//            path[i] = UriUtils.encodePath(path[i], StandardCharsets.UTF_8.name());
//        }
//        File file = new File(this.filePath + String.join("/",path) + readValue.hash);
//        if (file.exists()) throw new CustomException(400,"error 5");
//        List<Identity> identities;
//        try {
//            identities = mapper.readValue(new URL(this.restUrl + "/api/system/identities"), new TypeReference<List<Identity>>() {
//            });
//        } catch (ConnectException  e){
//            throw new CustomException(400,"error  40");
//        }
//        Identity identity = identities.parallelStream().filter(x -> x.certificate.equals(fileEntity.certificate) && x.state.equals("ACTIVATED")).findFirst().orElse(null);
//        if (identity == null) throw new CustomException(400,"error 16");
//              String urlFile = String.join("%2F",path);
//        FileEncrypted blockchainInfo = mapper.readValue(new URL(this.restUrl + "/api/FileEncrypted/" + urlFile), new TypeReference<FileEncrypted>() {
//        });
//        if (blockchainInfo == null) throw new CustomException(400,"error 3");
//        if (blockchainInfo.checksum.equals(readValue.hash) || Arrays.asList(blockchainInfo.propose_list).parallelStream().anyMatch(c -> c.proposing_file.checksum.equals(readValue.hash))) {
//            file.getParentFile().mkdirs();
//            FileUtils.copyInputStreamToFile(input, file);
//        } else {
//            throw new CustomException(400,"error 7");
//        }
//        //TODO: Check version
//        //TODO: Check type
//        return new EncryptFileEntity();
//    }
//
//    public void download(FileEntity fileEntity, OutputStream outputStream) throws Exception {
//        //TODO: Send signature = Increased Number,
//        //TODO: Check permission
//        //TODO: Get File
//        //TODO: Decrypt If needed
//        CryptoEntity cryptoEntity = new CryptoEntity();
//        cryptoEntity.certificate = fileEntity.certificate;
//        cryptoEntity.sign = fileEntity.sign;
//        cryptoEntity.data = fileEntity.message;
//        if (!this.encryptKeyService.verify(cryptoEntity)) throw new CustomException(400,"error 2");
//        ObjectMapper mapper = new ObjectMapper();
//        FileEntity readValue = mapper.readValue(cryptoEntity.data, FileEntity.class);
//        String[] path = readValue.src.split("/");
//        for (int i = 0; i < path.length; i++){
//            path[i] = UriUtils.encodePath(UriUtils.encodePath(path[i], StandardCharsets.UTF_8.name()), StandardCharsets.UTF_8.name());
//        }
//        String urlFile = String.join("%2F",path);
//        FileEncrypted blockchainInfo = mapper.readValue(new URL(this.restUrl + "/api/FileEncrypted/" + urlFile), new TypeReference<FileEncrypted>() {
//        });
//        if (blockchainInfo == null) throw new CustomException(400,"error 3");
//        if (blockchainInfo.checksum.equals(readValue.hash) || Arrays.asList(blockchainInfo.propose_list).parallelStream().anyMatch(c -> c.proposing_file.checksum.equals(readValue.hash))) {
//            List<Identity> identities = mapper.readValue(new URL(this.restUrl + "/api/system/identities"), new TypeReference<List<Identity>>() {
//            });
//            Identity identity = identities.parallelStream().filter(x -> x.certificate.equals(fileEntity.certificate) && x.state.equals("ACTIVATED")).findFirst().orElse(null);
//            if (identity == null) throw new CustomException(400,"error 16");
//            AccessInfo accessInfo = Arrays.stream(blockchainInfo.access_info_list).filter(t -> t.user.equals(identity.participant)).findFirst().orElse(null);
//            if (accessInfo == null) {
//                throw new CustomException(400,"error 17");
//            }
//            List<Crypto> cryptoList = Arrays.stream(accessInfo.crypto_list).filter(t -> t.identity.equals("resource:org.hyperledger.composer.system.Identity#" + identity.identityId)).collect(Collectors.toList());
//            boolean check = Arrays.stream(blockchainInfo.control_info.required_list)
//                    .allMatch(s -> cryptoList.parallelStream().anyMatch(k -> k.issuer.equals(s)));
//            check = check && (Arrays.stream(blockchainInfo.control_info.optional_list)
//                    .filter(s -> cryptoList.parallelStream()
//                            .anyMatch(k -> k.issuer.equals(s))).count() >= blockchainInfo.control_info.thresh_hold);
//            if (!check) throw new CustomException(400,"error 18");
//            File file = new File(this.filePath + String.join("/",path) + readValue.hash);
//            if (!file.exists()) throw new CustomException(400,"error 9");
//            JSONObject obj = new JSONObject();
//            obj.put("user",identity.participant);
//            obj.put("file", "resource:file.FileEncrypted#" + blockchainInfo.uid);
//            obj.put("action", "DOWNLOAD");
//            String json = obj.toJSONString();
//            StringEntity entity = new StringEntity(json,
//                    ContentType.APPLICATION_JSON);
//            HttpClient httpClient = HttpClientBuilder.create().build();
//            HttpPost request = new HttpPost(this.restUrl + "/api/LogRequest");
//            request.setEntity(entity);
//            HttpResponse response = httpClient.execute(request);
//            if(response.getStatusLine().getStatusCode() == 200){
//                IOUtils.copy(new FileInputStream(file), outputStream);
//            } else throw new CustomException(400,"error 19");
//        } else {
//            throw new CustomException(400,"error 7");
//        }
//    }

    public void cleanFolder() {
        //TODO: Check version
        //TODO: ......
    }
}
