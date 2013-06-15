package model.MARK_II;

public class Test_ColumnPosition extends junit.framework.TestCase {
    private ColumnPosition columnPosition;

    public void setUp() {
	this.columnPosition = new ColumnPosition(6, 9);
    }

    public void test_toString() {
	System.out.println(this.columnPosition.toString());
    }
}
