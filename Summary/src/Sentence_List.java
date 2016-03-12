import java.io.BufferedReader;
import java.io.FileReader;


public class Sentence_List {
	public static int[] getList(int total_sentences) throws Exception{
		int[] sentenceIndices;
			sentenceIndices=new int[(int)Math.ceil(total_sentences*0.2)];
		float[][] indices=new float[total_sentences][2];
		float temp1,temp2;
		String[] defuzzifiedValues=new String[total_sentences];
		
		BufferedReader br=new BufferedReader(new FileReader("files/Totalfeatures.txt"));
		//System.out.println("Helo");
		System.out.println("Total sENTENCES"+total_sentences);
		for(int i=0;i<total_sentences;i++){
			defuzzifiedValues[i]=br.readLine();
			defuzzifiedValues[i]=defuzzifiedValues[i].trim();
			//defuzzifiedValues[i]=defuzzifiedValues[i].substring(0,9);
			indices[i][0]=i;
			indices[i][1]=Float.parseFloat(defuzzifiedValues[i]);
			System.out.println(indices[i][0]+"\t"+indices[i][1]);
		}
		System.out.println("Helo");
		for(int i=0;i<total_sentences-1;i++){
			for(int j=i+1;j<total_sentences;j++){
				if(indices[i][1]<indices[j][1]){
					temp1=indices[i][0];
					temp2=indices[i][1];
					indices[i][0]=indices[j][0];
					indices[i][1]=indices[j][1];
					indices[j][0]=temp1;
					indices[j][1]=temp2;
				}
			}
		}
		
		for(int i=0;i<sentenceIndices.length;i++){
			sentenceIndices[i]=(int)indices[i][0];
			System.out.println(indices[i][0]+"\t"+indices[i][1]);
		}
		
		for(int i=0;i<sentenceIndices.length-1;i++){
			for(int j=i+1;j<sentenceIndices.length;j++){
				if(sentenceIndices[i]>sentenceIndices[j]){
					temp1=sentenceIndices[i];
					sentenceIndices[i]=sentenceIndices[j];
					sentenceIndices[j]=(int)temp1;
				}
			}
		}
		
		for(int i=0;i<sentenceIndices.length;i++){
			System.out.println(String.valueOf(sentenceIndices[i]));
		}
		
		br.close();
		
		return sentenceIndices;
	}
}