package model.MARK_I.observableDesign;

/**
 * Observable Design Pattern:
 *
 *  1) Observable is a class & Observer is an interface
 *  2) Observable class maintains a list of observers
 *  3) When an Observable object is updated it invokes the
 *  update() method of each of its observers to notify that
 *  it has changed
 */
public class UpdatableConnectionTest extends junit.framework.TestCase {
    private SimpleRetina retina; // extends Observable & notifies observers
	                   // when it's state changes

    //private SimpleRegion region;
    //private SimpleSpatialPooler spatialPooler;

    public void setUp() {
	this.retina = new SimpleRetina();

    }

    public void testSee2DifferentImages() {

    }
}
