package model;

import java.io.Serializable;

// -------------------------------------------------------------------------
/**
 * Represents a Synapse object found on a Segment object. Each Synapse object
 * has a input source AbstractCell object and a permanenceValue.
 *
 * @author Quinn Liu
 * @version Sep 14, 2012
 */
public class Synapse
    implements Serializable
{
    private AbstractCell      abstractCell;

    private float             permanenceValue;

    private boolean           connectedState;               // temporal pooler
    private boolean           previousConnectedState;       // temporal pooler

    /**
     * Initial permanceValues for distal Synapses.
     */
    public static final float INITIAL_PERMANENCE         = 0.3f;
    /**
     * Amount the permanenceValue is increased during learning.
     */
    public static final float PERMANCE_INCREASE          = 0.015f;
    /**
     * Amount the permanenceValue is decreased during learning.
     */
    public static final float PERMANCE_DECREASE          = 0.005f;
    /**
     * Minimal permanenceValue needed for a Synapse to be connected.
     */
    public static final float MINIMAL_CONNECTED_PERMANCE = 0.2f;


    // ----------------------------------------------------------
    /**
     * Create a new Synapse object with an AbstractCell object and
     * INITIAL_PERMANENCE value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public Synapse(AbstractCell abstractCell)
    {
        this.abstractCell = abstractCell;
        this.permanenceValue = INITIAL_PERMANENCE;
    }


    // ----------------------------------------------------------
    /**
     * Gets the AbstractCell of a Synapse object.
     *
     * @return The Abstract Cell object providing binary input states to this
     *         Synapse.
     */
    public AbstractCell getAbstractCell()
    {
        return this.abstractCell;
    }


    // ----------------------------------------------------------
    /**
     * Sets a new inputSource for a Synapse object.
     *
     * @param abstractCell
     *            The new inputSource to this Synapse object.
     */
    public void setAbstractCell(AbstractCell abstractCell)
    {
        this.abstractCell = abstractCell;
    }


    // ----------------------------------------------------------
    /**
     * Get the private permanenceValue field of a Synapse.
     *
     * @return The permanence value of a Synapse object.
     */
    public float getPermanenceValue()
    {
        return this.permanenceValue;
    }


    // ----------------------------------------------------------
    /**
     * Sets the permanenceValue for a Synapse object. Used only for testing.
     *
     * @param permanenceValue
     *            The new permanenceValue for a Synapse object.
     */
    public void setPermanenceValue(float permanenceValue)
    {
        if (permanenceValue <= 0.0f)
        {
            throw new IllegalStateException("permanenceValue cannot be <= 0.0f");
        }
        else if (permanenceValue > 1.0f)
        {
            throw new IllegalStateException("permanenceValue cannot be > 1.0f");
        }
        else
        {
            this.permanenceValue = permanenceValue;
        }
    }


    // ----------------------------------------------------------
    /**
     * Increase the permanenceValue of a Synapse object by PERMANENCE_INCREASE
     * while forcing permanenceValue to be always set < 1.0f.
     */
    public void increasePermance()
    {
        this.permanenceValue =
            Math.min(1.0f, this.permanenceValue + PERMANCE_INCREASE);
    }


    // ----------------------------------------------------------
    /**
     * Decrease the permanenceValue of a Synapse object by PERMANENCE_DECREASE
     * while forcing permanenceValue to be always set > 0.0f.
     */
    public void decreasePermance()
    {
        this.permanenceValue =
            Math.max(0.0f, this.permanenceValue - PERMANCE_DECREASE);
    }


    // ----------------------------------------------------------
    /**
     * Return whether this Synapse's permanenceValue is greater than the minimal
     * connected permanenceValue.
     *
     * @return true if this Synapse object is currently connected; otherwise
     *         false.
     */
    public boolean getConnectedState()
    {
        return (this.permanenceValue >= MINIMAL_CONNECTED_PERMANCE);
    }


    // ----------------------------------------------------------
    /**
     * Return whether this Synapse's permanenceVale is high enough to be connected
     * and the current input cell is active.
     *
     * @return true if this Synapse object is active; otherwise false.
     */
    public boolean getActiveState()
    {
        return (this.abstractCell.getActiveState() && this.getConnectedState());
    }


    // ----------------------------------------------------------
    /**
     * Return whether this Synapse was active due to the previous input.
     *
     * @return true if this Synapse was active due to the previous input at t-1.
     */
    public boolean getPreviousActiveState()
    {
        return (this.abstractCell.getPreviousActiveState() && this
            .getConnectedState());
    }


    // -------------------Temporal Pooler Methods----------------------------
    // TODO: test
    public void processCurrentTimeStep()
    {
        this.connectedState = this.getConnectedState();
    }

    // TODO: test
    public boolean previousActiveStateFromLearning()
    {
        return this.getPreviousActiveState() && this.abstractCell.getPreviousLearningState();
    }


    // ---------------------Additional Methods-------------------------------

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n============================");
        stringBuilder.append("\n----Synapse Information-----");
        stringBuilder.append("\nInputCellXYCoordinate:");
        stringBuilder.append("(" + this.abstractCell.getX() + ", "
            + this.abstractCell.getY() + ")");
        stringBuilder.append("\n      permanenceValue: ");
        stringBuilder.append(this.getPermanenceValue());
        stringBuilder.append("\n       connectedState: ");
        stringBuilder.append(this.getConnectedState());
        stringBuilder.append("\n          activeState: ");
        stringBuilder.append(this.getActiveState());
        stringBuilder.append("\n  previousActiveState: ");
        stringBuilder.append(this.getPreviousActiveState());
        stringBuilder.append("\n============================");
        String synapseInformation = stringBuilder.toString();
        return synapseInformation;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Synapse synapse = (Synapse)o;

        if (Float.compare(synapse.permanenceValue, permanenceValue) != 0)
            return false;
        if (!abstractCell.equals(synapse.abstractCell))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = 0;
        result += 2 * abstractCell.hashCode();
        result +=
            3 * (permanenceValue != +0.0f ? Float
                .floatToIntBits(permanenceValue) : 0);
        return result;
    }
}
