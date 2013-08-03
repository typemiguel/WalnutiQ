package model.MARK_II;

import java.util.HashSet;

import java.util.Set;

import model.MARK_II.ConnectTypes.RegionToRegionConnect;
import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 29, 2013
 */
public class Test_TemporalPooler extends junit.framework.TestCase {
    private Region parentRegion;
    private TemporalPooler temporalPooler;

    public void setUp() {
	this.parentRegion = new Region("parentRegion", 8, 8, 4, 20, 3);
	Region childRegion = new Region("childRegion", 64, 64, 4, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, this.parentRegion, 0, 0);
	this.temporalPooler = new TemporalPooler(this.parentRegion);
    }

    public void test_computeActiveStateOfAllNeurons() {
	Column[][] columns = this.parentRegion.getColumns();

	Column activeColumn_00 = columns[0][0];
	activeColumn_00.setActiveState(true);

	Set<Column> activeColumns = new HashSet<Column>();
	activeColumns.add(activeColumn_00);

//	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
//	for (int x = 0; x < columns.length; x++) {
//	    for (int y = 0; y < columns[0].length; y++) {
//		for (Neuron neuron : columns[x][y].getNeurons()) {
//		    if (x == 0 && y == 0) {
//			// checking if the for loop that sets all of the neurons
//			// within a Column was reached
//			assertTrue(neuron.getActiveState());
//		    } else {
//			assertFalse(neuron.getActiveState());
//		    }
//		}
//	    }
//	}
//	for (Neuron neuron : columns[0][0].getNeurons()) {
//	    neuron.setActiveState(false);
//	}
//
	Neuron[] neurons = activeColumn_00.getNeurons();
//	Neuron neuron_0 = neurons[0];
//
//	neuron_0.setPreviousPredictingState(true);
//
//	DistalSegment distalSegment_1 = new DistalSegment();
//	distalSegment_1.setPreviousActiveState(true);
//	neuron_0.addDistalSegment(distalSegment_1);
//	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
//	for (int x = 0; x < columns.length; x++) {
//	    for (int y = 0; y < columns[0].length; y++) {
//		for (Neuron neuron : columns[x][y].getNeurons()) {
//		    if (x == 0 && y == 0) {
//			// checking if the for loop that sets all of the neurons
//			// within a Column was reached again because the
//			// bestSegment was not a sequence segment
//			assertTrue(neuron.getActiveState());
//		    } else {
//			assertFalse(neuron.getActiveState());
//		    }
//		}
//	    }
//	}
//	for (Neuron neuron : columns[0][0].getNeurons()) {
//	    neuron.setActiveState(false);
//	}

	DistalSegment distalSegment_2 = new DistalSegment();
	distalSegment_2.setPreviousActiveState(true);
	distalSegment_2.setSequenceState(true);
	Neuron neuron_1 = neurons[1];
	neuron_1.addDistalSegment(distalSegment_2);
	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		Neuron[] neurons2 = columns[x][y].getNeurons();
		for (int i = 0; i < neurons2.length; i++) {
		    if (x == 0 && y == 0 && i == 1) {
			assertTrue(neurons2[i].getActiveState());
		    } else {
			assertFalse(neurons2[i].getActiveState());
		    }
		}
	    }
	}
    }

    public void test_computePredictiveStateOfAllNeurons() {

    }
}
