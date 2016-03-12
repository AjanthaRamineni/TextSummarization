import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;


public class POSTagging {
	public static String[] postagger(String tokenizer_output[]) throws Exception{
		InputStream modelIn =  new FileInputStream("en-pos-maxent.bin");
		POSModel model = new POSModel(modelIn);
		POSTaggerME tagger = new POSTaggerME(model);
		String postagger_output[] = tagger.tag(tokenizer_output);
		modelIn.close();
		return postagger_output;
	}
}