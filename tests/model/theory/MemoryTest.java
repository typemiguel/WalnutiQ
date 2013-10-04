package model.theory;

import model.MARK_II.Region;
import model.MARK_II.ColumnPosition;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 23, 2013
 */
public class MemoryTest extends junit.framework.TestCase {
    private Memory memory;
    private Region region;

    public void setUp() {
	this.memory = new Memory();
	this.region = new Region("region", 8, 8, 1, 50, 1);
    }

    public void test_getIdea() {
	Idea idea_1 = new Idea("love", this.region);
	Idea idea_2 = new Idea("hate", this.region);

	this.memory.addNewIdea(idea_1);
	this.memory.addNewIdea(idea_2);
	assertEquals(idea_1, this.memory.getIdea("love"));
    }

    public void test_addNewIdea() {
	Idea idea_1 = new Idea("date", this.region);
	Idea idea_2 = new Idea("date", this.region);

	Idea idea_3 = new Idea("date", this.region);
	ColumnPosition column_00 = new ColumnPosition(0, 0);
	Set<ColumnPosition> columnPositions = new HashSet<ColumnPosition>();
	columnPositions.add(column_00);
	idea_3.unionColumnPositions(columnPositions);

	this.memory.addNewIdea(idea_1);
	this.memory.addNewIdea(idea_2);
	assertEquals(1, this.memory.getIdeas().size());

	this.memory.addNewIdea(idea_3);
	assertEquals(2, this.memory.getIdeas().size());
    }
}
