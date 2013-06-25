package model.util;

import model.MARK_II.Cell;
import model.MARK_II.Column;
import model.MARK_II.Region;
import model.MARK_II.Synapse;
import java.util.Set;
import model.MARK_II.ConnectTypes.RegionToRegionConnect;
import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 24, 2013
 */
public class SynapsePermanencesViewer {
    private int greatestSynapseXIndex;
    private int greatestSynapseYIndex;
    private int squareLength = 9;
    private int distanceBetweenSquares = 1;

    public JPanel createContentPane(Region region) {
	JPanel synapseLayer = new JPanel();
	synapseLayer.setLayout(null);

	Column[][] columns = region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[x].length; y++) {
		Set<Synapse<Cell>> synapses = columns[x][y]
			.getProximalSegment().getSynapses();
		System.out.println("synapses size: " + synapses.size());
		for (Synapse synapse : synapses) {
		    int permanenceRepresentedAsGreyScale = 0;
		    Color greyScaleValue;
		    if (!synapse.isConnected()) {
			greyScaleValue = new Color(255, 0, 0);
		    } else { // synapse is connected
			     // permanenceValue between 0.2 and 1.0
			permanenceRepresentedAsGreyScale = (int) (255 * synapse
				.getPermanenceValue());
			System.out.println("additionalRed: "
				+ permanenceRepresentedAsGreyScale);
			greyScaleValue = new Color(
				permanenceRepresentedAsGreyScale,
				permanenceRepresentedAsGreyScale,
				permanenceRepresentedAsGreyScale);
		    }

		    JPanel redSquare = new JPanel();
		    redSquare.setBackground(greyScaleValue);
		    redSquare.setSize(this.squareLength, this.squareLength);

		    int synapseX = synapse.getCellXPosition();
		    int synapseY = synapse.getCellYPosition();
		    // calculate the correct location
		    int topLeftX = synapseX * squareLength + synapseX
			    * distanceBetweenSquares;
		    int topLeftY = synapseY * squareLength + synapseY
			    * distanceBetweenSquares;
		    redSquare.setLocation(topLeftX, topLeftY);

		    synapseLayer.add(redSquare);
		}

	    }
	}
	synapseLayer.setOpaque(true);
	return synapseLayer;
    }

    public SynapsePermanencesViewer(Region region) {
	String biologicalName = region.getBiologicalName();
	JFrame frame = new JFrame(biologicalName + " proximal segment synapse permanences");
	this.findLowerLayerDimensions(region);
	frame.setContentPane(this.createContentPane(region));

	int frameX = this.greatestSynapseXIndex * this.squareLength
		+ this.greatestSynapseXIndex * this.distanceBetweenSquares + 3
		* this.squareLength;
	int frameY = this.greatestSynapseYIndex * this.squareLength
		+ this.greatestSynapseYIndex * this.distanceBetweenSquares + 5
		* this.squareLength;
	frame.setSize(frameX, frameY);
	frame.setVisible(true);
    }

    void findLowerLayerDimensions(Region region) {
	// get largest Synapse position of largest Column position
	Column[][] allColumns = region.getColumns();
	Column columnWithLargestIndex = allColumns[region.getXAxisLength() - 1][region
		.getYAxisLength() - 1];
	// now find input layer x and y axis lengths whether the input layer
	// is a SensorCellLayer or a Region
	Set<Synapse<Cell>> synapses = columnWithLargestIndex
		.getProximalSegment().getSynapses();
	for (Synapse synapse : synapses) {
	    if (synapse.getCellXPosition() > this.greatestSynapseXIndex) {
		this.greatestSynapseXIndex = synapse.getCellXPosition();
	    }
	    if (synapse.getCellYPosition() > this.greatestSynapseYIndex) {
		this.greatestSynapseYIndex = synapse.getCellYPosition();
	    }
	}
	// above computation finds largest index in a 2D array which is always
	// one less in both x and y axis
	this.greatestSynapseXIndex += 1;
	this.greatestSynapseYIndex += 1;
    }

    public static void main(String[] args) {
	Region parentRegion = new Region("V2", 8, 8, 1, 20, 1);
	Region childRegion = new Region("V1", 64, 64, 1, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, parentRegion, 0, 0);

	SynapsePermanencesViewer object = new SynapsePermanencesViewer(
		parentRegion);
    }
}
