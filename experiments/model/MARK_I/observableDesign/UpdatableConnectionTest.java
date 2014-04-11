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
    private Retina retina;
    //private SimpleRegion region;
    //private SimpleSpatialPooler spatialPooler;

    public void setUp() {
	this.retina = new Retina();

	// need to make Region contain a Retina

	// TODO:
	// Region needs to contain lower Region or lower Retina or lower
	// input space
    }
}
