package model.MARK_I.observableDesign;

public class UpdatableConnectionTest extends junit.framework.TestCase {
    private SimpleRetina retina;
    //private SimpleRegion region;
    //private SimpleSpatialPooler spatialPooler;

    public void setUp() {
	this.retina = new SimpleRetina();

	// need to make Region contain a Retina

	// TODO:
	// Region needs to contain lower Region or lower Retina or lower
	// input space
    }
}
