

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BpTitles {
	
	private static HashMap<String,Boolean> stopwordsMap = new HashMap<String,Boolean>();

	/*
	 * Filter the stopwords
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
	
	private static void filterControTitles(String in, String outFile) throws IOException {
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
		    	StringBuilder s = new StringBuilder("");
		    	for(String str:lineList) {
		    		s.append(analyze(str));
		    		s.append(" ");
		    	}
		   	if(s.toString().trim().length()>0) {
		    		bw.write("X"+ "\t" + "C"+"\t"+s.toString());bw.newLine();
		    	} 
		    }
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
			System.out.println(s);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		String stopwords = "/home/sverma/work/hadoopStuff/data/stopwords.txt";
		buildStopMap(stopwords);
		String[] wordsToFilter = {"null","noncontroversial","controversial","violence","obscene_language","distasteful","gambling","sex","accident","violence","substance_abuse","crime","videos","video","part","Part","play","episode","chapter","generic"};
		//String[] wordsToFilter = {"null","controversial","noncontroversial"};
		addToStopMap(wordsToFilter);

		//String[] termsToFilter = {"videos","video","part","play","episode","chapter","generic"};

		//addToStopMap(termsToFilter);
		
		
		//contro titles
		//String controTitleFile = "controversialTitles";
		//String controTitleFileFiltered = "controversialTitlesFiltered";
		String inputFile = args[0];
		String filtered = args[1];
		filterControTitles( inputFile, filtered);
		
	} //main
	
} //BpTitles


