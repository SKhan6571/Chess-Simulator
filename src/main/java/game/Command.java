package game;

// Commands can be executed or undone
public interface Command {
    void execute();

    void undo();
}
