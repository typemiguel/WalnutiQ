package model.MARK_II;

import java.io.Serializable;

public abstract class Stub_AbstractCell implements Serializable {
    protected boolean isActive;

    public Stub_AbstractCell() {
	this.isActive = false;
    }

    public boolean getActiveState() {
	return this.isActive;
    }

    public void setActiveState(boolean isActive) {
	this.isActive = isActive;
    }
}
