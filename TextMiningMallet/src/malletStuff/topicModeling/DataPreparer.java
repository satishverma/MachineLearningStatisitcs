/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.topicModeling;

import cc.mallet.types.Instance;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class DataPreparer {
    
    private static Map<String,Boolean> stopwordsMap = new LinkedHashMap<String,Boolean>();
    private static boolean _debugInfo = false;
    
    public DataPreparer() {
        buildStopMap(Data.stopWordsFile);
        _debugInfo= false;
        
    } //Constructor
    
    
    /*
     * cat crime violence etc 
     * category contro noncontro all 
     * 
     */
    public List<Instance> getData(String cat, String category,String in) {
        
        List<Instance> data = new ArrayList<Instance>();
        /*
         * 1. Read the file , filter the lines which have category cat and put in data
         * 
         * 
         */
         if(_debugInfo) 
         System.out.println("PreparingData " +cat+" "+in);
         Scanner scan;
         String line;
         String lineProcessed;
         Instance instance;
         try {
		   scan = new Scanner(new BufferedReader(new FileReader(in)));
                   int count=0;
                   while(scan.hasNextLine()) {
		    	line = scan.nextLine();
		    	line = line.trim();
		    	String[] lineList = line.split(":");
                       
                        if(lineList[lineList.length-1].toLowerCase().equals(cat.toLowerCase())) {
                            if(_debugInfo)
                            System.out.println(_processCont(lineList[0]));
                            lineProcessed = _processCont(lineList[0]); //String
                            instance = new Instance(lineProcessed,category,String.valueOf(count),null);
                            data.add(instance);
                            count++;
                        }
                        
                   } //while
                   
                    
         }catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		  
		} finally {
			
		}
        
        
        
        return data;
    } //getData
    
    
    private String _processCont(String input) {
        String[] sList = input.split("\\s+"); 
        StringBuilder sb = new StringBuilder("");
        String tmp;
        
        for(String s:sList) {
            tmp = s.trim().toLowerCase();
            tmp = replaceStuff(tmp);
            tmp = replaceStuff(tmp);
            if(!stopwordsMap.containsKey(tmp))
                sb.append(tmp+" ");
        }
        
        return sb.toString();
    } //_process
    
    
    
    
    
    /*
     * 
     * 
     * 
     */
    public void serializeData(List<? extends String> data ) {
        //Write this list to file
        
    } 
    
    
    
    
    
    /*
     * 
     * 
     * 
     */
    public List<? extends String> readDataToList() {
        
        
        
        return null;
    } //readDataToList
    
    
    
    
    
    
    
    
    private static void buildStopMap(String stop) {
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
	
    
    
      private  String replaceStuff(String str) {

        if(str.length()<3) return "";
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

        return str;
    }
    
}
