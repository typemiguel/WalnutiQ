package model.util;

import java.util.Comparator;
import model.Column;

// -------------------------------------------------------------------------
/**
 * This class overrides the compare method of abstract class Comparator to
 * compare two Column objects based on their overlapScores. Create a comparator
 * object like the following in the class that needs an array of Columns sorted
 * based on a field within the Column objects.
 *
 * Comparator<Column> comparator = new CompareColumn();
 *
 * Then call Array.sort(arrayOfColumnObjectsToBeSorted,
 * comparator); arrayOfColumnObjectsToBeSorted is now sorted.
 *
 * @author Huanqing
 * @version Jan 9, 2013
 */
public class CompareColumn
    implements Comparator<Column>
{
    /**
     * Compares two Column objects based on their overlap scores.
     *
     * @param leftColumn
     *            The left-hand side of the comparison.
     * @param rightColumn
     *            The right-hand side of the comparison.
     * @return A negative integer if the left Column object's overlapScore is
     *         smaller than the right Column object's overlapScore. 0 if the
     *         Column overlapScores are the same. A positive integer if the
     *         right Column objects's overlapScore is greater than the left
     *         Column object's overlapScore
     */
    public int compare(Column leftColumn, Column rightColumn)
    {
        return leftColumn.getOverlapScore() - rightColumn.getOverlapScore();
    }
}
