
public class Sentence_Position_Feature {
	public static float[][] calculateSentencePositionFeature(int paragraph_count,String[][] segment_output){
		float sentencePositionFeature[][]=new float[150][150];
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				sentencePositionFeature[i][j]=1-((float)j)/((float)segment_output[i].length);
			}
		}
		
		return sentencePositionFeature;
	}
}
