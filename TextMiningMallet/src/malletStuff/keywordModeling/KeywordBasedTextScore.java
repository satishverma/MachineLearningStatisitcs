/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.keywordModeling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import mallet.FileStuff.FileUtilities;
import mallet.FileStuff.StopWordsUtil;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class KeywordBasedTextScore {
    
    private String kw; //for examples "Arrested"
    private HashMap<String,Integer> topWordsMap;
    private String cFile; //cData is the list of all data containing the kw for a given cat 
    private String ncFile;
    private ArrayList<String> cData;
    private ArrayList<String> ncData;
    private FileUtilities fileUtil;
    private boolean _debug = false;
    private Map<String, Boolean> stopwordsMap;
    
    
    public KeywordBasedTextScore(String keyword_, String cFile_, String ncFile_, boolean debug) {
        this.kw = keyword_;
     
        this.cFile=cFile_;
        this.ncFile=ncFile_;
        this._debug=debug;
        fileUtil = new FileUtilities();
        cData = new ArrayList<>();
        ncData = new ArrayList<>();
        topWordsMap = new HashMap<String,Integer>();
    } //constructor
    
    public void setStopWordsMap(Map<String, Boolean> stopwordsMap_) {
        this.stopwordsMap=stopwordsMap_;
    }
    
    
    /*
     * 
     * 
     * 
     */
    public List<String> prepareCData() {
        //get all the data which has the given keyword
        List<String> tempList =  fileUtil.readFile(this.cFile);
        String tmp;
        for(String s:tempList) {
            tmp = _process(s,Data.SourceType.Controversial);
            if(tmp!=null && tmp.contains(this.kw)) {
                if(_debug==true) {
                    System.out.println(tmp);
                }
                cData.add(tmp);
            }
        }
        return cData;
    } //prepareCData
    
    
    
     public List<String> prepareNCData() {
        List<String> tempList =  fileUtil.readFile(this.ncFile);
        String tmp;
        for(String s:tempList) {
            tmp = _process(s,Data.SourceType.NonControversial);
            if(tmp!=null && tmp.contains(this.kw)) {
                if(_debug==true) {
                    System.out.println(tmp);
                }
                //System.out.println(tmp);
                ncData.add(tmp);
            }
        }
        return ncData;
    } //prepareNCData
    
    
    public String _process(String s, Data.SourceType cat) {
        
        String res =null ;
        if(cat == Data.SourceType.Controversial) {
            
            //Split with ":" and process only the title for now
            
            String title = getTitleC(s);
            
            res = _processStr(title);
            if(_debug==true) {
                //System.out.println("Controversial");
                System.out.println(res);
            }
        }
        
        
        if(cat == Data.SourceType.NonControversial) {
            if(_debug==true) {
                //System.out.println("NonControversial");
            }
            String title = getTitleNC(s);
            if(_debug==true) {
                System.out.println(title);
            }
            
            res = _processStr(title);
        }
        
        
        
        return res;
    }
    
    
    
    
    
    public String getTitleC(String input) {
        String[] inArr = input.split(":");
        int index=0; String res="";
        for(int i=0;i<inArr.length;i++) {
            if (inArr[i].toLowerCase().equals("controversial")) {
                index=i;
                break;
            }
        }
        for(int i=0;i<index;i++){
            res = res+inArr[i];
        }
        return res;
        //get where controversial is present 
    }//getTitleC
    
    
    
    public String getTitleNC(String input) {
        String[] inArr = input.split(":");
        int index=0; String res="";
        for(int i=0;i<inArr.length;i++) {
            if (inArr[i].toLowerCase().equals("noncontroversial")) {
                index=i;
                break;
            }
        }//for
        for(int i=0;i<index;i++){
            res = res+inArr[i];
        }
        return res;
        
    } //getTitleNC
    
     private String _processStr(String input) {
        
        //System.out.println(input);
        
        String[] sList = input.split("\\s+"); 
        StringBuilder sb = new StringBuilder();
        String[] tmpArr; String tmp1="";
        
        
        for(String s:sList) {
            
            tmpArr = replaceStuff(s);
            if(tmpArr!=null) {
               //This implies that tmpArr has one or more elements 
               //pick an element at a time n make decision
               for(String t:tmpArr) {
                   if(!stopwordsMap.containsKey(t.trim())) {
                       sb.append(t+' ');
                   } //if
               } //for
                
            }//if
          
        } //for
        //System.out.println(sb.toString());
        if(sb.toString().trim().length()>0) {
            return sb.toString().toLowerCase();
        } else {
            return null;
        }
    } //_process
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    public void computeScores() {
        //compute score for each cData and ncData 
        
        int threshold = 0;
        //we have to analyze the scores also 
        List<Integer> cList = new ArrayList<Integer>();
        List<Integer> ncList = new ArrayList<Integer>();
        
        for(String s:cData) {
            //Controversial
            //tokenize
            int weight=0;
            
            String[] sArr = s.split("\\s+");
            for(String s_:sArr) {
                if(this.topWordsMap.containsKey(s_.trim())) {
                    weight+=this.topWordsMap.get(s_.trim());
                   // weight++;
                }
            }
            if(sArr.length>0){
               // weight = weight/sArr.length;
            }
            if(weight>threshold) {
                System.out.println(s+" "+weight +" C");
                 cList.add(weight);
            }
        }
        for(String s:ncData) {
            //NonControversial
            int weight=0;
            String[] sArr = s.split("\\s+");
            for(String s_:sArr) {
                if(this.topWordsMap.containsKey(s_.trim())) {
                   weight+=this.topWordsMap.get(s_.trim());
                   //weight++;
                }
            }
            if(sArr.length>0){
               //weight = weight/sArr.length;
            }
            if(weight>threshold) {
                System.out.println(s+" "+weight +" NC");
                ncList.add(weight);
            }
            
        }
        
        analyzeWeights(cList,ncList);
        
    } //computeScores
    
    
    
    private void analyzeWeights(List<Integer> cList,List<Integer> ncList) {
        int cSize = cList.size();
        int ncSize = ncList.size();
        System.out.println("Length of Cont data "+cSize);
        Collections.sort(cList);
        Collections.sort(ncList);
        System.out.println("Length of NCont data "+ncSize);
        Integer[] cArr = new Integer[cSize];
        cArr = cList.toArray(cArr);
        Integer[] ncArr = new Integer[ncSize];
        ncArr = ncList.toArray(ncArr);
        System.out.println("Range Cont " + cArr[0]+" "+cArr[cArr.length-1] +" Avg "+ arrAvg(cArr));
        printInterval(cArr);
        System.out.println("Range NCont " + ncArr[0]+" "+ncArr[ncArr.length-1]+" Avg "+ arrAvg(ncArr));
        printInterval(ncArr);
    }
    
    
    private void printInterval(Integer[] arr) {
        int min = arr[0];
        int max = arr[arr.length-1];
        int interval = 5;
        int i=0;
        while(i<=arr.length) {
            System.out.print(arr[i]+" ");
            i+=interval;
        }
        System.out.println();
    }
    
    
    private double arrAvg(Integer[] arr) {
        double sum=0.0;
        for(Integer i:arr) sum+=i;
        return sum/arr.length;
    }
    
    
     public String[] replaceStuff(String str) {

        if(str.length()<3) return null;
        str = str.replace("%20", " ");
        str = str.replace("&apos;", " ");
        str = str.replace("'s", "");
         str = str.replace("'", "");
        str = str.replace(".", "");
        str = str.replace(";", "");
        str = str.replace("(", "");
        str = str.replace(")", "");
        str = str.replace("%", " ");
        str = str.replace("/", "");
        str = str.replace("+", " ");
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
    
     
     private void buildTopWordsMap(String top) {
        if (top == null) {
            System.out.println("top file not set");
            System.exit(1);
        }

        String line;
        String kw = "";

        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(top)));
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                // process the lines
                String[] lineArr = line.split(",");
                this.topWordsMap.put(lineArr[0], Integer.parseInt(lineArr[1]));

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
    }// buildtopWordsMap
    
    public static void main(String[] args) {
        
        HashMap<String,Integer> topWordsMap = new HashMap<String,Integer>();
        
        //EXAMPLE TEST CASE
        topWordsMap.put("police", 30);
        topWordsMap.put("arrested", 28);
        topWordsMap.put("man", 26);
        topWordsMap.put("case", 24);
        topWordsMap.put("murder", 23);
        topWordsMap.put("woman", 20);
        topWordsMap.put("court", 18);
        topWordsMap.put("arrest", 18);
        topWordsMap.put("suspect", 17);
        topWordsMap.put("found", 17);
        topWordsMap.put("death", 17);
        topWordsMap.put("teen", 17);
        topWordsMap.put("jail", 15);
        topWordsMap.put("missing", 16);
        //driver build topWordsMap
       
        
        boolean debug=false;
        KeywordBasedTextScore driver = new KeywordBasedTextScore("arrested",Data.dataCCrime,Data.dataNC,debug);
        driver.buildTopWordsMap(Data.topWordsFile);
     
        
        
        //stop words util is set
        StopWordsUtil sw = new StopWordsUtil();
        
        Map<String, Boolean> stopwordsMap_  = sw.getStopWords();
        driver.setStopWordsMap(stopwordsMap_);
        
        driver.prepareCData();
        driver.prepareNCData();
        driver.computeScores();
        
    } //main
    
    
}
