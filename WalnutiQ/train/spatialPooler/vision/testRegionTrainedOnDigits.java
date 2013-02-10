package spatialPooler.vision;

import model.theory.Idea;
import java.util.HashSet;
import model.Column;
import java.util.Set;
import model.util.SaveOpenFile;
import model.Region;
import model.SpatialPooler;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.theory.*;

// -------------------------------------------------------------------------
/**
 * This class will train a SpatialPooler on handwritten digits 0 and 1.
 *
 * @author Quinn
 * @version Dec 4, 2012
 */
public class testRegionTrainedOnDigits
    extends junit.framework.TestCase
{
    private Region           bottomRegion;
    private SpatialPooler    bottomSpatialPooler;
    private Region           topRegion;
    private SpatialPooler    topSpatialPooler;
    private Memory           memory;
    private MemoryClassifier memoryClassifier;


    public void setUp()
    {
        this.bottomRegion = new Region(30, 40, 6, 8, 20.0f, 2, 1);
        this.bottomSpatialPooler = new SpatialPooler(this.bottomRegion);

        this.topRegion = new Region(6, 8, 3, 4, 50.0f, 2, 1);
        this.topSpatialPooler = new SpatialPooler(this.topRegion);

        this.memory = new Memory();
        // TODO: add memory to ideaClassifier after memory has been
        // trained
        this.memoryClassifier = new MemoryClassifier(this.memory);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @throws IOException
     */
    public void testTrainedBottomRegionOnDigitZero()
        throws IOException
    {
        this.trainRegionOnZero();
        this.trainRegionOnOne();

        // --------------------------Testing----------------------------------
        this.bottomSpatialPooler.getRegion().setSpatialLearning(false);
        Set<Column> activeColumns = new HashSet<Column>();

        byte[][] inputData0 =
            this.getDoubleByteArrayOfFile("zero0.bmp", this.bottomRegion);
        this.bottomSpatialPooler.nextTimeStep(inputData0);
        activeColumns.addAll(this.bottomSpatialPooler
            .performSpatialPoolingOnRegion());
        this.memoryClassifier.updateIdeas(activeColumns);
        // -------------------------------------------------------------------
        System.out.println(this.memoryClassifier.toString());
        this.saveAllFiles();
        // TODO: add topRegion to bottomRegion
        // TODO: print synapse permanenceValues as a colored map
    }

    private void trainRegionOnOne() throws IOException
    {
        this.bottomSpatialPooler.getRegion().setSpatialLearning(true);
        Idea oneIdea = new Idea("One");

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero0.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            oneIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero1.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            oneIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero2.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            oneIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        this.memory.addIdea(oneIdea);
    }


    private void trainRegionOnZero()
        throws IOException
    {
        this.bottomSpatialPooler.getRegion().setSpatialLearning(true);
        Idea zeroIdea = new Idea("Zero");

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero0.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            zeroIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero1.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            zeroIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        for (int i = 0; i < 1; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile("zero2.bmp", this.bottomRegion);

            this.bottomSpatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(this.bottomSpatialPooler
                .performSpatialPoolingOnRegion());

            zeroIdea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }

        this.memory.addIdea(zeroIdea);
    }


    private void saveAllFiles()
    {
        // TODO: save BrainActivity object

        String bottomRegionRelativePath =
            "savedModelObjects/htm/spatialPooler/vision/TrainedBottomRegion_Digits";
        assertTrue(SaveOpenFile.saveRegion(
            this.bottomRegion,
            bottomRegionRelativePath));
        /*
        String memoryClassifierRelativePath =
            "savedModelObjects/htm/spatialPooler/vision/MemoryClassifier_Digits";
        assertTrue(SaveOpenFile.saveMemoryClassifier(
            this.memoryClassifier,
            memoryClassifierRelativePath));
        */
    }


    // ----------------------------------------------------------
    /**
     * Returns the converted byte 2-D array of a .bmp file.
     *
     * @param fileName
     *            The name of the file without ".bmp"
     * @param region
     *            The region to be trained on the input data. Needed to match
     *            input data size to region's width and length.
     * @return An inputed .bmp file as a byte 2-D array.
     * @throws IOException
     */
    private byte[][] getDoubleByteArrayOfFile(String fileName, Region region)
        throws IOException
    {
        BufferedImage image = ImageIO.read(getClass().getResource(fileName));
        byte[][] alphaInputData =
            new byte[region.getInputXAxisLength()][region.getInputYAxisLength()];
        for (int x = 0; x < alphaInputData.length; x++)
        {
            for (int y = 0; y < alphaInputData[x].length; y++)
            {
                int color = image.getRGB(x, y);
                alphaInputData[x][y] = (byte)(color >> 23);
            }
        }
        return alphaInputData;
    }
}
