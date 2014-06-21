package model.MARK_II.parameters;

import model.MARK_II.Cell;
import model.MARK_II.Synapse;
import model.MARK_II.VisionCell;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 30, 2013
 */
public class ResetModelParametersTest extends junit.framework.TestCase {

    private Synapse synapse;

    public void setUp() {
        this.synapse = new Synapse<Cell>(new VisionCell(), 0, 0);
    }

    public void test_reset() {
        assertEquals(0.3, this.synapse.getPermanenceValue());
        this.synapse.increasePermanence();
        assertEquals(0.32, this.synapse.getPermanenceValue());
        ResetModelParameters.reset(0.3, 0.005, 0.2, 0.3, 0.2, 0.005, 0.01);
        this.synapse.increasePermanence();
        assertEquals(0.62, this.synapse.getPermanenceValue());
    }
}
