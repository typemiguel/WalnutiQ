package model.MARK_II;

import junit.framework.TestCase;

public class RegionInterpreterTest extends TestCase {

    private Region region;
    
    public void setUp() {
    	this.region = new Region("region", 5, 7, 4, 20, 3);
    	this.region.getColumn(0, 0).getNeuron(0).setActiveState(true);
    	this.region.getColumn(0, 0).getNeuron(3).setActiveState(true);
    	// TODO: write more lines like the one above to manually create some 
    	// regions that will create predictable x, y, and z values (at least 3 separate tests)
    }

    public void testGetNextRetinaPosition() {
    	RegionInterpreter interpreter = new RegionInterpreter(this.region);
    	
    	System.out.println(interpreter.getNextRetinaPosition());
    	
    }
}
