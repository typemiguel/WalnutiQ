package model.theory;

import java.io.Serializable;
import model.util.ColumnLocation;
import java.util.HashSet;
import java.util.Set;


// -------------------------------------------------------------------------
/**
 *  This class models memory by storing a set of idea objects.
 *
 *  @author Quinn Liu
 */
public class Memory implements Serializable
{
    private Set<Idea> ideas;

    /**
     * Create a new Memory object.
     */
    public Memory()
    {
        this.ideas = new HashSet<Idea>();
    }

    /**
     * Place a description of your method here.
     * @return The set of ideas within memory.
     */
    public Set<Idea> getIdeas()
    {
        return this.ideas;
    }

    /**
     * If there is more than one idea in memory with the same name, there
     * is no control over which idea can be returned.
     * @param ideaName The idea to return.
     * @return The idea object within this memory object.
     */
    public Idea getIdea(String ideaName)
    {
        if (this.ideas.size() != 0)
        {
            // if Idea HashSet becomes too large, make into TreeSet
            for (Idea idea : this.ideas)
            {
                if (idea.getName().equals(ideaName))
                {
                    return idea;
                }
                else
                {
                    return null;
                }
            }
            return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * @param idea The idea to be added to memory if new.
     * @return true if this idea has been added to memory; otherwise, the idea
     * was not new and thus not added to memory.
     */
    public boolean addIdea(Idea idea)
    {
        if (this.isNewIdea(idea))
        {
            this.ideas.add(idea);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @param idea The idea to be checked if it is new to memory.
     * @return true if the idea is new; otherwise return false.
     *
     */
    boolean isNewIdea(Idea idea)
    {
        if (this.ideas.size() != 0)
        {
            for (Idea uniqueIdea : this.ideas)
            {
                // a new idea can be the same name as another idea but
                // different meaning
                // ex. date means taking a girl on a date or the fruit
                if (uniqueIdea.getName().equals(idea.getName()) ||
                    uniqueIdea.getColumnLocations().equals(idea.getColumnLocations()))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * @param idea The idea to be removed from memory.
     * @return true if the idea was removed from memory; otherwise return false
     * if idea did not exist in memory.
     */
    public boolean removeIdea(Idea idea)
    {
        if (this.ideas.contains(idea))
        {
            this.ideas.remove(idea);
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
        stringBuilder.append("\n=============================");
        stringBuilder.append("\n-------Ideas in Memory-------");
        for (Idea idea : this.ideas)
        {
            stringBuilder.append("\n" + idea.getName() + ": ");

            int i = 1;
                for (ColumnLocation columnLocation : idea.getColumnLocations())
                {
                    stringBuilder.append("(" + columnLocation.getX() + ", "+ columnLocation.getY() + ")");
                    i++;
                    if (i % 4 ==0)
                    {
                        stringBuilder.append("\n ");
                    }
                }
        }
        stringBuilder.append("\n=============================");
        String brainActivityInformation = stringBuilder.toString();
        return brainActivityInformation;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Memory memory = (Memory)o;

        if (!ideas.equals(memory.ideas))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = 0;
        result += 7 * ideas.hashCode();
        return result;
    }
}
