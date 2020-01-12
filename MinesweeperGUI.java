import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperGUI
{
    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JMenu menu, menuDifficulty;
    private static JMenuItem menuItemE, menuItemM, menuItemH, menuItemC;
    private static JPanel panel;
    private static Minesweeper game;
    private static JLabel label;    

    private static void createGUI(final int width, final int height, final int numMines) {
        frame = new JFrame("Minesweeper");

        // Build the menu.
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        menuDifficulty = new JMenu("New Game");
        menuItemE = new JMenuItem("Easy");
        menuDifficulty.add(menuItemE);
        menuItemM = new JMenuItem("Medium");
        menuDifficulty.add(menuItemM);
        menuItemH = new JMenuItem("Hard");
        menuDifficulty.add(menuItemH);
        menuItemC = new JMenuItem("Custom");
        menuDifficulty.add(menuItemC);
        menu.add(menuDifficulty);
        frame.setJMenuBar(menuBar);

        try {
            game = new Minesweeper(width, height, numMines);
            panel = new JPanel();
            panel.setLayout(new GridLayout(height, width));
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    panel.add(game.getTile(h, w));
                    game.getTile(h, w).addActionListener(new ActionListener()
                    {
                      public void actionPerformed(ActionEvent e)
                      {
                        //game.visitTile(h, w);
                        Tile clickedButton = (Tile) e.getSource();
                        game.visitTile(clickedButton.iCoord, clickedButton.jCoord);
                      }
                    });
                }
            }
            frame.add(panel, BorderLayout.CENTER);
            label = new JLabel("MINES FOUND 0/10");
            frame.add(label, BorderLayout.SOUTH);
        } catch (final TooManyMinesException ex) {
            System.out.print("Error");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public static void main(final String[] args) {
        // TODO: this is the main method which should be used for the final
        // submission
        createGUI(10, 10, 5);
        System.out.println(game.toString());
    }
}