package model.MARK_I.observableDesign;

/**
 * Observable Design Pattern:
 *
 * 1) Observable is a class & Observer is an interface 2) Observable class
 * maintains a list of observers 3) When an Observable object is updated it
 * invokes the update() method of each of its observers to notify that it has
 * changed
 */
public class UpdatableConnectionTest extends junit.framework.TestCase {
    /**
     * SimpleRetina extends Observable & notifies observers when it's state
     * changes.
     */
    private SimpleRetina retina;

    /**
     * Inside SimpleRegion there is 1 Column. Inside Column there is 1 Synapse.
     * Synapse implements Observer with an update method that is called when
     * retina's state changes.
     */
    private SimpleRegion region;

    private SimpleSpatialPooler spatialPooler;

    public void setUp() {
	this.retina = new SimpleRetina();
	this.region = new SimpleRegion();
	SensorCellToColumnRectangleConnect retinaToRegion = new SensorCellToColumnRectangleConnect();
	retinaToRegion.connect(this.retina.getVisionCells(),
		this.region.getColumn());

	this.spatialPooler = new SimpleSpatialPooler(this.region);
    }

    public void testSee2DifferentImages() {
	assertFalse(this.spatialPooler.performSpatialPoolingOnRegion());

	this.retina.seeBMPImage("black");
	assertTrue(this.spatialPooler.performSpatialPoolingOnRegion());

	this.retina.seeBMPImage("white");
	assertFalse(this.spatialPooler.performSpatialPoolingOnRegion());
    }
}
