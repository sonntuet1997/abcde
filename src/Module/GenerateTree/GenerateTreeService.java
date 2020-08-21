package Module.GenerateTree;

import Module.Log.LogEntity;
import Module.Log.LogService;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.util.Hex;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Random;

import static Module.IqtreePathEntity.*;

/**
 * Created by Son on 6/15/2017.
 */
public class GenerateTreeService {

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
        return FileUtils.readFileToString(new File(IQTREE_RESULTFOLDER, filename + ".log"), StandardCharsets.UTF_8);
    }

    public LogEntity startGenerate(
            GenerateTreeRequestEntity generateTreeRequestEntity,
            FormDataBodyPart content,
            FormDataContentDisposition contentDisposition,
            final InputStream input) {
        try {
            String fileName;
            File file;
            LogEntity logEntity = new LogEntity();
            if (generateTreeRequestEntity.inputData.useExampleAlignmentFile) {
                file = new File(IQTREE_EXAMPLEALIGNMENT);
                fileName = file.getName();
                logEntity.processedFile = fileName;
            } else {
                MessageDigest md = MessageDigest.getInstance("MD5");
                DigestInputStream dis = new DigestInputStream(input, md);
                file = new File(contentDisposition.getFileName());
                FileUtils.copyInputStreamToFile(dis, file);
                String digest = Hex.getString(md.digest());
                fileName = digest + "-" + contentDisposition.getFileName();
                logEntity.processedFile = contentDisposition.getFileName();
                if (file.renameTo(new File(IQTREE_DATAFOLDER, fileName))) {
                    file.delete();
                }
                file = new File(IQTREE_DATAFOLDER, fileName);
            }
            logEntity.isProcessing = LogService.checkLog(logEntity);
            logEntity.url = fileName;

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
            LogService logService = new LogService(logEntity, builder);
            logService.start();
            return logEntity;
//            int exitCode = process.waitFor();
//            assert exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
