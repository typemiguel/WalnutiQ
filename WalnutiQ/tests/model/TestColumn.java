package model;

import model.Column;
import model.InputCell;
import model.Region;
import model.Segment;
import model.Synapse;
import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Tests all of the methods within class Column.
 *
 * @author Quinn Liu
 * @version Oct 26, 2012
 */
public class TestColumn
    extends junit.framework.TestCase
{
    private Region    twoLayerRegion;
    private Column    column00;         // 00 suffix means position within
                                         // imaginary Region 2D array of Column
                                         // objects
    private Column    column01;
    private Column    column33;
    private Column    column57;

    private Segment   proximalSegment00;
    private Segment   proximalSegment01;
    private Segment   proximalSegment33;
    private Segment   proximalSegment57;

    private InputCell inputCell32;
    private InputCell inputCell11;
    private InputCell inputCell44;

    private Synapse   connectedSynapse;
    private Synapse   activeSynapse;
    private Synapse   inActiveSynapse;


    /**
     * Four Column objects are created each with their own proximalSegment. Each
     * proximalSegment is initialized with the same three synapses. A connected,
     * active, and inActive synapse object.
     */
    public void setUp()
    {
        this.twoLayerRegion = new Region(30, 40, 6, 8, 4.0f, 2, 1);
        this.column00 = this.twoLayerRegion.getColumn(0, 0);
        this.column01 = this.twoLayerRegion.getColumn(0, 1);
        this.column33 = this.twoLayerRegion.getColumn(3, 3);
        this.column57 = this.twoLayerRegion.getColumn(5, 7);

        this.proximalSegment00 = this.column00.getProximalSegment();
        this.proximalSegment01 = this.column01.getProximalSegment();
        this.proximalSegment33 = this.column33.getProximalSegment();
        this.proximalSegment57 = this.column57.getProximalSegment();

        this.inputCell32 = this.proximalSegment00.getInputCell(3, 2);
        this.inputCell11 = this.proximalSegment00.getInputCell(1, 1);
        this.inputCell11.setActiveState(true);
        this.inputCell44 = this.proximalSegment00.getInputCell(4, 4);

        this.connectedSynapse =
            this.proximalSegment00.getSynapse(this.inputCell32);
        this.activeSynapse =
            this.proximalSegment00.getSynapse(this.inputCell11);
        this.inActiveSynapse =
            this.proximalSegment00.getSynapse(this.inputCell44);
        this.inActiveSynapse.setPermanenceValue(0.195f);
    }


    // ----------------------------------------------------------
    /**
     * Tests the Column class constructor method.
     */
    public void testColumn()
    {
        assertEquals(25, this.column00.getProximalSegment().getSynapses()
            .size());
        assertEquals(24, this.column00.getProximalSegment()
            .getConnectedSynapses().size());
        assertEquals(1, this.column00.getProximalSegment().getActiveSynapses()
            .size());

        // make sure there is a synapse for every cell in a 0-4 * 0-4 grid of
        // inputCells.
        // System.out.println(this.column00.getProximalSegment().getConnectedSynapses()
        //    .toString());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getRegion method within class Column.
     */
    public void testGetRegion()
    {
        assertEquals(this.twoLayerRegion, this.column00.getRegion());
        assertEquals(this.twoLayerRegion, this.column01.getRegion());
        assertEquals(this.twoLayerRegion, this.column33.getRegion());
        assertEquals(this.twoLayerRegion, this.column57.getRegion());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getInputX method within class Column.
     */
    public void testGetInputX()
    {
        assertEquals(0, this.column00.getInputX());
        assertEquals(0, this.column01.getInputX());
        assertEquals(15, this.column33.getInputX());
        assertEquals(25, this.column57.getInputX());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getInputY method within class Column.
     */
    public void testGetInputY()
    {
        assertEquals(0, this.column00.getInputY());
        assertEquals(5, this.column01.getInputY());
        assertEquals(15, this.column33.getInputY());
        assertEquals(35, this.column57.getInputY());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getX method within class Column.
     */
    public void testGetX()
    {
        assertEquals(0, this.column00.getX());
        assertEquals(0, this.column01.getX());
        assertEquals(3, this.column33.getX());
        assertEquals(5, this.column57.getX());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getY method within class Column.
     */
    public void testGetY()
    {
        assertEquals(0, this.column00.getY());
        assertEquals(1, this.column01.getY());
        assertEquals(3, this.column33.getY());
        assertEquals(7, this.column57.getY());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getActiveState method within class Column.
     */
    public void testGetActiveState()
    {
        assertFalse(this.column00.getActiveState());
        assertFalse(this.column01.getActiveState());
        assertFalse(this.column33.getActiveState());
        assertFalse(this.column57.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setActiveState method within class Column.
     */
    public void testSetActiveState()
    {
        this.column00.setActiveState(true);
        assertTrue(this.column00.getActiveState());
        this.column01.setActiveState(true);
        assertTrue(this.column00.getActiveState());
        this.column33.setActiveState(true);
        assertTrue(this.column00.getActiveState());
        this.column57.setActiveState(true);
        assertTrue(this.column00.getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getGetOverlapScore method within class Column.
     */
    public void testGetOverlapScore()
    {
        assertEquals(0, this.column00.getOverlapScore());
        assertEquals(0, this.column01.getOverlapScore());
        assertEquals(0, this.column33.getOverlapScore());
        assertEquals(0, this.column57.getOverlapScore());
    }


    // ----------------------------------------------------------
    /**
     * Tests the setGetOverlapScore method within class Column.
     */
    public void testSetOverlapScore()
    {
        this.column00.setOverlapScore(-10);
        this.column01.setOverlapScore(10);
        this.column33.setOverlapScore(25);
        this.column57.setOverlapScore(30);
        assertEquals(0, this.column00.getOverlapScore());
        assertEquals(10, this.column01.getOverlapScore());
        assertEquals(25, this.column33.getOverlapScore());
        assertEquals(30, this.column57.getOverlapScore());
    }


    // ----------------------------------------------------------
    /**
     * Tests the computeOverlapScore method within class Column.
     */
    public void testComputeOverlapScore()
    {
        // ((30*40)/(6*8)) * 1.0f = region minimumOverlapScore
        assertEquals(1.0f, this.twoLayerRegion.getMinimumOverlapScore(), 0.01f);
        assertEquals(1, this.column00.getProximalSegment().getActiveSynapses()
            .size());
        this.column00.computeOverlapScore();
        assertEquals(1, this.column00.getOverlapScore());

        this.column00.setOverlapScore(26);
        assertEquals(26, this.column00.getOverlapScore());
    }


    // ----------------------------------------------------------
    /**
     * Tests the getNeighborColumns and updateNeighborColumns method within
     * class Column on different inhibitionRadiuses and different Column
     * objects.
     */
    public void testUpdateNeighborColumns()
    {
        // ----------------testing on this.column00--------------------
        this.twoLayerRegion.setInhitbitionRadius(-1);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns = this.column00.getNeighborColumns();
        assertEquals(0, neighborColumns.size());

        this.twoLayerRegion.setInhitbitionRadius(0);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns1 = this.column00.getNeighborColumns();
        assertEquals(0, neighborColumns1.size());

        this.twoLayerRegion.setInhitbitionRadius(1);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns2 = this.column00.getNeighborColumns();
        assertEquals(3, neighborColumns2.size());

        this.twoLayerRegion.setInhitbitionRadius(3);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns3 = this.column00.getNeighborColumns();
        assertEquals(15, neighborColumns3.size());

        this.twoLayerRegion.setInhitbitionRadius(5);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns4 = this.column00.getNeighborColumns();
        assertEquals(35, neighborColumns4.size());

        // -------------------testing on this.column33----------------------
        this.twoLayerRegion.setInhitbitionRadius(-1);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns5 = this.column33.getNeighborColumns();
        assertEquals(0, neighborColumns5.size());

        this.twoLayerRegion.setInhitbitionRadius(0);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns6 = this.column33.getNeighborColumns();
        assertEquals(0, neighborColumns6.size());

        this.twoLayerRegion.setInhitbitionRadius(1);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns7 = this.column33.getNeighborColumns();
        assertEquals(8, neighborColumns7.size());

        this.twoLayerRegion.setInhitbitionRadius(3);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns8 = this.column33.getNeighborColumns();
        assertEquals(41, neighborColumns8.size());

        this.twoLayerRegion.setInhitbitionRadius(5);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns9 = this.column33.getNeighborColumns();
        assertEquals(47, neighborColumns9.size());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetNeighborColumns()
    {
        this.twoLayerRegion.setInhitbitionRadius(1);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns00 = new ArrayList<Column>();
        neighborColumns00.add(this.twoLayerRegion.getColumn(0, 1));
        neighborColumns00.add(this.twoLayerRegion.getColumn(1, 0));
        neighborColumns00.add(this.twoLayerRegion.getColumn(1, 1));
        assertEquals(neighborColumns00, this.column00.getNeighborColumns());

        this.twoLayerRegion.setInhitbitionRadius(2);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns33 = new ArrayList<Column>();
        for (int x = 1; x <= 5; x++)
        {
            for (int y = 1; y <= 5; y++)
            {
                if (x == 3 && y == 3)
                {
                    // Do not add column
                }
                else
                {
                    neighborColumns33.add(this.twoLayerRegion.getColumn(x, y));
                }
            }
        }
        assertEquals(neighborColumns33, this.column33.getNeighborColumns());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testSetNeighborColumns()
    {
        this.twoLayerRegion.setInhitbitionRadius(1);
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns00 = new ArrayList<Column>();
        neighborColumns00.add(this.twoLayerRegion.getColumn(0, 1));
        neighborColumns00.add(this.twoLayerRegion.getColumn(1, 0));
        neighborColumns00.add(this.twoLayerRegion.getColumn(1, 1));

        this.column00.setNeighborColumns(neighborColumns00);
        assertEquals(neighborColumns00, this.column00.getNeighborColumns());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetBoostValue()
    {
        this.column00.setBoostValue(-2.0f);
        this.column01.setBoostValue(1.0f);
        this.column33.setBoostValue(5.0f);
        this.column57.setBoostValue(10.0f);
        assertEquals(1.0f, this.column00.getBoostValue(), 0.01f);
        assertEquals(1.0f, this.column01.getBoostValue(), 0.01f);
        assertEquals(5.0f, this.column33.getBoostValue(), 0.01f);
        assertEquals(10.0f, this.column57.getBoostValue(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetActiveDutyCycle()
    {
        this.column00.setActiveDutyCycle(-2.0f);
        this.column01.setActiveDutyCycle(0.5f);
        this.column33.setActiveDutyCycle(0.505f);
        this.column57.setActiveDutyCycle(10.0f);
        assertEquals(1.0f, this.column00.getActiveDutyCycle(), 0.001f);
        assertEquals(0.5f, this.column01.getActiveDutyCycle(), 0.001f);
        assertEquals(0.505f, this.column33.getActiveDutyCycle(), 0.0001f);
        assertEquals(1.0f, this.column57.getActiveDutyCycle(), 0.001f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetOverlapDutyCycle()
    {
        // overlapDutyCycle value >= 0 || <= 1
        this.column00.setOverlapDutyCycle(-2.0f);
        this.column01.setOverlapDutyCycle(0.5f);
        this.column33.setOverlapDutyCycle(0.505f);
        this.column57.setOverlapDutyCycle(3.05f);
        assertEquals(1.0f, this.column00.getOverlapDutyCycle(), 0.01f);
        assertEquals(0.5f, this.column01.getOverlapDutyCycle(), 0.001f);
        assertEquals(0.505f, this.column33.getOverlapDutyCycle(), 0.0001f);
        assertEquals(1.0f, this.column57.getOverlapDutyCycle(), 0.001f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetLayer()
    {
        // 0 is first layer, 3 is 4th layer
        // TODO: implement for temporal pooler
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetProximalSegment()
    {
        assertEquals(this.proximalSegment00, this.column00.getProximalSegment());
        assertEquals(this.proximalSegment01, this.column01.getProximalSegment());
        assertEquals(this.proximalSegment33, this.column33.getProximalSegment());
        assertEquals(this.proximalSegment57, this.column57.getProximalSegment());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testIncreasePermanences()
    {
        this.connectedSynapse.setPermanenceValue(0.3f);
        assertEquals(.30f, this.proximalSegment00.getSynapse(this.inputCell32)
            .getPermanenceValue(), 0.0001f);
        this.column00.increasePermanences(5);
        assertEquals(.375f, this.proximalSegment00.getSynapse(this.inputCell32)
            .getPermanenceValue(), 0.0001f);

        this.activeSynapse.setPermanenceValue(0.3f);
        assertEquals(.3f, this.proximalSegment00.getSynapse(this.inputCell11)
            .getPermanenceValue(), 0.0001f);
        // bring connectedSynpase and activeSynapse's permanenceValues over 1.0f
        this.column00.increasePermanences(47);
        assertEquals(1.0f, this.proximalSegment00.getSynapse(this.inputCell11)
            .getPermanenceValue(), 0.0001f);

        this.inActiveSynapse.setPermanenceValue(0.195f);
        assertEquals(.195f, this.proximalSegment00.getSynapse(this.inputCell44)
            .getPermanenceValue(), 0.0001f);
        this.column00.increasePermanences(-2);
        assertEquals(.195f, this.proximalSegment00.getSynapse(this.inputCell44)
            .getPermanenceValue(), 0.0001f);

        // Not necessary to test on other column's proximalSegments
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testUpdatePermanences()
    {
        this.column00.updatePermanences();
        assertEquals(0.295f, this.proximalSegment00
            .getSynapse(this.inputCell32).getPermanenceValue(), 0.0001f);
        assertEquals(0.315f, this.proximalSegment00
            .getSynapse(this.inputCell11).getPermanenceValue(), 0.0001f);
        assertEquals(0.190f, this.proximalSegment00
            .getSynapse(this.inputCell44).getPermanenceValue(), 0.0001f);

        // not necessary to test other columns
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetConnectedSynapses()
    {
        assertEquals(24, this.column00.getConnectedSynapses().size());
        assertEquals(25, this.column01.getConnectedSynapses().size());
        assertEquals(25, this.column33.getConnectedSynapses().size());
        assertEquals(25, this.column57.getConnectedSynapses().size());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testAddConnectedSynapse()
    {
        Synapse connectedSynapse1 =
            this.proximalSegment00.getSynapse(this.inputCell32);
        this.column00.addConnectedSynapse(connectedSynapse1);
        assertEquals(
            connectedSynapse1,
            this.proximalSegment00.getSynapse(this.inputCell32));
        // Do not need to be tested on other global Column objects.
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testMaximumActiveDutyCycle()
    {
        this.column00.updateNeighborColumns();
        List<Column> neighborColumns = this.column00.getNeighborColumns();
        assertEquals(1.0f, this.column00.maximumActiveDutyCycle(neighborColumns));

        this.column00.setActiveDutyCycle(0.4f);
        this.column01.setActiveDutyCycle(0.5f);
        this.column57.setActiveDutyCycle(0.505f);
        List<Column> columns = new ArrayList<Column>();
        columns.add(column00);
        columns.add(column01);
        columns.add(column57);

        // usually used on neighborColumns
        assertEquals(
            0.505f,
            this.column33.maximumActiveDutyCycle(columns),
            0.0001);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testUpdateActiveDutyCycle()
    {
        this.column00.updateActiveDutyCycle();
        assertEquals(0.995f, this.column00.getActiveDutyCycle(), 0.0001f);

        this.column01.setActiveState(true);
        this.column01.updateActiveDutyCycle();
        assertEquals(1.0f, this.column01.getActiveDutyCycle(), 0.0001f);

        // do not need to be tested on other global Column objects.
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testUpdateOverlapDutyCycle()
    {
         // overlapDutyCycle automatically set to 1.0f
         this.column00.updateOverlapDutyCycle(); assertEquals(0.995f,
         this.column00.getOverlapDutyCycle(), 0.0001f);
         this.column01.setOverlapDutyCycle(.5f);
         this.column01.updateOverlapDutyCycle(); assertEquals(0.4975f,
         this.column01.getOverlapDutyCycle(), 0.0001f);
         this.column33.setOverlapDutyCycle(0.6f);
         this.column33.updateOverlapDutyCycle(); assertEquals(0.597f,
         this.column33.getOverlapDutyCycle(), 0.0001f);
         this.column57.setOverlapDutyCycle(2.0f);
         this.column57.updateOverlapDutyCycle(); assertEquals(0.995,
         this.column57.getOverlapDutyCycle(), 0.001f);

    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testBoostFunction()
    {
        // column00's initial activeDutyCycle is 1.0f
        float minimumDutyCycle1 = -0.5f;

        Exception thrown = null;
        try
        {
            this.column00.boostFunction(minimumDutyCycle1);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalStateException);
        assertEquals(
            "minimumDutyCycle cannot be less or equal to zero",
            thrown.getMessage());

        this.column01.setActiveDutyCycle(0.9f);
        float minimumDutyCycle2 = 0.5f;
        assertEquals(
            0.9f,
            this.column01.boostFunction(minimumDutyCycle2),
            0.001f);

        this.column33.setActiveDutyCycle(0.75f);
        float minimumDutyCycle3 = 0.8f;
        assertEquals(
            1.066f,
            this.column33.boostFunction(minimumDutyCycle3),
            0.001f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testPerformBoosting()
    {
        this.column00.updateNeighborColumns();

        // change activeDutyCycle of neighboring columns to column00
        this.column00.getNeighborColumns().get(0).setActiveDutyCycle(0.9f);
        this.column00.getNeighborColumns().get(1).setActiveDutyCycle(0.8f);
        this.column00.getNeighborColumns().get(2).setActiveDutyCycle(0.7f);

        this.column00.performBoosting();
        // Run through example:
        // minimumDutyCycle = 0.01 * .9(maximumActiveDutyCycle) = 0.009
        // newActiveDutyCycle = 0.995 * 1.0 = 0.995
        // this.boostValue = 0.995
        assertEquals(0.995f, this.column00.getBoostValue(), 0.00001f);
        this.column00.performBoosting();
        assertEquals(0.99002f, this.column00.getBoostValue(), 0.00001f);
        this.column00.setActiveState(true);
        this.column00.performBoosting();
        assertEquals(0.99007f, this.column00.getBoostValue(), 0.00001f);

        this.column00.getNeighborColumns().get(0).setOverlapDutyCycle(0.9f);
        this.column00.getNeighborColumns().get(1).setOverlapDutyCycle(0.8f);
        this.column00.getNeighborColumns().get(2).setOverlapDutyCycle(0.7f);

        this.column00.performBoosting();
        // Run through example:
        // minimumDutyCycle = 0.01 * .9(maximumActiveDutyCycle) = 0.009
        // this.overlapDutyCycle = 0.9801
        assertEquals(0.9801, this.column00.getOverlapDutyCycle(), 0.0001f);

        this.column00.setOverlapScore(26);
        this.column00.performBoosting();
        assertEquals(0.9802f, this.column00.getOverlapDutyCycle(), 0.0001f);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testNextTimeStep()
    {
        //TODO: test
    }
}
