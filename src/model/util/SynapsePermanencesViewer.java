package model.util;

import model.MARK_I.Cell;
import model.MARK_I.Column;
import model.MARK_I.Region;
import model.MARK_I.Synapse;

import java.awt.Dimension;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.Set;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Creates a Grid where each colored square represents a non overlapping Synapse
 * of a Region's 2d array of proximal Segments. The square's color represents
 * the Synapse's permanenceValue based on the following scale:
 * BLACK = Synapse not connected to Cell;
 * DARK RED ~= 0.25;
 * LIGHT RED ~= 1.0
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK I | July 29, 2013
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
			greyScaleValue = new Color(0, 0, 0);
		    } else { // synapse is connected
			     // permanenceValue between 0.2 and 1.0
			permanenceRepresentedAsGreyScale = (int) (255 * synapse
				.getPermanenceValue());
			greyScaleValue = new Color(
				permanenceRepresentedAsGreyScale,
				0,
				0);
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
	// Open a saved JSON Region file here...
	String regionAsString = JsonFileInputOutput
		.openObjectInTextFile("./tests/model/util/test_saveRegionObject.txt");
	Gson gson2 = new Gson();
	Region LGNRegion = gson2.fromJson(regionAsString, Region.class);

	SynapsePermanencesViewer object = new SynapsePermanencesViewer(
		LGNRegion);
    }
}
