/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.keywordModeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mallet.FileStuff.FileUtilities;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class KeywordBasedTextScore {
    
    private String kw; //for examples "Arrested"
    private HashMap<String,Integer> topWordsMap;
    private String cFile;
    private String ncFile;
    private ArrayList<String> cData;
    private ArrayList<String> ncData;
    private FileUtilities fileUtil;
    private boolean _debug = false;
    
    public KeywordBasedTextScore(String keyword_, HashMap<String,Integer> topWordsMap_, String cFile_, String ncFile_, boolean debug) {
        this.kw = keyword_;
        this.topWordsMap=topWordsMap_;
        this.cFile=cFile_;
        this.ncFile=ncFile_;
        this._debug=debug;
        fileUtil = new FileUtilities();
        cData = new ArrayList<>();
        ncData = new ArrayList<>();
    } //constructor
    
    
    /*
     * 
     * 
     * 
     */
    public List<String> prepareCData() {
        //get all the data which has the given keyword
        List<String> tempList = fileUtil.readFile(this.cFile);
        String tmp;
        for(String s:tempList) {
            tmp = _process(s,Data.SourceType.Controversial);
        }
        
        
        return null;
    } //prepareCData
    
    
    
    public String _process(String s, Data.SourceType cat) {
        
        String res =null ;
        if(cat == Data.SourceType.Controversial) {
            if(_debug==true) {
                System.out.println("Controversial");
            }
        }
        if(cat == Data.SourceType.NonControversial) {
            if(_debug==true) {
                System.out.println("NonControversial");
            }
        }
        
        
        
        return res;
    }
    
    
    public List<String> prepareNCData() {
        List<String> tempList =  fileUtil.readFile(this.ncFile);
        String tmp;
        for(String s:tempList) {
            tmp = _process(s,Data.SourceType.NonControversial);
        }
        return null;
    } //prepareNCData
    
    
    public void computeScores() {
        //compute score for each cData and ncData 
        // 
        
        for(String s:cData) {
            
        }
        for(String s:ncData) {
            
        }
        
        
    } //computeScores
    
    
    
    
    
    
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
        
        KeywordBasedTextScore driver = new KeywordBasedTextScore("arrested",topWordsMap,Data.dataC,Data.dataNC,true);
        driver.prepareCData();
        driver.prepareNCData();
        driver.computeScores();
        
    } //main
    
    
}
