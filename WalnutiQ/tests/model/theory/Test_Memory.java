package model.theory;

import model.MARK_II.ColumnPosition;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 23, 2013
 */
public class Test_Memory extends junit.framework.TestCase {
    private Memory memory;

    public void setUp() {
	this.memory = new Memory();
    }

    public void test_getIdea() {
	Idea idea_1 = new Idea("love");
	Idea idea_2 = new Idea("hate");

	this.memory.addNewIdea(idea_1);
	this.memory.addNewIdea(idea_2);
	assertEquals(idea_1, this.memory.getIdea("love"));
    }

    public void test_addNewIdea() {
	Idea idea_1 = new Idea("date");
	Idea idea_2 = new Idea("date");

	Idea idea_3 = new Idea("date");
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
