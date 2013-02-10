package model;

import model.AbstractCell;
import model.InputCell;
import model.Segment;
import model.Synapse;

// -------------------------------------------------------------------------
/**
 * Tests all of the methods within class Segment.
 *
 * @author Quinn Liu
 * @version Oct 15, 2012
 */
public class TestSegment
    extends junit.framework.TestCase
{
    private Segment      segment;

    // 32 suffix means position within input data's 2D array of InputCell
    // objects
    private AbstractCell inputCell32;
    private Synapse      connectedSynapse;
    private AbstractCell inputCell11;
    private Synapse      activeSynapse;
    private AbstractCell inputCell45;
    private Synapse      inActiveSynapse;


    public void setUp()
    {
        this.segment = new Segment();

        // initialize connected Synapse(not active)
        this.inputCell32 = new InputCell(3, 2);
        this.connectedSynapse = new Synapse(this.inputCell32);
        this.segment.addSynapse(this.connectedSynapse);

        // initialize active Synapse(also connected)
        this.inputCell11 = new InputCell(1, 1);
        this.activeSynapse = new Synapse(this.inputCell11);
        this.inputCell11.setActiveState(true);
        this.segment.addSynapse(this.activeSynapse);

        // initialize inactive Synapse(also not connected)
        this.inputCell45 = new InputCell(4, 5);
        this.inActiveSynapse = new Synapse(this.inputCell45);
        this.inActiveSynapse.setPermanenceValue(0.195f);
        this.segment.addSynapse(this.inActiveSynapse);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetInputCell()
    {
        assertEquals(inputCell11, this.segment.getInputCell(1, 1));
        assertNull(this.segment.getInputCell(5, 7)); // no Region object being
                                                     // tested

        System.out.println(this.segment);
    }


    // ----------------------------------------------------------
    /**
     * Tests getActiveState and setActiveState method on a Segment object.
     */
    public void testGetAndSetActiveState()
    {
        assertFalse(this.segment.getActiveState());
        this.segment.setActiveState(true);
        assertTrue(this.segment.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests getPreviousActiveState and setPreviousActiveStatemethod on a
     * Segment object.
     */
    public void testGetAndSetPreviousActiveState()
    {
        assertFalse(this.segment.getPreviousActiveState());
        this.segment.setPreviousActiveState(true);
        assertTrue(this.segment.getPreviousActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests getSequenceSegment and setSequenceSegment method on a Segment
     * object.
     */
    public void testGetAndSetSequenceSegment()
    {
        assertFalse(this.segment.getSequenceState());
        this.segment.setSequenceState(true);
        assertTrue(this.segment.getSequenceState());
    }


    // ----------------------------------------------------------
    /**
     * Test getSynapses() on a Segment object.
     */
    public void testGetSynapsesAndGetSynapse()
    {
        assertTrue(this.segment.getSynapses().contains(
            this.segment.getSynapse(this.inputCell11)));
        assertTrue(this.segment.getSynapses().contains(
            this.segment.getSynapse(this.inputCell32)));
        assertTrue(this.segment.getSynapses().contains(
            this.segment.getSynapse(this.inputCell45)));
    }

    // ----------------------------------------------------------
    /**
     * Test addSynapse() on a Segment object.
     */
    public void testAddSynapse()
    {
        // 2 added from setUp() constructor
        assertEquals(2, this.segment.getConnectedSynapses().size());
        AbstractCell inputCell22 = new InputCell(2, 2);
        Synapse newConnectedSynapse = new Synapse(inputCell22);
        this.segment.addSynapse(newConnectedSynapse);
        assertEquals(3, this.segment.getConnectedSynapses().size());

        assertEquals(1, this.segment.getActiveSynapses().size());
        AbstractCell inputCell33 = new InputCell(3, 3);
        inputCell33.setActiveState(true);
        Synapse newActiveSynapse = new Synapse(inputCell33);
        this.segment.addSynapse(newActiveSynapse);
        assertEquals(2, this.segment.getActiveSynapses().size());

        assertEquals(0, this.segment.getPreviousActiveSynapses().size());
        AbstractCell inputCell44 = new InputCell(4, 4);
        inputCell44.setPreviousActiveState(true);
        Synapse newPreviousActiveSynapse = new Synapse(inputCell44);
        this.segment.addSynapse(newPreviousActiveSynapse);
        assertEquals(1, this.segment.getPreviousActiveSynapses().size());
    }


    // ----------------------------------------------------------
    /**
     * Tests getConnectedSyanpses method on a Segment object.
     */
    public void testGetConnectedSynapses()
    {
        assertTrue(this.segment.getConnectedSynapses().contains(
            this.connectedSynapse));
        assertTrue(this.segment.getConnectedSynapses().contains(
            this.activeSynapse));
        assertFalse(this.segment.getConnectedSynapses().contains(
            this.inActiveSynapse));
    }


    // ----------------------------------------------------------
    /**
     * Tests getActiveSyanpses method on a Segment object.
     */
    public void testGetActiveSynapses()
    {
        assertFalse(this.segment.getActiveSynapses().contains(
            this.connectedSynapse));
        assertTrue(this.segment.getActiveSynapses()
            .contains(this.activeSynapse));
        assertFalse(this.segment.getActiveSynapses().contains(
            this.inActiveSynapse));
    }


    // ----------------------------------------------------------
    /**
     * Test getPreviousActiveSynapses on a Segment object.
     */
    public void testGetPreviousActiveSynapses()
    {
        assertFalse(this.segment.getPreviousActiveSynapses().contains(
            this.connectedSynapse));
        assertFalse(this.segment.getPreviousActiveSynapses().contains(
            this.activeSynapse));
        assertFalse(this.segment.getPreviousActiveSynapses().contains(
            this.inActiveSynapse));
    }


    // ----------------------------------------------------------
    /**
     * Tests updateProximalPermanences method on a Segment object.
     */
    public void testUpdateProximalPermanences()
    {
        assertEquals(0.300f, this.segment.getSynapse(this.inputCell32)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.300f, this.segment.getSynapse(this.inputCell11)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.195f, this.segment.getSynapse(this.inputCell45)
            .getPermanenceValue(), 0.01f);

        this.segment.updateProximalPermanences();

        assertEquals(0.295f, this.segment.getSynapse(this.inputCell32)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.315f, this.segment.getSynapse(this.inputCell11)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.190f, this.segment.getSynapse(this.inputCell45)
            .getPermanenceValue(), 0.01f);

        this.segment.updateProximalPermanences(true);

        assertEquals(0.310f, this.segment.getSynapse(this.inputCell32)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.330f, this.segment.getSynapse(this.inputCell11)
            .getPermanenceValue(), 0.01f);
        assertEquals(0.205f, this.segment.getSynapse(this.inputCell45)
            .getPermanenceValue(), 0.01f);
    }
    // TODO: testNextTimeStep() method
}
