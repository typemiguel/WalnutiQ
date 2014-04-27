package model.MARK_II;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 26, 2014
 */
public class NeuronTest extends junit.framework.TestCase {
    private Neuron neuron;

    private DistalSegment distalSegmentWith1ActiveSynapse;
    private DistalSegment distalSegmentWith2ActiveSynapses;

    private DistalSegment currentlyActiveDistalSegment;
    private DistalSegment previousActiveDistalSegment;

    public void setUp() {
	this.neuron = new Neuron();

	this.distalSegmentWith1ActiveSynapse = new DistalSegment();
	this.distalSegmentWith2ActiveSynapses = new DistalSegment();

	// 2 currently active Synapse
	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setActiveState(true);

	VisionCell activeVisionCell_2 = new VisionCell();
	activeVisionCell_2.setActiveState(true);

	this.distalSegmentWith1ActiveSynapse.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 0));
	this.distalSegmentWith2ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 0));
	this.distalSegmentWith2ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 1));

	this.currentlyActiveDistalSegment = new DistalSegment();
	this.previousActiveDistalSegment = new DistalSegment();
	this.previousActiveDistalSegment.setPreviousActiveState(true);
    }

    public void test_nextTimeStep() {
	this.neuron.setActiveState(true);
	this.neuron.setPredictingState(true);

	assertFalse(this.neuron.getPreviousActiveState());
	assertFalse(this.neuron.getPreviousPredictingState());

	this.neuron.nextTimeStep();

	assertTrue(this.neuron.getPreviousActiveState());
	assertTrue(this.neuron.getPreviousPredictingState());

	assertFalse(this.neuron.getActiveState());
	assertFalse(this.neuron.getPredictingState());
    }

    public void test_getBestPreviousActiveSegment() {
	// previousActiveSegments size = 0 & sequenceSegments size = 0
	assertNotNull(this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 0 & sequenceSegments size = 0
	this.neuron.addDistalSegment(this.distalSegmentWith1ActiveSynapse);
	this.neuron.addDistalSegment(this.distalSegmentWith2ActiveSynapses);
	assertNotNull(this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 1 & sequenceSegments size = 0
	this.neuron.addDistalSegment(this.previousActiveDistalSegment);
	assertEquals(this.previousActiveDistalSegment,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 2 & sequenceSegments size = 0
	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setPreviousActiveState(true);
	Synapse<Cell> previousActiveSynapse_1 = new Synapse<Cell>(
		activeVisionCell_1, 0.3, 0, 0);

	DistalSegment distalSegment_3 = new DistalSegment();
	distalSegment_3.addSynapse(previousActiveSynapse_1);
	distalSegment_3.setPreviousActiveState(true);
	this.neuron.addDistalSegment(distalSegment_3);
	assertEquals(distalSegment_3,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 3 & sequenceSegments size = 1
	DistalSegment distalSegment_4 = new DistalSegment();
	distalSegment_4.setPreviousActiveState(true);
	distalSegment_4.setSequenceState(true);
	this.neuron.addDistalSegment(distalSegment_4);
	assertEquals(distalSegment_4,
		this.neuron.getBestPreviousActiveSegment());

	// previousActiveSegments size = 3 & sequenceSegments size = 2
	DistalSegment distalSegment_5 = new DistalSegment();
	distalSegment_5.addSynapse(previousActiveSynapse_1);
	distalSegment_5.setPreviousActiveState(true);
	distalSegment_5.setSequenceState(true);
	this.neuron.addDistalSegment(distalSegment_5);
	assertEquals(distalSegment_5,
		this.neuron.getBestPreviousActiveSegment());
    }

    public void test_getBestActiveSegment() {
	this.neuron.addDistalSegment(this.distalSegmentWith1ActiveSynapse);
	this.neuron.addDistalSegment(this.distalSegmentWith2ActiveSynapses);

	assertEquals(this.distalSegmentWith2ActiveSynapses,
		this.neuron.getBestActiveSegment());
    }
}
