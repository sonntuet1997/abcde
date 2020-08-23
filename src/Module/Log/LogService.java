package Module.Log;

import Module.GenerateTree.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
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
            logEntity.logs = "";
            LogEndpoint.updateLogEntity(logEntity);
            LogEndpoint.fileSub.keySet().forEach(logEntity1 -> {
                System.out.println(logEntity1.url);
                System.out.println(logEntity1.status);
            });
            scan = new Scanner(process.getInputStream());
            List<String> t = new ArrayList<>();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    if (t.size() > 0) {
                        String content = String.join("\n", t);
                        logEntity.logs += content;
                        LogEndpoint.broadcast(logEntity, new Message("Processing", content));
                        t.clear();

                    }
                }
            };
            timer.schedule(task, 1000, 300);
            while (scan.hasNextLine()) {
                String next = scan.nextLine();
                t.add(next);
                System.out.println(next);
            }
            timer.cancel();
            //TODO: checkcode
            Message message = new Message();
            if(process.waitFor() == 0){
                message.status = "Success";
                LogEndpoint.broadcast(logEntity, message);
                logEntity.status = "Success";
                System.out.println("Suceessed: " + logEntity.url);
            } else {
                message.status = "Error";
                LogEndpoint.broadcast(logEntity, message);
                logEntity.status = "Error";
                System.out.println("Error: " + logEntity.url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
