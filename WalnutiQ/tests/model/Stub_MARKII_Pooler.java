package model;

public abstract class Stub_MARKII_Pooler {
    protected Stub_MARKII_Region region;

    // TODO: how to make this code parallel

    public Stub_MARKII_Region getRegion() {
	return this.region;
    }

    public void setRegion(Stub_MARKII_Region region) {
	this.region = region;
    }

    public boolean changeRegion(Stub_MARKII_Region newRegion)
    {
	if (newRegion != null) {
	    this.region = newRegion;
	    return true;
	} else {
	    return false;
	}
    }
}
