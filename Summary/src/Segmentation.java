import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class Segmentation {

	/**
	 * @param args
	 */
	public static String[] segment(String paragraph) throws Exception{
		// TODO Auto-generated method stub
		InputStream modelIn = new FileInputStream("E:/JavaCVProjects/Summerizer2/en-sent.bin");
		SentenceModel model = new SentenceModel(modelIn);
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
		String sentences[] = sentenceDetector.sentDetect(paragraph);
		//for(int i=0;i<sentences.length;i++){
		//	System.out.println(sentences[i]);
		//}
		modelIn.close();
		return sentences;
	}
}