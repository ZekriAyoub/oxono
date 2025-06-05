package g63551.dev3.oxono.model.command;

import java.util.Stack;

/**
 * Manages the execution, undo, and redo of commands.
 * It maintains two stacks: one for undoing and one for redoing actions.
 */
public class CommandManager {
    private final Stack<Command> undoStack; // Stack to store commands that can be undone
    private final Stack<Command> redoStack;  // Stack to store commands that can be redone

    /**
     * Constructs a CommandManager with empty undo and redo stacks.
     */
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Executes the given command and adds it to the undo stack.
     * Clears the redo stack as the history is altered.
     *
     * @param command the command to execute
     */
    public void doIt(Command command) {
        command.execute();
        undoStack.add(command);
        redoStack.clear();
    }

    /**
     * Undoes the last executed command, if any, and moves it to the redo stack.
     *
     * @throws IllegalStateException if there are no commands to undo
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            throw new IllegalStateException("undo stack is empty !");
        } else {
            Command c = undoStack.pop();
            c.unexecute();
            redoStack.push(c);
        }
    }

    /**
     * Redoes the last undone command, if any, and moves it to the undo stack.
     *
     * @throws IllegalStateException if there are no commands to redo
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            throw new IllegalStateException("redo stack is empty !");
        } else {
            Command c = redoStack.pop();
            c.execute();
            undoStack.push(c);
        }
    }

    /**
     * Checks if there are commands available to undo.
     *
     * @return true if there are commands to undo, false otherwise
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Checks if there are commands available to redo.
     *
     * @return true if there are commands to redo, false otherwise
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
