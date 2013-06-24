package model.theory;

import java.util.HashSet;
import java.util.Set;
import model.MARK_II.ColumnPosition;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 23, 2013
 */
public class Test_MemoryClassifier extends junit.framework.TestCase {
    private MemoryClassifier memoryClassifier;

    public void setUp() {
	ColumnPosition column_00 = new ColumnPosition(0, 0);
	ColumnPosition column_01 = new ColumnPosition(0, 1);

	Set<ColumnPosition> columnPositions = new HashSet<ColumnPosition>();
	columnPositions.add(column_00);
	columnPositions.add(column_01);

	Idea idea_1 = new Idea("revenge");
	idea_1.unionColumnPositions(columnPositions);

	Idea idea_2 = new Idea("forgiveness");
	ColumnPosition column_02 = new ColumnPosition(0, 2);
	columnPositions.add(column_02);
	idea_2.unionColumnPositions(columnPositions);

	Memory memory = new Memory();
	memory.addNewIdea(idea_1);
	memory.addNewIdea(idea_2);
	this.memoryClassifier = new MemoryClassifier(memory);
    }

    public void test_updateIdeas() {
	ColumnPosition column_00 = new ColumnPosition(0, 0);
	Set<ColumnPosition> regionOutput = new HashSet<ColumnPosition>();
	regionOutput.add(column_00);

	this.memoryClassifier.updateIdeas(regionOutput);
	assertEquals(50, this.memoryClassifier.getMemory().getIdea("revenge")
		.getAttentionPercentage(), 0.1);
	assertEquals(33.3,
		this.memoryClassifier.getMemory().getIdea("forgiveness")
			.getAttentionPercentage(), 0.1);
	//System.out.println(this.memoryClassifier.toString());
    }
}
