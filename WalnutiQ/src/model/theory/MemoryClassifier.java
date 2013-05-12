package model.theory;

import java.util.HashSet;
import model.MARKII_Column;
import java.util.Set;

/**
 * This class represents attention levels of ideas within a memory object due to
 * current input on a trained or untrained region.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | May 11, 2013
 */
public class MemoryClassifier {
    private Set<MARKII_Column> regionOutput;
    private Memory memory;

    public MemoryClassifier(Memory memory) {
	this.regionOutput = new HashSet<MARKII_Column>();
	this.memory = memory;
    }

    /**
     * Update all Idea objects within Memory with any different Columns that are
     * becoming active. No Column objects are removed from any Idea objects.
     *
     * @param regionOutput
     *            Active Columns of the Region object after executing the
     *            spatial pooler algorithm once.
     */
    public void updateIdeas(Set<MARKII_Column> regionOutput) {
	// update Region output with it's activeColumns
	this.regionOutput = regionOutput;

	// compare which set of columns in memories has the greatest
	// intersection with regionOutput
	Set<MARKII_Column> intersectionSet = new HashSet<MARKII_Column>(
		this.regionOutput);

	// iterate through all ideas in memory
	for (Idea idea : this.memory.getIdeas()) {
	    // intersection between two sets of Column objects
	    intersectionSet.retainAll(idea.getColumns());
	    int intersectionSetSize = intersectionSet.size();

	    // compute overlap of Region output with ideas in memory
	    float attention = ((float) intersectionSetSize)
		    / ((float) idea.getColumns().size());
	    float attentionPercentage = attention * 100;

	    // Because a Idea object in the HashSet will be changed and thus
	    // have it's hash code changed, the Idea object must be returned,
	    // the original object removed from the HashSet, the object's
	    // fields changed, and then add it back to the HashSet.
	    this.memory.removeIdea(idea);
	    idea.setAttentionPercentage(attentionPercentage);
	    this.memory.addIdea(idea);
	}
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n=============================");
        stringBuilder.append("\n------Neocortex Activity-----");
        for (Idea idea : this.memory.getIdeas())
        {
            stringBuilder.append("\n" + idea.getName() + ": "
                + idea.getAttentionPercentage() + "%");
        }
        stringBuilder.append("\nRegion ouput Column number: ");
        stringBuilder.append(this.regionOutput.size());
        stringBuilder.append("\n=============================");
        String brainActivityInformation = stringBuilder.toString();
        return brainActivityInformation;
    }
}
