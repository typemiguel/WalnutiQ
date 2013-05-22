package model.MARK_II;

import model.MARK_II.Stub_Segment.Stub_SynapseUpdateState;

import java.util.HashSet;

import java.util.Set;

import org.junit.Test;

public class Test_Segment extends junit.framework.TestCase {
    private Stub_Segment<Stub_AbstractCell> proximalSegment;

    public void setUp() {
	this.proximalSegment = new Stub_Segment<Stub_AbstractCell>();
    }

    // -----------------SpatialPooler proximal segments-----------------------
    // segments should add new synapses only during instantiation
    // segments can:
    // 1) increase permanences of all synapses
    // 2) decrease permanences of all synapses
    // 3) increase permanences of all active synapses

    public void testGetActiveState() {
	// a segment with 10 synapses will have an activation threshold of 2 synapses
	// if the PERCENT_ACTIVE_SYNAPSES_THRESHOLD is 0.2
	Set<Stub_Synapse<Stub_AbstractCell>> tenSynapses = new HashSet<Stub_Synapse<Stub_AbstractCell>>();
	// just test with SensorCell since we are not using any other fields unique to
	// Neuron class

	// add 1 active synapse
	Stub_VisionCell activeVisionCell_1 = new Stub_VisionCell();
	activeVisionCell_1.setActiveState(true);
	tenSynapses.add(new Stub_Synapse<Stub_AbstractCell>(new Stub_VisionCell(), 0.21));

	// and 9 inactive synapses
	for (int i = 1; i < 10; i++) {
	    tenSynapses.add(new Stub_Synapse<Stub_AbstractCell>(new Stub_VisionCell(), 0.20-i*0.01));
	}
	assertEquals(10, tenSynapses.size());

	// Case 1: number of active synapses within segment < activation threshold
	this.proximalSegment.setSynapses(tenSynapses);
	assertFalse(this.proximalSegment.getActiveState());

	// Case 2: number of active synapses within segment = activation threshold
	this.proximalSegment.getSynapses().remove(new Stub_Synapse<Stub_AbstractCell>(new Stub_VisionCell(), 0.19));
	Stub_VisionCell activeVisionCell_2 = new Stub_VisionCell();
	activeVisionCell_2.setActiveState(true);
	this.proximalSegment.getSynapses().add(new Stub_Synapse<Stub_AbstractCell>(activeVisionCell_2, 0.22));
	assertFalse(this.proximalSegment.getActiveState());

	// Case 3: number of active synapses within segment > activation threshold
	this.proximalSegment.getSynapses().remove(new Stub_Synapse<Stub_AbstractCell>(new Stub_VisionCell(), 0.17));
	Stub_VisionCell activeVisionCell_3 = new Stub_VisionCell();
	activeVisionCell_3.setActiveState(true);
	//this.proximalSegment.getSynapses().add(new Stub_MARKII_Synapse<Stub_MARKII_AbstractCell>(activeVisionCell_3, 0.23));
	System.out.println(this.proximalSegment.toString());
	assertTrue(this.proximalSegment.getActiveState());
    }

    public void testUpdateSynapsePermanences() {

    }

    public void testAddSynapse() {

    }

    public void testGetNumberOfActiveSynapses() {

    }



    // works with these contants in Synapse
    // PERMANCE_INCREASE = 0.015;
    // PERMANCE_DECREASE = 0.005;
    // MINIMAL_CONNECTED_PERMANCE = 0.2;

    // case 1: assert that a  must have a synapse

//    public void testIncreaseAllSynapsePermanences() {
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> unconnectedSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.1984);
//	this.proximalSegment.addSynapse(unconnectedSynapse);
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> toBeConnectedSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.1985);
//	this.proximalSegment.addSynapse(toBeConnectedSynapse);
//
//	// 0.1984  + 0.015 > 0.200 false
//	// 0.1985  + 0.015 > 0.200 true
//	this.proximalSegment.updateSynapsePermanences(Stub_SynapseUpdateState.INCREASE_ALL);
//	assertFalse(unconnectedSynapse.isConnected());
//	assertTrue(toBeConnectedSynapse.isConnected());
//
//	this.proximalSegment.updateSynapsePermanences(Stub_SynapseUpdateState.INCREASE_ALL);
//	assertTrue(unconnectedSynapse.isConnected());
//	assertTrue(toBeConnectedSynapse.isConnected());
//    }
//
//    public void testDecreaseAllSynapsePermanences() {
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> stillConnectedSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.2005);
//	this.proximalSegment.addSynapse(stillConnectedSynapse);
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> neverConnectedSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.2004);
//	this.proximalSegment.addSynapse(neverConnectedSynapse);
//
//	// 0.2005 - 0.005 > 0.2000 true
//	// 0.2004 - 0.005 > 0.2000 false
//	this.proximalSegment.updateSynapsePermanences(Stub_SynapseUpdateState.DECREASE_ALL);
//	assertTrue(stillConnectedSynapse.isConnected());
//	assertFalse(neverConnectedSynapse.isConnected());
//
//	this.proximalSegment.updateSynapsePermanences(Stub_SynapseUpdateState.DECREASE_ALL);
//	assertFalse(stillConnectedSynapse.isConnected());
//	assertFalse(neverConnectedSynapse.isConnected());
//    }
//
//    public void testIncreaseActiveSynapsePermanences() {
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> inActiveSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.199);
//	this.proximalSegment.addSynapse(inActiveSynapse);
//	Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> activeSynapse = new Stub_MARKII_Synapse(new Stub_MARKII_SensorCell(), 0.2);
//	this.proximalSegment.addSynapse(activeSynapse);
//	this.proximalSegment.updateSynapsePermanences(Stub_SynapseUpdateState.INCREASE_ACTIVE);
//	assertFalse(inActiveSynapse.isConnected());
//	assertTrue(activeSynapse.isConnected());
//    }
//
//    public void testActiveState()
//    {
//	Stub_MARKII_Synapse[] tenSynapses = new Stub_MARKII_Synapse[10];
//	// just test with SensorCell since we are not using any other fields unique to
//	// Neuron class
//
//	// add 1 active synapse
//	tenSynapses[0] = new Stub_MARKII_Synapse<Stub_MARKII_AbstractCell>(new Stub_MARKII_SensorCell(), 0.21);
//
//	for (int i = 1; i < tenSynapses.length; i++)
//	{
//	    // add 9 inactive synapses
//	    tenSynapses[i] = new Stub_MARKII_Synapse<Stub_MARKII_AbstractCell>(new Stub_MARKII_SensorCell(), 0.19);
//
//	}
//	// case 1: number of active synapses within segment < activation threshold
//
//
//	// case 2: number of active synapses within segment = activation threshold
//	// case 3: number of active synapses within segment > activation threshold
//    }
}
