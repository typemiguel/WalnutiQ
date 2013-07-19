package model.util;

import java.awt.Dimension;

import java.io.IOException;
import com.google.gson.Gson;
import model.theory.MemoryClassifier;
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
 * Creates a Grid where each square represents a Synapse of a Region's 2d array
 * of proximal Segments.
 *
 * A square colored red = Synapse is not connected
 *
 * dark grey grey light grey white Synapse permanenceValue = 0.2 0.5 0.8 1.0
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 26, 2013
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
		for (Synapse synapse : synapses) {
		    int permanenceRepresentedAsGreyScale = 0;
		    Color greyScaleValue;
		    if (!synapse.isConnected()) {
			greyScaleValue = new Color(255, 0, 0);
		    } else { // synapse is connected
			     // permanenceValue between 0.2 and 1.0
			permanenceRepresentedAsGreyScale = (int) (255 * synapse
				.getPermanenceValue());
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

    /**
     * This method only works for a Region with no overlapping Synapses on it's
     * Proximal Segments.
     */
    public SynapsePermanencesViewer(Region region) {
	String biologicalName = region.getBiologicalName();
	JFrame frame = new JFrame(biologicalName
		+ " proximal segment synapse permanences");
	Dimension bottomLayerDimensions = region.getBottomLayerXYAxisLength();
	this.greatestSynapseXIndex = bottomLayerDimensions.width;
	this.greatestSynapseYIndex = bottomLayerDimensions.height;
	System.out.println("X: " + this.greatestSynapseXIndex);
	frame.setContentPane(this.createContentPane(region));

	int frameX = this.greatestSynapseXIndex * this.squareLength
		+ this.greatestSynapseXIndex * this.distanceBetweenSquares;
	int frameY = this.greatestSynapseYIndex * this.squareLength
		+ this.greatestSynapseYIndex * this.distanceBetweenSquares;
	// For an currently unknown reason the bottom right corner of the frame
	// is offset by 17 pixels in the x direction and 40 pixels in the right
	// direction. The "+ 17" and "+ 40" are to compensate.
	frame.setSize(frameX + 17, frameY + 40);
	frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
	// Region parentRegion = new Region("V2", 8, 8, 1, 20, 1);
	// Region childRegion = new Region("V1", 64, 64, 1, 20, 3);
	// RegionToRegionConnect connectType = new
	// RegionToRegionRectangleConnect();
	// connectType.connect(childRegion, parentRegion, 0, 0);

	// Open a saved JSON Region file here...
	String regionAsString = JsonFileInputOutput
		.openObjectInTextFile("./tests/model/util/test_saveRegionObject.txt");
	Gson gson2 = new Gson();
	Region LGNRegion = gson2.fromJson(regionAsString, Region.class);

	SynapsePermanencesViewer object = new SynapsePermanencesViewer(LGNRegion);
    }
}
