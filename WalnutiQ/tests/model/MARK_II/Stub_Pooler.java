package model.MARK_II;

public abstract class Stub_Pooler {
    protected Stub_Region region;

    // TODO: how to make this code parallel

    public Stub_Region getRegion() {
	return this.region;
    }

    public void changeRegion(Stub_Region newRegion)
    {
	if (newRegion == null) {
	    throw new IllegalArgumentException(
		    "region in Pooler method changeRegion cannot be null");
	}
	this.region = newRegion;
    }
}
