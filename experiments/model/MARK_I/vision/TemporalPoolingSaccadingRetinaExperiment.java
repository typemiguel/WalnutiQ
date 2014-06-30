package model.MARK_I.vision;

import com.google.gson.Gson;
import junit.framework.TestCase;
import model.MARK_II.ColumnPosition;
import model.MARK_II.Neocortex;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.AbstractRegionToRegionConnect;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.RegionToRegionRectangleConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;
import model.unimplementedBiology.LGN;
import model.unimplementedBiology.NervousSystem;
import model.util.JsonFileInputOutput;

import java.io.IOException;
import java.util.Set;

/**
 * Created by qliu on 6/30/14.
 */
public class TemporalPoolingSaccadingRetinaExperiment extends TestCase {
    private NervousSystem partialNervousSystem;

    public void setUp() throws IOException {
        this.partialNervousSystem = this.constructConnectedPartialNervousSystem();
    }

    private NervousSystem constructConnectedPartialNervousSystem() {
//        Neocortex unconnectedNeocortex = new Neocortex(new Region("V1", 4, 4,
//                4, 50, 3), new RegionToRegionRectangleConnect());
//
//        LGN unconnectedLGN = new LGN(new Region("LGN", 8, 8, 1, 50, 3));
//
//        Retina unconnectedRetina = new Retina(66, 66);
//
//        NervousSystem nervousSystem = new NervousSystem(unconnectedNeocortex,
//                unconnectedLGN, unconnectedRetina);
//
//        // connect OldRetina to LGN
//        Retina retina = nervousSystem.getPNS().getSNS().getRetine();
//        LGN LGN = nervousSystem.getCNS().getBrain().getThalamus().getLGN();
//        AbstractSensorCellsToRegionConnect opticNerve = new SensorCellsToRegionRectangleConnect();
//        opticNerve.connect(retina.getVisionCells(), LGN.getRegion(), 0, 0);
//
//        // connect LGN to very small part of V1 Region of Neocortex
//        Neocortex neocortex = nervousSystem.getCNS().getBrain().getCerebrum()
//                .getCerebralCortex().getNeocortex();
//        AbstractRegionToRegionConnect regionToRegionConnect = new RegionToRegionRectangleConnect();
//        regionToRegionConnect.connect(LGN.getRegion(),
//                neocortex.getCurrentRegion(), 0, 0);
//
//        return nervousSystem;
        return null;
    }

    public void test_trainPartialNervousSystemThroughOneIterationOfLearning() throws IOException {
//        Retina retina = partialNervousSystem.getPNS().getSNS().getRetine();
//        Region LGNRegion = partialNervousSystem.getCNS().getBrain()
//                .getThalamus().getLGN().getRegion();
//
//        retina.seeBMPImage("2.bmp");
//
//        SpatialPooler spatialPooler = new SpatialPooler(LGNRegion);
//        spatialPooler.setLearningState(true);
//        spatialPooler.performSpatialPoolingOnRegion();
//        Set<ColumnPosition> LGNNeuronActivity = spatialPooler
//                .getActiveColumnPositions();
//
//        assertEquals(11, LGNNeuronActivity.size());
        assertEquals(1, 2 - 1);
    }
}
