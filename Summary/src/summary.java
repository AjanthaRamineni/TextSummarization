import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public  class summary extends JFrame implements ActionListener
{

 JTextArea indoc,outdoc;
 ArrayList als;
 File fp;  
 Hashtable hs;
 double scnt;
 

 public summary()
  {

	Container con=getContentPane();
	con.setLayout(new BorderLayout());			
	con.add(addToolbar(),BorderLayout.NORTH);
	con.add(addwinSplit(),BorderLayout.CENTER);	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	setTitle("Document summarization");
	setSize(800,600);
	setVisible(true);	
	als=new ArrayList();
	hs=new Hashtable();
  }


  public JPanel addToolbar()
   {
	JToolBar jtb=new JToolBar();									
				
	JButton jb4=new JButton("Document");
	jb4.addActionListener(this);
	jb4.setForeground(new Color(200,100,150));	
	jtb.add(jb4);
		
	JButton jb1=new JButton("Sentense");
	jb1.addActionListener(this);
	jb1.setForeground(new Color(200,100,150));	
	jtb.add(jb1);
		
	JButton jb2=new JButton("Stopword");
	jb2.addActionListener(this);
	jb2.setForeground(new Color(200,100,150));	
	jtb.add(jb2);


	JButton jb3=new JButton("Unique word");
	jb3.addActionListener(this);
	jb3.setForeground(new Color(200,100,150));	
	jtb.add(jb3);
			
        JButton jb5=new JButton("Stemming");
	jb5.addActionListener(this);
	jb5.setForeground(new Color(200,100,150));	
	jtb.add(jb5);

	JButton jb10=new JButton("Title Feature");
	jb10.addActionListener(this);
	jb10.setForeground(new Color(200,100,150));	
	jtb.add(jb10);
	
	JButton jb11=new JButton("Length");
	jb11.addActionListener(this);
	jb11.setForeground(new Color(200,100,150));	
	jtb.add(jb11);
	
	JButton jb12=new JButton("SentencePoisition");
	jb12.addActionListener(this);
	jb12.setForeground(new Color(200,100,150));	
	jtb.add(jb12);
	
	JButton jb13=new JButton("ProperNoun");
	jb13.addActionListener(this);
	jb13.setForeground(new Color(200,100,150));	
	jtb.add(jb13);
	
	JButton jb14=new JButton("Numerical");
	jb14.addActionListener(this);
	jb14.setForeground(new Color(200,100,150));	
	jtb.add(jb14);
	
	JButton jb15=new JButton("TermWeight");
	jb15.addActionListener(this);
	jb15.setForeground(new Color(200,100,150));	
	jtb.add(jb15);
	
	JButton jb16=new JButton("ThematicWord");
	jb16.addActionListener(this);
	jb16.setForeground(new Color(200,100,150));	
	jtb.add(jb16);
	
	JButton jb17=new JButton("Summary1");
	jb17.addActionListener(this);
	jb17.setForeground(new Color(200,100,150));	
	jtb.add(jb17);
	
	JButton jb6=new JButton("Significant");
	jb6.addActionListener(this);
	jb6.setForeground(new Color(200,100,150));	
	jtb.add(jb6);
				

	JButton jb7=new JButton("Weight");
	jb7.addActionListener(this);
	jb7.setForeground(new Color(200,100,150));	
	jtb.add(jb7);

        JButton jb8=new JButton("Ranking");
	jb8.addActionListener(this);
	jb8.setForeground(new Color(200,100,150));	
	jtb.add(jb8); 


	JButton jb9=new JButton("Summary");
	jb9.addActionListener(this);
	jb9.setForeground(new Color(200,100,150));	
	jtb.add(jb9); 
						
	JPanel jp=new JPanel();
	jp.setLayout(new GridLayout(1,1));
	jp.add(jtb);		
	return jp;
  }


  public JSplitPane addwinSplit()
     {
	JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	jsp.setDividerLocation(350);
	jsp.setOneTouchExpandable(true);

	indoc=new JTextArea(20,60);			
	indoc.setFont(new Font("New Times Roman",Font.PLAIN,15));
	JScrollPane js1 = new JScrollPane(indoc);			
				
	outdoc=new JTextArea(20,60);
	outdoc.setFont(new Font("New Times Roman",Font.PLAIN,15));
	JScrollPane js2 = new JScrollPane(outdoc);		
		
	jsp.setTopComponent(js1);					
	jsp.setBottomComponent(js2);				
	return jsp;
     }

  public static void coordinate(File file) throws Exception{
	  String paragraph[]=new String[150];
	  String segment_output[][]=new String[150][150];
	  String tokenizer_output[][][]=new String[150][150][200];
	  String postagger_output[][][]=new String[150][150][200];
	  int paragraph_count=0,sentence_length[][]=new int[150][150],
	        sentence_length_counter=0,total_sentences=0,
	 			sentenceIndices[];
	  float sentenceLengthFeature[][]=new float[150][150];
	  float sentencePositionFeature[][]=new float[150][150];
	  float titleFeature[][]=new float[150][150];
	  float properNounFeature[][]=new float[150][150];
	  float numericalDataFeature[][]=new float[150][150];
	  float ThematicwordFeature[][]=new float[150][150];
	  float termWeightFeature[][]=new float[150][150];
	  String[] sentences;
	  PrintWriter summaryFile=new PrintWriter(new FileWriter("files/summary.txt"));
		PrintWriter segmentationFile=new PrintWriter(new FileWriter("files/segmentation.txt"));
		PrintWriter tokenizationFile=new PrintWriter(new FileWriter("files/tokenization.txt"));
		PrintWriter postaggingFile=new PrintWriter(new FileWriter("files/pos-tagging.txt"));
		PrintWriter titlewriter = new PrintWriter("files/Title_feature.txt");
		PrintWriter nounwriter = new PrintWriter("files/ProperNoun.txt");
		PrintWriter lengthwriter = new PrintWriter("files/SentenceLength.txt");
		PrintWriter poisitionwriter = new PrintWriter("files/Sentencepoisition.txt");
		PrintWriter numberwriter = new PrintWriter("files/Numerical.txt");
		PrintWriter weightwriter = new PrintWriter("files/TermWeight.txt");
		titlewriter.flush();
		nounwriter.flush();
		lengthwriter.flush();
		poisitionwriter.flush();
		numberwriter.flush();
		weightwriter.flush();
		BufferedReader reader=new BufferedReader(new FileReader(file));
		while((paragraph[paragraph_count++]=reader.readLine())!=null){
			if(reader.readLine()==null){
				break;
			}
			//System.out.println(paragraph[paragraph_count]);
		}
		System.out.println("No of Paragraphs"+ paragraph_count);
		for(int i=0;i<paragraph_count;i++){
			segment_output[i]=Segmentation.segment(paragraph[i]);
			//System.out.println(segment_output[i]);
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				segmentationFile.println((segment_output[i][j]));
			}
		}
        segmentationFile.close();
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				tokenizer_output[i][j]=Tokenizer.tokenize(segment_output[i][j]);
			}
		}
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					tokenizationFile.print((tokenizer_output[i][j][k]+"\t"));
				}
				tokenizationFile.println();
			}
			tokenizationFile.println();
		}
		
		tokenizationFile.close();
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				postagger_output[i][j]=POSTagging.postagger(tokenizer_output[i][j]);
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					postaggingFile.print((postagger_output[i][j][k]+"\t"));
				}
				postaggingFile.println();
			}
			postaggingFile.println();
		}
		
		postaggingFile.close();
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				total_sentences++;
				sentence_length_counter=0;
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					if((postagger_output[i][j][k].equals("."))||
						(postagger_output[i][j][k].equals("?")||
						(postagger_output[i][j][k].equals("!"))||
						(postagger_output[i][j][k].equals(","))||
						(postagger_output[i][j][k].equals(":"))||
						(postagger_output[i][j][k].equals(";"))||
						(postagger_output[i][j][k].equals("-LRB-"))||
						(postagger_output[i][j][k].equals("-RRB-"))||
						(postagger_output[i][j][k].equals("POS"))||
						(postagger_output[i][j][k].equals("''")))){
						continue;
					}
					else{
						sentence_length[i][j]=++sentence_length_counter;
					}
				}
			}
		}
		/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			System.out.println(String.valueOf(sentence_length[i][j]));
		}
	}*/
	
	sentenceLengthFeature=Sentence_Length_Feature.calculateSentenceLengthFeature(paragraph_count,segment_output,sentence_length);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			lengthwriter.println(String.valueOf(sentenceLengthFeature[i][j]));
		}
	}*/
	
	sentencePositionFeature=Sentence_Position_Feature.calculateSentencePositionFeature(paragraph.length,segment_output);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			poisitionwriter.println(String.valueOf(sentencePositionFeature[i][j]));
		}
	}*/
	
	titleFeature=Title_Feature.calculateTitleFeature(tokenizer_output,paragraph_count,segment_output,sentence_length[0][0]);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			titlewriter.println(String.valueOf(titleFeature[i][j]));
		}
	}*/
	
	properNounFeature=Proper_Noun_Feature.calculateProperNounFeature(paragraph_count, segment_output, postagger_output, sentence_length);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			nounwriter.println(String.valueOf(properNounFeature[i][j]));
		}
	}*/
	
	numericalDataFeature=Numerical_Data_Feature.calculateNumericalDataFeature(paragraph_count, segment_output, postagger_output, sentence_length);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			numberwriter.println(String.valueOf(numericalDataFeature[i][j]));
		}
	}*/
	
	Term_Weight_Feature.calculateTermWeightFeature(paragraph_count,segment_output,tokenizer_output,total_sentences,titleFeature,sentenceLengthFeature,sentencePositionFeature,properNounFeature,numericalDataFeature,file);
	
	/*for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			weightwriter.println(String.valueOf(termWeightFeature[i][j]));
		}
	}*/
	//ThematicwordFeature= Thematic_Word_Feature.calculateThematicWordFeature(paragraph_count,segment_output,tokenizer_output,category);
	//br.close();
	int x=0;
	sentences=new String[total_sentences];
	
	for(int i=0;i<paragraph_count;i++){
		for(int j=0;j<segment_output[i].length;j++){
			sentences[x++]=segment_output[i][j];
			//System.out.println(sentences[x]);
		}
	}
	int l1=x;
	/*for(int k1=0;k1<l1;k1++)
	   System.out.println(sentences[k1]);*/
	sentenceIndices=Sentence_List.getList(total_sentences);
	System.out.println("Hello Selecting Sentences");
	
	String[] summarySentences;
	int t=0;
	if(sentenceIndices[0]!=0){
		//summaryFile.print(sentenceIndices[i]);
		summaryFile.println(sentences[0]);
		summarySentences=new String[sentenceIndices.length+1];
		summarySentences[t++]=sentences[0];
	}
	else{
		summarySentences=new String[sentenceIndices.length];
	}
	int k1;
	
	for(int i=0;i<sentenceIndices.length;i++){
		summaryFile.println("["+sentenceIndices[i]+"]"+sentences[sentenceIndices[i]]);
		summarySentences[t++]=sentences[sentenceIndices[i]];
		
	}
	k1=t;
	for(int i1=0; i1<k1;i1++)
	{
		System.out.println(sentenceIndices[i1]);
	}
	summaryFile.close();
	//br.close();
	
	reader.close();
  }
 public void setdocument(File fp) {

	try {
	    int sz=(int)fp.length();
	    byte bs[]=new byte[sz];			
	    FileInputStream fis=new FileInputStream(fp);
	    fis.read(bs);
	    indoc.setText(new String(bs));   		  			
	    fis.close(); 	
	  }

	catch(IOException ex){}
   }


