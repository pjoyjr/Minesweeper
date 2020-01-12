/**
 * Minesweeper.java
 * 
 * Includes gameplay logic to play a game of minesweeper.
 */

import javax.swing.JOptionPane;

public class Minesweeper
{
    public static final int EASY_MINES    = 10;
    public static final int EASY_WIDTH    = 10;
    public static final int EASY_HEIGHT   = 10;
    public static final int MEDIUM_MINES  = 40;
    public static final int MEDIUM_WIDTH  = 15;
    public static final int MEDIUM_HEIGHT = 15;
    public static final int HARD_MINES    = 100;
    public static final int HARD_WIDTH    = 20;
    public static final int HARD_HEIGHT   = 20;
    //private MinesweeperGUI gui;         // gui for the game board
    private MinesweeperBoard board;     // game board information
    private int flagsMarked;            // flags marked by the player
    private int numMines;               // mines in the game
    private boolean tileActivity;       // tile activity enabled or disabled

    public Minesweeper(final int width, final int height, final int numMines)
        throws TooManyMinesException
    {
        this.board = new MinesweeperBoard(width, height, numMines);
        this.numMines = numMines;
        this.tileActivity = true;
    }

    // logic applied to the game board when a tile is flagged
    public void toggleFlag(final int i, final int j)
    {
        if(board.getTile(i, j).getVisited() == false && this.tileActivity == true)
        {
            if(board.getTile(i, j).getFlagged() == true)
            {
                board.getTile(i, j).setFlagged(false);
            }
            else
            {
                board.getTile(i, j).setFlagged(true);
            }
        }
    }

    // logic applied to the game board when a tile is visited
    public void visitTile(final int i, final int j)
    {
        // TODO: If the tile has already been visited, then nothing should be done.
        // If the tile needs to be visited, then it should be visited.  If the
        // visited tile has no adjacent tiles, then expandVisit should be called
        // on this tile.
        if(board.getTile(i, j).getVisited() == false && this.tileActivity == true)
        {
            //if mine, then game over
            if(board.getTile(i, j).getTileInformation() == -1)
            {
                System.out.println("TODO: You lose!");
                this.tileActivity = false;
            }
            else if(board.getTile(i, j).getTileInformation() == 0)
            {
                board.getTile(i, j).setText(board.getTile(i, j).toString());
                expandVisit(i, j);
            }
            else
            {
                board.getTile(i, j).setText(board.getTile(i, j).toString());
                board.getTile(i, j).setVisited(true);
            }
        }
    }

    // a recursive function to expand the tile selected if there are no
    // adjacent mines to the tile.  Note that this method is recursive.  Since
    // we have not covered recursion, you do not have to understand exactly
    // how this function is written.
    private void expandVisit(final int i, final int j)
    {
        // set this tile to have been visited
        board.setVisited(i, j, true);
        // for all 8 neighbors, as long as the number of mines is 0, keep
        // expanding the visit
        final int width = board.getWidth();
        final int height = board.getHeight();
        for (int u=Math.max(0, i-1); u<=Math.min(width-1, i+1); u++)
        {
            for (int v=Math.max(0, j-1); v<=Math.min(height-1, j+1); v++)
            {
                // if not visited do something
                if (!board.getVisited(u, v))
                {
                    final int tileInfo = board.getMineInformation(u, v);
                    final boolean flagged = board.getFlagged(u, v);
                    if (!flagged)
                    {
                        // expand if tileInfo is 0
                        if (tileInfo == 0)
                        {
                            board.getTile(u,v).setText(board.getTile(u, v).toString());
                            expandVisit(u, v);
                        }
                        // if a number just set it as visited
                        else if (tileInfo > 0)
                            board.getTile(u,v).setText(board.getTile(u, v).toString());
                            board.setVisited(u, v, true);
                        // else tileInfo -> mine or flag
                    }
                }
            }
        }
    }

    public int getMineInformation(final int i, final int j)
    {
        return board.getMineInformation(i, j);
    }

    public int getWidth()
    {
        return board.getWidth();
    }

    public int getHeight()
    {
        return board.getHeight();
    }

    public int getFlagsMarked()
    {
        return flagsMarked;
    }

    public int getNumMines()
    {
        return numMines;
    }

    public Tile getTile(final int i, final int j)
    {
        return board.getTile(i, j);
    }

    // checks and returns true if the victory condition is satisfied, false
    // otherwise
    public boolean victory()
    {
        // TODO:  check for the victory condition and return true if the game
        // is won, false otherwise.
        return false;
    }

    public String toString()
    {
        return "" + board;
    }
}