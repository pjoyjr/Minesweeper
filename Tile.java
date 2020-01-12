/**
 * Tile.java
 * 
 * Represents a single tile on the minesweeper board.
 */

import javax.swing.*;

public class Tile extends JButton
{
    public static final int TILE_SIZE = 12;     // tile size for the GUI
    public static final int MINE = -1;          // mine identifier
    public int iCoord, jCoord;                 // position of the tile
    private int mineInformation;                // information about the tile
    private boolean flagged;                    // user placed a flag?
    private boolean visited;                    // tile selected by user
    private static final long serialVersionUID = 1L;


    public Tile(final int i, final int j)
    {
        iCoord = i;
        jCoord = j;
        mineInformation = 0;
        super.setText("-");
        super.setSize(TILE_SIZE, TILE_SIZE);
        visited = false;
    }

    // sets the tile information
    public void setTileInformation(final int mineInformation)
    {
        this.mineInformation = mineInformation;
    }

    // sets a tile to be visited based on the boolean state
    public void setVisited(final boolean visited)
    {
        this.visited = visited;
    }

    // sets a tile to be flagged based on the boolean state
    public void setFlagged(final boolean flagged)
    {
        this.flagged = flagged;
    }

    public int getTileInformation()
    {
        return mineInformation;
    }

    public boolean getVisited()
    {
        return visited;
    }

    public boolean getFlagged()
    {
        return flagged;
    }

    public String toString()
    {
        if (!flagged)
        {
            switch (mineInformation)
            {
                case MINE:
                    return "M";
                case 0:
                    return " ";
                default:
                    return "" + String.valueOf(mineInformation);
            }
        }
        return "F";
    }
}