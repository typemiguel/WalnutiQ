package model.MARK_II.connectTypes;

import model.MARK_II.*;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class RegionToRegionRectangleConnect extends
        AbstractRegionToRegionConnect {
    @Override
    public void connect(Region childRegion, Region parentRegion,
                        int numberOfColumnsToOverlapAlongXAxisOfRegion,
                        int numberOfColumnsToOverlapAlongYAxisOfRegion) {

        super.checkParameters(childRegion, parentRegion,
                numberOfColumnsToOverlapAlongXAxisOfRegion,
                numberOfColumnsToOverlapAlongYAxisOfRegion);

        Column[][] parentRegionColumns = parentRegion.getColumns();
        int parentRegionXAxisLength = parentRegionColumns.length; // = 8
        int parentRegionYAxisLength = parentRegionColumns[0].length; // = 8

        Column[][] childRegionColumns = childRegion.getColumns();
        int childRegionXAxisLength = childRegionColumns.length; // = 66
        int childRegionYAxisLength = childRegionColumns[0].length; // = 66

        int connectingRectangleXAxisLength = Math
                .round((childRegionXAxisLength
                        + numberOfColumnsToOverlapAlongXAxisOfRegion
                        * parentRegionXAxisLength - numberOfColumnsToOverlapAlongXAxisOfRegion)
                        / parentRegionXAxisLength); // = 10
        int connectingRectangleYAxisLength = Math
                .round((childRegionYAxisLength
                        + numberOfColumnsToOverlapAlongYAxisOfRegion
                        * parentRegionYAxisLength - numberOfColumnsToOverlapAlongYAxisOfRegion)
                        / parentRegionYAxisLength); // = 10

        int shiftAmountXAxis = connectingRectangleXAxisLength
                - numberOfColumnsToOverlapAlongXAxisOfRegion; // = 10 - 2
        int shiftAmountYAxis = connectingRectangleYAxisLength
                - numberOfColumnsToOverlapAlongYAxisOfRegion; // = 10 - 2

        for (int parentColumnX = 0; parentColumnX < parentRegionXAxisLength; parentColumnX++) {
            for (int parentColumnY = 0; parentColumnY < parentRegionYAxisLength; parentColumnY++) {

                // xStart = 0, 8, 16, 24, 32, 40, 48, 56
                // yStart = 0, 8, 16, 24, 32, 40, 48, 56
                int xStart = parentColumnX * shiftAmountXAxis;
                int yStart = parentColumnY * shiftAmountYAxis;

                // xEnd = 10, 18, 26, 34, 42, 50, 58, 66
                // yEnd = 10, 18, 26, 34, 42, 50, 58, 66
                int xEnd = xStart + connectingRectangleXAxisLength;
                int yEnd = yStart + connectingRectangleYAxisLength;

                Column parentColumn = parentRegionColumns[parentColumnX][parentColumnY];

                for (int childColumnX = xStart; childColumnX < xEnd; childColumnX++) {
                    for (int childColumnY = yStart; childColumnY < yEnd; childColumnY++) {

                        for (Neuron childColumnXYNeuron : childRegionColumns[childColumnX][childColumnY]
                                .getNeurons()) {
                            // # of synapses connected/add =
                            // connectingRectangleXAxisLength *
                            // connectingRectangleYAxisLength *
                            // column.getNeurons.length
                            parentColumn.getProximalSegment().addSynapse(
                                    new Synapse<Cell>(childColumnXYNeuron,
                                            childColumnX, childColumnY));
                        }
                    }
                }
            }
        }
    }
}
