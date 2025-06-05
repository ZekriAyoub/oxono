package g63551.dev3.oxono.console;

import g63551.dev3.oxono.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleController {

    private final Console console;

    /**
     * Initializes a new ConsoleController with the provided Console.
     *
     * @param console The Console object that handles console interactions.
     */
    public ConsoleController(Console console) {
        this.console = console;
    }

    /**
     * Starts the console interface by invoking the startConsole() method of the Console.
     */
    public void start() {
        console.startConsole();
    }

    /**
     * Processes a given command string and performs the corresponding action on the game.
     * It supports commands for inserting tokens, moving pieces, undoing, and redoing actions.
     *
     * @param enter The input command string entered by the user.
     * @param game The Game object that contains the game state and logic.
     * @param view The ConsoleView object used to display the game state on the console.
     */
    public static void checkCommand(String enter, Game game, ConsoleView view) {
        // Handle different command types
        if (tryInsertToken(enter, game)) return;
        if (tryMovePiece(enter, game, Symbol.CIRCLE)) return;
        if (tryMovePiece(enter, game, Symbol.CROSS)) return;
        if (enter.equals("undo")) {
            game.undo();
        } else if (enter.equals("redo")) {
            game.redo();
        } else {
            System.out.println("Commande non reconnue ! Veuillez réessayer !");
        }
    }

    /**
     * Tries to handle an insert token command, which specifies coordinates.
     *
     * @param enter The input command string entered by the user.
     * @param game The Game object where the action is performed.
     * @return true if the command was recognized and executed, false otherwise.
     */
    private static boolean tryInsertToken(String enter, Game game) {
        Pattern addTokenPattern = Pattern.compile("(\\d+) (\\d+)$");
        Matcher matcher = addTokenPattern.matcher(enter);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            game.insert(x, y);
            return true;
        }
        return false;
    }

    /**
     * Tries to handle a move piece command, which moves either a circle or cross.
     *
     * @param enter The input command string entered by the user.
     * @param game The Game object where the action is performed.
     * @param symbol The symbol (CIRCLE or CROSS) being moved.
     * @return true if the command was recognized and executed, false otherwise.
     */
    private static boolean tryMovePiece(String enter, Game game, Symbol symbol) {
        Pattern movePattern = Pattern.compile("^" + symbol.toString() + " (\\d+) (\\d+)$");
        Matcher matcher = movePattern.matcher(enter);
        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            game.move(x, y, symbol);
            return true;
        }
        return false;
    }
}
