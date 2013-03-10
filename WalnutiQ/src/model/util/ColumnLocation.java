package model.util;

import java.io.Serializable;
import model.Column;

// TODO: write test class
public class ColumnLocation implements Serializable
{
    private int x;
    private int y;

    public ColumnLocation()
    {
        this.x = -1;
        this.y = -1;
    }

    public ColumnLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public ColumnLocation(Column column)
    {
        this.x = column.getX();
        this.y = column.getY();
    }

    public int getX()
    {
        return this.x;
    }

    public boolean setX(int x)
    {
        if (x >=0)
        {
            this.x = x;
            return true;
        }
        return false;
    }

    public int getY()
    {
        return this.y;
    }

    public boolean setY(int y)
    {
        if (y >=0)
        {
            this.y = y;
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n============================");
        stringBuilder.append("\n-ColumnLocation Information-");
        stringBuilder.append("\nXYCoordinate:");
        stringBuilder.append("(" + this.getX() + ", "
            + this.getY() + ")");
        stringBuilder.append("\n============================");
        String synapseInformation = stringBuilder.toString();
        return synapseInformation;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnLocation other = (ColumnLocation)obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
