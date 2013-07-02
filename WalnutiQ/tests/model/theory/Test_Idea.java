package model.theory;

import model.MARK_II.ColumnPosition;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 18, 2013
 */
public class Test_Idea extends junit.framework.TestCase {
    private Idea idea;

    public void setUp() {
	this.idea = new Idea("Zero");
    }

    public void test_unionColumns() {
	ColumnPosition column_00 = new ColumnPosition(0, 0);
	Set<ColumnPosition> columns_1 = new HashSet<ColumnPosition>();
	columns_1.add(column_00);
	this.idea.unionColumnPositions(columns_1);
	assertEquals(1, this.idea.getColumnPositions().size());

	// make column_01 different than column_00 before adding it to idea
	Set<ColumnPosition> columns_2 = new HashSet<ColumnPosition>();
	columns_2.add(column_00);
	ColumnPosition column_01 = new ColumnPosition(0, 1);
	columns_2.add(column_01);
	this.idea.unionColumnPositions(columns_2);
	assertEquals(2, this.idea.getColumnPositions().size());

	// make column_02 the same as column_01 before adding it to idea
	Set<ColumnPosition> columns_3 = new HashSet<ColumnPosition>();
	columns_3.add(column_01);
	ColumnPosition column_02 = new ColumnPosition(0, 1);
	columns_3.add(column_02);
	this.idea.unionColumnPositions(columns_3);
	assertEquals(2, this.idea.getColumnPositions().size());
    }

    public void test_HashSetRemoveColumnPosition() {
	ColumnPosition column_03 = new ColumnPosition(0, 3);

	Set<ColumnPosition> columns = new HashSet<ColumnPosition>();
	columns.add(column_03);

	this.idea.getColumnPositions().add(column_03);
	assertEquals(1, this.idea.getColumnPositions().size());

	assertTrue(this.idea.getColumnPositions().remove(column_03));
	assertEquals(0, this.idea.getColumnPositions().size());
    }
}
