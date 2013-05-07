package model;

/**
 * A functor class that provides the implementation for connecting a lower
 * region to a higher region with non-overlaping proximal segments.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | April 28, 2013
 */
public class MARKII_NonOverlapBinaryCellConnectionsFunctor implements
	ConnectionInterface {
    // connect two regions with overlap as undirected bipartite graph
    public void connect(MARKII_Region parentRegion, MARKII_Region childRegion) {
	// adding synapses with neurons of childRegion to proximalSegments
	// of parentRegion

	MARKII_Column[][] parentColumns = parentRegion.getColumns();
	int parentXAxisLength = parentColumns.length;
	int parentYAxisLength = parentColumns[0].length;
	for (int parentColumnX = 0; parentColumnX < parentXAxisLength; parentColumnX++) {
	    for (int parentColumnY = 0; parentColumnY < parentYAxisLength; parentColumnY++) {
		MARKII_Column parentColumn = parentColumns[parentColumnX][parentColumnY];
		MARKII_Neuron[] neurons = parentColumn.getNeurons();

		int xStart = (int) Math.ceil(parentColumnX / parentXAxisLength);
		int yStart = (int) Math.ceil(parentColumnY / parentYAxisLength);

		// flooring
		int xEnd = (parentColumnX + 1) / parentXAxisLength;
		int yEnd = (parentColumnY + 1) / parentYAxisLength;

		MARKII_Column[][] childColumns = childRegion.getColumns();
		for (int childColumnX = xStart; childColumnX < xEnd; childColumnX++) {
		    for (int childColumnY = yStart; childColumnY < yEnd; childColumnY++) {

			for (MARKII_Neuron childNeuron : childColumns[childColumnX][childColumnY]
				.getNeurons()) {
			    parentColumn.getProximalSegment().addSynapse(
				    new MARKII_Synapse(childNeuron, 0.3));
			}
		    }
		}
	    }
	}
    }
}
