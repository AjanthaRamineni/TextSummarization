
public class Numerical_Data_Feature {
	public static float[][] calculateNumericalDataFeature(int paragraph_count,String[][] segment_output,String[][][] postagger_output,int[][] sentence_length){
		float[][] numericalDataFeature=new float[50][50];
		int numerical_data_counter=0;
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				numerical_data_counter=0;
				for(int k=0;k<postagger_output[i][j].length;k++){
					if(postagger_output[i][j][k].equals("CD")){
						numerical_data_counter++;
					}
				}
				numericalDataFeature[i][j]=((float)numerical_data_counter/(float)sentence_length[i][j]);
			}
		}
		
		return numericalDataFeature;
	}
}
