package model.MARK_II;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 18, 2013
 */
public class Test_ColumnPosition extends junit.framework.TestCase {
    private ColumnPosition columnPosition69;
    private ColumnPosition columnPosition_69;
    private ColumnPosition columnPosition96;

    public void setUp() {
	this.columnPosition69 = new ColumnPosition(6, 9);
	this.columnPosition_69 = new ColumnPosition(6, 9);
	this.columnPosition96 = new ColumnPosition(9, 6);
    }

    public void test_equals() {
	assertTrue(this.columnPosition69.equals(this.columnPosition_69));
	assertFalse(this.columnPosition69.equals(this.columnPosition96));
    }

    public void test_toString() {
	System.out.println(this.columnPosition69.toString());
    }
}
