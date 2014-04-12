package model.MARK_I.observableDesign;

import java.util.Set;
import model.MARK_I.Synapse;
import model.MARK_I.Cell;

public class SimpleSpatialPooler {
    private SimpleRegion simpleRegion;

    public SimpleSpatialPooler(SimpleRegion simpleRegion) {
	this.simpleRegion = simpleRegion;
    }

    public boolean performSpatialPoolingOnRegion() {

	Set<Synapse<Cell>> synapses = this.simpleRegion.getColumn()
		.getProximalSegment().getSynapses();

	for (Synapse synapse : synapses) {
	    if (synapse.getCell().getActiveState()) {
		return true;
	    } else {
		return false;
	    }
	}
	return false;
    }
}
