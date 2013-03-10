package model.util;

import model.theory.Memory;
import model.Region;
import model.theory.MemoryClassifier;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import model.theory.MemoryClassifier;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// -------------------------------------------------------------------------
/**
 * Provides static methods for opening and saving objects used in the model.
 *
 * @author Quinn Liu
 * @version Jan 10, 2013
 */
public class InputOutput
{
    // ----------------------------------------------------------
    /**
     * Saves a Region object at the specified location and file name given.
     *
     * @param region
     *            The Region object to be saved.
     * @param relativePath
     *            The path starting from WalnutIQRepository project folder
     *            including the file name at the end of the path.
     * @return true if Region is saved.
     */
    public static boolean saveRegion(Region region, String relativePath)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(relativePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(region);
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveMemory(Memory memory, String relativePath)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(relativePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(memory);
            oos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Open a Region object with the given fileName.
     *
     * @param relativePath The path of which Region object you want to
     *        return starting from WalnutIQRepository project folder including
     *        the file name at the end of the path.
     * @return The desired Region object.
     */
    public static Region openRegion(String relativePath)
    {
        try
        {
            FileInputStream fis = new FileInputStream(relativePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object readObject = ois.readObject();
            ois.close();

            if (readObject != null && readObject instanceof MemoryClassifier)
            {
                return (Region)readObject;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Memory openMemory(String relativePath)
    {
        try
        {
            FileInputStream fis = new FileInputStream(relativePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object readObject = ois.readObject();
            ois.close();

            if (readObject != null && readObject instanceof Memory)
            {
                return (Memory)readObject;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
