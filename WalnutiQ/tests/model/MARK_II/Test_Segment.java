package model.MARK_II;

/**
 * SpatialPooler proximal Segments
 * 1) Segments should add new Synapses only during instantiation
 * 2) Segments can:
 *    - increase permanences of all Synapses on itself
 *    - decrease permanences of all Synapses on itself
 *    - increase permanences of all active Synapses on itself
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 8, 2013
 */
public class Test_Segment extends junit.framework.TestCase {
    private Segment<Cell> proximalSegment;

    public void setUp() {
	this.proximalSegment = new Segment<Cell>();
    }

    public void test_getActiveState() {
	// a Segment with 10 Synapses will have an activation threshold of 2 Synapses
	// if the PERCENT_ACTIVE_SYNAPSES_THRESHOLD is 0.2

	// add 1 active synapse
	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setActiveState(true);
	this.proximalSegment.addSynapse(new Synapse<Cell>(activeVisionCell_1, 0.2, 0, 1));

	// add 9 inactive synapses
	VisionCell inActiveVisionCell = null;
	for (int i = 1; i < 10; i++) {
	    	inActiveVisionCell = new VisionCell();
		this.proximalSegment.addSynapse(new Synapse<Cell>(inActiveVisionCell, 0.2, i, 1));
	}
	assertEquals(10, this.proximalSegment.getSynapses().size());

	// Case 1: number of active synapses within segment < activation threshold
	assertFalse(this.proximalSegment.getActiveState());

	// Case 2: number of active synapses within segment = activation threshold
	Synapse<Cell> synapseToRemove_1 = new Synapse<Cell>(inActiveVisionCell, 0.2, 1, 1);
	assertTrue(this.proximalSegment.removeSynapse(synapseToRemove_1));

	VisionCell activeVisionCell_2 = new VisionCell();
	activeVisionCell_2.setActiveState(true);
	this.proximalSegment.getSynapses().add(new Synapse<Cell>(activeVisionCell_2, 0.2, 10, 1));
	assertFalse(this.proximalSegment.getActiveState());

	// Case 3: number of active synapses within segment > activation threshold
	Synapse<Cell> synapseToRemove_2 = new Synapse<Cell>(inActiveVisionCell, 0.2, 2, 1);
	assertTrue(this.proximalSegment.removeSynapse(synapseToRemove_2));
	VisionCell activeVisionCell_3 = new VisionCell();
	activeVisionCell_3.setActiveState(true);
	this.proximalSegment.getSynapses().add(new Synapse<Cell>(activeVisionCell_3, 0.2, 11, 1));
	assertTrue(this.proximalSegment.getActiveState());
    }

    public void test_updateSynapsePermanences() {

    }

    public void test_addSynapse() {

    }

    public void test_getNumberOfActiveSynapses() {

    }

    public void test_toString() {

    }
}
