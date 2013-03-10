package model.theory;

import java.io.Serializable;
import model.util.ColumnLocation;
import model.Column;
import java.util.HashSet;
import java.util.Set;

/**
 * Hypothesis: An idea = an millisecond activation state of a set of cells,
 * segments, and synapses allowed by the connection strength of a regions
 * distributed synapse permanence values(connection strengths).
 */
public class Idea implements Serializable
{
    private String              name;

    private float               attentionPercentage;

    // stores the millisecond activation state of a sparse distributed set of
    // cells created by segments and the connection strength of synapses
    private Set<ColumnLocation> columnLocations;

    /**
     * Instantiate a new Idea object.
     * @param ideaName The idea object's name.
     */
    public Idea(String ideaName)
    {
        this.name = ideaName;
        this.attentionPercentage = 0;
        this.columnLocations = new HashSet<ColumnLocation>();
    }

    /**
     * Instantiate a new Idea object.
     * @param ideaName The idea object's name.
     * @param columns The unique set of columns that represent this idea.
     */
    public Idea(String ideaName, Set<Column> columns)
    {
        this.name = ideaName;
        this.attentionPercentage = 0;
        this.columnLocations = Idea.convertToColumnLocations(columns);
    }

    /**
     * @return Name of this idea object.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return AttentionPercentage of this idea object to the current region
     * output during training.
     */
    public float getAttentionPercentage()
    {
        return this.attentionPercentage;
    }

    /**
     * @param attentionPercentage This idea object's attentionPercentage.
     * @return This idea object's attentionPercentage.
     */
    public boolean setAttentionPercentage(float attentionPercentage)
    {
        if (attentionPercentage >= 0 && attentionPercentage <= 100)
        {
            this.attentionPercentage = attentionPercentage;
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * @return This idea object's set of columnLocations.
     */
    public Set<ColumnLocation> getColumnLocations()
    {
        return this.columnLocations;
    }


    /**
     * @param columns The Column objects to be converted to ColumnLocation objects.
     * @return The converted set of ColumnLocation objects.
     */
    public static Set<ColumnLocation> convertToColumnLocations(
        Set<Column> columns)
    {
        Set<ColumnLocation> columnLocations = new HashSet<ColumnLocation>();

        for (Column column : columns)
        {
            ColumnLocation columnLocation = new ColumnLocation();
            columnLocation.setX(column.getX());
            columnLocation.setY(column.getY());
            columnLocations.add(columnLocation);
        }
        return columnLocations;
    }

    /**
     * @param columns The set of columns that might be added if not already in
     * this idea object's set of columns.
     * @return true if new column objects were added to this Idea objects set
     * of columns; otherwise, return false.
     */
    public boolean addColumns(Set<Column> columns)
    {
        Set<ColumnLocation> columnLocations1 = new HashSet<ColumnLocation>();
        columnLocations1 = Idea.convertToColumnLocations(columns);

        // the set within this Idea object is unioned with the parameter columns
        // set
        if (this.columnLocations.containsAll(columnLocations1))
        {
            return false;
        }
        else
        {
            // performs a union of the two sets
            this.columnLocations.addAll(columnLocations1);
            return true;
        }
    }


    /**
     * @param column The column to be removed from this idea if it exists.
     * @return true if a columnLocation was removed from this idea object;
     * otherwise, return false.
     */
    public boolean removeColumn(Column column)
    {
        ColumnLocation columnLocation = new ColumnLocation(column);

        if (this.columnLocations.contains(columnLocation))
        {
            this.columnLocations.remove(columnLocation);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n============================");
        stringBuilder.append("\n------Idea Information------");
        stringBuilder.append("\n                 name:");
        stringBuilder.append(this.getName());
        stringBuilder.append("\n  attentionPercentage: ");
        stringBuilder.append(this.getAttentionPercentage() + " %");
        stringBuilder.append("\n      columnLocations: ");
        stringBuilder.append(this.getColumnLocations());
        stringBuilder.append("\n============================");
        String synapseInformation = stringBuilder.toString();
        return synapseInformation;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Idea))
            return false;

        Idea idea = (Idea)o;

        if (attentionPercentage != idea.attentionPercentage)
            return false;
        if (columnLocations != null ? !columnLocations
            .equals(idea.columnLocations) : idea.columnLocations != null)
            return false;
        if (name != null ? !name.equals(idea.name) : idea.name != null)
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = (int)(31 * result + attentionPercentage);
        result =
            31 * result
                + (columnLocations != null ? columnLocations.hashCode() : 0);
        return result;
    }
}
