package model;

public abstract class Pooler {
    protected MARKII_Region region;

    // TODO: how to make this code parallel

    public boolean changeRegion(MARKII_Region newRegion)
    {
	if (newRegion != null) {
	    this.region = newRegion;
	    return true;
	} else {
	    return false;
	}
    }
}
