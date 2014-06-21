package model.MARK_II.connectTypes;

import model.MARK_II.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 19th, 2014
 */
public class RegionToRegionRandomConnect extends AbstractRegionToRegionConnect {

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

        List<Point> allSynapsePositions = new ArrayList<Point>(
                childRegionXAxisLength * childRegionYAxisLength);
        // generate all possible Synapse positions
        for (int x = 0; x < childRegionXAxisLength; x++) {
            for (int y = 0; y < childRegionYAxisLength; y++) {
                allSynapsePositions.add(new Point(x, y));
            }
        }
        Collections.shuffle(allSynapsePositions);

        // ((2+66) * (2+66))/(8*8) = 68*68/64 = 72.25 > 66
        int numberOfSynapsesToAddToColumn = ((numberOfColumnsToOverlapAlongXAxisOfRegion + childRegionXAxisLength) * (numberOfColumnsToOverlapAlongYAxisOfRegion + childRegionYAxisLength))
                / (parentRegionXAxisLength * parentRegionYAxisLength);

        for (int parentColumnX = 0; parentColumnX < parentRegionXAxisLength; parentColumnX++) {
            for (int parentColumnY = 0; parentColumnY < parentRegionYAxisLength; parentColumnY++) {

                // add numberOfSynapsesForEachColumn Synapses to each Column
                // randomly
                Column parentColumn = parentRegionColumns[parentColumnX][parentColumnY];
                for (int i = 0; i < numberOfSynapsesToAddToColumn; i++) {
                    int randomSynapseXPosition = allSynapsePositions.get(i).x;
                    int randomSynapseYPosition = allSynapsePositions.get(i).y;

                    for (Neuron childColumnXYNeuron : childRegionColumns[randomSynapseXPosition][randomSynapseYPosition]
                            .getNeurons()) {
                        parentColumn.getProximalSegment().addSynapse(
                                new Synapse<Cell>(childColumnXYNeuron,
                                        randomSynapseXPosition,
                                        randomSynapseYPosition));
                    }
                }
            }
        }
    }

    /**
     * @param minimum
     * @param maximum Must be greater than min.
     * @return Integer between minimum and maximum inclusive.
     */
    public int randomIntegerInRange(int minimum, int maximum) {
        Random randomGenerator = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNumber = randomGenerator.nextInt((maximum - minimum) + 1)
                + minimum;

        return randomNumber;
    }

}
