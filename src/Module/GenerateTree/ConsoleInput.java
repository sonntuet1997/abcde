package Module.GenerateTree;

import java.io.BufferedReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConsoleInput {
    private final int tries;
    private final int timeout;
    private final TimeUnit unit;

    public ConsoleInput(int tries, int timeout, TimeUnit unit) {
        this.tries = tries;
        this.timeout = timeout;
        this.unit = unit;
    }

    public String readLine(BufferedReader br) throws InterruptedException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        List<String> input = new ArrayList<>();
        try {
            // start working
            for (int i = 0; i < tries; i++) {
                System.out.println(String.valueOf(i + 1) + ". loop");
                Future<String> result = ex.submit(
                        new ConsoleInputReadTask(br, input));
                try {
                    if (result.get(timeout, unit) == null && input.size() == 0) return null;
                    break;
                } catch (ExecutionException e) {
                    e.getCause().printStackTrace();
                    return null;
                } catch (TimeoutException e) {
                    System.out.println("Cancelling reading task");
                    System.out.println(input);
                    result.cancel(true);
                    System.out.println("\nThread cancelled. input is null");
                }
            }
        } finally {
            ex.shutdownNow();
        }
        return String.join("\n", input);
    }
}
