package input;

import org.xml.sax.InputSource;

import java.util.Scanner;

public class ConsoleInputSource implements Runnable {
    private final InputListener listener;
    private volatile boolean running = true;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleInputSource(InputListener listener) {
        this.listener = listener;
    }

    public void start(){
        new Thread(this).start();
    }

    @Override
    public void run(){
        while(running) {
            if (!scanner.hasNextLine()) continue;
            String input = scanner.nextLine();
            listener.onInput(new ConsoleLineEvent(input));
        }
    }

    public void stop(){
        running = false;
    }
}
