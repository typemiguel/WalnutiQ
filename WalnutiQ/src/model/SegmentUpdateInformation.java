package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * // -------------------------------------------------------------------------
 * /** Data structure that contains 3 pieces of Segment information required to
 * update a Segment if a Region is temporal learning. 1) Segment reference (Null
 * if a new Segment) 2) A list of existing active Synapses 3) A boolean
 * representing if this Segment is a sequence Segment (default = false) This
 * data structure also computes which learning Cells at this time step are
 * available for a Segment to (connect and) add to Synpases, should the Segment
 * get updated.
 *
 * @author Huanqing
 * @version Feb 9, 2013
 */
public class SegmentUpdateInformation implements Serializable
{
    private Cell         cell;
    private Segment      segment;
    private Set<Synapse> activeSynapses;

    private Set<Cell>    learningCells;
    private boolean      addNewSynapsesState;
    private int          numberOfPredictionSteps;

    // once Synapses are added, they are stored here to visualize later
    private Set<Synapse> newSynapsesToAdd;
    private final Random random = new Random(4242);

    /**
     * Create a new SegmentUpdateInformation object to be used to modify
     * the state of a Region by ...
     * 1) adding a new Segment to a Cell or ...
     * 2) adding a new Synapse to a Segment or ...
     * 3) updating permanenceValues of existing Synapses on a Segment.
     */
    SegmentUpdateInformation(Cell cell, Segment segment,
        Set<Synapse> activeSynapses, boolean addNewSynapses)
    {
        this.cell = cell;
        this.segment = segment;
        this.activeSynapses = new HashSet<Synapse>();
        this.activeSynapses.addAll(activeSynapses);

        this.learningCells = new HashSet<Cell>();
        this.newSynapsesToAdd = new HashSet<Synapse>();
        this.addNewSynapsesState = addNewSynapses;
        this.numberOfPredictionSteps = 1;
    }

    public Segment getSegment()
    {
        return this.segment;
    }

    public Set<Synapse> getActiveSynapses()
    {
        return this.activeSynapses;
    }

    public boolean getAddNewSynapsesState()
    {
        return this.addNewSynapsesState;
    }

    public Set<Cell> getLearningCells()
    {
        return this.learningCells;
    }

    Segment createNewSegmentConnectedToPreviousLearningCells()
    {
        Segment newSegment = new Segment();
        Set<Synapse> addedSynapses = new HashSet<Synapse>();


        return newSegment;
    }

    /**
     * Create learningCell set size new Synapses for this Segment to attach to
     * the parameter of learningCells.
     * @param learningCells Set of available learning Cells to connect to with Synapses.
     * @param addedSynapses The set to be populated with one new Synapse for
     * each learning Cell.
     */
    public void createSynapsesToLearningCells(Set<Cell> learningCells, Set<Synapse> addedSynapses)
    {
        for (Cell cell : learningCells)
        {
            Synapse newSynapse = new Synapse(cell);
            newSynapse.setPermanenceValue(0.0f);
            addedSynapses.add(newSynapse);
        }
    }
}