public void removestopwords() {
  try{
     FileWriter fileWriter;
     File file = new File("outputs.txt");  
     fileWriter = new FileWriter(file);
     fileWriter.flush();
    Stopword sp=new Stopword();
    Special spl=new Special();		
    for(int i=0;i<als.size(); i++) {    
	slist sl=(slist)als.get(i);    
	sl.setsrsentence(spl.remove(sl.getrawsentense()));
	sl.setsrsentence(sp.remove(sl.getsrsentence()));
    } 	
    outdoc.setText("");
    outdoc.setText("NO OF SENTENSE :"+als.size() +"\n\n");
    for(int i=0;i<als.size();i++)
      {	  
	 slist sl=(slist)als.get(i);  	 
	  outdoc.setText(outdoc.getText() + "\n" + (i+1) +":  "+ sl.getsrsentence());
      }
            String content= outdoc.getText();
            content = content.replaceAll("(?!\\r)\\n", "\r\n");
            fileWriter.write(content);
            fileWriter.close();
   }
   catch(IOException e)
   {}
  	        
 }

public void separatesentense(File fp)
 {	
	Sentense sc=new Sentense(fp);
	sc.separatesentense(als);									         		
	outdoc.setText("");	
	outdoc.setText("NO OF SENTENSE :"+als.size() +"\n\n");
	scnt=als.size();
	for(int i=0;i<als.size();i++)
	 {	  
	   slist sl=(slist)als.get(i);  	 
	   outdoc.setText( outdoc.getText() + "\n " + (i+1) +":  " + sl.getrawsentense());
	 }		
 }



