package model.theory;

import java.util.HashSet;

import java.util.Set;

import model.MARK_II.Column;

public class Test_Idea extends junit.framework.TestCase {
    private Idea idea;

    public void setUp() {
	this.idea = new Idea("Zero");
    }

    public void test_unionColumns() {
	Column column1 = new Column(2);
	Set<Column> columns = new HashSet<Column>();
	columns.add(column1);
	this.idea.unionColumns(columns);
	assertEquals(1, columns.size());

	// make column2 different than column1 before
	// adding it to idea
	Column column2 = new Column(3);
	columns.add(column2);
	this.idea.unionColumns(columns);
	assertEquals(2, columns.size());
    }

    public void test_removeColumn() {

    }
}
