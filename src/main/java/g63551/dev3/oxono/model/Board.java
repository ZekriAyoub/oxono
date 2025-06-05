package g63551.dev3.oxono.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game board for the OXONO game. The board is a square grid containing pieces
 * such as totems and tokens. It manages the state of the game, including positions of totems,
 * token placements, and movement validation.
 */
public class Board {

    private final Piece[][] board;
    private Position posX;
    private Position posO;
    private final int size;
    private final Totem totemO;
    private final Totem totemX;
    private Symbol lastMoved;

    /**
     * Creates a new game board with the specified size.
     * Initializes the positions of the two totems randomly.
     *
     * @param size the size of the board (number of rows and columns)
     */
    public Board(int size) {
        this.size = size;
        this.board = new Piece[size][size];
        this.totemO = new Totem(Symbol.CIRCLE);
        this.totemX = new Totem(Symbol.CROSS);

        Position position1 = new Position((size / 2) - 1, (size / 2) - 1);
        Position position2 = new Position(size / 2, size / 2);

        this.posO = position1;
        this.posX = position2;
//        randomPositionsTotem(position1, position2);

        this.board[posO.getX()][posO.getY()] = totemO;
        this.board[posX.getX()][posX.getY()] = totemX;
    }

    /**
     * Randomly assigns initial positions to the totems.
     *
     * @param position1 the first possible position
     * @param position2 the second possible position
     */
    private void randomPositionsTotem(Position position1, Position position2) {
        Random random = new Random();
        if (random.nextBoolean()) {
            this.posO = position1;
            this.posX = position2;
        } else {
            this.posO = position2;
            this.posX = position1;
        }
    }

    /**
     * Gets the symbol of the last moved piece.
     *
     * @return the symbol of the last moved piece
     */
    public Symbol getLastMoved() {
        return lastMoved;
    }

    /**
     * Sets the symbol of the last moved piece.
     *
     * @param lastMoved the symbol to set
     */
    public void setLastMoved(Symbol lastMoved) {
        this.lastMoved = lastMoved;
    }

    /**
     * Checks if a given position on the board is empty.
     *
     * @param pos the position to check
     * @return true if the position is empty, false otherwise
     */
    public boolean isEmpty(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        if (!isValidPosition(x, y)) {
            return false;
        }
        return board[x][y] == null;
    }

    /**
     * Moves a totem to a new position on the board.
     *
     * @param totem the totem to move
     * @param p     the target position
     * @throws IllegalArgumentException if the move is invalid
     */
    public void move(Totem totem, Position p) {
        if (!isValidMove(totem, p)) {
            throw new IllegalArgumentException("Invalid position for moving this totem pole!");
        } else {
            Position pos = this.getPosTotem(totem.getSymbol());
            this.board[p.getX()][p.getY()] = totem;
            this.board[pos.getX()][pos.getY()] = null;
            this.setPosTotem(totem, p);
        }
    }

    /**
     * Checks if a move for a totem is valid.
     *
     * @param totem  the totem to move
     * @param target the target position
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Totem totem, Position target) {
        Position current = getPosTotem(totem.getSymbol());
        int x = current.getX();
        int y = current.getY();

        if (!isValidPosition(target.getX(), target.getY()) || !isEmpty(target)) {
            return false;
        }

        if (isVerticallyBlocked(x, y) && isHorizontallyBlocked(x, y)) {
            return true;
        } else if (areSurroundingCellsOccupied(x, y)) {
            return canJumpOverPieces(x, y, target);
        } else if (target.getX() == x || target.getY() == y) {
            return isPathClear(current, target);
        } else {
            return false;
        }
    }

    /**
     * Checks if a totem can jump over pieces to reach a target position.
     * A totem can only move in a straight line (vertically or horizontally),
     * and this method determines whether the path is valid.
     *
     * @param startX the starting X position
     * @param startY the starting Y position
     * @param target the target position
     * @return true if the totem can reach the target position by jumping over pieces,
     * false otherwise
     */
    private boolean canJumpOverPieces(int startX, int startY, Position target) {
        int targetX = target.getX();
        int targetY = target.getY();

        // Calculate movement direction
        int dx = Integer.compare(targetX, startX);
        int dy = Integer.compare(targetY, startY);

        // Movement must be either horizontal or vertical
        if (dx != 0 && dy != 0) {
            return false;
        }

        int x = startX + dx;
        int y = startY + dy;

        // Check each position along the path
        while (isValidPosition(x, y)) {
            if (isEmpty(new Position(x, y))) {
                // Target is reached and free
                return x == targetX && y == targetY;
            }
            // Continue movement
            x += dx;
            y += dy;
        }

        // Path is blocked or target is unreachable
        return false;
    }


