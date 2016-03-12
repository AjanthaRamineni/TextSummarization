import java.io.*;


public class Term_Weight_Feature {
	public static void calculateTermWeightFeature(int paragraph_count,String[][] segment_output,
			String[][][] tokenizer_output,int total_sentences,float[][] titleFeature,
			float[][] sentenceLengthFeature,float[][] sentencePositionFeature,
			float[][] properNounFeature,float[][] numericalDataFeature,File fp) throws Exception{
		float[][] termWeightFeature=new float[150][150];
		float[] similarityFeature=new float[150];
		float[][] thematicWordFeature=new float[150][150];
		float[][] totalfeatures = new float[250][250];
		PrintWriter featuresFile=new PrintWriter(new FileWriter("files/features.txt"));
		PrintWriter weightWriter=new PrintWriter(new FileWriter("files/TermWeight.txt"));
		PrintWriter ThematicWriter=new PrintWriter(new FileWriter("files/ThematicWord.txt"));
		PrintWriter titlewriter = new PrintWriter("files/Title_feature.txt");
		PrintWriter nounwriter = new PrintWriter("files/ProperNoun.txt");
		PrintWriter lengthwriter = new PrintWriter("files/SentenceLength.txt");
		PrintWriter poisitionwriter = new PrintWriter("files/Sentencepoisition.txt");
		PrintWriter numberwriter = new PrintWriter("files/Numerical.txt");
		PrintWriter totalwriter = new PrintWriter("files/Totalfeatures.txt");
		String[] stopWordsList={"a","able","about","across","after","all","almost","also","am",
				"among","an","and","any","are","as","at","be","because","been","but","by","can",
				"cannot","could","dear","did","do","does","either","else","ever","every","for",
				"from","get","got","had","has","have","he","her","hers","him","his","how",
				"however","i","if","in","into","is","it","its","just","least","let","like",
				"likely","may","me","might","most","must","my","neither","no","nor","not","of",
				"off","often","on","only","or","other","our","own","rather","said","say","says",
				"she","should","since","so","some","than","that","the","their","them","then",
				"there","these","they","this","tis","to","too","twas","us","wants","was","we",
				"were","what","when","where","which","while","who","whom","why","will","with",
				"would","yet","you","your"};
		
		int[][][] tf=new int[150][150][200];
		int[][][] ni=new int[150][150][200];
		float[][][] tw=new float[150][150][200];
		float[][] twtemp=new float[total_sentences][];
		float[][] twsum=new float[150][150];
		float max=twsum[0][0];
		int flag1=0,flag2=0,flag3=0,match_counter=0;
		int t=0;
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				outer:for(int k=0;k<tokenizer_output[i][j].length;k++){
					match_counter=0;
					flag1=0;
					tf[i][j][k]=1;
					ni[i][j][k]=1;
					
					if((tokenizer_output[i][j][k].equals("."))||
							(tokenizer_output[i][j][k].equals("?")||
							(tokenizer_output[i][j][k].equals("!"))||
							(tokenizer_output[i][j][k].equals(","))||
							(tokenizer_output[i][j][k].equals(":"))||
							(tokenizer_output[i][j][k].equals(";"))||
							(tokenizer_output[i][j][k].equals("("))||
							(tokenizer_output[i][j][k].equals(")"))||
							(tokenizer_output[i][j][k].equals("'"))||
							(tokenizer_output[i][j][k].equals("'s")))){
							continue;
						}
						else{
							for(int l=0;l<stopWordsList.length;l++){
								if(tokenizer_output[i][j][k].equals(stopWordsList[l])){
									continue outer;
								}
							}
						}
					
					inner:for(int l=0;l<=i;l++){
						for(int m=0;m<segment_output[l].length;m++){
							flag3=0;
							for(int n=0;n<tokenizer_output[l][m].length;n++){
								flag2=0;
								if(l==i&&m==j&&n==k){
									break inner;
								}
								if(tokenizer_output[i][j][k].equals(tokenizer_output[l][m][n])){
										flag1=1;
										if(l!=i||m!=j)
											flag2=1;
										match_counter++;
										tf[l][m][n]++;
								}
								if(flag2==1){
										ni[l][m][n]++;
									if(flag3==0){
										ni[i][j][k]++;
									}
									flag3=1;
								}
							}
						}
					}
					if(flag1==1){
						tf[i][j][k]+=match_counter;
					}
				}
			}
		}
		
		
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					tw[i][j][k]=(float)(tf[i][j][k]*(Math.log10(total_sentences/ni[i][j][k])));
					twsum[i][j]+=tw[i][j][k];
				}
			}
		}
		
		
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				if(max<twsum[i][j]){
					max=twsum[i][j];
				}
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				termWeightFeature[i][j]=twsum[i][j]/max;
				//System.out.println(String.valueOf(termWeightFeature[i][j]));
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				twtemp[t]=new float[tokenizer_output[i][j].length];
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					twtemp[t][k]=tw[i][j][k];
				}
				t++;
			}
		}
		
		
		
		similarityFeature=Similarity_Feature.calculateSimilarityFeature(twtemp, t);
		thematicWordFeature= Thematic_Word_Feature.calculateThematicWordFeature( paragraph_count, segment_output, tokenizer_output,  fp);
		
		int x=0;
		int y=0;
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				weightWriter.println(termWeightFeature[i][j]);
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				featuresFile.println(titleFeature[i][j]+"\t"
										+sentenceLengthFeature[i][j]+"\t"
										+termWeightFeature[i][j]+"\t"
										+sentencePositionFeature[i][j]+"\t"
										+similarityFeature[x++]+"\t"
										+properNounFeature[i][j]+"\t"
										+thematicWordFeature[i][j]+"\t"
										+numericalDataFeature[i][j]);
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				ThematicWriter.println(thematicWordFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				titlewriter.println(titleFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				lengthwriter.println(sentenceLengthFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				poisitionwriter.println(sentencePositionFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				nounwriter.println(properNounFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				numberwriter.println(numericalDataFeature[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				totalfeatures[i][j]=titleFeature[i][j]+sentenceLengthFeature[i][j]+termWeightFeature[i][j]+sentencePositionFeature[i][j]+properNounFeature[i][j]+thematicWordFeature[i][j]+numericalDataFeature[i][j];
				totalwriter.println(totalfeatures[i][j]);
			}
		}
		weightWriter.close();
		featuresFile.close();
		ThematicWriter.close();
		titlewriter.close();
		nounwriter.close();
		lengthwriter.close();
		poisitionwriter.close();
		numberwriter.close();
		totalwriter.close();
	}
}
