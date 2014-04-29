package model.MARK_II;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import model.MARK_I.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 27, 2014
 */
public class TemporalPoolerTest extends junit.framework.TestCase {
    private Retina retina;
    private Region region;
    private SpatialPooler spatialPooler;
    private TemporalPooler temporalPooler;

    private DistalSegment distalSegmentWith1ActiveSynapse;
    private DistalSegment distalSegmentWith2ActiveSynapses;
    private DistalSegment distalSegmentWith3ActiveSynapses;
    private DistalSegment distalSegmentWith4ActiveSynapses;

    private DistalSegment distalSegmentWith1PreviousActiveSynapse;

    public void setUp() throws IOException {

	// images this retina will see are all 66x66 pixels
	this.retina = new Retina(66, 66);

	this.region = new Region("Region", 8, 8, 3, 77.8, 1);

	AbstractSensorCellsToRegionConnect retinaToRegion = new SensorCellsToRegionRectangleConnect();
	retinaToRegion.connect(this.retina.getVisionCells(), this.region, 0, 0);

	this.spatialPooler = new SpatialPooler(this.region);
	this.spatialPooler.setLearningState(true);

	this.retina.seeBMPImage("2.bmp");
	this.spatialPooler.performSpatialPoolingOnRegion();
	assertEquals("((6, 2), (1, 3), (1, 5), (4, 4))",
		this.spatialPooler.getActiveColumnPositionsAsString());

	this.temporalPooler = new TemporalPooler(this.spatialPooler, 25);
	this.temporalPooler.setLearningState(true);
	// this.temporalPooler.performTemporalPoolingOnRegion();

	this.setUpDistalSegments();
    }

    public void test_performTemporalPoolingOnRegion() {

    }

