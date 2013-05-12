package model;

public abstract class Stub_MARKII_Pooler {
    protected Stub_MARKII_Region region;

    // TODO: how to make this code parallel

    public Stub_MARKII_Region getRegion() {
	return this.region;
    }

    public boolean setRegion(Stub_MARKII_Region region) {
	if (region != null) {
	    this.region = region;
	    return true;
	} else {
	    return false;
	}
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