public double difpos(String str1,String str2)
{     
  int s1=str1.length();
  int s2=str2.length(); 
  int sz=(s1 > s2 ) ? s1 : s2 ;
  int mz=(s1 < s2 ) ? s1 : s2 ;
  double dp=mz,sm=0;  
  for ( int i=0;i<mz; i++ )
   {
       if( str1.charAt(i) != str2.charAt(i) )
        {
	       dp=i+1;
               break;
	}
       else
	  sm++;	
   }   
   return(sm*(dp/sz));             
}


private void addword(String tok,int sp,int wp)
{
    wlist wl=new wlist(tok);
    wl.incrcount(sp+1,wp);    
    hs.put(tok,wl);
}
private void upword(String tok,int sp,int wp)
{    
     wlist wl=(wlist)hs.remove(tok);
     wl.incrcount(sp+1,wp);
     hs.put(tok,wl);     		
}

private void delword(Object tok)
{    
     wlist wl=(wlist)hs.remove(tok);
     if( wl.getcount() > 3 )
         hs.put(tok,wl);          
}


private void setwight(Object tok)
{    
     double wg=0.0;
     wlist wl=(wlist)hs.get(tok);
     double tf=wl.getcount();
     double df=wl.sentensecount(); 
     wg=tf*Math.log10(scnt/df);         
     wl.weight(wg);         	      
}