    /**
     * Checks if all the cells surrounding a specific position are occupied.
     * The method verifies the four adjacent cells (up, down, left, right)
     * relative to the given position.
     *
     * @param x the X coordinate of the position to check
     * @param y the Y coordinate of the position to check
     * @return true if all adjacent cells are occupied or out of bounds,
     * false if at least one adjacent cell is empty
     */
    private boolean areSurroundingCellsOccupied(int x, int y) {
        // Check the cell above
        if (x - 1 >= 0 && isEmpty(new Position(x - 1, y))) {
            return false;
        }

        // Check the cell below
        if (x + 1 < this.getSize() && isEmpty(new Position(x + 1, y))) {
            return false;
        }

        // Check the cell to the left
        if (y - 1 >= 0 && isEmpty(new Position(x, y - 1))) {
            return false;
        }

        // Check the cell to the right
        if (y + 1 < this.getSize() && isEmpty(new Position(x, y + 1))) {
            return false;
        }

        // All surrounding cells are occupied or out of bounds
        return true;
    }


    /**
     * Checks if a position is vertically blocked on the game board.
     * A position is considered vertically blocked if all cells in the same column
     * above and below the given position are occupied or out of bounds.
     *
     * @param x the X coordinate (row) of the position to check
     * @param y the Y coordinate (column) of the position to check
     * @return true if all cells in the column above and below are occupied
     * or out of bounds, false otherwise
     */
    private boolean isVerticallyBlocked(int x, int y) {
        // Check cells above the current position
        for (int row = x - 1; row >= 0; row--) {
            if (isEmpty(new Position(row, y))) {
                return false;
            }
        }

        // Check cells below the current position
        for (int row = x + 1; row < this.size; row++) {
            if (isEmpty(new Position(row, y))) {
                return false;
            }
        }

        // All cells in the column are blocked
        return true;
    }


    /**
     * Checks if a position is horizontally blocked on the game board.
     * A position is considered horizontally blocked if all cells in the same row
     * to the left and right of the given position are occupied or out of bounds.
     *
     * @param x the X coordinate (row) of the position to check
     * @param y the Y coordinate (column) of the position to check
     * @return true if all cells in the row to the left and right are occupied
     * or out of bounds, false otherwise
     */
    private boolean isHorizontallyBlocked(int x, int y) {
        // Check cells to the left of the current position
        for (int col = y - 1; col >= 0; col--) {
            if (this.isEmpty(new Position(x, col))) {
                return false;
            }
        }

        // Check cells to the right of the current position
        for (int col = y + 1; col < this.size; col++) {
            if (this.isEmpty(new Position(x, col))) {
                return false;
            }
        }

        // All cells in the row are blocked
        return true;
    }


    /**
     * Checks if the path between two positions is clear on the game board.
     * The path is considered clear if all cells between the starting and target positions
     * are empty. This method supports horizontal and vertical paths only.
     *
     * @param start  the starting position
     * @param target the target position
     * @return true if the path is clear, false otherwise
     */
    private boolean isPathClear(Position start, Position target) {
        // Check for a clear horizontal path
        if (start.getX() == target.getX()) {
            int y1 = Math.min(start.getY(), target.getY());
            int y2 = Math.max(start.getY(), target.getY());
            for (int y = y1 + 1; y < y2; y++) {
                if (!isEmpty(new Position(start.getX(), y))) {
                    return false;
                }
            }
        }
        // Check for a clear vertical path
        else if (start.getY() == target.getY()) {
            int x1 = Math.min(start.getX(), target.getX());
            int x2 = Math.max(start.getX(), target.getX());
            for (int x = x1 + 1; x < x2; x++) {
                if (!isEmpty(new Position(x, start.getY()))) {
                    return false;
                }
            }
        }

        // The path is clear
        return true;
    }


    /**
     * Checks if the given position (x, y) is valid within the board's boundaries.
     * A position is valid if both x and y are non-negative and within the size of the board.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the position is within the bounds of the board, false otherwise
     */
    private boolean isValidPosition(int x, int y) {
        return (x >= 0 && x < size && y >= 0 && y < size);
    }

