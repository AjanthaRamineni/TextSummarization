import java.io.*;
import java.util.StringTokenizer;

public class Stopword 
{       
   
   String stwd[];
   public Stopword ()
    {
        int cnt=0,sz=0;char bt[]=null;
       try {
         File fp=new File("stopwords.txt");	
         FileReader fis=new FileReader(fp);
         sz=(int)fp.length();
         bt=new  char [sz];
         fis.read(bt);
         fis.close();	  		
          }
      catch(IOException ex) {}       
      stwd=getTokens(new String(bt));		        	
   }

public void display () 
 {
            for (int i=0;i<stwd.length;i++)
	System.out.println(stwd[i]);	
   }

 public boolean  isStopword( String word) 
  {
         boolean flag=false;        
          for (int i =0;i<stwd.length;i++)  {
             if(stwd[i].equalsIgnoreCase(word) ) {
	  flag=true;
	   break;	
               }
            }
          return flag;	
   }

  public String[]  getTokens(String sen) 
      {
	 int sz=0,cnt=0;String words[]=null;
	StringTokenizer  stk=new StringTokenizer(sen) ;
	sz=stk.countTokens();
     	 words=new String[sz];	        	
     	 while ( stk.hasMoreTokens()) 
	{
           	words[cnt]=new String(stk.nextToken());	     				
           	cnt++;
       	}		
	return words;	        
    }
  
  public String remove(String sen) 
  {
     String  dsen="";
     String  words[]=getTokens(sen);	
      for (int j=0;j<words.length; j++) 
          { 	
               if ( ! isStopword(words[j] )  )  	
	dsen = dsen +words[j] +" ";              		
          }		
         return dsen;	         		
    }    

  public static void main(String str[])
   {
	Stopword sp=new Stopword();	
        System.out.println(sp.remove("a hello you are mine of about  at  are  murugan kannan"));	
   }
   
}