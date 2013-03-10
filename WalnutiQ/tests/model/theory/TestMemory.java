package model.theory;

import java.util.Set;
import model.Column;
import java.util.HashSet;
import model.Region;

// -------------------------------------------------------------------------
/**
 *  This class tests all methods within class Memory.
 *
 *  @author Quinn Liu
 */
public class TestMemory extends junit.framework.TestCase
{
    private Region region;
    private Set<Column> columns;
    private Memory memory;

    public void setUp()
    {
        this.region = new Region(30, 40, 6, 8, 4.0f, 1, 2);
        this.columns = new HashSet<Column>();
        this.columns.add(region.getColumn(0, 0));
        this.memory = new Memory();
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetIdeas()
    {
        Idea twinIdea1 = new Idea("Date", this.columns);
        assertTrue(this.memory.addIdea(twinIdea1));
        Set<Idea> ideas = new HashSet<Idea>();
        ideas.add(twinIdea1);
        assertEquals(ideas, this.memory.getIdeas());
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testIsNewIdea()
    {
        Idea twinIdea1 = new Idea("Date", this.columns);
        this.memory.addIdea(twinIdea1);

        // if 1st and 2nd "if statements" in method terminates to true
        Memory emptyMemory = new Memory();
        assertTrue(emptyMemory.isNewIdea(twinIdea1));

        // if 1st "if statement" is true and 2nd "if statement" is false
        assertFalse(this.memory.isNewIdea(twinIdea1));

        Column column11 = this.region.getColumn(1, 1);
        this.columns.add(column11);
        Idea twinIdea2 = new Idea("Date", this.columns);
        assertFalse(this.memory.isNewIdea(twinIdea2));

        // if 1st "if statement" terminates to false
        assertEquals(0, emptyMemory.getIdeas().size());
        assertTrue(emptyMemory.isNewIdea(twinIdea1));
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testAddIdea()
    {
        Idea newIdea = new Idea("New");
        assertTrue(this.memory.addIdea(newIdea));
        assertFalse(this.memory.addIdea(newIdea));
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testRemoveIdea()
    {
        Idea twinIdea1 = new Idea("Date", this.columns);
        assertTrue(this.memory.addIdea(twinIdea1));
        assertEquals(1, this.memory.getIdeas().size());

        Idea newIdea = new Idea("New");
        assertFalse(this.memory.removeIdea(newIdea));
        assertEquals(1, this.memory.getIdeas().size());

        Idea twinIdea2 = new Idea("Date", this.columns);
        assertTrue(this.memory.removeIdea(twinIdea2));
        assertEquals(0, this.memory.getIdeas().size());
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testToString()
    {
        this.columns.add(this.region.getColumn(1, 1));
        this.columns.add(this.region.getColumn(1, 2));
        this.columns.add(this.region.getColumn(1, 3));
        this.columns.add(this.region.getColumn(1, 4));
        this.columns.add(this.region.getColumn(1, 5));
        this.columns.add(this.region.getColumn(1, 6));
        this.columns.add(this.region.getColumn(2, 1));
        this.columns.add(this.region.getColumn(2, 2));
        this.columns.add(this.region.getColumn(2, 3));
        this.columns.add(this.region.getColumn(2, 4));
        this.columns.add(this.region.getColumn(2, 5));
        this.columns.add(this.region.getColumn(2, 6));
        Idea idea = new Idea("Brain", this.columns);

        this.memory.addIdea(idea);

        System.out.println(this.memory.toString());
    }
}
