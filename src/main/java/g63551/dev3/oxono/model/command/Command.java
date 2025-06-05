package g63551.dev3.oxono.model.command;

/**
 * Represents a command that can be executed and unexecuted.
 * This interface defines the methods for performing and reversing an action.
 */
public interface Command {

    /**
     * Executes the command.
     */
    void execute();

    /**
     * Reverses the execution of the command.
     */
    void unexecute();
}
