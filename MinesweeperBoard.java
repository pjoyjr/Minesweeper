/**
 * MinesweeperBoard.java
 * 
 * Represents the board for a minesweeper game.
 */

import java.util.Random;

public class MinesweeperBoard
{
    private Tile[][] boardTiles;        // the board as a 2d array
    private int width, height;          // width/height of the board

    // constructor, throws a TooManyMinesException if the number of mines
    // exceeds the total number of tiles given by width and height.
    public MinesweeperBoard(final int width, final int height,
        final int numMines) throws TooManyMinesException
    {
        if(numMines > (width*height))
        {
            throw new TooManyMinesException("Too Many Mines Requested");
        }
        this.width = width;
        this.height = height;
        boardTiles = new Tile[width][height];
        for(int h = 0; h < height; h++)
        {
            for(int w = 0; w < width; w++)
            {
                boardTiles[h][w] = new Tile(h, w);
            }
        }
        setMines(numMines);
    }

    // set numMines mines in random locations across the board.  Also set up
    // the numerical counts for the adjacent tiles to the mines.
    private void setMines(final int numMines)
    {
        // lay down the mines
        final int maxIndex = width * height;
        Random ranGen = new Random();
        int minesSet = 0;
        while (minesSet < numMines)
        {
            final int boardIdx = ranGen.nextInt(maxIndex);
            final int i = boardIdx % width;
            final int j = boardIdx / width;
            if (boardTiles[i][j].getTileInformation() != Tile.MINE)
            {
                boardTiles[i][j].setTileInformation(Tile.MINE);
                for (int u=i-1; u<=i+1; u++)
                {
                    for (int v=j-1; v<=j+1; v++)
                    {
                        if (0 <= u && u < width &&
                            0 <= v && v < height &&
                            !(i==u && j==v) &&
                            boardTiles[u][v].getTileInformation() != Tile.MINE)
                        {
                            final int numCurrMines =
                                      boardTiles[u][v].getTileInformation();
                            boardTiles[u][v].setTileInformation(numCurrMines+1);
                        }
                    }
                }
                minesSet++;
            }
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Tile getTile(final int i, final int j)
    {
        return boardTiles[i][j];
    }

    public int getMineInformation(final int i, final int j)
    {
        return boardTiles[i][j].getTileInformation();
    }

    public void setTileInformation(final int i, final int j,
        final int mineInformation)
    {
        boardTiles[i][j].setTileInformation(mineInformation);
    }

    public void setVisited(final int i, final int j, final boolean visited)
    {
        boardTiles[i][j].setVisited(visited);
    }

    public void setFlagged(final int i, final int j)
    {
        boardTiles[i][j].setFlagged();
    }

    public int getTileInformation(final int i, final int j)
    {
        return boardTiles[i][j].getTileInformation();
    }

    public boolean getVisited(final int i, final int j)
    {
        return boardTiles[i][j].getVisited();
    }

    public int getFlagged(final int i, final int j)
    {
        return boardTiles[i][j].getFlagged();
    }

    public String toString()
    {
        String boardAsString = "";
        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
                boardAsString += boardTiles[i][j];
            boardAsString += "\n";
        }
        return boardAsString;
    }


}