import java.util.ArrayList;

public class  wlist
{

 private String word;
 private double cnt;
 private double scnt;
 private double wght; 
 
 private ArrayList spl;
 private ArrayList wpl;
 
 public wlist (String wd) {
    word= new String(wd);
    spl=new ArrayList();
    wpl=new ArrayList();
    cnt=0;scnt=0;
 }  

 public  void incrcount(int sp,int wp) {
   cnt++;
   sentensepos(sp);
   wordpos(wp);
  } 

 public double getcount() {   return cnt; }
 public String getword() {   return word; }
 public void sentensepos(int sp) { 
 if(! spl.contains(sp+""))
     scnt++;
  spl.add(sp+""); 
     
 }

 public void wordpos(int wp) {  wpl.add(wp+"");  }
 public ArrayList getwordpos() { return wpl; };
 public ArrayList getsentensepos() { return spl; }  
 public void   weight(double wg) { wght=wg; }
 public double weight() { return wght; }
 public double sentensecount() { return scnt; }
 

}