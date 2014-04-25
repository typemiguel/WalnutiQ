package model.MARK_I;

import java.util.ArrayList;
import java.util.List;

public class SegmentUpdateList {
    List<SegmentUpdate> segmentUpdateList;

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
	for (SegmentUpdate segmentUpdate : this.segmentUpdateList) {
	    if (segmentUpdate.getNeuronColumnPosition().equals(columnPosition)
		    && segmentUpdate.getNeuronIndex() == neuronIndex) {
		return segmentUpdate;
	    }
	}
	return null;
    }

    public boolean deleteSegmentUpdate(ColumnPosition columnPosition,
	    int neuronIndex) {
	for (int i = 0; i < this.segmentUpdateList.size(); i++) {
	    SegmentUpdate currentSegmentUpdate = this.segmentUpdateList.get(i);

	    if (currentSegmentUpdate.getNeuronColumnPosition().equals(
		    columnPosition)
		    && currentSegmentUpdate.getNeuronIndex() == neuronIndex) {
		this.segmentUpdateList.remove(i);
		return true;
	    }
	}
	return false;
    }
}
