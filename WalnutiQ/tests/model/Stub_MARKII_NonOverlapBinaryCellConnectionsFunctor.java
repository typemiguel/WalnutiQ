package model;

public class Stub_MARKII_NonOverlapBinaryCellConnectionsFunctor implements
	Stub_MARKII_ConnectionInterface {
    // connect two regions with overlap as undirected bipartite graph
    public void connect(Stub_MARKII_Region parentRegion, Stub_MARKII_Region childRegion) {
	// adding synapses with neurons of childRegion to proximalSegments
	// of parentRegion

	Stub_MARKII_Column[][] parentColumns = parentRegion.getColumns();
	int parentXAxisLength = parentColumns.length;
	int parentYAxisLength = parentColumns[0].length;
	for (int parentColumnX = 0; parentColumnX < parentXAxisLength; parentColumnX++) {
	    for (int parentColumnY = 0; parentColumnY < parentYAxisLength; parentColumnY++) {
		Stub_MARKII_Column parentColumn = parentColumns[parentColumnX][parentColumnY];
		Stub_MARKII_Neuron[] neurons = parentColumn.getNeurons();

		int xStart = (int) Math.ceil(parentColumnX / parentXAxisLength);
		int yStart = (int) Math.ceil(parentColumnY / parentYAxisLength);

		// flooring
		int xEnd = (parentColumnX + 1) / parentXAxisLength;
		int yEnd = (parentColumnY + 1) / parentYAxisLength;

		Stub_MARKII_Column[][] childColumns = childRegion.getColumns();
		for (int childColumnX = xStart; childColumnX < xEnd; childColumnX++) {
		    for (int childColumnY = yStart; childColumnY < yEnd; childColumnY++) {

			for (Stub_MARKII_Neuron childNeuron : childColumns[childColumnX][childColumnY]
				.getNeurons()) {
			    parentColumn.getProximalSegment().addSynapse(
				    new Stub_MARKII_Synapse(childNeuron, 0.3));
			}
		    }
		}
	    }
	}
    }
}
