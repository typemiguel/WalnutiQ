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

	// make column2 different than column1 before
	// adding it to idea
	ColumnPosition column_01 = new ColumnPosition(0, 1);
	columns.add(column_01);
	this.idea.unionColumnPositions(columns);
	assertEquals(2, columns.size());
    }

    public void test_removeColumn() {

    }
}
