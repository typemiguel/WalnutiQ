package model.MARK_II;

public class RegionInterpreter {
	private Region region;
	public RegionInterpreter(Region region){
		this.region = region;
	}
	
	public String getNextRetinaPosition(){
		Column[][] columns = this.region.getColumns();
    	for (int x = 0; x < columns.length; x++) {
    		for (int y = 0; y < columns[x].length; y++) {
    			for (Neuron n : columns[x][y].getNeurons()){
    				if (n.getActiveState() == true){
    					System.out.println(n.getActiveState());
    				}
    			}
    		}
    	}
    	return "";
	}
}
