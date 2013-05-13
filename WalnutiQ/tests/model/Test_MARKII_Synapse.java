package model;

public class Test_MARKII_Synapse extends junit.framework.TestCase {
    private Stub_MARKII_VisionCell visionCell_1;
    private Stub_MARKII_Neuron neuron_1;
    private Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> synapse_1;

    public void setUp() {
	this.visionCell_1 = new Stub_MARKII_VisionCell();
	this.synapse_1 = new Stub_MARKII_Synapse<Stub_MARKII_AbstractCell>(
		this.visionCell_1);

    }

    public void testMARKII_Synapse() {

    }

    public void testIsConnected() {

    }

    public void testIncreasePermance() {

    }

    public void testDecreasePermanence() {

    }

    public void testToString() {

    }
}
