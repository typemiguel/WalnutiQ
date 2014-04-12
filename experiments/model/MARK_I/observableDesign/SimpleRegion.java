package model.MARK_I.observableDesign;

import model.MARK_I.Column;

public class SimpleRegion {
    private Column column;

    public SimpleRegion() {
	this.column = new Column(1);
    }

    public Column getColumn() {
	return this.column;
    }
}
