
package philoplus.philoPlusClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadingFile {
    
    private  static String fileNameImgPathForFollowing ="C:\\Users\\Seha\\Desktop\\ImgPathForFollowing.txt";
    
    
    public static FileReader readFile() throws FileNotFoundException{
     FileReader fr=new FileReader(fileNameImgPathForFollowing);
     return fr;
    }
    
    public static  String  getLinePathToSaveImage(String searchWord){
       String valueFounded ="";
        try {
            FileReader readFile = readFile();
            BufferedReader br = new BufferedReader(readFile); //Creation of BufferedReader object
             int count=0;   //Intialize the word to zero
             String s;
             String[] words=null;  //Intialize the word Array
              List<String> lines = new ArrayList();
             while((s=br.readLine())!=null)   //Reading Content from the file
             {
                words=s.split("_");  //Split the word using space
                 for (int i = 0; i < words.length; i++) {
                     lines.add(words[i]);
                 }
              }
            for (int i = 0; i < lines.size(); i++) {
                if (searchWord.equals(lines.get(i))) {
                valueFounded =   lines.get(i+1);
                    System.out.println(valueFounded);
                }
            }
        }catch (FileNotFoundException ex) {
           Database.alertMessage("هناك مشكلة في الاتصال بالملف "+ fileNameImgPathForFollowing);
        } catch (IOException ex) {
           Database.alertMessage("هناك مشكلة في الاتصال بالملف "+ fileNameImgPathForFollowing);
        }
        System.out.println("returned value is "+ valueFounded);
        return valueFounded;
    }
   
}
