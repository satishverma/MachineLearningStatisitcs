/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.topicModeling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class StringFilterer {
    
    
    private static Map<String,Boolean> stopwordsMap = new LinkedHashMap<String,Boolean>();

	/*
	 * Filter the stopwords
	 * 
         * Rules for FILTERING
         * 
         * 
         * 
         * 
         * 
         * 
         * 
	 */
	private static String analyze(String s) {
		String[] sList = s.split("\\s+");
		StringBuilder sb = new StringBuilder(" ");
		for(String str:sList) {
			if(!stopwordsMap.containsKey(str.toLowerCase())) {
				{
				str = str.toLowerCase();
				str=str.replace("(", "");
				str=str.replace(")", "");
				str=str.replace("%", " ");
				str=str.replace("/", "");
				str=str.replace("+", "");
				str=str.replace("^", "");
				str=str.replace("\\", "");
				str=str.replace("-", "");
				if (!str.matches("[0-9]+") && !stopwordsMap.containsKey(str)){ 
				  if( !stopwordsMap.containsKey(str)) {
					sb.append(str+" ");
				  }
				 }
				}
				
			}
		
		}
		return sb.toString();
	}
	
	private static void filter(String in, String outFile) throws IOException {
		BufferedWriter bw=null;FileWriter fw=null;
		try {
		    Scanner scan = new Scanner(new BufferedReader(new FileReader(in)));
		    File outF = new File(outFile);
		    if(!outF.exists()) outF.createNewFile();
		    fw = new FileWriter(outF.getAbsoluteFile());
		    bw = new BufferedWriter(fw);
		    while(scan.hasNextLine()) {
		    	String line = scan.nextLine();
		    	line = line.trim();
		    	String[] lineList = line.split(":");
                        String[] tokens = tokenize(lineList);
                        
                        StringBuilder sb = new StringBuilder("");
                        for(String s:tokens) {
                            if(!stopwordsMap.containsKey(s))  
                                sb.append(s+" ");
                           // System.out.print(s+" ");
                        }
                        //System.out.println();
                        if(sb.toString().trim().length()>0) {
		    		bw.write("X"+ "\t" + "C"+"\t"+sb.toString());
                                bw.newLine();
		    	}//if
                        
                        
                        
                        
                        //OLD STUFF
                        /*
		    	StringBuilder s = new StringBuilder("");
		    	for(String str:lineList) {
		    		s.append(analyze(str));
		    		s.append(" ");
		    	}
                        String tmp = s.toString().trim().toLowerCase();
                        System.out.println(tmp);
                        
		   	if(s.toString().trim().length()>0) {
		    		bw.write("X"+ "\t" + "C"+"\t"+s.toString());
                                bw.newLine();
		    	}//if
                        
                        */
                        
                        
                        
		    }//while
		    if(bw!=null)
			    bw.close();
			if(fw!=null)    
			fw.close();
		    
		}catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		  
		} finally {
			
		}
		
		
	} //filterControTitles
	
        
        
        
        
        
        /*
         * tokenize
         * 
         * 
         */
        
         private static String[] tokenize(String[] strArr) {
             
             StringBuilder sb = new StringBuilder();
             String tmp;
             
             for(String s:strArr) {
                 String[] sSplit = s.split("\\s+");
                 for (String ss:sSplit) {
                     tmp = ss.trim().toLowerCase();
                     tmp = replaceStuff(tmp);
                     tmp = replaceStuff(tmp);
                     //System.out.print(tmp+" ");
                     sb.append(tmp+" ");
                 }
             }//for
             //System.out.println();
             return sb.toString().split(" ");
         }
         
         
         
  private static String replaceStuff(String str) {

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
	
	
	private static void addToStopMap(String[] wordsToFilter) {
		for(String s:wordsToFilter) {
			stopwordsMap.put(s, true);
			//System.out.println(s);
		}
	}
        
        
       private static void printStopList() {
        Iterator it = stopwordsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
           // System.out.println(pairs.getKey() + "," + pairs.getValue());
        }
    }

	
	public static void main(String[] args) throws Exception {
		
		String stopwords = Data.stopWordsFile;
		buildStopMap(stopwords);

                //update the map dynamically based on our ideas
                
                String[] wordsToFilter = {"null","noncontroversial","controversial","violence","obscenelanguage","distasteful","gambling","sex","accident","violence","substanceabuse","crime"};
		//String[] wordsToFilter = {"null","controversial","noncontroversial"};
		addToStopMap(wordsToFilter);

		String[] termsToFilter = {"videos","video","part","play","episode","chapter","generic"};
		addToStopMap(termsToFilter);
		printStopList();
		
		//contro titles
		//String controTitleFile = "controversialTitles";
		//String controTitleFileFiltered = "controversialTitlesFiltered";
		//String inputFile = args[0];
		//String filtered = args[1];
		filter( args[0], args[1]);
		
	} //main
    
}
