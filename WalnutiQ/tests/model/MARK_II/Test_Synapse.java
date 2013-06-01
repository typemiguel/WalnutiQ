package model.MARK_II;

public class Test_Synapse extends junit.framework.TestCase {
    private Stub_VisionCell visionCell_1;
    private Stub_VisionCell visionCell_null;
    private Stub_Synapse<Stub_Cell> synapse_1;
    private Stub_Synapse<Stub_Cell> synapse_2;
    private Stub_Synapse<Stub_Cell> synapse_3;
    private Stub_Synapse<Stub_Cell> synapse_4;

    public void setUp() {
	this.visionCell_1 = new Stub_VisionCell();
	this.visionCell_null = null;
	this.synapse_1 = new Stub_Synapse<Stub_Cell>(
		this.visionCell_1);
	this.synapse_2 = new Stub_Synapse<Stub_Cell>(
		this.visionCell_1, 0.19);
    }

    public void testMARKII_Synapse() {
	assertEquals(Stub_Synapse.INITIAL_PERMANENCE,
		this.synapse_1.getPermanenceValue(), 0.001);
	assertEquals(0.19, this.synapse_2.getPermanenceValue(), 0.001);

	try {
	    this.synapse_3 = new Stub_Synapse<Stub_Cell>(
			this.visionCell_null, 0.21);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    // no implementation
	}

	try {
	    this.synapse_4 = new Stub_Synapse<Stub_Cell>(
			this.visionCell_1, 1.1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    // no implementation
	}

	try {
	    this.synapse_4 = new Stub_Synapse<Stub_Cell>(
			this.visionCell_1, -0.1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    // no implementation
	}
    }

    public void testIsConnected() {
	assertTrue(this.synapse_1.isConnected());
	assertFalse(this.synapse_2.isConnected());
    }

    public void testIncreasePermance() {
	this.synapse_1.increasePermanence();
	assertEquals(Stub_Synapse.INITIAL_PERMANENCE
		+ Stub_Synapse.PERMANENCE_INCREASE,
		synapse_1.getPermanenceValue(), 0.0001);

	this.synapse_1.setPermanenceValue(0.999);
	this.synapse_1.increasePermanence();
	assertEquals(1.0, this.synapse_1.getPermanenceValue(), 0.0001);
    }

    public void testDecreasePermanence() {
	this.synapse_1.decreasePermanence();
	assertEquals(Stub_Synapse.INITIAL_PERMANENCE
		- Stub_Synapse.PERMANENCE_DECREASE,
		synapse_1.getPermanenceValue(), 0.0001);

	this.synapse_1.setPermanenceValue(0.001);
	this.synapse_1.decreasePermanence();
	assertEquals(0.0, this.synapse_1.getPermanenceValue(), 0.0001);
    }

    public void testToString() {
	System.out.println(this.synapse_1.toString());
	System.out.println(this.synapse_2.toString());
    }
}
