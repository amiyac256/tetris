

import java.util.ArrayList;
 
/**
 * Keeps track of an creates the grid and display.
 * Moves the tetrad in directions corresponding to the keys pressed.
 * Runs the play method for the game in the main method.
 * 
 *
 * @author Amiya Chokhawala
 * @version January 26, 2021
 * @version Februart 3, 2021
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad tetrad;

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris() 
    {       
        grid = new MyBoundedGrid(20, 10);
        display = new BlockDisplay(grid);
        tetrad = new Tetrad(grid);
        display.setTitle("Amiya's Tetris");
        display.setArrowListener(this);
        display.showBlocks();
    }

    /**
     * If locations are empty and valid, when the down arrow is pressed
     * the tetrad moves down by one. 
     */
    public void upPressed()
    {
        if(tetrad.rotate())
            display.showBlocks();
    }

    /**
     * If locations are empty and valid, when the up arrow is pressed
     * the tetrad moves up by one. 
     */
    public void downPressed()
    {
        if(tetrad.translate(1,0))
            display.showBlocks();
    }

    /**
     * If locations are empty and valid, when the left arrow is pressed
     * the tetrad moves left by one. 
     */
    public void leftPressed()
    {
        if (tetrad.translate(0,-1))
            display.showBlocks();
    }

    /**
     * If locations are empty and valid, when the right arrow is pressed
     * the tetrad moves right by one. 
     */
    public void rightPressed()
    {
        if (tetrad.translate(0,1))
            display.showBlocks();
    }

    /**
     * If locations are empty and valid, when the space bar is pressed 
     * a "hard drop" is triggered and the current tetrad drops as far as possible.
     */
    public void spacePressed()
    {
        while (tetrad.translate(1,0))
            display.showBlocks();
    }

    /**
     * Pauses for one second, moves the tetrad down by one row and redraws the display. 
     * If it is not able to shift the tetrad down further, it creates a new tetrad. 
     */
    public void play()
    {
        boolean continuePlay = true;
        while(continuePlay) 
        {
            try 
            {
                downPressed();
                Thread.sleep(1000);
                // check if pressDown is possible or not if not 
                if(!tetrad.translate(1,0))
                {
                    clearCompletedRows();
                    // new Tetrad
                    tetrad = new Tetrad(grid);
                    // if tetris grid full
                    if(!tetrad.translate(1,0)) 
                    {
                        continuePlay = false;
                    }
                }
                display.showBlocks();
            }
            catch(InterruptedException e)
            {
                downPressed();
            }
        }
    }

    /**
     * Returns true if every cell in a given row is occupied;
     *         otherwise, returns false
     * 
     * @param row the given row 
     * 
     * @precondition 0 <= row < number of rows 
     * 
     */
    private boolean isCompletedRow(int row)
    {
        ArrayList<Location> locations = grid.getOccupiedLocations();
        Object[][] occupantArray = new Object[grid.getNumRows()][grid.getNumCols()]; 
        for (Location location : locations) 
        {
            occupantArray[location.getRow()][location.getCol()] = true;
        }
        for(int col = 0; col < grid.getNumCols(); col++)
        {
            if (occupantArray[row][col] == null) 
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes every block in the given row
     * and moves every block in the row above down one
     * 
     * @param row the given row 
     * 
     * @precondition 0 <= row < number of rows and the row is filled with blocks
     */
    private void clearRow(int row)    
    {
        ArrayList<Location> locations = grid.getOccupiedLocations();
        Object[][] occupantArray = new Object[grid.getNumRows()][grid.getNumCols()]; 
        for (Location location : locations) 
        {
            if (location.getRow() == (row - 1)) 
            {
                Block block = grid.get(location);
                Location loc = new Location(row, location.getCol());
                block.moveTo(loc);
            }
        }
    }

    /**
     * Clears all completed rows. 
     */
    private void clearCompletedRows()
    {
        for (int i = 0; i < 10; i++)
        {
            if (isCompletedRow(i))
            {
                clearRow(i);
            }
        }
    }

    /**
     * Main method for the Tetris class. Runs the play method. 
     * 
     * @param args command-line arguments for invocation
     */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.play(); 
    }
}

