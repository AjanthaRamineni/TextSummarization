
public class Proper_Noun_Feature {
	public static float[][] calculateProperNounFeature(int paragraph_count,String[][] segment_output,String[][][] postagger_output,int[][] sentence_length){
		float[][] properNounFeature=new float[150][10];
		int proper_noun_counter=0;
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				proper_noun_counter=0;
				for(int k=0;k<postagger_output[i][j].length;k++){
					if((postagger_output[i][j][k].equals("NNP"))||
							(postagger_output[i][j][k].equals("NNPS"))){
						proper_noun_counter++;
					}
				}
				properNounFeature[i][j]=((float)proper_noun_counter/(float)sentence_length[i][j]);
			}
		}
		
		return properNounFeature;
	}
}
