import java.awt.*;

/**
 * This Represents a square in the game that displays a number indicating how many neighboring squares contain mines.
 * This class extends the {@link Square} class and provides the functionality to handle the number of neighboring mines,
 * as well as rendering the square with its respective visual representation.
 */
public class NumberSquare extends Square {
    private int neighbors; 
    private Color numberColor; 

    /**
     * Constructs a NumberSquare with the specified number of neighboring mines, row, and column.
     * Initializes the background and number color of the square.
     *
     * @param neighbors The number of neighboring mines.
     * @param row The row position of the square in the grid.
     * @param col The column position of the square in the grid.
     */
    public NumberSquare(int neighbors, int row, int col) {
        super(row, col);
        this.neighbors = neighbors;
        this.numberColor = Color.BLUE; 
        setBackgroundColor(Color.LIGHT_GRAY); 
    }

    /**
     * Returns the number of neighboring mines for this square.
     *
     * @return The number of neighboring mines.
     */
    @Override
    public int getNeighbors() {
        return neighbors;
    }

    /**
     * Returns whether this square is a mine. In the case of a NumberSquare, it is always false.
     *
     * @return false, since a NumberSquare is not a mine.
     */
    @Override
    public boolean isMine() {
        return false;
    }

    /**
     * Sets the color used to display the number of neighboring mines.
     *
     * @param color The color to be used for the number display.
     */
    public void setNumberColor(Color color) {
        this.numberColor = color;
    }

    /**
     * Returns the current color used to display the number of neighboring mines.
     *
     * @return The color used to display the number.
     */
    public Color getNumberColor() {
        return numberColor;
    }

    /**
     * Draws the square on the screen, including its background, border, and number (if uncovered).
     * If the square is flagged, it will draw a flag on top of it.
     * If the square is covered, it will be drawn with the covered color.
     *
     * @param g2 The Graphics2D object used for drawing.
     * @param size The size of the square (width and height).
     * @param leftMargin The left margin for positioning the square.
     * @param topMargin The top margin for positioning the square.
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
                int fx = x + (size - flagSize) / 2; 
                int fy = y + (size - flagSize) / 2; 
                g2.fillRect(fx, fy, flagSize, flagSize); 
            }
        } else { 
            g2.setColor(getBackgroundColor());
            g2.fillRect(x + borderWidth, y + borderWidth, size - 2 * borderWidth, size - 2 * borderWidth);

            if (neighbors > 0) { 
                g2.setColor(numberColor);
                g2.setFont(new Font("Arial", Font.BOLD, size / 2)); 
                FontMetrics fm = g2.getFontMetrics();
                String text = String.valueOf(neighbors); 
                int textWidth = fm.stringWidth(text); 
                int textHeight = fm.getAscent(); 

                
                int tx = x + (size - textWidth) / 2;
                int ty = y + (size + textHeight) / 2 - borderWidth;

                g2.drawString(text, tx, ty); 
            }
        }
    }
}
