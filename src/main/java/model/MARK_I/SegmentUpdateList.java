package model.MARK_I;

import java.util.ArrayList;
import java.util.List;

public class SegmentUpdateList {
    List<SegmentUpdate> segmentUpdateList;

    // need to be accessible by neuron position within Region

    public SegmentUpdateList() {
	this.segmentUpdateList = new ArrayList<SegmentUpdate>();
    }

    public void add(SegmentUpdate segmentUpdate) {
	this.segmentUpdateList.add(segmentUpdate);
    }

    public void clear() {
	this.segmentUpdateList.clear();
    }

    public SegmentUpdate getSegmentUpdate(ColumnPosition columnPosition,
	    int neuronIndex) {
	return null;
    }

    public void deleteSegmentUpdate(ColumnPosition columnPosition,
	    int neuronIndex) {

    }
}
