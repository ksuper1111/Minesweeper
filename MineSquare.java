import java.awt.*;

/**
 * Represents a mine-containing square 
 * This square displays like a covered tile until uncovered.
 */
public class MineSquare extends Square {

    /**
     * Constructs a MineSquare. Initially looks like a covered square.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     */
    public MineSquare(int row, int col) {
        super(row, col);
        setBackgroundColor(Color.LIGHT_GRAY); // Hidden like other squares
    }

    /**
     * Returns -1 to indicate this square is a mine.
     *
     * @return -1 always
     */
    @Override
    public int getNeighbors() {
        return -1;
    }

    /**
     * Indicates that this square is a mine.
     *
     * @return true
     */
    @Override
    public boolean isMine() {
        return true;
    }

    /**
     * Draws the mine square.
     * Appears like a normal square when covered. If uncovered, reveals a black mine.
     *
     * @param g2         the graphics context
     * @param size       the size of the square
     * @param leftMargin the left margin of the grid
     * @param topMargin  the top margin of the grid
     */
    @Override
    public void draw(Graphics2D g2, int size, int leftMargin, int topMargin) {
        int x = leftMargin + col * size;
        int y = topMargin + row * size;
        int borderWidth = (int)(0.05 * size);

        
        g2.setColor(getBorderColor());
        g2.fillRect(x, y, size, size);

        if (!isUncovered()) {
            g2.setColor(getCoveredColor());
            g2.fillRect(x + borderWidth, y + borderWidth, size - 2 * borderWidth, size - 2 * borderWidth);

            if (isFlagged()) {
                g2.setColor(getFlagColor());
                int flagSize = size / 3;
                int fx = x + size / 2 - flagSize / 2;
                int fy = y + size / 2 - flagSize / 2;
                g2.fillRect(fx, fy, flagSize, flagSize);
            }
        } else {
            g2.setColor(Color.RED); 
            g2.fillRect(x + borderWidth, y + borderWidth, size - 2 * borderWidth, size - 2 * borderWidth);

            // Draw the mine
            g2.setColor(Color.BLACK);
            g2.fillOval(x + size / 4, y + size / 4, size / 2, size / 2);
        }
    }
}
