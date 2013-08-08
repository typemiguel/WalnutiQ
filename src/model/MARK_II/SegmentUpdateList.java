package model.MARK_II;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | August 8, 2013
 */
public class SegmentUpdateList {
    List<SegmentUpdate> segmentUpdates;

    public SegmentUpdateList() {
	this.segmentUpdates = new ArrayList<SegmentUpdate>();
    }

    public List<SegmentUpdate> getSegmentUpdates() {
	return this.segmentUpdates;
    }
}
