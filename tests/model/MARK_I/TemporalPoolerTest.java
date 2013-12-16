package model.MARK_I;

import model.MARK_I.connectTypes.RegionToRegionConnect;
import model.MARK_I.connectTypes.RegionToRegionRectangleConnect;

import model.MARK_I.Cell;
import model.MARK_I.Column;
import model.MARK_I.DistalSegment;
import model.MARK_I.Neuron;
import model.MARK_I.Region;
import model.MARK_I.Synapse;
import model.MARK_I.TemporalPooler;
import model.MARK_I.VisionCell;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK I | August 3, 2013
 */
public class TemporalPoolerTest extends junit.framework.TestCase {
    private Region parentRegion;
    private TemporalPooler temporalPooler;

    public void setUp() {
	this.parentRegion = new Region("parentRegion", 8, 8, 4, 20, 3);
	Region childRegion = new Region("childRegion", 64, 64, 4, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, this.parentRegion, 0, 0);
	this.temporalPooler = new TemporalPooler(this.parentRegion);
    }

    public void test_computeActiveStateOfAllNeuronsWithNoBestSegment() {
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
			// checking if the for loop that sets all of the neurons
			// within a Column was reached
			assertTrue(neuron.getActiveState());
		    } else {
			assertFalse(neuron.getActiveState());
		    }
		}
	    }
	}
    }

    public void test_computeActiveStateOfAllNeuronsWithOneBestSegment() {
	Column[][] columns = this.parentRegion.getColumns();
	Column activeColumn_00 = columns[0][0];
	activeColumn_00.setActiveState(true);

	Neuron[] neurons = activeColumn_00.getNeurons();

	Neuron neuron_0 = neurons[0];
	neuron_0.setPreviousPredictingState(true);

	DistalSegment distalSegment = new DistalSegment();
	distalSegment.setPreviousActiveState(true);
	neuron_0.addDistalSegment(distalSegment);

	Set<Column> activeColumns = new HashSet<Column>();
	activeColumns.add(activeColumn_00);
	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		for (Neuron neuron : columns[x][y].getNeurons()) {
		    if (x == 0 && y == 0) {
			// checking if the for loop that sets all of the neurons
			// within a Column was reached again because the
			// bestSegment was not a sequence segment
			assertTrue(neuron.getActiveState());
		    } else {
			assertFalse(neuron.getActiveState());
		    }
		}
	    }
	}
    }

    public void test_compuateActiveStateOfAllNeuronsWithBestSequenceSegment() {
	Column[][] columns = this.parentRegion.getColumns();
	Column activeColumn_00 = columns[0][0];
	activeColumn_00.setActiveState(true);

	Neuron[] neurons = activeColumn_00.getNeurons();

	DistalSegment distalSegment = new DistalSegment();
	distalSegment.setPreviousActiveState(true);
	distalSegment.setSequenceState(true);
	Neuron neuron_0 = neurons[0];
	neuron_0.setPreviousPredictingState(true);
	neuron_0.addDistalSegment(distalSegment);

	Set<Column> activeColumns = new HashSet<Column>();
	activeColumns.add(activeColumn_00);
	this.temporalPooler.computeActiveStateOfAllNeurons(activeColumns);
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		Neuron[] neurons2 = columns[x][y].getNeurons();
		for (int i = 0; i < neurons2.length; i++) {
		    if (x == 0 && y == 0 && i == 0) {
			assertTrue(neurons2[i].getActiveState());
		    } else {
			assertFalse(neurons2[i].getActiveState());
		    }
		}
	    }
	}
    }

    public void test_getBestActiveSegment() {
	Neuron neuron_1 = new Neuron();
	assertNull(this.temporalPooler.getBestActiveSegment(neuron_1));

	DistalSegment distalSegment_1 = new DistalSegment();
	neuron_1.addDistalSegment(distalSegment_1);
	assertEquals(distalSegment_1,
		this.temporalPooler.getBestActiveSegment(neuron_1));

	DistalSegment distalSegment_2 = new DistalSegment();
	VisionCell visionCell_1 = new VisionCell();
	visionCell_1.setActiveState(true);
	Synapse synapse_1 = new Synapse<Cell>(visionCell_1, 0, 0);
	distalSegment_2.addSynapse(synapse_1);
	neuron_1.addDistalSegment(distalSegment_2);

	DistalSegment distalSegment_3 = new DistalSegment();
	VisionCell visionCell_2 = new VisionCell();
	visionCell_2.setActiveState(true);
	Synapse synapse_2 = new Synapse<Cell>(visionCell_2, 0, 1);
	distalSegment_3.addSynapse(synapse_1);
	distalSegment_3.addSynapse(synapse_2);
	neuron_1.addDistalSegment(distalSegment_3);
	assertEquals(distalSegment_3,
		this.temporalPooler.getBestActiveSegment(neuron_1));
    }
}
