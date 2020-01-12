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
    private static boolean locked;

    private static ActionListener menuListen = new ActionListener(){
        public void actionPerformed(ActionEvent event){
            if(event.getSource()==menuItemE)
                System.out.println("Easy clicked");
            if(event.getSource()==menuItemM)
                System.out.println("Medium clicked");
            if(event.getSource()==menuItemH)
                System.out.println("Hard clicked");
            if(event.getSource()==menuItemC)
                System.out.println("Custom clicked");
        }
     };

    private static void createGUI(final int width, final int height, final int numMines) {
        frame = new JFrame("Minesweeper");

        // Build the menu.
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        menuDifficulty = new JMenu("New Game");
        menuItemE = new JMenuItem("Easy");
        menuItemE.addActionListener(menuListen);
        menuDifficulty.add(menuItemE);
        menuItemM = new JMenuItem("Medium");
        menuItemM.addActionListener(menuListen);
        menuDifficulty.add(menuItemM);
        menuItemH = new JMenuItem("Hard");
        menuItemH.addActionListener(menuListen);
        menuDifficulty.add(menuItemH);
        menuItemC = new JMenuItem("Custom");
        menuItemC.addActionListener(menuListen);

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
                                    if(game.getTile(clickedButton.iCoord, clickedButton.jCoord).getFlagged() == 0)
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
                                if(game.getTileActivity() == false || game.victory())
                                    
                                    createGUI(10, 10, 12);
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
        createGUI(10, 10, 12);
    }

    
}