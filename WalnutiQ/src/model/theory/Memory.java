package model.theory;

import java.util.Iterator;
import model.Segment;
import java.util.HashSet;
import java.util.Set;

public class Memory
{
    // pick the idea that matches most closely to the output of a Region
    private Set<Idea> ideas;

    public Memory()
    {
        this.ideas = new HashSet<Idea>();
    }

    public Set<Idea> getIdeas()
    {
        return this.ideas;
    }

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
                    uniqueIdea.getColumns().equals(idea.getColumns()))
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
