import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperGUI
{
    private static JFrame frame, frameWin;
    private static JMenuBar menuBar;
    private static JMenu menu, menuDifficulty;
    private static JMenuItem menuItemE, menuItemM, menuItemH, menuItemC;
    private static JPanel panel;
    private static Minesweeper game;
    private static JLabel label;
    private static boolean locked = false;
    private static int height, width, numMines, diff = 0;
    private static int[] newDiff;

    public static void main(final String[] args) 
    {
        buildGUI();
    }

    private static int[] customGame()
    {
        int[] customValues = new int[3];
        JTextField hField = new JTextField(2);
        JTextField wField = new JTextField(2);
        JTextField mField = new JTextField(2);

        JPanel myPanel = new JPanel();

        myPanel.add(new JLabel("Height: (max 20)"));
        myPanel.add(hField);
        myPanel.add(Box.createHorizontalStrut(5)); // a spacer
        myPanel.add(new JLabel("Width: (max 20)"));
        myPanel.add(wField);
        myPanel.add(Box.createHorizontalStrut(5)); // a spacer
        myPanel.add(new JLabel("Mines: (max h*w)"));
        myPanel.add(mField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Custom game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            customValues[0] = Integer.parseInt(hField.getText());
            customValues[1] = Integer.parseInt(wField.getText());
            customValues[2] = Integer.parseInt(mField.getText());
            if(customValues[0] > 20 || customValues[1] > 20 || customValues[2] >= (customValues[0]*customValues[1]))
            {
                customValues[0] = 20;
                customValues[1] = 20;
                customValues[2] = 20;
            }
        }
        return customValues;
    }
    
    private static void buildGUI(){
        frame = new JFrame("Minesweeper");

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

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);

        updateGUI();
    }

    private static void updateGUI()
    { 
        if(panel != null)
        {
            frame.remove(panel);
            frame.remove(label);
        }
        try 
        {
            switch(diff)
            {
                case 0:
                    game = new Minesweeper(Minesweeper.EASY_WIDTH, Minesweeper.EASY_HEIGHT, Minesweeper.EASY_MINES);
                    break;
                case 1:
                    game = new Minesweeper(Minesweeper.MEDIUM_WIDTH, Minesweeper.MEDIUM_HEIGHT, Minesweeper.MEDIUM_MINES);
                    break;
                case 2:
                    game = new Minesweeper(Minesweeper.HARD_WIDTH, Minesweeper.HARD_HEIGHT, Minesweeper.HARD_MINES);
                    break;
                case 3:
                    game = new Minesweeper(newDiff[0],newDiff[1],newDiff[2]);
                    break;
                default:
                    game = new Minesweeper(25, 25, 12);
                    break;
            }
        } catch (final TooManyMinesException ex) 
        {
            System.out.print("Error");
        }

        height = game.getHeight();
        width = game.getWidth();
        numMines = game.getNumMines();

        panel = new JPanel();
        panel.setLayout(new GridLayout(height, width));
        for (int h = 0; h < height; h++) 
        {
            for (int w = 0; w < width; w++) 
            {
                panel.add(game.getTile(h, w));
                game.getTile(h,w).addMouseListener(boardListen);
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        label = new JLabel("MINES FOUND: 0/" + String.valueOf(numMines));
        frame.add(label, BorderLayout.SOUTH);

        
        frame.setVisible(true);

    }


    private static ActionListener menuListen = new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource()==menuItemE)
            {
                diff = 0;
            }
            if(event.getSource()==menuItemM)
            {
                diff = 1;
            }
            if(event.getSource()==menuItemH)
            {
                diff = 2;
            }
            if(event.getSource()==menuItemC)
            {
                diff = 3;
                newDiff = new int[3];
                newDiff = customGame(); 

            }
            locked = false;
            updateGUI();
        }
     };

     private static MouseListener boardListen = new MouseAdapter()
     {
        public void mouseClicked(MouseEvent e)
        {
            if(!locked)
            {
                Tile clickedTile = (Tile) e.getSource();
                final int h = clickedTile.iCoord;
                final int w = clickedTile.jCoord;
                //left click
                if(e.getButton() == 1)
                {
                    //button.setText("F");
                    if(clickedTile.getFlagged() == 0)
                        game.visitTile(h, w);
                }
                //right click
                if(e.getButton() == 2 || e.getButton() == 3)
                    game.toggleFlag(h, w);
                //if lose lock GUI
                if(!game.getTileActivity())
                    locked = true;
                label.setText(game.updateLabel());
                if(game.victory())
                {
                    frameWin =new JFrame();  
                    JOptionPane.showMessageDialog(frameWin,"You Win!"); 
                }
            }
        }    
    };
}