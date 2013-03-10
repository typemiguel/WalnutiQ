package spatialPooler.vision;

import model.theory.Idea;
import java.util.HashSet;
import model.Column;
import java.util.Set;
import model.util.InputOutput;
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

    private Memory           memory;
    private MemoryClassifier memoryClassifier;


    public void setUp()
    {
        this.bottomRegion = new Region(30, 40, 6, 8, 20.0f, 2, 1);
        this.bottomSpatialPooler = new SpatialPooler(this.bottomRegion);

        this.memory = new Memory();
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
        this.trainSpatialPoolerOnImageIdea(this.memory, this.bottomSpatialPooler,
            "Zero", "zero1.bmp", 1);
//        this.trainSpatialPoolerOnImageIdea(this.memory, this.bottomSpatialPooler,
//            "Zero", "zero1.bmp", 1);

//        // --------------------------Testing----------------------------------
//        this.bottomSpatialPooler.getRegion().setSpatialLearning(false);
//        byte[][] inputData = this.getDoubleByteArrayOfFile("zero0.bmp", this.bottomRegion);
//        this.bottomSpatialPooler.nextTimeStep(inputData);
//
//        Set<Column> activeColumns = new HashSet<Column>();
//        activeColumns.addAll(this.bottomSpatialPooler.performSpatialPoolingOnRegion());
//        this.memoryClassifier.updateIdeas(activeColumns);
//        // -------------------------------------------------------------------

        System.out.println("minimumOverlapScore: " + this.bottomRegion.getMinimumOverlapScore());
        System.out.println(this.memory);
        System.out.println(this.memoryClassifier);


        this.saveRegionAndMemory();
        // TODO: add topRegion to bottomRegion
        // TODO: print synapse permanenceValues as a colored map
    }


    public void trainSpatialPoolerOnImageIdea(
        Memory memory,
        SpatialPooler spatialPooler,
        String ideaName,
        String bmpFileName,
        int numberOfTrainingIterations)
        throws IOException
    {
        spatialPooler.getRegion().setSpatialLearning(true);
        Idea idea = new Idea(ideaName);

        for (int i = 0; i < numberOfTrainingIterations; i++)
        {
            Set<Column> activeColumnsForTraining = new HashSet<Column>();
            byte[][] inputDataBytes =
                this.getDoubleByteArrayOfFile(
                    bmpFileName,
                    spatialPooler.getRegion());

            spatialPooler.nextTimeStep(inputDataBytes);
            activeColumnsForTraining.addAll(spatialPooler
                .performSpatialPoolingOnRegion());

            idea.addColumns(activeColumnsForTraining);
            activeColumnsForTraining.clear();
        }
        memory.addIdea(idea);
    }

    private void saveRegionAndMemory()
    {
        String bottomRegionRelativePath =
            "savedModelObjects/spatialPooler/vision/TrainedBottomRegion_Digits";
        assertTrue(InputOutput.saveRegion(
            this.bottomRegion,
            bottomRegionRelativePath));

         String memoryRelativePath =
         "savedModelObjects/spatialPooler/vision/TrainedMemory_Digits";
         assertTrue(InputOutput.saveMemory(
         this.memory,
         memoryRelativePath));
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
