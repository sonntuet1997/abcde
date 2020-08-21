package Module.Log;

import Module.GenerateTree.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LogService extends Thread {
    public static HashMap<LogEntity, LogService> LogServices = new HashMap<>();
    public ProcessBuilder builder;
    public LogEntity logEntity;
    public Scanner scan;
    public Process process;

    public LogService(LogEntity logEntity, ProcessBuilder builder) {
        LogServices.put(logEntity, this);
        this.builder = builder;
        this.logEntity = logEntity;
    }

    public static boolean checkLog(LogEntity logEntity) {
        return LogServices.containsKey(logEntity);
    }

    public void close(Message message) throws IOException, EncodeException {
        List<ProcessHandle> de = process.descendants().collect(Collectors.toList());
        for (ProcessHandle ob : de) {
            System.out.println(ob.destroy());
        }
        process.destroy();
        scan.close();
        LogEndpoint.close(logEntity, message);
    }

    @Override
    public void run() {
        try {
            process = builder.start();
            logEntity.status = "Processing";
            LogEndpoint.updateLogEntity(logEntity);
            LogEndpoint.fileSub.keySet().forEach(logEntity1 -> {
                System.out.println(logEntity1.url);
                System.out.println(logEntity1.status);
            });
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
                LogEndpoint.broadcast(logEntity, new Message("Processing", next));
            }
            //TODO: checkcode
            Message message = new Message();
            message.status = "Success";
            LogEndpoint.broadcast(logEntity, message);
            logEntity.status = "Success";
            System.out.println("Suceessed: " + logEntity.url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
