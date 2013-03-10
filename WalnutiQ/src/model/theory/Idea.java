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

    // a set of columns represents an idea
    private Set<ColumnLocation> columnLocations;


    public Idea(String idea)
    {
        this.name = idea;
        this.attentionPercentage = 0;
        // this.columnIndices = new

        this.columnLocations = new HashSet<ColumnLocation>();
    }


    public Idea(String idea, Set<Column> columns)
    {
        this.name = idea;
        this.attentionPercentage = 0;
        this.columnLocations = this.convertToColumnLocations(columns);
    }


    public String getName()
    {
        return this.name;
    }


    public float getAttentionPercentage()
    {
        return this.attentionPercentage;
    }


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


    public Set<ColumnLocation> getColumnLocations()
    {
        return this.columnLocations;
    }


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


    public boolean addColumns(Set<Column> columns)
    {
        Set<ColumnLocation> columnLocations = new HashSet<ColumnLocation>();
        columnLocations = this.convertToColumnLocations(columns);

        // the set within this Idea object is unioned with the parameter columns
        // set
        if (this.columnLocations.containsAll(columnLocations))
        {
            return false;
        }
        else
        {
            // performs a union of the two sets
            this.columnLocations.addAll(columnLocations);
            return true;
        }
    }


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
