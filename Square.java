import java.awt.*;

/**
 * An Abstract class representing a square in Minesweeper.
 * The squares may be uncovered, flagged, or covered, and each square has a position (row, column) and various colors 
 * that are associated with weather its covered, uncovered, or flagged.
 */
public abstract class Square {
    protected int row, col; 
    protected boolean uncovered, flagged; 
    private Color backgroundColor, coveredColor, borderColor, flagColor; 

    /**
     * Constructs a Square object with a specified position (row, col) in the grid.
     * as well as initializes the square to be covered and not flagged. 
     * Also sets default colors.
     *
     * @param row The row position of the square in the grid.
     * @param col The column position of the square in the grid.
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.uncovered = false;
        this.flagged = false;

        
        this.backgroundColor = Color.LIGHT_GRAY;
        this.coveredColor = Color.GREEN;
        this.borderColor = Color.WHITE;
        this.flagColor = Color.RED;
    }

    

    /**
     * This gets the row position of the square in the grid.
     *
     * @return The row position.
     */
    public int getRow() { return row; }

    /**
     * This gets the column position of the square in the grid.
     *
     * @return The column position.
     */
    public int getCol() { return col; }

    /**
     * this is used to return whether the square is uncovered.
     *
     * @return true if the square is uncovered, false if it is covered.
     */
    public boolean isUncovered() { return uncovered; }

    /**
     * this returns whether the square is flagged.
     *
     * @return true if the square is flagged, false otherwise.
     */
    public boolean isFlagged() { return flagged; }

    /**
     * Gets the background color of the square this is used when uncovered.
     *
     * @return The background color.
     */
    public Color getBackgroundColor() { return backgroundColor; }

    /**
     * This gets the color of the square when covered.
     *
     * @return The color of the covered square.
     */
    public Color getCoveredColor() { return coveredColor; }

    /**
     * this gets the border color of the square.
     *
     * @return The border color.
     */
    public Color getBorderColor() { return borderColor; }

    /**
     * this gets the color used to represent the flag on the square.
     *
     * @return The flag color.
     */
    public Color getFlagColor() { return flagColor; }

    /**
     * this sets the background color of the square (used when uncovered).
     *
     * @param color The color to set as the background.
     */
    public void setBackgroundColor(Color color) { this.backgroundColor = color; }

    /**
     * this sets the color of the square when covered.
     *
     * @param color The color to set as the covered color.
     */
    public void setCoveredColor(Color color) { this.coveredColor = color; }

    /**
     * this sets the border color of the square.
     *
     * @param color The color to set as the border color.
     */
    public void setBorderColor(Color color) { this.borderColor = color; }

    /**
     * this sets the color used to represent the flag on the square.
     *
     * @param color The color to set as the flag color.
     */
    public void setFlagColor(Color color) { this.flagColor = color; }

    /**
     * Toggles the flag status of the square. If it is flagged, it will be unflagged, and vice versa.
     */
    public void toggleFlag() {
        flagged = !flagged;
    }

    /**
     * Uncovers the square if it is not flagged.
     * If the square is flagged, it will remain covered.
     */
    public void uncover() {
        if (!flagged) {
            uncovered = true;
        }
    }

    

    /**
     * Returns the number of neighboring squares that contain mines.
     *
     * @return The number of neighboring mines.
     */
    public abstract int getNeighbors();

    /**
     * Returns whether this square contains a mine.
     *
     * @return true if the square contains a mine, false otherwise.
     */
    public abstract boolean isMine();

    /**
     * This draws the square on the screen. 
     *
     * @param g2 The Graphics2D object used for drawing.
     * @param size The size of the square (width and height).
     * @param leftMargin The left margin for positioning the square.
     * @param topMargin The top margin for positioning the square.
     */
    public abstract void draw(Graphics2D g2, int size, int leftMargin, int topMargin);
}
