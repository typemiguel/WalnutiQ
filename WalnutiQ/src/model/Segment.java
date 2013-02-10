package model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

// -------------------------------------------------------------------------
/**
 * Represents a dendrite segment that forms synapses with other cells. A Segment
 * object can be a proximal dendrite for spatial pooling or distal dendrite for
 * temporal pooling.
 *
 * @author Quinn Liu
 * @version Oct 8, 2012
 */
public class Segment
    implements Serializable
{
    private boolean            activeState;
    private boolean            previousActiveState;

    private boolean            sequenceState;
    private int                numberOfPredictionSteps;  // TemporalPooler

    // can still call object's methods to change it's state, but reference to
    // the object cannot be changed due to keyword "final"
    private final Set<Synapse> synapses;

    private Set<Synapse>       connectedSynapses;
    private Set<Synapse>       activeSynapses;
    private Set<Synapse>       previousActiveSynapses;

    /**
     * Minimal number of active synapses needed for a Segment to become active.
     */
    public static final int    SEGMENT_ACTIVE_SYNAPSES_THRESHOLD = 15;

    /**
     * The maximum number of predictions into the future that can be generated.
     */
    public static final int    MAXIMUM_TIME_STEPS                = 10;


    // ----------------------------------------------------------
    /**
     * Intialize a new Segment object.
     */
    public Segment()
    {
        this.activeState = false;
        this.previousActiveState = false;
        this.sequenceState = false; // temporal pooler field

        this.synapses = new HashSet<Synapse>();
        this.connectedSynapses = new HashSet<Synapse>();
        this.activeSynapses = new HashSet<Synapse>();
        this.previousActiveSynapses = new HashSet<Synapse>();
    }


    // ----------------------------------------------------------
    /**
     * Return the desired InputCell within this Segment's set of all Synapses in
     * O(n) time.
     *
     * @param x
     *            x axis position of a InputCell object.
     * @param y
     *            y axis position of a InputCell object.
     * @return The InputCell with x and y fields matching the method parameters.
     */
    public InputCell getInputCell(int x, int y)
    {
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getAbstractCell().getX() == x
                && synapse.getAbstractCell().getY() == y
                && synapse.getAbstractCell().getColumnIndex() == -1)
            {
                return (InputCell)synapse.getAbstractCell();
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Get the private activeState field of a Segment object.
     *
     * @return true if this Segment is active; otherwise false.
     */
    public boolean getActiveState()
    {
        return activeState;
    }


    // ----------------------------------------------------------
    /**
     * Sets the new activeState of a Segment object.
     *
     * @param activeState
     *            true if active; false if inactive.
     */
    public void setActiveState(boolean activeState)
    {
        this.activeState = activeState;
    }


    // ----------------------------------------------------------
    /**
     * Get the private previousActiveState field of a Segment object.
     *
     * @return the activeState of a Segment object at time t-1.
     */
    public boolean getPreviousActiveState()
    {
        return previousActiveState;
    }


    // ----------------------------------------------------------
    /**
     * Set the private previousActiveState field of a Segment object.
     *
     * @param previousActiveState
     *            true if previously active; otherwise false.
     */
    public void setPreviousActiveState(boolean previousActiveState)
    {
        this.previousActiveState = previousActiveState;
    }


    // TODO: test
    public int getNumberOfPredictionSteps()
    {
        return this.numberOfPredictionSteps;
    }


    // TODO: test
    public void setNumberOfPredictionSteps(int numberOfPredictionSteps)
    {
        this.numberOfPredictionSteps =
            Math.min(Math.max(1, numberOfPredictionSteps), Segment.MAXIMUM_TIME_STEPS);
        this.setSequenceState(numberOfPredictionSteps == 1);
    }


    // ----------------------------------------------------------
    /**
     * Returns the list of Synapses a Segment contains.
     *
     * @return This Segment object's list of Synapse objects.
     */
    public Set<Synapse> getSynapses()
    {
        return this.synapses;
    }


    // ----------------------------------------------------------
    /**
     * Returns a specific synapse object in a list by searching for a synapse
     * with the same abstractCell in O(n) time.
     *
     * @param abstractCell
     *            The identifier used to find a specific synapse.
     * @return The synapse object containing the abstractCell parameter. If
     *         synapse cannot be found return null.
     */
    public Synapse getSynapse(AbstractCell abstractCell)
    {
        if (this.synapses.size() == 0)
        {
            throw new IllegalStateException(
                "no synape could be found because this.synapses is empty");
        }
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getAbstractCell().equals(abstractCell))
            {
                return synapse;
            }
        }
        return null; // if synapse is not in set of all synapses
    }


    // ----------------------------------------------------------
    /**
     * Add a Synapse object to this Segment object.
     *
     * @param synapse
     *            The Synapse object to be added.
     */
    public void addSynapse(Synapse synapse)
    {
        this.synapses.add(synapse);
    }


    // ----------------------------------------------------------
    /**
     * Return the current set of connectedSynapses for a Segment in O(n) time.
     *
     * @return Set of synapses that are connected.
     */
    public Set<Synapse> getConnectedSynapses()
    {
        this.connectedSynapses.clear();
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getConnectedState())
            {
                this.connectedSynapses.add(synapse);
            }
        }
        return this.connectedSynapses;
    }


    // ----------------------------------------------------------
    /**
     * Return the current set of activeSynapses for a Segment. A synapse must be
     * connected and active due to current input to be in the set of
     * activeSynapses.
     *
     * @return Set of synapses that are active.
     */
    public Set<Synapse> getActiveSynapses()
    {
        this.activeSynapses.clear();
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getActiveState())
            {
                this.activeSynapses.add(synapse);
            }
        }
        return this.activeSynapses;
    }


    // ----------------------------------------------------------
    /**
     * Return the current Set of activeSynapses for a Segment. A synapse must be
     * connected and active due to input @ t -1 to be in the set of
     * previousActiveSynapses.
     *
     * @return Set of synapses that were active previously.
     */
    public Set<Synapse> getPreviousActiveSynapses()
    {
        this.previousActiveSynapses.clear();
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getPreviousActiveState())
            {
                this.previousActiveSynapses.add(synapse);
            }
        }
        return this.previousActiveSynapses;
    }


    // ----------------------------------------------------------
    /**
     * Updates all permanence values of each Synapse on a proximal dendrite
     * based on current activity of a proximal dendrite. If a Synapse is in
     * activeState, it's permanence will be increased. Otherwise, it's
     * permanence is decreased.
     */
    public void updateProximalPermanences()
    {
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getActiveState())
            {
                synapse.increasePermance();
            }
            else
            {
                synapse.decreasePermance();
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Updates all permanence values of each Synapse on a proximal dendrite
     * based on parameter. Used specifically for boosting.
     *
     * @param increaseOrDecrease
     *            If true increase all Synpase permanenceValues; otherwise
     *            decrease permanceValue of all Synapses on Segment.
     */
    public void updateProximalPermanences(boolean increaseOrDecrease)
    {
        for (Synapse synapse : this.synapses)
        {
            if (increaseOrDecrease)
            {
                synapse.increasePermance();
            }
            else
            {
                synapse.decreasePermance();
            }
        }
    }


    // ==============Only Temporal Pooler Methods================
    // ----------------------------------------------------------
    /**
     * Return the desired Cell within this Segment's set of all Synapses in O(n)
     * time.
     *
     * @param x
     *            X axis position of a AbstractCell object within first octant.
     * @param y
     *            Y axis position of a AbstractCell object within first octant.
     * @param columnIndex
     *            Z axis position of a AbstractCell object.
     * @return The Cell with x, y, and columnIndex fields matching the method
     *         parameters.
     */
    public Cell getCell(int x, int y, int columnIndex)
    {
        for (Synapse synapse : this.synapses)
        {
            if (synapse.getAbstractCell().getX() == x
                && synapse.getAbstractCell().getY() == y
                && synapse.getAbstractCell().getColumnIndex() == columnIndex)
            {
                return (Cell)synapse.getAbstractCell();
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * The current state of this Segment will be set to the previous state and
     * the current will be set to not active. Additionally, the previous active
     * synapses set will be cleared and replaced with the current active
     * synapses and the current active synapses will be cleared. then the
     * current state will be set to no cell activity.
     */
    public void nextTimeStep()
    {
        this.previousActiveState = this.activeState;
        this.activeState = false;

        this.previousActiveSynapses.clear();
        this.previousActiveSynapses.addAll(this.activeSynapses);
        this.activeSynapses.clear();
    }


    // -------------------Temporal Pooler Methods----------------------------

    // ----------------------------------------------------------
    /**
     * Gets the sequenceSegment state of a Segment object.
     *
     * @return true of this segment predicts the input of the next time step;
     *         otherwise false.
     */
    public boolean getSequenceState()
    {
        return this.sequenceState;
    }


    // ----------------------------------------------------------
    /**
     * Sets the new sequenceSegment state of a Segment object.
     *
     * @param sequenceState
     *            the prediction state for the next time state by this Segment.
     */
    public void setSequenceState(boolean sequenceState)
    {
        this.sequenceState = sequenceState;
    }


    /**
     * Returns true if the number of (connected Synapses on this Segment that
     * were active due to learning state at time t-1) is greater than
     * activationThreshold.
     * @return true if this Segment was previously active from learning; otherwise
     * return false.
     */
    public boolean previousActiveStateFromLearning()
    {
        int connectedSynapses1 = 0;
        for (Synapse synapse : this.synapses)
        {
            if (synapse.previousActiveStateFromLearning())
            {
                connectedSynapses1++;
            }
        }
        return connectedSynapses1 >= Segment.SEGMENT_ACTIVE_SYNAPSES_THRESHOLD;
    }


    // TODO: test
    /**
     * Compute all active Synapses of for this Segment and this Segment's
     * activeState based on current input.
     */
    public void processCurrentTimeStep()
    {
        this.activeSynapses.clear();
        for (Synapse synapse : this.synapses)
        {
            synapse.processCurrentTimeStep();
            if (synapse.getActiveState())
            {
                this.activeSynapses.add(synapse);
            }
        }
        this.activeState =
            this.activeSynapses.size() >= Segment.SEGMENT_ACTIVE_SYNAPSES_THRESHOLD;
    }


    // ------------------------------------------------------------------

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n=============================");
        stringBuilder.append("\n-----Segment Information-----");
        stringBuilder.append("\n           activeState: ");
        stringBuilder.append(this.getActiveState());
        stringBuilder.append("\n   previousActiveState: ");
        stringBuilder.append(this.getPreviousActiveState());
        stringBuilder.append("\n       sequenceSegment: ");
        stringBuilder.append(this.getSequenceState());
        stringBuilder.append("\n    number of synapses: ");
        stringBuilder.append(this.getSynapses().size());
        stringBuilder.append("\n     connectedSynapses: ");
        stringBuilder.append(this.getConnectedSynapses().size());
        stringBuilder.append("\n        activeSynapses: ");
        stringBuilder.append(this.getActiveSynapses().size());
        stringBuilder.append("\npreviousActiveSynapses: ");
        stringBuilder.append(this.getPreviousActiveSynapses().size());
        stringBuilder.append("\n=============================");
        String segmentInformation = stringBuilder.toString();
        return segmentInformation;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Segment segment = (Segment)o;

        if (activeState != segment.activeState)
            return false;
        if (previousActiveState != segment.previousActiveState)
            return false;
        if (sequenceState != segment.sequenceState)
            return false;
        if (!activeSynapses.equals(segment.activeSynapses))
            return false;
        if (!connectedSynapses.equals(segment.connectedSynapses))
            return false;
        if (!previousActiveSynapses.equals(segment.previousActiveSynapses))
            return false;
        if (!synapses.equals(segment.synapses))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = (activeState ? 1 : 0);
        result += 2 * (previousActiveState ? 1 : 0);
        result += 3 * (sequenceState ? 1 : 0);
        result += 5 * synapses.hashCode();
        result += 7 * connectedSynapses.hashCode();
        result += 11 * activeSynapses.hashCode();
        result += 13 * previousActiveSynapses.hashCode();
        return result;
    }
}