public void Uniquewords()
 {     	        
   for(int i=0;i<als.size(); i++) 
     {    
	slist sl=(slist)als.get(i);    			
	String sen=sl.getsrsentence();	
        int wc=0;
	StringTokenizer stk=new StringTokenizer(sen," ");	
	while ( stk.hasMoreElements() )
	 {			   
	   String tok=(String)stk.nextElement();
	   tok=tok.trim(); 
	   wc++;	   	
	   if(!hs.containsKey(tok) && tok.length() >=3)	              					   		    
	       addword(tok,i,wc);
	    else if(hs.containsKey(tok) )
	       upword(tok,i,wc);	         		    
	 }	         
      }      	

  outdoc.setText("No of unique words:" +hs.size() + "\n");
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount() );		      	
  }  		
}



public void  stemword(String w1,String w2)
{

  if( !hs.containsKey(w2) || !hs.containsKey(w1) )
     {
        // System.out.print("return:");
	return; 
     }
   wlist wl1=(wlist)hs.remove(w1);
   wlist wl2=(wlist)hs.remove(w2);
	
   ArrayList wp=wl2.getwordpos();
   ArrayList sp=wl2.getsentensepos();
  
	
  for(int i=0;i<wp.size();i++)
   {
    String wp2=(String)wp.get(i); 
    String sp2=(String)sp.get(i); 	
    wl1.incrcount(Integer.parseInt(wp2),Integer.parseInt(sp2));
   }

   hs.put(w1,wl1);       

}


