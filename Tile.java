/**
 * Tile.java
 * 
 * Represents a single tile on the minesweeper board.
 */

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

public class Tile extends JButton
{
    public static final int TILE_SIZE = 12;     // tile size for the GUI
    public static final int MINE = -1;          // mine identifier
    public int iCoord, jCoord;                 // position of the tile
    private int mineInformation;                // information about the tile
    private int flagged;                    // user placed a flag?
    private boolean visited;                    // tile selected by user
    private static final long serialVersionUID = 1L;


    public Tile(final int i, final int j)
    {
        iCoord = i;
        jCoord = j;
        mineInformation = 0;
        super.setText(toString());
        super.setBackground(new Color(123, 123, 123));
        visited = false;
        super.setMinimumSize(new Dimension(TILE_SIZE, TILE_SIZE));
        super.setMargin(new Insets(0, 0, 0, 0));
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
        super.setBackground(new Color(255, 255, 255));
        if(getTileInformation() == MINE)
            super.setBackground(new Color(255, 0, 0));
    }

    // sets a tile to be flagged based on the boolean state
    public void setFlagged()
    {
        //0 denotes no flag, 1 denotes mine, 2 denotes maybe mine
        this.flagged = (this.flagged+1)%3;
    }

    public int getTileInformation()
    {
        return mineInformation;
    }

    public boolean getVisited()
    {
        return visited;
    }

    public int getFlagged()
    {
        return flagged;
    }

    public String toString()
    {
        if(visited)
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
        if(flagged == 1)
            return "F";
        if(flagged == 2)
            return "?";
        return " ";
    }
}