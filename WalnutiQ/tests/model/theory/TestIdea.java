package model.theory;

import model.util.ColumnLocation;
import model.Region;
import java.util.HashSet;
import java.util.Set;
import model.*;

// -------------------------------------------------------------------------
/**
 *  Test all methods within class Idea.
 *
 *  @author Huanqing
 *  @version Mar 10, 2013
 */
public class TestIdea
    extends junit.framework.TestCase
{
    private Region region;
    private Idea emptyIdea;
    private Idea bigIdea;


    public void setUp()
    {
        this.region = new Region(30, 40, 6, 8, 4.0f, 1, 2);

        this.emptyIdea = new Idea("Empty idea");

        Column column00 = this.region.getColumn(0, 0);
        Set<Column> activeColumns = new HashSet<Column>();
        activeColumns.add(column00);
        this.bigIdea = new Idea("Big idea", activeColumns);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetName()
    {
        assertEquals("Empty idea", this.emptyIdea.getName());
        assertEquals("Big idea", this.bigIdea.getName());
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetAndSetAttentionPercentage()
    {
        assertFalse(this.emptyIdea.setAttentionPercentage(-1));
        assertFalse(this.emptyIdea.setAttentionPercentage(101));
        assertTrue(this.bigIdea.setAttentionPercentage(50));
        assertEquals(50.0f, this.bigIdea.getAttentionPercentage(), 0.01f);
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetColumnLocations()
    {
        ColumnLocation columnLocation = new ColumnLocation();
        columnLocation.setX(0);
        columnLocation.setY(0);

        Set<ColumnLocation> setColumnLocations = new HashSet<ColumnLocation>();
        setColumnLocations.add(columnLocation);

        assertEquals(setColumnLocations, this.bigIdea.getColumnLocations());
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testConvertToColumnLocations()
    {
        Column column11 = this.region.getColumn(1, 1);
        Column column22 = this.region.getColumn(2, 2);

        Set<Column> columnSet = new HashSet<Column>();
        columnSet.add(column11);
        columnSet.add(column22);

        Set<ColumnLocation> columnLocationSet = new HashSet<ColumnLocation>();
        columnLocationSet = Idea.convertToColumnLocations(columnSet);

        assertTrue(columnLocationSet.contains(new ColumnLocation(1, 1)));
        assertTrue(columnLocationSet.contains(new ColumnLocation(2, 2)));
        assertFalse(columnLocationSet.contains(new ColumnLocation(3, 3)));
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testAddColumns()
    {
        Column column11 = this.region.getColumn(1, 1);
        Column column22 = this.region.getColumn(2, 2);

        Set<Column> columnSet1 = new HashSet<Column>();
        columnSet1.add(column11);
        columnSet1.add(column22);

        this.emptyIdea.addColumns(columnSet1);
        assertEquals(2, this.emptyIdea.getColumnLocations().size());
        assertTrue(this.emptyIdea.getColumnLocations().contains(new ColumnLocation(1, 1)));
        assertTrue(this.emptyIdea.getColumnLocations().contains(new ColumnLocation(2, 2)));
        assertFalse(this.emptyIdea.getColumnLocations().contains(new ColumnLocation(3, 3)));
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testRemoveColumn()
    {
        Column column00 = this.region.getColumn(0, 0);
        Column column11 = this.region.getColumn(1, 1);

        // method returns false within Column object to be
        // removed is not in Idea object
        assertFalse(this.bigIdea.removeColumn(column11));

        assertEquals(1, this.bigIdea.getColumnLocations().size());
        assertTrue(this.bigIdea.removeColumn(column00));
        assertEquals(0, this.bigIdea.getColumnLocations().size());
    }
}
