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
	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		for (Neuron neuron : columns[x][y].getNeurons()) {
		    if (x == 0 && y == 0) {
			assertTrue(neuron.getActiveState());
		    } else {
			assertFalse(neuron.getActiveState());
		    }
		}
	    }
	}

	Neuron[] neurons = activeColumn_00.getNeurons();
	Neuron neuron_0 = neurons[0];

	neuron_0.setPreviousPredictingState(true);

	DistalSegment distalSegment = new DistalSegment();
	distalSegment.setPreviousActiveState(true);
	neuron_0.addDistalSegment(distalSegment);


    }

    public void test_computePredictiveStateOfAllNeurons() {

    }
}