public void stemming()
 {     	        

   int sz=hs.size();
   double wdis[][]=new double[sz][sz];
   Set s1=hs.keySet();
   Object obj[]=s1.toArray();   
  
    for(int i=0;i<sz;i++) {        
	String str1=(String) obj[i];
      for(int j=0;j<sz;j++) {
	String str2=(String) obj[j];
	if(i!=j)
	wdis[i][j]=difpos(str1,str2);		
      }
    } 

    for(int i=0;i<sz;i++) {        
	String str1=(String)obj[i];
      for(int j=0;j<sz;j++) {
	String str2=(String)obj[j];	
	if(i!=j && wdis[i][j] >= 3.0 ) {              
	   stemword(str1,str2); 		
	 }	  			
      }
    } 

  outdoc.setText("No of unique words:" +hs.size() + "\n");
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount());		      	
  }  		     
}

public void stemming1()
{
      outdoc.setText("");
      char[] w = new char[501];
      Stemmer s = new Stemmer();
      try
      {
         File file = new File("outputs.txt");
         FileInputStream in = new FileInputStream(file);

         try
         { while(true)

           {  int ch = in.read();
              if (Character.isLetter((char) ch))
              {
                 int j = 0;
                 while(true)
                 {  ch = Character.toLowerCase((char) ch);
                    w[j] = (char) ch;
                    if (j < 500) j++;
                    ch = in.read();
                    if (!Character.isLetter((char) ch))
                    {
                       /* to test add(char ch) */
                       for (int c = 0; c < j; c++) s.add(w[c]);

                       /* or, to test add(char[] w, int j) */
                       /* s.add(w, j); */

                       s.stem();
                       {  String u;

                          /* and now, to test toString() : */
                          u = s.toString();

                          /* to test getResultBuffer(), getResultLength() : */
                          /* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */
                          outdoc.setText(outdoc.getText()+u);
                          //System.out.print(u);
                       }
                       break;
                    }
                 }
              }
              if (ch < 0) break;
              outdoc.setText(outdoc.getText()+(char)ch);
             // outdoc.getText();
              //System.out.print((char)ch);
           }
         }
         catch (IOException e)
         {  System.out.println("error reading ");
            //break;
         }
      }
      catch (FileNotFoundException e)
      {  System.out.println("file not found");
        // break;
      }
}
public void significant()
{

  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() )   
     delword(key.nextElement());
    
  outdoc.setText("No of Significant words:" +hs.size() + "\n");
  key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount() + ":"+wl.sentensecount() );		      	
  }  		     
}


public void weight()
{    
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements())     
    setwight(key.nextElement());

  outdoc.setText("Weight of Significant words:" + "\n");
  key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.weight());		      	
  }  		       
}


public String ranking()
{  
 slist  sl=null;  double max=0.0;int mi=0;
  
 for(int i=0;i<als.size();i++)
  { 	
   sl=(slist)als.get(i);    			
   String sen=sl.getsrsentence();	
   Enumeration key=hs.keys();	    
   while(key.hasMoreElements())     
    {	
     String str=(String)key.nextElement();         	
     if(sen.indexOf(str) != -1 )
      {
       wlist wl=(wlist)hs.get(str); 
       sl.weight(wl.weight());      		
      } 	       	
    }    
  }	

  
  for(int i=0;i<als.size();i++) { 	
      sl=(slist)als.get(i);    			
     if( sl.weight() > max )
       {	
	max =  sl.weight();   	
	mi=i;
       }
  }

  
  outdoc.setText("\t SUMMARY \n" ); 
  String str1=sl.getrawsentense();
  sl=(slist)als.get(mi);    			    
  outdoc.setText(outdoc.getText() + sl.getrawsentense() +"\n" );
  outdoc.setText(outdoc.getText() +"\n Sentense  and Ranking "  +"\n");

  for(int i=0;i<als.size();i++) { 	
    sl=(slist)als.get(i);    			
     outdoc.setText(outdoc.getText() +"\n" + (i+1) +" : " + sl.getrawsentense() + " : " + sl.weight());     
  }  	

  return str1;
}

