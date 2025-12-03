package input;

import java.util.Queue;

public class InputManager implements InputListener{
    private final Queue<InputEvent> inputQueue = new java.util.LinkedList<>();

    @Override
    public void onInput(InputEvent event) {
        inputQueue.add(event);
    }

    public InputEvent getNextInput() {
        return inputQueue.poll();
    }
}
