
public class Similarity_Feature {
	public static float[] calculateSimilarityFeature(float[][] tw,int t) throws Exception{
		float[] similarityFeature=new float[t];
		float[] simsum=new float[t];
		float[][] sim=new float[t][t];
		float twsum=0,v1,v2,twsum1=0,twsum2=0,simmax;
		int min;
		
		
		
		for(int i=0;i<t;i++){
			sim[i][i]=0;
		}
		
		for(int i=0;i<t-1;i++){
			for(int j=i+1;j<t;j++){
				
				min=tw[i].length<tw[j].length?tw[i].length:tw[j].length;
				
				twsum=0;
				twsum1=0;
				twsum2=0;
				
				for(int k=0;k<min;k++){
					twsum=twsum+(tw[i][k]*tw[j][k]);
				
				//for(int k=0;k<tw[i].length;k++)
					twsum1=twsum1+(float)(Math.pow(tw[i][k], 2));
				
				//for(int k=0;k<tw[j].length;k++)
					twsum2=twsum1+(float)(Math.pow(tw[j][k], 2));
				}
					
				v1=(float)(Math.sqrt(twsum1));
				v2=(float)(Math.sqrt(twsum2));
				sim[i][j]=(twsum/(v1*v2));
				sim[j][i]=sim[i][j];
			}
		}
		
		for(int i=0;i<t;i++){
			for(int j=0;j<t;j++){
				simsum[i]=simsum[i]+sim[i][j];
			}
		}
		
		simmax=simsum[0];
		
		for(int i=0;i<t;i++){
			if(simmax<simsum[i])
				simmax=simsum[i];
		}
		
		for(int i=0;i<t;i++){
			similarityFeature[i]=simsum[i]/simmax;
		}
		
		/*for(int i=0;i<t;i++){
			System.out.println(String.valueOf(similarityFeature[i]));
		}*/
		
		return similarityFeature;
	}
}
