package g63551.dev3.oxono.console;

import g63551.dev3.oxono.model.Color;
import g63551.dev3.oxono.model.Game;
import g63551.dev3.oxono.model.Symbol;

import java.util.Scanner;

public class Console {

    private final ConsoleView consoleView;
    private Game game;

    public Console(ConsoleView view) {
        this.consoleView = view;
    }

    /**
     * Starts the console game loop, asking the user for input to play the game.
     * It initializes the game, manages user input, updates the game state, and displays the game status.
     */
    public void startConsole() {
        Scanner scanner = new Scanner(System.in);

        int size = getBoardSizeFromUser(scanner);
        initializeGame(size);

        while (!game.isEnd()) {
            String enter = getUserMove(scanner);
            if (enter.equals("ABANDON")) break;
            processMove(enter);
            consoleView.display(game);
            displayInformation();

            // Bot's turn to make a move
            game.autoPlay();
            consoleView.display(game);
        }
    }

    private int getBoardSizeFromUser(Scanner scanner) {
        System.out.println("What size would you like for the game board?");
        int size = scanner.nextInt();
        scanner.nextLine();
        return size;
    }

    private void initializeGame(int size) {
        this.game = new Game(size, 1);  // Initializes the game with a given size and player level
        consoleView.display(game);
        displayInformation();
    }

    private String getUserMove(Scanner scanner) {
        System.out.println("Which token would you like to move (CROSS OR CIRCLE)? And where would you like to place it? (ABANDON to quit)");
        return scanner.nextLine();
    }

    private void processMove(String enter) {
        if (enter.equals("ABANDON")) return;

        ConsoleController.checkCommand(enter, game, consoleView);

        System.out.println("At which position would you like to place your token?");
        String positionToken = new Scanner(System.in).nextLine();
        ConsoleController.checkCommand(positionToken, game, consoleView);
    }

    /**
     * Displays the current game information, including the number of empty cells,
     * the current player, and the number of remaining tokens for each player.
     */
    public void displayInformation() {
        System.out.println("The number of empty cells is " + game.countEmpty());
        System.out.println("The current player is " + game.displayCurrentPlayer());
        System.out.println("Number of remaining X tokens: " + game.getNbTokens(Color.BLACK, Symbol.CROSS) + " for player BLACK");
        System.out.println("Number of remaining O tokens: " + game.getNbTokens(Color.BLACK, Symbol.CIRCLE) + " for player BLACK");
        System.out.println("Number of remaining X tokens: " + game.getNbTokens(Color.PINK, Symbol.CROSS) + " for player PINK");
        System.out.println("Number of remaining O tokens: " + game.getNbTokens(Color.PINK, Symbol.CIRCLE) + " for player PINK");
    }
}
