package Module.GenerateTree;

import javax.websocket.EncodeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LogService extends Thread {
    public static HashMap<String, LogService> LogServices = new HashMap<>();
    public ProcessBuilder builder;
    public String fileName;
    public Scanner scan;
    public Process process;

    public LogService(String fileName, ProcessBuilder builder) {
        LogServices.put(fileName, this);
        this.builder = builder;
        this.fileName = fileName;
    }

    public void close(Message message) throws IOException, EncodeException {
        List<ProcessHandle> de = process.descendants().collect(Collectors.toList());
        for (ProcessHandle ob : de) {
            System.out.println(ob.destroy());
        }
        process.destroy();
        scan.close();
        LogEndpoint.close(fileName, message);
    }

    @Override
    public void run() {
        try {
            process = builder.start();
            scan = new Scanner(process.getInputStream());
//            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while (true) {
//                line = r.readLine();
//                if (line == null) {
//                    break;
//                }
//                System.out.println(line);
//                LogEndpoint.broadcast(fileName, new Message("Processing", line));
//            }
            while (scan.hasNextLine()) {
                String next = scan.nextLine();
                System.out.println(next);
                LogEndpoint.broadcast(fileName, new Message("Processing", next));
            }
            //TODO: checkcode
            Message message = new Message();
            message.status = "Success";
            close(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
