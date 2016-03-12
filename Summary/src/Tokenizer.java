import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


public class Tokenizer{

	/**
	 * @param args
	 */
	public static String[] tokenize(String segment_output) throws Exception{
		// TODO Auto-generated method stub
		InputStream modelIn = new FileInputStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(modelIn);
		TokenizerME tokenizer = new TokenizerME(model);
		String tokens[] = tokenizer.tokenize(segment_output);
		//for(int i=0;i<tokens.length;i++){
		//	System.out.print(tokens[i]+"\t");
		//}
		//System.out.println(tokens.length);
		modelIn.close();
		return tokens;
	}
}