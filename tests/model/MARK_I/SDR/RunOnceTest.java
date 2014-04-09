package model.MARK_I.SDR;

import java.io.IOException;

import model.MARK_I.Region;
import model.theory.Idea;

public class RunOnceTest extends junit.framework.TestCase {
    public void setUp() {

    }

    public void testRunOnce() throws IOException {
	RunOnce.getSpatialPoolingScoreWithGivenParametersForMarkNullaModel(50,
		3);
    }
}
