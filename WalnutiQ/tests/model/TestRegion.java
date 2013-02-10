package model;

import model.Region;

// -------------------------------------------------------------------------
/**
 * Tests all of the methods within class Region.
 *
 * @author Quinn Liu
 * @version Oct 6, 2012
 */
public class TestRegion
    extends junit.framework.TestCase
{
    private Region oneLayerRegion;
    private Region twoLayerRegion;


    public void setUp()
    {
        this.oneLayerRegion = new Region(30, 40, 6, 8, 20.0f, 1, 1);
        this.twoLayerRegion = new Region(60, 60, 20, 20, 50.0f, 2, 4);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetInputXAxisLength()
    {
        assertEquals(30, this.oneLayerRegion.getInputXAxisLength());
        assertEquals(60, this.twoLayerRegion.getInputXAxisLength());
        System.out.println(this.oneLayerRegion);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetInputYAxisLength()
    {
        assertEquals(40, this.oneLayerRegion.getInputYAxisLength());
        assertEquals(60, this.twoLayerRegion.getInputYAxisLength());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetNumberOfInputCellsForColumnToConnectToOnXAxis()
    {
        assertEquals(
            5.0,
            this.oneLayerRegion
                .getNumberOfInputCellsForColumnToConnectToOnXAxis(),
            0.01f);
        assertEquals(
            3.0,
            this.twoLayerRegion
                .getNumberOfInputCellsForColumnToConnectToOnXAxis(),
            0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetNumberOfInputCellsForColumnToConnectToOnYAxis()
    {
        assertEquals(
            5.0,
            this.oneLayerRegion
                .getNumberOfInputCellsForColumnToConnectToOnYAxis(),
            0.01f);
        assertEquals(
            3.0,
            this.twoLayerRegion
                .getNumberOfInputCellsForColumnToConnectToOnYAxis(),
            0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetXAxisLength()
    {
        assertEquals(6, this.oneLayerRegion.getXAxisLength());
        assertEquals(20, this.twoLayerRegion.getYAxisLength());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetYAxisLength()
    {
        assertEquals(8, this.oneLayerRegion.getYAxisLength());
        assertEquals(20, this.twoLayerRegion.getYAxisLength());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetCellsPerColumn()
    {
        assertEquals(1, this.oneLayerRegion.getCellsPerColumn());
        assertEquals(2, this.twoLayerRegion.getCellsPerColumn());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetInhitbitionRadius()
    {
        assertEquals(1.0f, this.oneLayerRegion.getInhitbitionRadius());
        this.oneLayerRegion.setInhitbitionRadius(3.0f);
        assertEquals(3.0f, this.oneLayerRegion.getInhitbitionRadius());

        assertEquals(1.0f, this.twoLayerRegion.getInhitbitionRadius());
        this.twoLayerRegion.setInhitbitionRadius(5.0f);
        assertEquals(5.0f, this.twoLayerRegion.getInhitbitionRadius());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetColumns()
    {
        assertEquals(48, this.oneLayerRegion.getColumns().length);
        assertEquals(400, this.twoLayerRegion.getColumns().length);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetColumn()
    {
        assertEquals(0, this.oneLayerRegion.getColumn(0, 0).getX());
        assertEquals(3, this.oneLayerRegion.getColumn(3, 5).getX());
        // this is column at column 6 row 8
        assertEquals(5, this.oneLayerRegion.getColumn(5, 7).getX());
    }


    // ----------------------------------------------------------
    /**
     * Test method getMinimumOverlapScore method in class Region.
     * minimumOverlapScore is a private final float and thus does not change.
     */
    public void testGetAndSetMinimumOverlapScore()
    {
        assertEquals(5.0f, this.oneLayerRegion.getMinimumOverlapScore(), 0.01f);
        assertEquals(4.5f, this.twoLayerRegion.getMinimumOverlapScore(), 0.01f);
        this.oneLayerRegion.setMinimumOverlapScore(24.0f);
        assertEquals(24.0f, this.oneLayerRegion.getMinimumOverlapScore(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetDesiredLocalActivity()
    {
        assertEquals(1, this.oneLayerRegion.getDesiredLocalActivity());
        assertEquals(4, this.twoLayerRegion.getDesiredLocalActivity());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetSpatialLearning()
    {
        assertFalse(this.oneLayerRegion.getSpatialLearning());
        this.oneLayerRegion.setSpatialLearning(true);
        assertTrue(this.oneLayerRegion.getSpatialLearning());

        assertFalse(this.twoLayerRegion.getSpatialLearning());
        this.twoLayerRegion.setSpatialLearning(true);
        assertTrue(this.twoLayerRegion.getSpatialLearning());
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetTemporalLearning()
    {
        assertFalse(this.oneLayerRegion.getTemporalLearning());
        this.oneLayerRegion.setTemporalLearning(true);
        assertTrue(this.oneLayerRegion.getTemporalLearning());

        assertFalse(this.twoLayerRegion.getTemporalLearning());
        this.twoLayerRegion.setTemporalLearning(true);
        assertTrue(this.twoLayerRegion.getTemporalLearning());
    }

}
