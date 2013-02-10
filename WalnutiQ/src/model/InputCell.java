package model;

import java.io.Serializable;

// -------------------------------------------------------------------------
/**
 * Represents a single input bit from an external sensor source. This is used by
 * a proximalSegment object as data for the SpatialPooler object and
 * TemporalPooler object.
 *
 * @author Quinn Liu
 * @version Oct 14, 2012
 */
public class InputCell
    extends AbstractCell
    implements Serializable
{
    // ----------------------------------------------------------
    /**
     * Create a new InputCell object.
     *
     * @param x
     * @param y
     */
    public InputCell(int x, int y)
    {
        super(x, y);
    }



    // ----------------------------------------------------------
    /**
     * The current state of this InputCell will be set to the previous state and
     * the current state will be set to not active.
     */
    @Override
    public void nextTimeStep()
    {
        this.previousActiveState = this.activeState;
        this.activeState = false;
    }


    @Override
    public boolean equals(Object o)
    {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        InputCell inputCell = (InputCell)o;

        if (activeState != inputCell.activeState)
            return false;
        if (previousActiveState != inputCell.previousActiveState)
            return false;
        if (columnIndex != inputCell.columnIndex)
            return false;
        if (x != inputCell.x)
            return false;
        if (y != inputCell.y)
            return false;
        if (columnIndex != inputCell.columnIndex)
            return false;

        return true;

    }


    @Override
    public int hashCode()
    {
        int result = 0;
        result += 2 * x;
        result += 3 * y;
        result += 5 * columnIndex;
        result += 7 * (activeState ? 1 : 0);
        result += 11 * (previousActiveState ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n===========================");
        stringBuilder.append("\n---InputCell Information---");
        stringBuilder.append("\n             (x, y): ");
        stringBuilder.append("(" + this.getX() + ", " + this.getY() + ")");
        stringBuilder.append("\n        activeState: ");
        stringBuilder.append(this.getActiveState());
        stringBuilder.append("\npreviousActiveState: ");
        stringBuilder.append(this.getPreviousActiveState());
        stringBuilder.append("\n===========================");
        String inputCellInformation = stringBuilder.toString();
        return inputCellInformation;
    }
}
