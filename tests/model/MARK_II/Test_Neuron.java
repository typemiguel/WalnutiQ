package model.MARK_II;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 21, 2013
 */
public class Test_Neuron extends junit.framework.TestCase {
    private Neuron neuron;

    public void setUp() {
	this.neuron = new Neuron();
    }

    public void test_getBestPreviousActiveSegment() {
	// previousActiveSegments size = 0
	assertNull(this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 1 with no sequence Segments
	Segment distalSegment_1 = new Segment();
	distalSegment_1.setPreviousActiveState(true);
	this.neuron.addDistalSegment(distalSegment_1);
	assertEquals(distalSegment_1,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 2 with 0 sequence Segment
	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setPreviousActiveState(true);
	Synapse<Cell> previousActiveSynapse_1 = new Synapse<Cell>(activeVisionCell_1, 0.3, 0, 0);

	Segment distalSegment_2 = new Segment();
	distalSegment_2.addSynapse(previousActiveSynapse_1);
	distalSegment_2.setPreviousActiveState(true);
	this.neuron.addDistalSegment(distalSegment_2);
	assertEquals(distalSegment_2,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 3 with 1 sequence Segment
	Segment distalSegment_3 = new Segment();
	distalSegment_3.setPreviousActiveState(true);
	distalSegment_3.setSequenceState(true);
	this.neuron.addDistalSegment(distalSegment_3);
	assertEquals(distalSegment_3,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 3 with
	// 2 sequence Segments
	Segment distalSegment_4 = new Segment();
	distalSegment_4.addSynapse(previousActiveSynapse_1);
	distalSegment_4.setPreviousActiveState(true);
	distalSegment_4.setSequenceState(true);
	this.neuron.addDistalSegment(distalSegment_4);
	assertEquals(distalSegment_4,
		this.neuron.getBestPreviousActiveSegment());
    }
}
