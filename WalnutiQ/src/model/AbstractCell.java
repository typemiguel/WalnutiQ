package model;

import java.io.Serializable;

// -------------------------------------------------------------------------
/**
 * This class provides the minimum fields required to set up an AbstractCell.
 *
 * @author Quinn Liu
 * @version Nov 20, 2012
 */
public abstract class AbstractCell implements Serializable
{
    /**
     * x axis position of a AbstractCell object within first octant.
     */
    protected final int x;
    /**
     * y axis position of a AbstractCell object within first octant.
     */
    protected final int y;
    /**
     * z axis position of a AbstractCell object.
     */
    protected final int columnIndex;

    /**
     * Represents whether or not a AbstractCell object is active.
     */
    protected boolean   activeState;
    /**
     * Represents whether or not a AbstractCell object was previously active.
     */
    protected boolean   previousActiveState;

    protected boolean learningState;

    /**
     * Used for the temporal pooling algorithm.
     */
    protected boolean   previousLearningState; // TemporalPooler
    //--------------------------

    // ----------------------------------------------------------
    /**
     * Constructor for creating a new InputCell object.
     *
     * @param x
     *            x axis position of a AbstractCell object within first octant.
     * @param y
     *            y axis position of a AbstractCell object within first octant.
     */
    public AbstractCell(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.columnIndex = -1;
        this.activeState = false;
        this.previousActiveState = false;
    }


    // ----------------------------------------------------------
    /**
     * Constructor for creating a new Cell object.
     *
     * @param column
     * @param columnIndex
     */
    public AbstractCell(Column column, int columnIndex)
    {
        this.x = column.getX();
        this.y = column.getY();
        this.columnIndex = columnIndex;
        this.activeState = false;
        this.previousActiveState = false;
    }


    // ----------------------------------------------------------
    /**
     * Get the private field x of an AbstractCell object.
     *
     * @return The X axis position of this AbstractCell object.
     */
    public int getX()
    {
        return this.x;
    }


    // ----------------------------------------------------------
    /**
     * Get the private field y of an AbstractCell object.
     *
     * @return The Y axis position of this AbstractCell object.
     */
    public int getY()
    {
        return this.y;
    }


    // ----------------------------------------------------------
    /**
     * Get the private field columnIndex of an AbstractCell object.
     *
     * @return The columnIndex position of this AbstractCell object.
     */
    public int getColumnIndex()
    {
        return this.columnIndex;
    }


    // ----------------------------------------------------------
    /**
     * Returns whether an AbstractCell object is currently active.
     *
     * @return true if activeState is true; otherwise false.
     */
    public boolean getActiveState()
    {
        return this.activeState;
    }


    // ----------------------------------------------------------
    /**
     * Sets the private field activeState with true or false.
     *
     * @param activeState
     *            true represents active while false represents inactive.
     */
    public void setActiveState(boolean activeState)
    {
        this.activeState = activeState;
    }


    // ----------------------------------------------------------
    /**
     * Returns whether an AbstractCell was previously active.
     *
     * @return true if previousActiveState is true; otherwise false.
     */
    public boolean getPreviousActiveState()
    {
        return this.previousActiveState;
    }


    // ----------------------------------------------------------
    /**
     * Sets the private field previousActiveState with true or false.
     *
     * @param previousActiveState
     *            true represents active while false represents inactive.
     */
    public void setPreviousActiveState(boolean previousActiveState)
    {
        this.previousActiveState = previousActiveState;
    }


    // ----------------------------------------------------------
    /**
     * This method is automatically inherited by both classes InputCell and
     * Cell. However, in class Cell there is a overridden method
     * getDistalState(). This means when getDistalState() is called on Cell
     * object, it will always return true, and when getDistalState()is called on
     * a InputCell object it will always return false. In other words this
     * method returns true if this AbstractCell object represents a Cell
     * connected to distalSegments and false if this AbstractCell object is
     * connected to a proximalSegment.
     *
     * @return true if being called on a Cell object and false if being called
     *         on a InputCell object.
     */
    public boolean getDistalState()
    {
        return false;
    }

    //=====================Only Temporal Pooler Methods=======================
    // TODO: test
    public boolean getLearningState()
    {
        return this.learningState;
    }

    // TODO: test
    public void setLearningState(boolean learningState)
    {
        this.learningState = learningState;
    }


    // TODO: test
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return true if this Cell's learningState was true in the last time step.
     */
    public boolean getPreviousLearningState()
    {
        return this.previousLearningState;
    }

    // TODO: test
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param previousLearningState The new value for this Cell's previous
     * learningState.
     */
    public void setPreviousLearningState(boolean previousLearningState)
    {
        this.previousLearningState = previousLearningState;
    }
    //========================================================================

    // ----------------------------------------------------------
    /**
     * Set all of the previous states of the AbstractCell with the current
     * states of the AbstractCell. Then initialize all fields that represent the
     * current time step. i.e. activeState = false ...
     */
    public abstract void nextTimeStep();


    // ----------------------------------------------------------
    /**
     * 1 of 2 methods to be implemented so .equals(Object o) can be called on
     * both Cell and InputCell objects to check if their respective data fields
     * are equal.
     */
    public abstract boolean equals(Object o);


    // ----------------------------------------------------------
    /**
     * 1 of 2 methods to be implemented so .equals(Object o) can be called on
     * both Cell and InputCell objects to check if their respective data fields
     * are equal.
     */
    public abstract int hashCode();
}
