package g63551.dev3.oxono.model;

import g63551.dev3.oxono.model.command.CommandManager;
import g63551.dev3.oxono.model.command.InsertTokenCmd;
import g63551.dev3.oxono.model.command.MoveTotemCmd;
import g63551.dev3.oxono.model.observer.Observable;
import g63551.dev3.oxono.model.observer.Observer;

import java.util.ArrayList;
import java.util.List;


public class Game implements Observable {

    private final List<Observer> observers;
    private final Board board;
    private Player toPlay;
    private Symbol toInsert;
    private final Player black;
    private final Player pink;
    private GameState gameState;
    private final CommandManager cmManager;
    private boolean isEnd;

    public Game(int boardSize, int level) {
        this.observers = new ArrayList<>();
        this.board = new Board(boardSize);
        this.black = new Player(Color.BLACK);
        this.pink = new Player(Color.PINK);
        this.toPlay = pink;
        this.gameState = GameState.MOVE;
        this.cmManager = new CommandManager();
        this.isEnd = false;
        this.black.setStrategy(level);
    }

    public void move(int row, int col, Symbol symbol) {
        Position pos = new Position(row, col);
        Totem totem = board.getTotem(symbol);
        if (this.gameState == GameState.MOVE && this.toPlay.getNbTokens(totem.getSymbol()) > 0) {
            MoveTotemCmd moveTotemCmd = new MoveTotemCmd(board, totem, board.getPosTotem(totem.getSymbol()), pos, toInsert);
            cmManager.doIt(moveTotemCmd);
            this.setGameState(GameState.INSERT);
            this.setToInsert(totem.getSymbol());
            this.notifyObservers();
        }
    }

    private void won(int row, int col) {
        int size = board.getSize();

        for (int j = Math.max(0, col - 3); j <= Math.min(size - 4, col); j++) {
            if (board.checkAlignment(row, j, 0, 1)) {
                setEnd(true);
            }
        }

        for (int i = Math.max(0, row - 3); i <= Math.min(size - 4, row); i++) {
            if (board.checkAlignment(i, col, 1, 0)) {
                setEnd(true);
            }
        }
    }

    public void insert(int row, int col) {
        Token token = new Token(toInsert, this.toPlay.getColor());
        Position pos = new Position(row, col);
        if (this.gameState == GameState.INSERT) {
            InsertTokenCmd insertTokenCmd = new InsertTokenCmd(board, token, pos, toPlay);
            cmManager.doIt(insertTokenCmd);
            this.won(pos.getX(), pos.getY());
            if (!isEnd) {
                this.setToPlay();
                this.setGameState(GameState.MOVE);
            }
            this.notifyObservers();
        }
    }

    public boolean isValidMove(Totem totem, Position position) {
        return (board.isValidMove(totem, position));
    }

    public Symbol getToInsert() {
        return toInsert;
    }

    public GameState getGameState() {
        return gameState;
    }

    private void setToPlay() {
        this.toPlay = (toPlay == black) ? pink : black;
    }

    private void setToInsert(Symbol toInsert) {
        this.toInsert = toInsert;
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void undo() {
        if (this.getGameState() == GameState.MOVE && this.toPlay == pink) {
            for (int i = 0; i < 3; i++) {
                undoSettings();
            }
        } else {
            undoSettings();
        }
    }

    private void undoSettings() {
        cmManager.undo();
        if (gameState == GameState.INSERT) {
            setGameState(GameState.MOVE);
        } else if (gameState == GameState.MOVE) {
            setGameState(GameState.INSERT);
            setToInsert(board.getLastMoved());
            setToPlay();
        }
        this.notifyObservers();
    }

    public void redo() {
        if (this.getGameState() == GameState.INSERT && this.toPlay == pink) {
            for (int i = 0; i < 3; i++) {
                redoSettings();
            }
        } else {
            redoSettings();
        }
    }

    private void redoSettings() {
        cmManager.redo();
        if (gameState == GameState.MOVE) {
            setGameState(GameState.INSERT);
            setToInsert(board.getLastMoved());
        } else if (gameState == GameState.INSERT) {
            setGameState(GameState.MOVE);
            setToPlay();
        }
        this.notifyObservers();
    }

    public Piece getPieceAtPos(int row, int col) {
        return board.getPieceAtPos(row, col);
    }

    public List<Position> getPossibleTotemMoves(Symbol symbol) {
        return board.getPossibleTotemMoves(symbol);
    }

    public List<Position> getEmptyPositions(Symbol symbol, Color color) {
        return board.getEmptyPositions(symbol, color);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public boolean canUndo() {
        return cmManager.canUndo();
    }

    public boolean canRedo() {
        return cmManager.canRedo();
    }

    public boolean isDraw() {
        return black.getNbTokensO() + black.getNbTokensX() == 0 &&
                pink.getNbTokensO() + pink.getNbTokensX() == 0 || countEmpty() == 0 && !isEnd;
    }

    public boolean isEnd() {
        return isEnd;
    }

    private void setEnd(boolean end) {
        isEnd = end;
    }

    public int countEmpty() {
        return this.board.countEmpty();
    }

    public int getSize() {
        return this.board.getSize();
    }

    public void autoPlay() {
        black.play(this);
    }

    public Position getPositionTotem(Symbol symbol) {
        return board.getPosTotem(symbol);
    }

    public Color getCurrentColor() {
        return this.toPlay.getColor();
    }


    public int getNbTokens(Color color, Symbol symbol) {
        Player player = (color == Color.PINK) ? pink : black;
        return (symbol == Symbol.CIRCLE) ? player.getNbTokensO() : player.getNbTokensX();
    }

    public String displayCurrentPlayer() {
        return toPlay.toString();
    }

}