    /**
     * Inserts the given token at the specified position on the board.
     * If the position is not valid for insertion, an exception is thrown.
     * The position must be empty and either adjacent to the reference position or have surrounding cells occupied.
     *
     * @param token the token to be inserted
     * @param p     the position at which to insert the token
     * @throws IllegalArgumentException if the position is not valid for insertion
     */
    public void insert(Token token, Position p) {
        if (!isValidInsert(token, p)) {
            throw new IllegalArgumentException("Invalid position for insertion!");
        } else {
            this.board[p.getX()][p.getY()] = token;
        }
    }

    /**
     * Determines if the given token can be inserted at the specified position.
     * A valid insertion requires the position to be empty and either adjacent to the reference position
     * or the surrounding cells to be occupied by the reference token.
     *
     * @param token the token to be inserted
     * @param pos   the position where the token will be inserted
     * @return true if the position is valid for insertion, false otherwise
     */
    public boolean isValidInsert(Token token, Position pos) {
        if (!isEmpty(pos)) {
            return false;
        }

        Symbol symbol = token.getSymbol();
        Position referencePos = (symbol == Symbol.CIRCLE) ? this.posO : this.posX;

        if (areSurroundingCellsOccupied(referencePos.getX(), referencePos.getY())) {
            return true;
        }

        return isAdjacent(pos, referencePos);
    }

    /**
     * Checks if the given position is adjacent to the reference position.
     * Two positions are considered adjacent if they are horizontally or vertically neighboring.
     *
     * @param pos          the position to check
     * @param referencePos the reference position to compare against
     * @return true if the position is adjacent to the reference position, false otherwise
     */
    private boolean isAdjacent(Position pos, Position referencePos) {
        int x = pos.getX();
        int y = pos.getY();
        int refX = referencePos.getX();
        int refY = referencePos.getY();

        return (x == refX + 1 && y == refY) ||
                (x == refX - 1 && y == refY) ||
                (x == refX && y == refY + 1) ||
                (x == refX && y == refY - 1);
    }


    /**
     * Returns the position of the CROSS token.
     *
     * @return the position of the CROSS token
     */
    public Position getPosX() {
        return posX;
    }

    /**
     * Returns the position of the CIRCLE token.
     *
     * @return the position of the CIRCLE token
     */
    public Position getPosO() {
        return posO;
    }

    /**
     * Returns the position of the token corresponding to the given symbol.
     *
     * @param symbol the symbol of the token (CIRCLE or CROSS)
     * @return the position of the corresponding token
     */
    public Position getPosTotem(Symbol symbol) {
        return (symbol == Symbol.CIRCLE) ? this.posO : (symbol == Symbol.CROSS) ? this.posX : null;
    }

    /**
     * Returns the Totem associated with the given symbol.
     *
     * @param symbol the symbol of the Totem (CIRCLE or CROSS)
     * @return the Totem associated with the symbol
     */
    public Totem getTotem(Symbol symbol) {
        return (symbol == Symbol.CIRCLE) ? totemO : (symbol == Symbol.CROSS) ? totemX : null;
    }

    /**
     * Returns the Piece at the specified position on the board.
     *
     * @param i the row index of the position
     * @param j the column index of the position
     * @return the Piece at the given position
     * @throws IllegalArgumentException if the position is out of bounds
     */
    public Piece getPieceAtPos(int i, int j) {
        if (!isValidPosition(i, j)) {
            throw new IllegalArgumentException("out of bounds");
        }
        return board[i][j];
    }

