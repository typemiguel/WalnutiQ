package model.MARK_II.connectTypes;

import model.MARK_II.*;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class SensorCellsToRegionRectangleConnect extends
        AbstractSensorCellsToRegionConnect {
    @Override
    public void connect(SensorCell[][] sensorCells, Region region,
                        int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
                        int numberOfColumnsToOverlapAlongYAxisOfSensorCells) {

        super.checkParameters(sensorCells, region,
                numberOfColumnsToOverlapAlongXAxisOfSensorCells,
                numberOfColumnsToOverlapAlongYAxisOfSensorCells);

        Column[][] regionColumns = region.getColumns();
        int regionXAxisLength = regionColumns[0].length;
        int regionYAxisLength = regionColumns.length;

        int sensorCellsXAxisLength = sensorCells[0].length;
        int sensorCellsYAxisLength = sensorCells.length;

        int connectingRectangleXAxisLength = Math
                .round((sensorCellsXAxisLength
                        + numberOfColumnsToOverlapAlongXAxisOfSensorCells
                        * regionXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCells)
                        / regionXAxisLength);
        int connectingRectangleYAxisLength = Math
                .round((sensorCellsYAxisLength
                        + numberOfColumnsToOverlapAlongYAxisOfSensorCells
                        * regionYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCells)
                        / regionYAxisLength);

        int shiftAmountXAxis = connectingRectangleXAxisLength
                - numberOfColumnsToOverlapAlongXAxisOfSensorCells;
        int shiftAmountYAxis = connectingRectangleYAxisLength
                - numberOfColumnsToOverlapAlongYAxisOfSensorCells;
        for (int columnRowPosition = 0; columnRowPosition < regionXAxisLength; columnRowPosition++) {
            for (int columnColumnPosition = 0; columnColumnPosition < regionYAxisLength; columnColumnPosition++) {

                int rowStart = columnRowPosition * shiftAmountXAxis;
                int columnStart = columnColumnPosition * shiftAmountYAxis;

                int rowEnd = rowStart + connectingRectangleXAxisLength;
                int columnEnd = columnStart + connectingRectangleYAxisLength;

                Column column = regionColumns[columnRowPosition][columnColumnPosition];

                for (int sensorCellRowPosition = rowStart; sensorCellRowPosition < rowEnd; sensorCellRowPosition++) {
                    for (int sensorCellColumnPosition = columnStart; sensorCellColumnPosition < columnEnd; sensorCellColumnPosition++) {
                        // # of Synapses connected/add to this proximal Segment
                        // = connectingRectangleXAxisLength *
                        // connectingRectangleYAxisLength
                        column.getProximalSegment().addSynapse(
                                new Synapse<Cell>(
                                        sensorCells[sensorCellRowPosition][sensorCellColumnPosition],
                                        sensorCellRowPosition, sensorCellColumnPosition));
                    }
                }
            }
        }
    }
}
