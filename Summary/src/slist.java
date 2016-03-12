public class slist
{

 String  rsen="",srsen="";
 private double wght;
 public slist(String sen) {
   rsen=new String(sen);
   wght=0.0;	
 }


 public void setrawsentense(String sen) {
	rsen=new String(sen);      	
 }
 
 public void setsrsentence(String rsen) {
	srsen=new String(rsen);
 }  

 public String getrawsentense() {
     return rsen;
 }   
 public String getsrsentence() {
	return srsen;
 }

 public void   weight(double wg) { wght=wght+wg;} 
 public double weight() { return wght; }  
 

}