package g63551.dev3.oxono.console;

/**
 * The MainConsole class is the entry point for the OXONO game in the console.
 * It initializes the console view, the game logic, and the controller to handle user input.
 * The application runs in the console without a graphical user interface (GUI).
 */
public class MainConsole {

    /**
     * The main method is the entry point for the OXONO console game.
     * It initializes the console view, game, and controller, and starts the game in the console.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        Console console = new Console(consoleView);
        ConsoleController consoleController = new ConsoleController(console);
        consoleController.start();
    }
}
