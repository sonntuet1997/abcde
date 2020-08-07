//package Module.GenerateTree;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static Module.EncryptFile.EncryptFileService.getRandomKey;
//
//
//public class GenerateTree {
//    public static GenerateTreeService generateTreeService = new GenerateTreeService();
//
//    public static void main(String[] args) throws IOException {
//        byte[] key = getRandomKey();
//        InputStream inputStream = Files.newInputStream(Paths.get("Untitled Diagram.png"));
//        try (FileOutputStream fos = new FileOutputStream("Untitled Diagram encryptAndHash.png")) {
//            generateTreeService.encryptAndHash(inputStream,key,fos);
//        }
//        InputStream input2Stream = Files.newInputStream(Paths.get("Untitled Diagram encryptAndHash.png"));
//        try (FileOutputStream fos = new FileOutputStream("Untitled Diagram decrypt.png")) {
//            generateTreeService.decrypt(input2Stream,key,fos);
//        }
//    }
//}