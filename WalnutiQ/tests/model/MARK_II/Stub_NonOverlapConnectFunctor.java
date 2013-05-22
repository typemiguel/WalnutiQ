package model.MARK_II;

public class Stub_NonOverlapConnectFunctor implements
	Stub_ConnectionInterface {
    // connect two regions with overlap as undirected bipartite graph
    public void connect(Stub_Region parentRegion,
	    Stub_Region childRegion) {
	if (parentRegion == null) {
	    throw new IllegalArgumentException(
		    "parentRegion in connect method cannot be null");
	} else if (childRegion == null) {
	    throw new IllegalArgumentException(
		    "chilRegion in connect method cannot be null");
	}
	// adding synapses with neurons of childRegion to proximalSegments
	// of parentRegion
	Stub_Column[][] parentColumns = parentRegion.getColumns();
	int parentXAxisLength = parentColumns.length;
	int parentYAxisLength = parentColumns[0].length;
	for (int parentColumnX = 0; parentColumnX < parentXAxisLength; parentColumnX++) {
	    for (int parentColumnY = 0; parentColumnY < parentYAxisLength; parentColumnY++) {
		Stub_Column parentColumn = parentColumns[parentColumnX][parentColumnY];
		Stub_Neuron[] neurons = parentColumn.getNeurons();

		int xStart = (int) Math.ceil(parentColumnX / parentXAxisLength);
		int yStart = (int) Math.ceil(parentColumnY / parentYAxisLength);

		// flooring
		int xEnd = (parentColumnX + 1) / parentXAxisLength;
		int yEnd = (parentColumnY + 1) / parentYAxisLength;

		Stub_Column[][] childColumns = childRegion.getColumns();
		for (int childColumnX = xStart; childColumnX < xEnd; childColumnX++) {
		    for (int childColumnY = yStart; childColumnY < yEnd; childColumnY++) {

			for (Stub_Neuron childNeuron : childColumns[childColumnX][childColumnY]
				.getNeurons()) {
			    parentColumn.getProximalSegment().addSynapse(
				    new Stub_Synapse(childNeuron, 0.3));
			}
		    }
		}
	    }
	}
    }
}
