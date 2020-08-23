package Module.GenerateTree;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<String> {
    public BufferedReader br;
    public List<String> list;

    public ConsoleInputReadTask(BufferedReader br, List<String> list) {
        this.br = br;
        this.list = list;

    }

    public String call() throws IOException {
        System.out.println("ConsoleInputReadTask run() called.");
        String input;
        System.out.println("Please type something: ");
        // wait until we have data to complete a readLine()
        while ((input = br.readLine()) != null) {
            System.out.println("AAAAAAAAAAAAAAA");
            System.out.println(input);
            list.add(input);
        }
        return null;
    }
}