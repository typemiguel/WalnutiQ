package model;

import model.MARKII_Segment.SynapseUpdateState;

public class Test_MARKII_Segment extends junit.framework.TestCase{

//    private MARKII_Segment<MARKII_AbstractCell> proximalSegment;
//
//
//    public void SetUp()
//    {
//	this.proximalSegment = new MARKII_Segment<MARKII_AbstractCell>();
//    }
//
//    // -----------------SpatialPooler proximal segments-----------------------
//    // add new synapses only during instantiation
//    // increase permanences of all synapses
//    // decrease permanences of all synapses
//    // increase permanences of all active synapses
//
//    // works with these contants in Synapse
//    // PERMANCE_INCREASE = 0.015;
//    // PERMANCE_DECREASE = 0.005;
//    // MINIMAL_CONNECTED_PERMANCE = 0.2;
//
//    // case 1: assert that a  must have a synapse
//
//    public void testIncreaseAllSynapsePermanences() {
//	MARKII_Synapse<MARKII_AbstractCell> unconnectedSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.1984);
//	this.proximalSegment.addSynapse(unconnectedSynapse);
//	MARKII_Synapse<MARKII_AbstractCell> toBeConnectedSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.1985);
//	this.proximalSegment.addSynapse(toBeConnectedSynapse);
//
//	// 0.1984  + 0.015 > 0.200 false
//	// 0.1985  + 0.015 > 0.200 true
//	this.proximalSegment.updateSynapsePermanences(SynapseUpdateState.INCREASE_ALL);
//	assertFalse(unconnectedSynapse.isConnected());
//	assertTrue(toBeConnectedSynapse.isConnected());
//
//	this.proximalSegment.updateSynapsePermanences(SynapseUpdateState.INCREASE_ALL);
//	assertTrue(unconnectedSynapse.isConnected());
//	assertTrue(toBeConnectedSynapse.isConnected());
//    }
//
//    public void testDecreaseAllSynapsePermanences() {
//	MARKII_Synapse<MARKII_AbstractCell> stillConnectedSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.2005);
//	this.proximalSegment.addSynapse(stillConnectedSynapse);
//	MARKII_Synapse<MARKII_AbstractCell> neverConnectedSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.2004);
//	this.proximalSegment.addSynapse(neverConnectedSynapse);
//
//	// 0.2005 - 0.005 > 0.2000 true
//	// 0.2004 - 0.005 > 0.2000 false
//	this.proximalSegment.updateSynapsePermanences(SynapseUpdateState.DECREASE_ALL);
//	assertTrue(stillConnectedSynapse.isConnected());
//	assertFalse(neverConnectedSynapse.isConnected());
//
//	this.proximalSegment.updateSynapsePermanences(SynapseUpdateState.DECREASE_ALL);
//	assertFalse(stillConnectedSynapse.isConnected());
//	assertFalse(neverConnectedSynapse.isConnected());
//    }
//
//    public void testIncreaseActiveSynapsePermanences() {
//	MARKII_Synapse<MARKII_AbstractCell> inActiveSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.199);
//	this.proximalSegment.addSynapse(inActiveSynapse);
//	MARKII_Synapse<MARKII_AbstractCell> activeSynapse = new MARKII_Synapse(new MARKII_SensorCell(), 0.2);
//	this.proximalSegment.addSynapse(activeSynapse);
//	this.proximalSegment.updateSynapsePermanences(SynapseUpdateState.INCREASE_ACTIVE);
//	assertFalse(inActiveSynapse.isConnected());
//	assertTrue(activeSynapse.isConnected());
//    }
//
//    public void testActiveState()
//    {
//	MARKII_Synapse[] tenSynapses = new MARKII_Synapse[10];
//	// just test with SensorCell since we are not using any other fields unique to
//	// Neuron class
//
//	// add 1 active synapse
//	tenSynapses[0] = new MARKII_Synapse<MARKII_AbstractCell>(new MARKII_SensorCell(), 0.21);
//
//	for (int i = 1; i < tenSynapses.length; i++)
//	{
//	    // add 9 inactive synapses
//	    tenSynapses[i] = new MARKII_Synapse<MARKII_AbstractCell>(new MARKII_SensorCell(), 0.19);
//
//	}
//	// case 1: number of active synapses within segment < activation threshold
//
//
//	// case 2: number of active synapses within segment = activation threshold
//	// case 3: number of active synapses within segment > activation threshold
//    }
    // ------------------------------------------------------------------------
}
