package model;

import model.AbstractCell;
import model.InputCell;
import model.Synapse;

// -------------------------------------------------------------------------
/**
 * Tests all methods within class Synapse.
 *
 * @author Quinn Liu
 * @version Oct 12, 2012
 */
public class TestSynapse
    extends junit.framework.TestCase
{
    private AbstractCell inputCell32;
    private AbstractCell inputCell45;
    private Synapse   synapse;


    /**
     * Instantiates a new Synapse object to be tested.
     */
    public void setUp()
    {
        this.inputCell32 = new InputCell(3, 2);
        this.inputCell45 = new InputCell(4, 5);
        this.synapse = new Synapse(this.inputCell32);
    }


    // ----------------------------------------------------------
    /**
     * Tests getInputSource method on a Synapse object.
     */
    public void testGetInputCell()
    {
        assertFalse(this.synapse.getAbstractCell().getActiveState());
        this.inputCell32.setActiveState(true);
        assertTrue(this.synapse.getAbstractCell().getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests setInputSource method on a Synapse object. This is done by setting
     * a synapse to in-active inputCell32 and asserting the activeState of the
     * synapse is false. Then setting active inputCell45 to the synapse
     * originally connected to inputCell32 and asserting that the activeState of
     * the synapse is now true.
     */
    public void testSetInputCell()
    {
        this.inputCell32.setActiveState(false);
        assertFalse(this.synapse.getActiveState());

        this.inputCell45.setActiveState(true);
        this.synapse.setAbstractCell(this.inputCell45);
        assertTrue(this.synapse.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests initial getPermanenceValue method on a Synapse object.
     */
    public void testGetPermanenceValue()
    {
        assertEquals(0.3f, this.synapse.getPermanenceValue());
    }


    // ----------------------------------------------------------
    /**
     * Tests initial setPermanenceValue method on a Synapse object.
     */
    public void testSetPermanenceValue()
    {
        Exception thrown = null;
        try
        {
            this.synapse.setPermanenceValue(-0.5f);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalStateException);
        assertEquals(
            "permanenceValue cannot be <= 0.0f",
            thrown.getMessage());

        thrown = null;
        try
        {
            this.synapse.setPermanenceValue(1.5f);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalStateException);
        assertEquals(
            "permanenceValue cannot be > 1.0f",
            thrown.getMessage());

        this.synapse.setPermanenceValue(0.5f);
        assertEquals(0.5f, this.synapse.getPermanenceValue(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Tests the increasePermance method on a Synapse object.
     */
    public void testIncreasePermance()
    {
        this.synapse.increasePermance();
        assertEquals(0.315f, this.synapse.getPermanenceValue());

        this.synapse.setPermanenceValue(0.99f);
        this.synapse.increasePermance();
        assertEquals(1.0f, this.synapse.getPermanenceValue(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Tests the decreasePermance method on a Synapse object.
     */
    public void testDecreasePermance()
    {
        this.synapse.decreasePermance();
        assertEquals(0.29500002f, this.synapse.getPermanenceValue());

        this.synapse.setPermanenceValue(0.004f);
        this.synapse.decreasePermance();
        assertEquals(0.0f, this.synapse.getPermanenceValue(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getConnectedState of this synapse object.
     */
    public void testGetConnectedState()
    {
        assertTrue(Synapse.INITIAL_PERMANENCE > Synapse.MINIMAL_CONNECTED_PERMANCE);
        assertTrue(this.synapse.getConnectedState());

        this.synapse
            .setPermanenceValue(Synapse.MINIMAL_CONNECTED_PERMANCE - 0.05f);
        assertFalse(this.synapse.getConnectedState());
    }


    // ----------------------------------------------------------
    /**
     * Tests getActiveState method on a Synapse object.
     */
    public void testGetActiveState()
    {
        assertFalse(this.synapse.getActiveState());
        this.inputCell32.setActiveState(true);
        assertTrue(this.synapse.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests getPreviousActiveState method on a Synapse object.
     */
    public void testGetPreviousActiveState()
    {
        assertFalse(this.synapse.getPreviousActiveState());
        this.inputCell32.setPreviousActiveState(true);
        assertTrue(this.synapse.getPreviousActiveState());
    }
}
