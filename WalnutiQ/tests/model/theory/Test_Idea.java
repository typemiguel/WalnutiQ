package model.theory;

import model.MARK_II.ColumnPosition;

import java.util.HashSet;

import java.util.Set;

import model.MARK_II.Column;

public class Test_Idea extends junit.framework.TestCase {
    private Idea idea;

    public void setUp() {
	this.idea = new Idea("Zero");
    }

    public void test_unionColumns() {
	ColumnPosition column_00 = new ColumnPosition(0, 0);
	Set<ColumnPosition> columns = new HashSet<ColumnPosition>();
	columns.add(column_00);
	this.idea.unionColumnPositions(columns);
	assertEquals(1, columns.size());

	// make column_01 different than column_00 before adding it to idea
	ColumnPosition column_01 = new ColumnPosition(0, 1);
	columns.add(column_01);
	this.idea.unionColumnPositions(columns);
	assertEquals(2, columns.size());

	// make column_02 the same as column_01 before adding it to idea
	ColumnPosition column_02 = new ColumnPosition(0, 1);
	columns.add(column_02);
	this.idea.unionColumnPositions(columns);
	assertEquals(2, columns.size());
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
