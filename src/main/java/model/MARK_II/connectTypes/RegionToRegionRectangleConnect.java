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
                        int numberOfColumnsToOverlapAlongNumberOfRows,
                        int numberOfColumnsToOverlapAlongNumberOfColumns) {

        super.checkParameters(childRegion, parentRegion,
                numberOfColumnsToOverlapAlongNumberOfRows,
                numberOfColumnsToOverlapAlongNumberOfColumns);

        Column[][] parentRegionColumns = parentRegion.getColumns();
        int parentRegionNumberOfRows = parentRegionColumns.length; // = 8
        int parentRegionNumberOfColumns = parentRegionColumns[0].length; // = 8

        Column[][] childRegionColumns = childRegion.getColumns();
        int childRegionNumberOfRows = childRegionColumns.length; // = 66
        int childRegionNumberOfColumns = childRegionColumns[0].length; // = 66

        int connectingRectangleNumberOfRows = Math
                .round((childRegionNumberOfRows
                        + numberOfColumnsToOverlapAlongNumberOfRows
                        * parentRegionNumberOfRows - numberOfColumnsToOverlapAlongNumberOfRows)
                        / parentRegionNumberOfRows); // = 10
        int connectingRectangleNumberOfColumns = Math
                .round((childRegionNumberOfColumns
                        + numberOfColumnsToOverlapAlongNumberOfColumns
                        * parentRegionNumberOfColumns - numberOfColumnsToOverlapAlongNumberOfColumns)
                        / parentRegionNumberOfColumns); // = 10

        int shiftAmountInRows = connectingRectangleNumberOfRows
                - numberOfColumnsToOverlapAlongNumberOfRows; // = 10 - 2
        int shiftAmountInColumns = connectingRectangleNumberOfColumns
                - numberOfColumnsToOverlapAlongNumberOfColumns; // = 10 - 2

        for (int parentColumnRowPosition = 0; parentColumnRowPosition < parentRegionNumberOfRows; parentColumnRowPosition++) {
            for (int parentColumnColumnPosition = 0; parentColumnColumnPosition < parentRegionNumberOfColumns; parentColumnColumnPosition++) {

                // rowStart = 0, 8, 16, 24, 32, 40, 48, 56
                // columnStart = 0, 8, 16, 24, 32, 40, 48, 56
                int rowStart = parentColumnRowPosition * shiftAmountInRows;
                int columnStart = parentColumnColumnPosition * shiftAmountInColumns;

                // rowEnd = 10, 18, 26, 34, 42, 50, 58, 66
                // columnEnd = 10, 18, 26, 34, 42, 50, 58, 66
                int rowEnd = rowStart + connectingRectangleNumberOfRows;
                int columnEnd = columnStart + connectingRectangleNumberOfColumns;

                Column parentColumn = parentRegionColumns[parentColumnRowPosition][parentColumnColumnPosition];

                for (int childColumnRowPosition = rowStart; childColumnRowPosition < rowEnd; childColumnRowPosition++) {
                    for (int childColumnColumnPosition = columnStart; childColumnColumnPosition < columnEnd; childColumnColumnPosition++) {

                        for (Neuron childColumnNeuron : childRegionColumns[childColumnRowPosition][childColumnColumnPosition]
                                .getNeurons()) {
                            // # of synapses connected/add =
                            // connectingRectangleXAxisLength *
                            // connectingRectangleYAxisLength *
                            // column.getNeurons.length
                            parentColumn.getProximalSegment().addSynapse(
                                    new Synapse<Cell>(childColumnNeuron,
                                            childColumnRowPosition, childColumnColumnPosition));
                        }
                    }
                }
            }
        }
    }
}
