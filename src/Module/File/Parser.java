package Module.File;

import org.apache.commons.codec.CharEncoding;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class Parser {
    public static void main(String[] args) throws NullPointerException, URISyntaxException, UnsupportedEncodingException {
        System.out.println("URI      : " + UriUtils.encodeQuery("Thư mục mới asds dasdasd sad", CharEncoding.UTF_8));
    }
//    public static void main(String args[]) {
//        Parser x = new Parser();
//        File file = new File("Files");
//        x.addJavaFiles(file);
//    }

    private void addJavaFiles(final File files) {
        for (final File fileEntry : files.listFiles()) {
            if (fileEntry.isDirectory()) {
                addJavaFiles(fileEntry);
            } else{
                System.out.println(fileEntry.getPath());
//              fileEntry.getPath()
            }
        }
    }
}
