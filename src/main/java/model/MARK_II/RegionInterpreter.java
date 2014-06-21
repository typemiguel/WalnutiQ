package model.MARK_II;

public class RegionInterpreter {
	private Region region;

	public RegionInterpreter(Region region) {
		this.region = region;
	}

	public Float[] getNextRetinaPosition() {

		/*
		 * Visit each neuron. For each visited, alternate among using value as 
		 * X, Y, or Z coordinate. 
		 * Keep a count of the total number visited and use this to calculate percentages. 
		 */
		
		int neuronCounter = 0;

		int rawXValues = 0;
		int rawYValues = 0;
		int rawZValues = 0;
		
		int xValuesCount = 0;
		int yValuesCount = 0;
		int zValuesCount = 0;
		
		Column[][] columns = this.region.getColumns();
		for (int x = 0; x < columns.length; x++) {
			for (int y = 0; y < columns[x].length; y++) {
				for (Neuron n : columns[x][y].getNeurons()) {					
					int neuronValue = (n.getActiveState()) ? 1 : 0;

					switch (neuronCounter % 3) {
						case 0:
							rawXValues += neuronValue;
							xValuesCount++;
							break;
						case 1:
							rawYValues += neuronValue;
							yValuesCount++;
							break;
						case 2:
							rawZValues += neuronValue;
							zValuesCount++;
							break;
					}

					neuronCounter++;
				}
			}
		}

		Float xPercentage = rawXValues / (float) xValuesCount;
		//System.out.println(xPercentage);

		Float yPercentage = rawYValues / (float) yValuesCount;
		//System.out.println(yPercentage);

		Float zPercentage = rawZValues / (float) zValuesCount;
		//System.out.println(zPercentage);
		
		return new Float[]{xPercentage, yPercentage, zPercentage};
	}
}