    public void test_phaseOneCase1() {
	// Case 1: bottomUpPredicted = false learningCellChosen = false
	this.setUpCurrentLearningNeuronListForTemporalPooler();
	this.temporalPooler.phaseOne(this.spatialPooler.getActiveColumns());

	Column[][] columns = this.spatialPooler.getRegion().getColumns();
	int numberOfActiveNeurons = 0;
	// all neurons in Region should have their active state on
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		for (Neuron neuron : columns[x][y].getNeurons()) {
		    if (neuron.getActiveState()) {
			numberOfActiveNeurons++;
		    }
		}
	    }
	}

	// 4 active columns where each column has 3 neurons
	assertEquals(12, numberOfActiveNeurons);

	// 4 learning neurons were chosen and given a new distal segment
	// each with newSynapseCount new synapses
	assertEquals(4, this.temporalPooler.getSegmentUpdateList().size());

	for (SegmentUpdate segmentUpdate : this.temporalPooler
		.getSegmentUpdateList().getList()) {
	    assertEquals(3, segmentUpdate.getSynapsesWithActiveCells().size());
	    assertEquals(0, segmentUpdate.getSynpasesWithDeactiveCells().size());
	}
    }

    public void test_phaseOneCase2() {
	// Case 2: bottomUpPredicted = true learningCellChosen = false
	this.setUpCurrentLearningNeuronListForTemporalPooler();
	this.temporalPooler.phaseOne(this.spatialPooler.getActiveColumns());
    }

    public void test_phaseOneCase3() {
	// Case 3: bottomUpPredicted = false learningCellChosen = true
	this.setUpCurrentLearningNeuronListForTemporalPooler();
	this.temporalPooler.phaseOne(this.spatialPooler.getActiveColumns());
    }

    public void test_phaseOneCase4() {
	// Case 4: bottomUpPredicted = true learningCellChosen = true
	this.setUpCurrentLearningNeuronListForTemporalPooler();
	this.temporalPooler.phaseOne(this.spatialPooler.getActiveColumns());
    }

    public void test_getSegmentActiveSynapses() {
	// Case 1: previousTimeStep = true & newSynapses = true
	this.setUpCurrentLearningNeuronListForTemporalPooler();

	SegmentUpdate segmentUpdate = this.temporalPooler
		.getSegmentActiveSynapses(new ColumnPosition(0, 0), 0,
			this.distalSegmentWith1PreviousActiveSynapse, true,
			true);

	assertEquals(4, segmentUpdate.getSynapsesWithActiveCells().size());
	assertEquals(0, segmentUpdate.getSynpasesWithDeactiveCells().size());

	// Case 2: previousTimeStep = false & newSynapses = false
	this.setUpCurrentLearningNeuronListForTemporalPooler();

	SegmentUpdate segmentUpdate2 = this.temporalPooler
		.getSegmentActiveSynapses(new ColumnPosition(0, 0), 0,
			this.distalSegmentWith1PreviousActiveSynapse, false,
			false);

	assertEquals(0, segmentUpdate2.getSynapsesWithActiveCells().size());
	assertEquals(4, segmentUpdate2.getSynpasesWithDeactiveCells().size());

	// ---------------------------------------------------------------------
	DistalSegment twoActiveAndOnePreviouslyActiveSynapses = new DistalSegment();

	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setActiveState(true);

	VisionCell activeVisionCell_2 = new VisionCell();
	activeVisionCell_2.setActiveState(true);

	twoActiveAndOnePreviouslyActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 10));
	twoActiveAndOnePreviouslyActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 11));

	VisionCell activeVisionCell_3 = new VisionCell();
	activeVisionCell_3.setPreviousActiveState(true);
	twoActiveAndOnePreviouslyActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_3, 0.2, 0, 12));
	// ---------------------------------------------------------------------

	SegmentUpdate segmentUpdate3 = this.temporalPooler
		.getSegmentActiveSynapses(new ColumnPosition(0, 0), 0,
			twoActiveAndOnePreviouslyActiveSynapses, false, false);

	assertEquals(2, segmentUpdate3.getSynapsesWithActiveCells().size());
	assertEquals(1, segmentUpdate3.getSynpasesWithDeactiveCells().size());
    }

    public void test_addRandomlyChosenSynapsesFromCurrentLearningNeurons() {
	try {
	    Set<Synapse<Cell>> synapses = this.temporalPooler
		    .addRandomlyChosenSynapsesFromCurrentLearningNeurons(
			    new HashSet<Synapse<Cell>>(), new DistalSegment(),
			    new ColumnPosition(0, 0));
	    fail("should've thrown an exception!");
	} catch (IllegalStateException expected) {
	    assertEquals(
		    "currentLearningNeurons in TemporalPooler class "
			    + "addRandomlyChosenSynapsesFromCurrentLearningNeurons method cannot be size 0",

		    expected.getMessage());
	}

	DistalSegment distalSegment1 = this
		.setUpCurrentLearningNeuronListForTemporalPooler();

	Set<Synapse<Cell>> synapses = this.temporalPooler
		.addRandomlyChosenSynapsesFromCurrentLearningNeurons(
			new HashSet<Synapse<Cell>>(), distalSegment1,
			new ColumnPosition(0, 0));
	// even though newSynapseCount is 25 if there are only 3 unique
	// learning neurons, then only 3 synapses will be added
	assertEquals(3, synapses.size());
	assertEquals(3, distalSegment1.getSynapses().size());
    }

    DistalSegment setUpCurrentLearningNeuronListForTemporalPooler() {
	Neuron neuron1 = new Neuron();
	Neuron neuron2 = new Neuron();
	Neuron neuron3 = new Neuron();
	DistalSegment distalSegment1 = new DistalSegment();
	neuron1.addDistalSegment(distalSegment1);
	DistalSegment distalSegment2 = new DistalSegment();
	neuron2.addDistalSegment(distalSegment2);
	DistalSegment distalSegment3 = new DistalSegment();
	neuron3.addDistalSegment(distalSegment3);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron1);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron2);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron3);
	return distalSegment1;
    }

    public void test_generatePotentialSynapses() {
	Neuron neuron = new Neuron();
	neuron.addDistalSegment(this.distalSegmentWith3ActiveSynapses);
	neuron.addDistalSegment(this.distalSegmentWith4ActiveSynapses);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron);

	List<Synapse<Cell>> synapses2 = this.temporalPooler
		.generatePotentialSynapses(2, new ColumnPosition(0, 0));
	assertEquals(3, synapses2.size());
    }

    public void test_createNewSynapsesConnectedToCurrentLearningNeurons() {
	try {
	    this.temporalPooler
		    .createNewSynapsesConnectedToCurrentLearningNeurons(
			    new ArrayList<Synapse<Cell>>(), 1,
			    new ColumnPosition(0, 0));
	    fail("should've thrown an exception!");
	} catch (IllegalStateException expected) {
	    assertEquals(
		    "currentLearningNeurons in TemporalPooler class "
			    + "createNewSynapsesConnectedToCurrentLearningNeurons method cannot be size 0",
		    expected.getMessage());
	}

	Neuron neuron = new Neuron();
	neuron.addDistalSegment(this.distalSegmentWith1ActiveSynapse);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron);

	List<Synapse<Cell>> newSynapses = this.temporalPooler
		.createNewSynapsesConnectedToCurrentLearningNeurons(
			new ArrayList<Synapse<Cell>>(), 1, new ColumnPosition(
				3, 3));
	assertEquals(1, newSynapses.size());
	Synapse<Cell> sameSynapse = new Synapse<Cell>(neuron, 3, 3);
	assertEquals(sameSynapse, newSynapses.get(0));
    }

    public void test_phaseTwo() {

    }

    public void test_phaseThree() {

    }

    public void test_adaptSegments() {

    }

    public void test_getBestMatchingNeuronIndex() {
	Column column = new Column(4, new ColumnPosition(0, 0));
	Neuron neuron0 = new Neuron();
	Neuron neuron1 = new Neuron();
	Neuron neuron2 = new Neuron();
	Neuron neuron3 = new Neuron();
	neuron0.addDistalSegment(distalSegmentWith1ActiveSynapse);
	neuron1.addDistalSegment(distalSegmentWith2ActiveSynapses);
	neuron2.addDistalSegment(distalSegmentWith4ActiveSynapses);
	neuron3.addDistalSegment(distalSegmentWith3ActiveSynapses);
	column.setNeuron(neuron0, 0);
	column.setNeuron(neuron1, 1);
	column.setNeuron(neuron2, 2);
	column.setNeuron(neuron3, 3);

	assertEquals(2, this.temporalPooler.getBestMatchingNeuronIndex(column));
    }

    void setUpDistalSegments() {
	this.distalSegmentWith1ActiveSynapse = new DistalSegment();
	this.distalSegmentWith2ActiveSynapses = new DistalSegment();
	this.distalSegmentWith3ActiveSynapses = new DistalSegment();
	this.distalSegmentWith4ActiveSynapses = new DistalSegment();

	// 4 currently active Synapse
	VisionCell activeVisionCell_1 = new VisionCell();
	activeVisionCell_1.setActiveState(true);

	VisionCell activeVisionCell_2 = new VisionCell();
	activeVisionCell_2.setActiveState(true);

	VisionCell activeVisionCell_3 = new VisionCell();
	activeVisionCell_3.setActiveState(true);

	VisionCell activeVisionCell_4 = new VisionCell();
	activeVisionCell_4.setActiveState(true);

	this.distalSegmentWith1ActiveSynapse.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 0));

	this.distalSegmentWith2ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 1));
	this.distalSegmentWith2ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 2));

	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 3));
	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 4));
	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_3, 0.2, 0, 5));

	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 6));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 7));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_3, 0.2, 0, 8));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_4, 0.2, 0, 9));

	this.distalSegmentWith1PreviousActiveSynapse = new DistalSegment();
	VisionCell activeVisionCell_5 = new VisionCell();
	activeVisionCell_5.setPreviousActiveState(true);
	this.distalSegmentWith1PreviousActiveSynapse
		.addSynapse(new Synapse<Cell>(activeVisionCell_5, 0.2, 0, 0));
    }
}
