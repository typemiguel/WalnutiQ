package model.MARK_I;

import java.util.ArrayList;
import java.util.List;

public class SegmentUpdateList {
    List<SegmentUpdate> segmentUpdateList;
    // need to be accessible by neuron position within Region

    public SegmentUpdateList() {
	this.segmentUpdateList = new ArrayList<SegmentUpdate>();
    }

    public List<SegmentUpdate> getList() {
	return this.segmentUpdateList;
    }

    public List<SegmentUpdate> getSegmentUpdatesForNeuron(Neuron neuron) {
	return null;
    }
}
