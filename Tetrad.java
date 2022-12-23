import java.awt.Color;
import java.util.ArrayList;
/**
 * Picks a random tetrad shape and intializes it. 
 * Adds and removes blocks from the grid. 
 * "Translates" the block by moving it to the right or down.
 * Rotates the block 90 degrees when the up arrow key is pressed. 
 *
 * @author Amiya Chokhawala 
 * @version January 28, 2021 
 */
public class Tetrad
{
    private Block[] blocks;
    private MyBoundedGrid<Block> grid;

    /**
     * Constructor for objects of class Tetris
     * Picks a random tetrad shape by a number (1 to 7) with different colors 
     * (red, gray, cyan, yellow, magenta, blue, and green) 
     * and intializes them to appear in the middle of the top row of the grid. 
     * 
     * @param grid the grid with the blocks and tetrad shapes
     * 
     */
    public Tetrad(MyBoundedGrid<Block> grid) 
    {
        this.blocks = new Block[4];
        this.grid = grid;
        Location[] locs = new Location[4];

        int mid = blocks.length / 2;
        int randomNumber = (int) (Math.random() * 7);
        if (randomNumber == 0) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.RED);
            }
            locs[0] = new Location(0,mid);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(2,mid);
            locs[3] = new Location(3,mid);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 1) 
        { 
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GRAY);
            }
            locs[0] = new Location(0,mid-1);
            locs[1] = new Location(0,mid);
            locs[2] = new Location(0,mid+1);
            locs[3] = new Location(1,mid);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 2) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.CYAN);
            }
            locs[0] = new Location(0,mid);
            locs[1] = new Location(0,mid+1);
            locs[2] = new Location(1,mid);
            locs[3] = new Location(1,mid+1);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 3) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.YELLOW);
            }
            locs[0] = new Location(0,mid);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(2,mid);
            locs[3] = new Location(3,mid);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 4) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.PINK);
            }
            locs[0] = new Location(0,mid+1);
            locs[1] = new Location(1,mid+1);
            locs[2] = new Location(2,mid+1);
            locs[3] = new Location(2,mid);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 5) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.BLUE);
            }
            locs[0] = new Location(0,mid);
            locs[1] = new Location(0,mid+1);
            locs[2] = new Location(1,mid);
            locs[3] = new Location(1,mid-1);
            this.addToLocations(grid, locs);
        }

        if (randomNumber == 6) 
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
                blocks[i].setColor(Color.GREEN);
            }
            locs[0] = new Location(0,mid);
            locs[1] = new Location(0,mid+1);
            locs[2] = new Location(1,mid+1);
            locs[3] = new Location(1,mid+2);
            this.addToLocations(grid, locs);
        }
    }

    /**
     * Puts the tetrad blocks at the location given in the locs array
     * 
     * @param grid grid in which the blocks are being placed
     * @param locs the array that stores the locations 
     * 
     * @precondition blocks are not in any grid 
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs) 
    {
        for (int i = 0; i < locs.length; i++) 
        {            
            blocks[i].putSelfInGrid(grid, locs[i]);
        }
    }

    /**
     * Removes blocks from the grid and returns the old locations of the blocks.
     * 
     * @precondition the blocks are in the grid
     * 
     * @return locs array of the old locations
     */
    private Location[] removeBlocks()
    {
        Location[] locs = new Location[blocks.length];
        for (int i = 0; i < blocks.length; i++)
        {
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * Checks if each of the locs is valid (on the grid) and empty in the grid 
     * and returns true if they are; otherwise, false         
     * 
     * @param grid the grid on which the locs should be 
     * @param locs the array of locs being checked 
     * 
     * @return true if the locs are all on the grid and empty; otherwise, false
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (Location loc : locs)
        {
            if ((!grid.isValid(loc))|| grid.get(loc) != null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Attempts to move the tetrad deltaRow rows down and deltaColumn columns to the right,
     * if those positions are valid and empty; return true if it works, 
     * but false otherwise.
     * 
     * @param deltaRow number of rows the tetrad is being moved down
     * @param deltaCol number of columns the tetrad is being moved to the right
     * 
     * @return true if it successful, and false otherwise.
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        MyBoundedGrid <Block> grid = blocks[0].getGrid();

        Location [] beforeLocs = removeBlocks();
        Location[] afterLocs = new Location[blocks.length];

        for (int i = 0; i < afterLocs.length; i++)
        {
            afterLocs[i] = new Location(beforeLocs[i].getRow() + deltaRow, 
                beforeLocs[i].getCol() + deltaCol);            
        }

        if (areEmpty(grid, afterLocs))
        {
            addToLocations(grid, afterLocs);
            return true;
        } 
        else 
        {
            addToLocations(grid, beforeLocs);
            return false;
        }
    }

    /**
     * Attempts to rotate the tetrad clockwise by about 90 degrees about its center
     * if the needed positions are empty. returns true if it works, and false otherwise.
     * 
     * @return true if it successful, and false otherwise
     */
    public boolean rotate()
    {
        MyBoundedGrid <Block> grid = blocks[0].getGrid();

        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];

        int row = oldLocs[0].getRow();
        int col = oldLocs[0].getCol();

        for (int i = 0; i < newLocs.length; i++)
        {
            newLocs[i] = new Location(row - col + oldLocs[i].getCol(), 
                                      row + col - oldLocs[i].getRow());
        }

        if (areEmpty(grid, newLocs))
        {
            addToLocations(grid, newLocs);
            return true;
        } 
        else 
        {
            addToLocations(grid, oldLocs);
            return false;
        }
    }
}
