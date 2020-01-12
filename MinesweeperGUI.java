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
    private static Integer diff = 0; 
    private static boolean locked;

    private static void createGUI(final int width, final int height, final int numMines) {
        frame = new JFrame("Minesweeper");

        // Build the menu.
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        menuDifficulty = new JMenu("New Game");
        menuItemE = new JMenuItem("Easy");
        menuItemE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                    diff = 1;
            }
        });
        menuDifficulty.add(menuItemE);
        menuItemM = new JMenuItem("Medium");
        menuItemM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                    diff = 2;
            }
        });
        menuDifficulty.add(menuItemM);
        menuItemH = new JMenuItem("Hard");
        menuItemH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                    diff = 3;
            }
        });
        menuDifficulty.add(menuItemH);
        menuItemC = new JMenuItem("Custom");
        menuItemC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                    diff = 4;
            }
        });
        menuDifficulty.add(menuItemC);
        menu.add(menuDifficulty);
        frame.setJMenuBar(menuBar);

        label = new JLabel("MINES FOUND: 0/" + String.valueOf(numMines));
        try {
            game = new Minesweeper(width, height, numMines);
            locked = false;
            panel = new JPanel();
            panel.setLayout(new GridLayout(height, width));
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    panel.add(game.getTile(h, w));
                    game.getTile(h,w).addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e){
                            if(!locked)
                            {
                                Tile clickedButton = (Tile) e.getSource();
                                //left click
                                if(e.getButton() == 1) {
                                    //button.setText("F");
                                    if(game.getTile(clickedButton.iCoord, clickedButton.jCoord).toString() != "F")
                                    {
                                        game.visitTile(clickedButton.iCoord, clickedButton.jCoord);
                                    }
                                }
                                //right click
                                if(e.getButton() == 2 || e.getButton() == 3) {
                                    //button.setText("F");
                                    game.toggleFlag(clickedButton.iCoord, clickedButton.jCoord);
                                }
                                //if lose lock GUI
                                if(!game.getTileActivity())
                                {
                                    locked = true;
                                }
                                label.setText(game.updateLabel());
                            }
                        }
                          
                    });
                }
            }
            frame.add(panel, BorderLayout.CENTER);
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
    }
}