package model.theory;

import model.Column;
import java.util.HashSet;
import java.util.Set;

public class Idea
{
    private String name;

    private float attentionPercentage;

    // a set of columns represents an idea
    private Set<Column> columns;

    public Idea(String idea)
    {
        this.name = idea;
        this.attentionPercentage = 0;
        this.columns = new HashSet<Column>();
    }

    public Idea(String idea, Set<Column> columns)
    {
        this.name = idea;
        this.attentionPercentage = 0;
        this.columns = columns;
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

    public Set<Column> getColumns()
    {
        return this.columns;
    }


    public boolean addColumns(Set<Column> columns)
    {
        // the set within this Idea object is unioned with the parameter columns
        // set
        if (this.columns.containsAll(columns))
        {
            return false;
        }
        else
        {
            this.columns.addAll(columns);
            return true;
        }
    }


    public boolean removeColumn(Column column)
    {
        if (this.columns.contains(column))
        {
            this.columns.remove(column);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Idea)) return false;

        Idea idea = (Idea) o;

        if (attentionPercentage != idea.attentionPercentage) return false;
        if (columns != null ? !columns.equals(idea.columns) : idea.columns != null) return false;
        if (name != null ? !name.equals(idea.name) : idea.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = (int)(31 * result + attentionPercentage);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }
}
