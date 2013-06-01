package model.MARK_II;

import java.io.Serializable;

public abstract class Stub_Cell implements Serializable {
    protected boolean isActive;

    public Stub_Cell() {
	this.isActive = false;
    }

    public boolean getActiveState() {
	return this.isActive;
    }

    public void setActiveState(boolean isActive) {
	this.isActive = isActive;
    }
}
