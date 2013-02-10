package model.theory;

import model.Region;
import java.util.HashSet;
import java.util.Set;
import model.*;

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

    public void testGetName()
    {
        assertEquals("Empty idea", this.emptyIdea.getName());
        assertEquals("Big idea", this.bigIdea.getName());
    }

    public void testGetAndSetAttentionPercentage()
    {
        assertFalse(this.emptyIdea.setAttentionPercentage(-1));
        assertFalse(this.emptyIdea.setAttentionPercentage(101));
        assertTrue(this.bigIdea.setAttentionPercentage(50));
        assertEquals(50.0f, this.bigIdea.getAttentionPercentage(), 0.01f);
    }

    public void testGetColumns()
    {
        Set<Column> activeColumns = new HashSet<Column>();
        assertEquals(1, this.bigIdea.getColumns().size());
    }

    public void testAddColumns()
    {
        Column column11 = this.region.getColumn(1, 1);
        Column column22 = this.region.getColumn(2, 2);
        Column column33 = this.region.getColumn(3, 3);
        Column column44 = this.region.getColumn(4, 4);

        Set<Column> columnSet1 = new HashSet<Column>();
        columnSet1.add(column11);
        columnSet1.add(column22);

        Set<Column> columnSet2 = new HashSet<Column>();
        columnSet1.add(column11);
        columnSet1.add(column22);
        columnSet2.add(column33);
        columnSet2.add(column44);

        this.emptyIdea.addColumns(columnSet1);
        assertEquals(columnSet1, this.emptyIdea.getColumns());
        assertFalse(this.emptyIdea.addColumns(columnSet1));
        assertTrue(this.emptyIdea.addColumns(columnSet2));
        assertEquals(4, this.emptyIdea.getColumns().size());
    }

    public void testRemoveColumn()
    {
        Column column00 = this.region.getColumn(0, 0);
        Column column11 = this.region.getColumn(1, 1);

        // method returns false within Column object to be
        // removed is not in Idea object
        assertFalse(this.bigIdea.removeColumn(column11));

        assertEquals(1, this.bigIdea.getColumns().size());
        assertTrue(this.bigIdea.removeColumn(column00));
        assertEquals(0, this.bigIdea.getColumns().size());
    }
}
