package model.MARK_II;

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

    public void setUp() throws IOException {

	// images this retina will see are all 66x66 pixels
	this.retina = new Retina(66, 66);

	this.region = new Region("Region", 8, 8, 1, 77.8, 1);

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

    public void test_phaseOne() {

    }

    public void test_getSegmentActiveSynapses() {
	// Case 1: previousTimeStep = true & newSynapses = true

	// Case 2: previousTimeStep = false & newSynapses = false

    }

    public void test_addRandomlyChosenSynapsesFromCurrentLearningNeurons() {

    }

    public void test_generatePotentialSynapses() {
	Neuron neuron = new Neuron();
	neuron.addDistalSegment(this.distalSegmentWith3ActiveSynapses);
	neuron.addDistalSegment(this.distalSegmentWith4ActiveSynapses);
	this.temporalPooler.getCurrentLearningNeurons().add(neuron);

	this.temporalPooler.generatePotentialSynapses(2, new ColumnPosition(0,
		0));
    }

    public void test_createNewSynapsesConnectedToCurrentLearningNeurons() {

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
		activeVisionCell_1, 0.2, 0, 0));
	this.distalSegmentWith2ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 1));

	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 0));
	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 1));
	this.distalSegmentWith3ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_3, 0.2, 0, 2));

	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_1, 0.2, 0, 0));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_2, 0.2, 0, 1));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_3, 0.2, 0, 2));
	this.distalSegmentWith4ActiveSynapses.addSynapse(new Synapse<Cell>(
		activeVisionCell_4, 0.2, 0, 3));
    }
}
