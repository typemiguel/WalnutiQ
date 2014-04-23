package main.java.model.MARK_I;

import java.util.Set;

/**
 * Idea behind temporal pooling: SDRs that occur adjacent in time probably have
 * a common underlying cause. Several times a second your eyes fixate on a
 * different part of the image causing a complete change in input. Despite this
 * changing input your perception is stable. Somewhere in higher regions there
 * must be neurons that remain active.
 *
 * Input into TemporalPooler: activeColumns of a Region at time t computed by
 * SpatialPooler
 *
 * Output from TemporalPooler: boolean OR of the actual active??? and predictive
 * state for each neuron in the set of activeColumns of a Region???
 *
 * Sequence memory: learning sequences of SDRs??? Uses the columns to represent
 * inputs uniquely in different contexts???
 *
 * Temporal Pooler: forms a stable representation over sequences???
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 22, 2014
 */
public class TemporalPooler extends Pooler {
    private SpatialPooler spatialPooler;

    public TemporalPooler(SpatialPooler spatialPooler) {
	this.spatialPooler = spatialPooler;
	super.region = spatialPooler.getRegion();
    }

    public void performTemporalPoolingOnRegion() {
	Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
	this.phaseOne(activeColumns);

	// phase 2
	this.phaseTwo(activeColumns);

	// phase 3
	this.phaseThree();
    }

    public void phaseOne(Set<Column> activeColumns) {

    }

    public void phaseTwo(Set<Column> activeColumns) {

    }

    public void phaseThree() {

    }
}
