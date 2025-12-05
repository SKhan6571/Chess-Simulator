package input;


public interface InputEvent{
}

// might have to make these public if they're used elsewhere
record ConsoleLineEvent(String line) implements InputEvent{}
record MouseClickEvent(int x, int y, boolean leftClick) implements InputEvent {}