    /**
     * Sets the position of the given Totem on the board.
     *
     * @param totem the Totem to be placed
     * @param pos   the position to set the Totem
     */
    public void setPosTotem(Totem totem, Position pos) {
        if (totem.getSymbol() == Symbol.CIRCLE) {
            this.posO = pos;
        } else if (totem.getSymbol() == Symbol.CROSS) {
            this.posX = pos;
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if there is an alignment of tokens starting from the specified position and continuing in the given direction.
     * The alignment is determined by checking up to 3 consecutive pieces in the specified direction (dx, dy).
     * An alignment is considered valid if all pieces in the sequence either share the same symbol or the same color.
     *
     * @param x  the row index of the starting position
     * @param y  the column index of the starting position
     * @param dx the change in row index per step (typically 1 or -1)
     * @param dy the change in column index per step (typically 1 or -1)
     * @return true if there is a valid alignment of tokens, false otherwise
     * @throws IllegalArgumentException if the starting position is out of bounds or not a valid token position
     */
    public boolean checkAlignment(int x, int y, int dx, int dy) {
        // Get the first piece at the specified position
        Piece first = this.getPieceAtPos(x, y);

        // If the position is out of bounds or does not contain a Token, return false
        if (first == null || !(first instanceof Token)) {
            return false;
        }

        // Get the symbol and color of the first token to compare against subsequent tokens
        Symbol firstSymbol = ((Token) first).getSymbol();
        Color firstColor = ((Token) first).getColor();

        // Flags to check if the symbols and colors are consistent across the sequence
        boolean sameSymbol = true;
        boolean sameColor = true;

        // Check the next 3 positions in the specified direction (dx, dy)
        for (int k = 1; k < 4; k++) {
            // Calculate the coordinates of the next piece in the sequence
            int nx = x + k * dx;
            int ny = y + k * dy;

            // Get the piece at the next position
            Piece next = this.getPieceAtPos(nx, ny);

            // If the next position is out of bounds or does not contain a valid token, return false
            if (next == null || !(next instanceof Token)) {
                return false;
            }

            // Cast the next piece to a Token and compare its symbol and color with the first token
            Token nextToken = (Token) next;

            if (nextToken.getSymbol() != firstSymbol) {
                sameSymbol = false;
            }
            if (nextToken.getColor() != firstColor) {
                sameColor = false;
            }

            // If neither the symbol nor the color match across the sequence, return false
            if (!sameSymbol && !sameColor) {
                return false;
            }
        }

        // Return true if either the symbol or the color is consistent across the sequence
        return sameSymbol || sameColor;
    }


    /**
     * Counts the number of empty positions on the board.
     *
     * @return the number of empty positions
     */
    public int countEmpty() {
        int count = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (isEmpty(new Position(i, j))) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * Gets all possible moves for a totem.
     *
     * @param symbol the symbol of the totem
     * @return a list of positions where the totem can move
     */
    public List<Position> getPossibleTotemMoves(Symbol symbol) {
        List<Position> possibleMoves = new ArrayList<>();

        Position currentPos = this.getPosTotem(symbol);

        for (int row = 0; row < this.getSize(); row++) {
            for (int col = 0; col < this.getSize(); col++) {
                Position targetPos = new Position(row, col);

                Totem totem = getTotem(symbol);
                if (this.isValidMove(totem, targetPos)) {
                    possibleMoves.add(targetPos);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Returns a list of empty positions on the board where a token with the given symbol and color can be inserted.
     * The method checks all positions on the board and adds those that are valid for insertion of the specified token.
     * A valid position is determined by the `isValidInsert` method.
     *
     * @param symbol the symbol of the token to be inserted (e.g., CIRCLE or CROSS)
     * @param color  the color of the token to be inserted
     * @return a list of positions where the token can be inserted
     */
    public List<Position> getEmptyPositions(Symbol symbol, Color color) {
        Token token = new Token(symbol, color);
        List<Position> emptyPositions = new ArrayList<>();

        for (int row = 0; row < this.getSize(); row++) {
            for (int col = 0; col < this.getSize(); col++) {
                Position pos = new Position(row, col);

                if (isValidInsert(token, pos)) {
                    emptyPositions.add(pos);
                }
            }
        }
        return emptyPositions;
    }

    /**
     * Removes a token from the specified position.
     *
     * @param pos the position to remove the token from
     * @throws IllegalArgumentException if the position is out of bounds
     */
    public void removeToken(Position pos) {
        if (!isValidPosition(pos.getX(), pos.getY())) {
            throw new IllegalArgumentException("Out of bounds !");
        }
        this.board[pos.getX()][pos.getY()] = null;
    }

    /**
     * Moves a totem back to a previous position.
     *
     * @param totem the totem to move back
     * @param p     the previous position
     * @throws IllegalArgumentException if the position is out of bounds
     */
    public void backMove(Totem totem, Position p) {
        if (!isValidPosition(p.getX(), p.getY())) {
            throw new IllegalArgumentException("Out of bounds !");
        }
        Position pos = this.getPosTotem(totem.getSymbol());
        this.board[p.getX()][p.getY()] = totem;
        this.board[pos.getX()][pos.getY()] = null;
        this.setPosTotem(totem, p);
    }

}