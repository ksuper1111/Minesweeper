import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GameComponent is a custom component that handles rendering and user interaction
 * for a Minesweeper game. It tracks game state, updates messages, and responds to mouse input.
 */
public class GameComponent extends JComponent implements MouseListener {
    private Grid gameGrid;
    private String message;
    private String userProgress;
    private Color textColor;
    private boolean started;
    private boolean gameOver;

    /**
     * Constructs the Minesweeper game component with default board size and settings.
     */
    public GameComponent() {
        gameGrid = new Grid(10, 12, 10, 100, 100); // 10x12 board, 10 mines, 100px margin
        message = "Click any tile to start!";
        userProgress = "0/10";
        textColor = Color.BLACK;
        started = false;
        gameOver = false;

        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(this);
    }

    /**
     * Starts the game loop, checking for win condition and repainting at the regular intervals.
     */
    public void start() {
        while (true) {
            checkWin();
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the player has won the game and updates the message accordingly.
     */
    private void checkWin() {
        if (!gameOver && gameGrid.hasWon()) {
            message = "You win!";
            gameOver = true;
        }
    }

    /**
     * Paints the game grid and status messages to the screen.
     *
     * @param g The graphics context used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameGrid.draw((Graphics2D) g);

        g.setColor(textColor);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(message, 20, 40);
        g.drawString(userProgress, 20, 70);
    }

    /**
     * Handles mouse release events for uncovering or flagging squares.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver) return;

        String action = (e.getButton() == MouseEvent.BUTTON1) ? "uncover" : "flag";
        started = gameGrid.userMove(e.getX(), e.getY(), action, started);

        if (started && !gameOver) {
            message = "Find the mines!";
        }

        if (gameGrid.isMineUncovered()) {
            gameOver = true;
            message = "You lost!";
        }

        userProgress = gameGrid.getNumFlaggedSquares() + "/" + gameGrid.getNumMines();
    }

    // Unused MouseListener methods 
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
