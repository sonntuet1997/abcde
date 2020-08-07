package Module.GenerateTree;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.util.Hex;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

/**
 * Created by Son on 6/15/2017.
 */
public class GenerateTreeService {
    Map<String, String> map = System.getenv();
    String IQTREE_HOME = map.get("IQTREE_HOME") != null ? map.get("IQTREE_HOME") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\bin";
    String IQTREE_DATAFOLDER = map.get("IQTREE_DATAFOLDER") != null ? map.get("IQTREE_DATAFOLDER") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\data";
    String IQTREE_RESULTFOLDER = map.get("IQTREE_RESULTFOLDER") != null ? map.get("IQTREE_RESULTFOLDER") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\result";
    public GenerateTreeService() {
        try {
            Files.createDirectories(Paths.get(IQTREE_DATAFOLDER));
            Files.createDirectories(Paths.get(IQTREE_RESULTFOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getRandomKey() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);
        return array;
    }

    public String getLogContent(String filename) throws IOException {
       return FileUtils.readFileToString(new File(IQTREE_RESULTFOLDER,filename + ".log"), StandardCharsets.UTF_8);
    }

    public String startGenerate(
            FormDataBodyPart content,
            FormDataContentDisposition contentDisposition,
            final InputStream input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DigestInputStream dis = new DigestInputStream(input, md);
            File file = new File(contentDisposition.getFileName());
            FileUtils.copyInputStreamToFile(dis, file);
            String digest = Hex.getString(md.digest());
            String fileName = digest + "-" + contentDisposition.getFileName();
            if (file.renameTo(new File(IQTREE_DATAFOLDER, fileName))) {
                file.delete();
            }
            file = new File(IQTREE_DATAFOLDER, fileName);
            //TODO: OS Compatibility
            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File(IQTREE_HOME));
            File result = new File(IQTREE_RESULTFOLDER, fileName);
            if (isWindows) {
                builder.command("cmd.exe", "/c", "iqtree", "-pre", result.getAbsolutePath(), "-s", file.getAbsolutePath(), "-redo");
            } else {
                builder.command("sh", "-c", "ls");
            }
            LogService logService = new LogService(fileName,builder);
            logService.start();
            return fileName;
//            int exitCode = process.waitFor();
//            assert exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
