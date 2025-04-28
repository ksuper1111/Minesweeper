import javax.swing.*;

public class Minesweeper {
    /**************************************
     * Please evaluate for:
     * - first selection can’t be a mine
     * - number colors by value
     * - recursive uncovering
     **************************************/

    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper By Kali Banghart - CS 2100 Final Project SP25");
        GameComponent game = new GameComponent();

        window.setSize(800, 700); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(game);
        window.setVisible(true);

        game.start(); 
    }
}