public void summarize()
{

  
separatesentense(fp);	
removestopwords();
Uniquewords();		
stemming();
significant();	
weight();		
String str=ranking();

outdoc.setText("SUMMARY \n\n" ); 
outdoc.setText(outdoc.getText() + str);




}
public void titlefeature()
{
	outdoc.setText("");
	File file = new File("files/Title_feature.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void length()
{
	outdoc.setText("");
	File file = new File("files/SentenceLength.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void Poisition()
{
	outdoc.setText("");
	File file = new File("files/Sentencepoisition.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void ProperNoun()
{
	outdoc.setText("");
	File file = new File("files/ProperNoun.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void Numerical()
{
	outdoc.setText("");
	File file = new File("files/Numerical.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void TermWeight()
{
	outdoc.setText("");
	File file = new File("files/TermWeight.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void summary1()
{
	outdoc.setText("");
	File file = new File("files/summary.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void ThematicWord()
{
	outdoc.setText("");
	File file = new File("files/ThematicWord.txt");
	try{
	BufferedReader in = new BufferedReader(new FileReader(file));
	String line = in.readLine();
	while(line != null){
		  outdoc.setText(outdoc.getText()+line + "\n");
		  line = in.readLine();
		}
	}
	catch(Exception e){ System.out.println("File Not Found");}
}
public void actionPerformed(ActionEvent ae)
  {
	if(ae.getActionCommand().equals("Document") )		 
	      {					         	
		JFileChooser fc=new JFileChooser();
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
		{
		      als.clear();	
		      hs.clear();	
		      fp=fc.getSelectedFile();
		      setdocument(fp);  						   
		}
	     }	     	
	else if(ae.getActionCommand().equals("Sentense")) 
		separatesentense(fp);	      
	else if(ae.getActionCommand().equals("Stopword")) 
                removestopwords();
         					      
	else if(ae.getActionCommand().equals("Unique word")) 
		  Uniquewords();				      
        else if(ae.getActionCommand().equals("Stemming")) 
        {
		  stemming1();
		  try{coordinate(fp);
		  System.out.println("Coordinate Complte");}
		  catch(Exception e){}
		 
        }
	else if(ae.getActionCommand().equals("Significant")) 	
		  significant();	
	else if(ae.getActionCommand().equals("Title Feature")) 	
		  titlefeature();
	else if(ae.getActionCommand().equals("Length")) 	
		  length();
	else if(ae.getActionCommand().equals("SentencePoisition")) 	
		  Poisition();
	else if(ae.getActionCommand().equals("ProperNoun")) 	
		ProperNoun();
	else if(ae.getActionCommand().equals("Numerical")) 	
		Numerical();
	else if(ae.getActionCommand().equals("TermWeight")) 	
		TermWeight();
	else if(ae.getActionCommand().equals("ThematicWord")) 	
		ThematicWord();
	else if(ae.getActionCommand().equals("Weight")) 	
		  weight();		
	else if(ae.getActionCommand().equals("Ranking")) 	
		  ranking();	
	else if(ae.getActionCommand().equals("Summary1")) 	
		  summary1();	
	else if(ae.getActionCommand().equals("Summary")) 	
		  summarize();	
			      
  }

 public static void main(String str[])
 {
     JFrame.setDefaultLookAndFeelDecorated(true);
     summary sm=new summary();
	
 }   

}