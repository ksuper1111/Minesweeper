import java.awt.*;
import java.util.Random;

/**
 * Represents the Minesweeper grid, which composed of Square objects.
 * Handles creation, logic for uncovering and flagging, and drawing of the grid.
 */
public class Grid {
    private Square[][] grid;
    private int width, height, numMines;
    private int topMargin, leftMargin;
    private int squareSize;
    private int numFlaggedSquares;
    private boolean mineUncovered;

    /**
     * Constructs the game grid with given parameters.
     * 
     * @param height      number of rows
     * @param width       number of columns
     * @param numMines    number of mines to place
     * @param topMargin   top pixel margin for rendering
     * @param leftMargin  left pixel margin for rendering
     */
    public Grid(int height, int width, int numMines, int topMargin, int leftMargin) {
        this.height = height;
        this.width = width;
        this.numMines = numMines;
        this.topMargin = topMargin;
        this.leftMargin = leftMargin;
        this.squareSize = 30;
        this.numFlaggedSquares = 0;
        this.mineUncovered = false;
        this.grid = new Square[height][width];
        createGrid();
    }

    /** @return The number of flagged squares on the grid. */
    public int getNumFlaggedSquares() {
        return numFlaggedSquares;
    }

    /** @return The total number of mines on the grid. */
    public int getNumMines() {
        return numMines;
    }

    /** @return Whether a mine has been uncovered (game over condition). */
    public boolean isMineUncovered() {
        return mineUncovered;
    }

    /**
     * Generates a new grid with mines and number squares.
     */
    public void createGrid() {
        Random rand = new Random();
        int minesPlaced = 0;

        // Place mines randomly
        while (minesPlaced < numMines) {
            int row = rand.nextInt(height);
            int col = rand.nextInt(width);
            if (grid[row][col] == null) {
                grid[row][col] = new MineSquare(row, col);
                minesPlaced++;
            }
        }

        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == null) {
                    int neighbors = countAdjacentMines(row, col);
                    NumberSquare ns = new NumberSquare(neighbors, row, col);
                    ns.setNumberColor(getColorForNumber(neighbors));
                    grid[row][col] = ns;
                }
            }
        }
    }

    /**
     * Counts adjacent mines around a square.
     *
     * @param r The row index.
     * @param c The column index.
     * @return The number of adjacent mines.
     */
    private int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nr = r + i;
                int nc = c + j;
                if (nr >= 0 && nr < height && nc >= 0 && nc < width && grid[nr][nc] instanceof MineSquare) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Gets the color to use for a number square based on the mine count.
     *
     * @param n The number of adjacent mines.
     * @return A color representing the number.
     */
    private Color getColorForNumber(int n) {
        switch (n) {
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.RED;
            case 4: return Color.MAGENTA;
            case 5: return Color.ORANGE;
            case 6: return Color.CYAN;
            case 7: return Color.PINK;
            case 8: return Color.GRAY;
            default: return Color.BLACK;
        }
    }

    /**
     * Flags or unflags a square at the given position.
     *
     * @param r Row index
     * @param c Column index
     */
    public void flag(int r, int c) {
        Square s = grid[r][c];
        if (!s.isUncovered()) {
            s.toggleFlag();
            if (s.isFlagged()) {
                numFlaggedSquares++;
            } else {
                numFlaggedSquares--;
            }
        }
    }

    /**
     * Uncovers a square. If it's a mine, the game ends. If it's empty (0), recursively uncovers neighbors.
     *
     * @param r Row index
     * @param c Column index
     * @return The number of adjacent mines, or -1 if it was a mine.
     */
    public int uncoverSquare(int r, int c) {
        Square s = grid[r][c];
        s.uncover();

        if (s.isMine()) {
            mineUncovered = true;
            // Reveal entire board
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    grid[i][j].uncover();
                }
            }
            return -1;
        }

        // Recursively uncover adjacent empty squares
        if (s.getNeighbors() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nr = r + i;
                    int nc = c + j;
                    if (nr >= 0 && nr < height && nc >= 0 && nc < width) {
                        Square neighbor = grid[nr][nc];
                        if (!neighbor.isUncovered() && !neighbor.isFlagged()) {
                            uncoverSquare(nr, nc);
                        }
                    }
                }
            }
        }

        return s.getNeighbors();
    }

    /**
     * Handles a user's move (click or flag) based on mouse coordinates and action type.
     *
     * @param mouseX  Mouse x-coordinate
     * @param mouseY  Mouse y-coordinate
     * @param action  "uncover" or "flag"
     * @param started Whether the game has already started
     * @return True if the move is valid; false if the game has not started due to mine placement
     */
    public boolean userMove(int mouseX, int mouseY, String action, boolean started) {
        int col = (mouseX - leftMargin) / squareSize;
        int row = (mouseY - topMargin) / squareSize;

        if (col >= 0 && col < width && row >= 0 && row < height) {
            Square s = grid[row][col];

            if (action.equals("uncover")) {
                if (!started) {
                    // First click: ensure the first square is not a mine
                    while (s instanceof MineSquare) {
                        grid = new Square[height][width];
                        createGrid();
                        s = grid[row][col];
                    }
                }

                if (!s.isFlagged() && !s.isUncovered()) {
                    int result = uncoverSquare(row, col);
                    if (result == -1) {
                        mineUncovered = true;
                    }
                }
            } else if (action.equals("flag")) {
                flag(row, col);
            }

            return true;
        }

        return started;
    }

    /**
     * Checks if the player has won the game (all non-mine squares uncovered and all mines flagged).
     *
     * @return True if the player has won; false otherwise.
     */
    public boolean hasWon() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Square s = grid[row][col];
                if (!s.isMine() && !s.isUncovered()) return false;
                if (s.isMine() && !s.isFlagged()) return false;
            }
        }
        return true;
    }

    /**
     * Draws the grid of squares on the screen.
     *
     * @param g2 The graphics context to draw with.
     */
    public void draw(Graphics2D g2) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] != null) {
                    grid[row][col].draw(g2, squareSize, leftMargin, topMargin);
                }
            }
        }
    }
}
