package model;

import model.InputCell;

// -------------------------------------------------------------------------
/**
 * Tests all of the methods within class InputCell.
 *
 * @author Quinn Liu
 * @version Oct 15, 2012
 */
public class TestInputCell
    extends junit.framework.TestCase
{
    private InputCell inputCell;


    /**
     * Instantiates a new InputCell object to be tested.
     */
    public void setUp()
    {
        this.inputCell = new InputCell(3, 2);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getX method on a InputCell object.
     */
    public void testGetX()
    {
        assertEquals(3, this.inputCell.getX());
        System.out.println(this.inputCell);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getY method on a InputCell object.
     */
    public void testGetY()
    {
        assertEquals(2, this.inputCell.getY());
    }

    // ----------------------------------------------------------
    /**
     * Tests the getColumnIndex() method.
     */
    public void testGetColumnIndex()
    {
        assertEquals(-1, this.inputCell.getColumnIndex());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setActiveState and getActiveState method on a InputCell object.
     */
    public void testSetAndGetActiveState()
    {
        assertFalse(this.inputCell.getActiveState());
        this.inputCell.setActiveState(true);
        assertTrue(this.inputCell.getActiveState());
        this.inputCell.setActiveState(false);
        assertFalse(this.inputCell.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setPreviousActiveState and getPreviousActiveState method on a
     * InputCell object.
     */
    public void testSetAndGetPreviousActiveState()
    {
        assertFalse(this.inputCell.getPreviousActiveState());
        this.inputCell.setPreviousActiveState(true);
        assertTrue(this.inputCell.getPreviousActiveState());
        this.inputCell.setPreviousActiveState(false);
        assertFalse(this.inputCell.getPreviousActiveState());
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testNextTimeStep()
    {
        this.inputCell.setPreviousActiveState(false);
        this.inputCell.setActiveState(true);

        this.inputCell.nextTimeStep();
        assertTrue(this.inputCell.getPreviousActiveState());
        assertFalse(this.inputCell.getActiveState());
    }
}
