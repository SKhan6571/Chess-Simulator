package input;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputManager implements InputListener{
    private final BlockingQueue<InputEvent> inputQueue = new LinkedBlockingQueue<>();

    @Override
    public void onInput(InputEvent event) {
        if (event != null) {
            inputQueue.offer(event);
        }
    }

    public InputEvent getNextInput() throws InterruptedException{
        return inputQueue.take();
    }
}
