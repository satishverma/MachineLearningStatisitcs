/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author sverma
 */
public class StringUtil {
    
    private static Map<String,Boolean> stopwordsMap = new LinkedHashMap<String,Boolean>();
    
    //what datastructure do you want to store the data in ?? NONE RITE NOW
    
    
    public static String filterText(String input) {
        String[] sList = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        String[] tmpArr;
        String tmp1 = "";

        for (String s : sList) {
            tmpArr = replaceStuff(s);
            if (tmpArr != null) {
                //This implies that tmpArr has one or more elements 
                //pick an element at a time n make decision
                for (String t : tmpArr) {
                    if (!stopwordsMap.containsKey(t.trim())) {
                        sb.append(t + ' ');
                    } //if
                } //for

            }//if

        } //for

        //System.out.println(sb.toString());
        if (sb.toString().trim().length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    } //filterText
    
    
    
    
      public static void buildStopMap(String stop) {
		if(stop==null) {
		    System.out.println("stop file not set");
		    System.exit(1);
		}
		
		String line; String kw="";
		
		try {
		    Scanner scan = new Scanner(new BufferedReader(new FileReader(stop)));
		    while (scan.hasNextLine()) {
			line = scan.nextLine();
			// process the lines
			stopwordsMap.put(line.trim(), true);
			
		    }
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    
		}
	}// buildStopMap
      
      
       public static String[] replaceStuff(String str) {

        if(str.length()<3) return null;
        str = str.replace("%20", " ");
        str = str.replace("&apos;", " ");
        str = str.replace("'s", "");
         str = str.replace("'", "");
        str = str.replace(".", "");
        str = str.replace("(", "");
        str = str.replace(")", "");
        str = str.replace("%", " ");
        str = str.replace("/", "");
        str = str.replace("+", "");
        str = str.replace("^", "");
        str = str.replace("\\", "");
        str = str.replace("-", "");
        str = str.replace("~", "");
        str = str.replace("!", "");
        str = str.replace("#", "");
        str = str.replace(",", "");
        str = str.replace("?", "");
        str = str.replace("-", "");
        str = str.replace("_", "");
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace("|", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\\d+", " ") ; //str.matches("[0-9]+")
        str.trim();

        
        return str.split("\\s+");
    }
       
       
        public static void addToStopMap(String[] wordsToFilter) {
		for(String s:wordsToFilter) {
			stopwordsMap.put(s, true);
			//System.out.println(s);
		}
	}//addToStopMap
    
}
