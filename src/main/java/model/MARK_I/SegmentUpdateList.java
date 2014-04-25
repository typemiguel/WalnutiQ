package main.java.model.MARK_I;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class SegmentUpdateList {
    List<SegmentUpdate> segmentUpdateList;

    public SegmentUpdateList() {
	this.segmentUpdateList = new ArrayList<SegmentUpdate>();
    }

    public List<SegmentUpdate> getList() {
	return this.segmentUpdateList;
    }

    private class SegmentUpdate {
	private int segmentIndex;
	private Set<Synapse<Cell>> synapsesWithActiveCells;
	private Set<Synapse<Cell>> synapsesWithDeactiveCells;

	/** same as sequenceSegment */
	private boolean predictsFeedForwardInputOnNextTimeStep;

	public SegmentUpdate() {
	    this.segmentIndex = -1;
	    this.synapsesWithActiveCells = new HashSet<Synapse<Cell>>();
	    this.synapsesWithDeactiveCells = new HashSet<Synapse<Cell>>();
	    this.predictsFeedForwardInputOnNextTimeStep = false;
	}
    }
}
