package model.theory;

import java.util.HashSet;
import java.util.Set;

/**
* @author Quinn Liu (quinnliu@vt.edu)
* @version June 18, 2013
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
	if (ideaName == null) {
            throw new IllegalArgumentException(
        	    "ideaName in Memory class getIdea method cannot be null");
        }
	// if Idea HashSet becomes too large, make into TreeSet
	for (Idea idea : this.ideas) {
	    if (idea.getName().equals(ideaName)) {
		return idea;
	    }
	}
	throw new IllegalStateException("You cannot get an Idea that does not" +
	    		"exist within a Memory object.");
    }

    public void addNewIdea(Idea idea) {
	if (idea == null) {
            throw new IllegalArgumentException(
        	    "idea in Memory class addNewIdea method cannot be null");
        }
	if (this.isNewIdea(idea)) {
	    this.ideas.add(idea);
	}
    }

    boolean isNewIdea(Idea idea) {
	for (Idea uniqueIdea : this.ideas) {
	    // a new Idea can be the same name as another Idea but it must
	    // have a different meaning
	    // ex. date means taking a girl on a date or the fruit
	    if (uniqueIdea.getName().equals(idea.getName())
		&& uniqueIdea.getColumnPositions().equals(
			idea.getColumnPositions())) {
		return false;
	    } else {
		return true;
	    }
	}
	return true;
    }
}
