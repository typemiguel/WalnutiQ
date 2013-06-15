package model.theory;

import java.util.HashSet;
import java.util.Set;

/**
* @author Quinn Liu (quinnliu@vt.edu)
* @version MARK II | June 15, 2013
*/
public class Memory {
    // pick the Idea that matches most closely to the output of a Region
    private Set<Idea> ideas;

    public Memory() {
	this.ideas = new HashSet<Idea>();
    }

    public Set<Idea> getIdeas() {
	return this.ideas;
    }

    public Idea getIdea(String ideaName) {
	if (this.ideas.size() != 0) {
	    // if Idea HashSet becomes too large, make into TreeSet
	    for (Idea idea : this.ideas) {
		if (idea.getName().equals(ideaName)) {
		    return idea;
		} else {
		    return null;
		}
	    }
	    return null;
	} else {
	    return null;
	}
    }

    public boolean addIdea(Idea idea) {
	if (this.isNewIdea(idea)) {
	    this.ideas.add(idea);
	    return true;
	} else {
	    return false;
	}
    }

    private boolean isNewIdea(Idea idea) {
	if (this.ideas.size() != 0) {
	    for (Idea uniqueIdea : this.ideas) {
		// a new Idea can be the same name as another Idea but it must
		// have a different meaning
		// ex. date means taking a girl on a date or the fruit
		if (uniqueIdea.getName().equals(idea.getName())
			|| uniqueIdea.getColumnPositions().equals(
				idea.getColumnPositions())) {
		    return false;
		} else {
		    return true;
		}
	    }
	    return false;
	} else {
	    return true;
	}
    }

    public boolean removeIdea(Idea idea) {
	// TODO: test if this works since Idea objects have
	// a hashset of ColumnPosition objects
	if (this.ideas.contains(idea)) {
	    this.ideas.remove(idea);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ideas == null) ? 0 : ideas.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Memory other = (Memory) obj;
	if (ideas == null) {
	    if (other.ideas != null)
		return false;
	} else if (!ideas.equals(other.ideas))
	    return false;
	return true;
    }
}
