package model.util;

import model.SpatialPooler;

// -------------------------------------------------------------------------
/**
 * Provides methods for viewing Region object properties nicely on the console.
 *
 * @author Quinn
 * @version Dec 6, 2012
 */
public class RegionConsoleViewer
{
    // ----------------------------------------------------------
    /**
     * Returns a 2-D array of chars representing each Column's activeState
     * within a Region inside of a SpatialPooler object.
     *
     * @param spatialPooler
     * @return A 2-D char array of Columns' overlapScores.
     */
    public static char[][] getColumnActiveStatesCharArray(
        SpatialPooler spatialPooler)
    {
        char[][] columnActiveStates =
            new char[spatialPooler.getRegion().getXAxisLength()][spatialPooler
                .getRegion().getYAxisLength()];
        for (int x = 0; x < columnActiveStates.length; x++)
        {
            for (int y = 0; y < columnActiveStates[x].length; y++)
            {
                if (spatialPooler.getRegion().getColumn(x, y).getActiveState())
                {
                    // 'a' represents an active column at a specific time step
                    columnActiveStates[x][y] = 'a';
                }
                else
                {
                    // 'i' represents an inactive column at a specific time step
                    columnActiveStates[x][y] = 'i';
                }
            }
        }
        return columnActiveStates;
    }


    // ----------------------------------------------------------
    /**
     * Returns a 2-D array of integers representing each Column's overlapScore
     * within a Region inside of a SpatialPooler object.
     *
     * @param spatialPooler
     * @return A 2-D integer array of Columns' overlapScores.
     */
    public static int[][] getColumnOverlapScoresIntArray(
        SpatialPooler spatialPooler)
    {
        int[][] columnOverlapScores =
            new int[spatialPooler.getRegion().getXAxisLength()][spatialPooler
                .getRegion().getYAxisLength()];
        for (int x = 0; x < columnOverlapScores.length; x++)
        {
            for (int y = 0; y < columnOverlapScores[x].length; y++)
            {
                columnOverlapScores[x][y] =
                    spatialPooler.getRegion().getColumn(x, y).getOverlapScore();
            }
        }
        return columnOverlapScores;
    }


    // ----------------------------------------------------------
    /**
     * Prints a byte 2-D array to the console.
     *
     * @param doubleByteArray
     *            The 2-D byte array to be printed.
     */
    public static void printDoubleByteArray(byte[][] doubleByteArray)
    {
        for (int x = 0; x < doubleByteArray.length; x++)
        {
            System.out.println();
            for (int y = 0; y < doubleByteArray[x].length; y++)
            {
                System.out.print(doubleByteArray[x][y]);
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Prints a int 2-D array to the console.
     *
     * @param doubleIntArray
     *            The 2-D int array to be printed.
     */
    public static void printDoubleIntArray(int[][] doubleIntArray)
    {
        for (int x = 0; x < doubleIntArray.length; x++)
        {
            System.out.println();
            for (int y = 0; y < doubleIntArray[x].length; y++)
            {
                System.out.print(doubleIntArray[x][y]);
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Prints a char 2-D array to the console.
     *
     * @param doubleCharArray
     *            The 2-D char array to be printed.
     */
    public static void printDoubleCharArray(char[][] doubleCharArray)
    {
        for (int x = 0; x < doubleCharArray.length; x++)
        {
            System.out.println();
            for (int y = 0; y < doubleCharArray[x].length; y++)
            {
                System.out.print(doubleCharArray[x][y]);
            }
        }
    }
}
