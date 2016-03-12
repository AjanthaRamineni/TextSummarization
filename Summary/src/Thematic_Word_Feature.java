import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Thematic_Word_Feature {
	public static float[][] calculateThematicWordFeature(int paragraph_count,String[][] segment_output,String[][][] tokenizer_output, File fp) throws Exception{
		float[][] thematicWordFeature=new float[150][150];
		int[][] thematicWordCount=new int[150][150];
		
		String str;
		int x=0;
		//String[] thematicWordList=new String[100];
		List<String> thematicWordList=new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fp));
		
		/*if(category==0){
			br=new BufferedReader(new FileReader("sports.txt"));
		}
		else{
			br=new BufferedReader(new FileReader("technical.txt"));
		}*/
		
		while((str=br.readLine())!=null){
			thematicWordList.add(str);
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					for(int l=0;l<thematicWordList.size();l++){
						if(tokenizer_output[i][j][k].contains(thematicWordList.get(l))){
							thematicWordCount[i][j]++;
						}
					}
				}
			}
		}
		
		int max=thematicWordCount[0][0];
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				if(max<thematicWordCount[i][j]){
					max=thematicWordCount[i][j];
				}
			}
		}
		for(int i=0;i<thematicWordCount.length;i++){
			for(int j=0;j<thematicWordCount[i].length;j++){
				thematicWordFeature[i][j]=((float)thematicWordCount[i][j])/((float)max);
			}
		}
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(thematicWordFeature[i][j]));
			}
		}*/
		
		br.close();
		return thematicWordFeature;
	}
}
