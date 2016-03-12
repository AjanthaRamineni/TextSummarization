
public class Sentence_Length_Feature {
	public static float[][] calculateSentenceLengthFeature(int paragraph_count,String[][] segment_output,int[][] sentence_length){
		float sentenceLengthFeature[][]=new float[150][150];
		int max=sentence_length[0][0];
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				if(max<sentence_length[i][j]){
					max=sentence_length[i][j];
				}
			}
		}
		System.out.println("Max Vaulue"+max);
		System.out.println("Sentence Length"+sentence_length[0][0]);
		for(int i=0;i<sentence_length.length;i++){
			for(int j=0;j<sentence_length[i].length;j++){
				sentenceLengthFeature[i][j]=((float)sentence_length[i][j])/((float)max);
			}
		}
		return sentenceLengthFeature;
	}
}
