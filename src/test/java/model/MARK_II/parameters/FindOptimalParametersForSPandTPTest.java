package model.MARK_II.parameters;

import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 30, 2014
 */
public class FindOptimalParametersForSPandTPTest extends
	junit.framework.TestCase {
    public void setUp() {
	// not necessary since all methods in class FindOptimalParametersForSDR
	// are static(meaning you can directly call them without creating an
	// object)
    }

    public void test_printToFileSDRScoreFor1RetinaTo1RegionModelFor1Digit()
	    throws IOException {
	// 1 7.8 53.8 32.0 557.3
	// 1.000000000000000000000000000000E+00
	// 7.897848242173036048541234777076E+00
	// 5.385647704178850858625082764775E+01
	// 3.201692771346944255128619261086E+01
	// 5.573149187962278119812253862619E+03
	assertEquals(
		-4.004,
		FindOptimalParametersForSPandTP
			.printToFileSPandTPScoreFor1RetinaTo1RegionModelFor1Digit(
				1.000000000000000000000000000000E+00,
				7.897848242173036048541234777076E+00,
				5.385647704178850858625082764775E+01,
				3.201692771346944255128619261086E+01,
				5.573149187962278119812253862619E+03, 0.02,
				0.005, 0.2, 0.3, 0.2, 0.005, 0.01,
				"./src/test/java/model/MARK_II/parameters/SPandTPScore.txt"),
		.00001);
    }
}
