package model.theory;

import model.MARK_II.Cell;
import model.MARK_II.Synapse;

import model.MARK_II.Region;

import model.MARK_II.ColumnPosition;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 18, 2013
 */
public class IdeaTest extends junit.framework.TestCase {
    private Idea idea;

    public void setUp() {
	Region region = new Region("region", 8, 8, 1, 50, 1);
	this.idea = new Idea("Zero", region);
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

    public void test_updateActiveFrequencies() {
	ColumnPosition columnPosition_01 = new ColumnPosition(2, 3);
	Set<ColumnPosition> columnPositions_1 = new HashSet<ColumnPosition>();
	columnPositions_1.add(columnPosition_01);

	int[][] activationFrequenciesOfColumns = this.idea
		.getActivationFrequenciesOfColumns();
	assertEquals(0, activationFrequenciesOfColumns[2][3]);
	this.idea.updateActiveFrequencies(columnPositions_1);
	assertEquals(1, activationFrequenciesOfColumns[2][3]);

	// add a ColumnPosition that is out of bounds for the Region
	// this Idea was trained using.
	ColumnPosition columnPosition_69 = new ColumnPosition(6, 9);
	Set<ColumnPosition> columnPositions_2 = new HashSet<ColumnPosition>();
	columnPositions_2.add(columnPosition_69);

	try {
	    this.idea.updateActiveFrequencies(columnPositions_2);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "columnPositions in Idea class updateActiveFrequencies "
			    + "method is out of bounds for the dimensions "
			    + "of the Region this Idea was created from",
		    expected.getMessage());
	}
    }

    public void test_getTopXActiveColumnPositions() {
	int[][] afoc = this.idea
		.getActivationFrequenciesOfColumns();
	// the next 3 lines sets how often certain Columns became
	// the output of the spatial pooling algorithm
	afoc[1][1] = 1;
	afoc[2][3] = 2;
	afoc[5][7] = 3;

	Set<ColumnPosition> columnPositions = new HashSet<ColumnPosition>();

	columnPositions = this.idea.getTopXActiveColumnPositions(1);
	assertTrue(columnPositions.contains(new ColumnPosition(5, 7)));
	assertFalse(columnPositions.contains(new ColumnPosition(2, 3)));
	assertFalse(columnPositions.contains(new ColumnPosition(1, 1)));

	columnPositions = this.idea.getTopXActiveColumnPositions(2);
	assertTrue(columnPositions.contains(new ColumnPosition(5, 7)));
	assertTrue(columnPositions.contains(new ColumnPosition(2, 3)));
	assertFalse(columnPositions.contains(new ColumnPosition(1, 1)));

	columnPositions = this.idea.getTopXActiveColumnPositions(3);
	assertTrue(columnPositions.contains(new ColumnPosition(5, 7)));
	assertTrue(columnPositions.contains(new ColumnPosition(2, 3)));
	assertTrue(columnPositions.contains(new ColumnPosition(1, 1)));
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
