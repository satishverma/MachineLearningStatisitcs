/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Data;

import BP.TopicModeling.TopicModeling;
import BP.Utils.ListUtil;
import cc.mallet.types.Instance;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sverma
 */
public class ControversialData extends Data {
    
    
    private   String[] wordsToFilter = {"null","part","video","episode","review"};
    
    
    public ControversialData() {
        super();
        category = CATEGORY.CONTROVERSIAL; 
        
        //ADD THESE CONTRO DATA SPECIFIC STUFF
        BP.Utils.StringUtil.addToStopMap(wordsToFilter);
    }
    
    
    
    public void LDA() {
        //convert the data into a list of instance and ur done 
        List<Instance> instanceList = new ArrayList<Instance>();
        int count=0;
        Instance inst;
        for(String s:this.ldaInputData) {
            //System.out.println(s);
            //Create Instance 
            inst = new Instance(s,"Controversial",String.valueOf(count),null);
            instanceList.add(inst);  //Add to instanceList
        }
        
        
        
        
        
        TopicModeling lda = new TopicModeling();
        lda.run(instanceList);
        try {
            lda.analyzeAlphabet("C");
        } catch (IOException ex) {
            Logger.getLogger(ControversialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        lda.displayTopWords("C");
        try {
            lda.getTopWords(10000,"C");
        } catch (IOException ex) {
            Logger.getLogger(ControversialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } //LDA
    
    
    public void prepareData(String path) {
        if(this.debugOn) {
            System.out.println("Cat Name " + category);
            ListUtil.printList(kwList);
            ListUtil.printList(this.subCatList);
        }
        
        //What should this do ?? 
        //Prepare data based on criterion 
        //first filter based on SubCat and then on KW if both are set 
        // if only KW is set then filter based on KW 
        //if only SubCat is set then filter based on SubCat
        generateData(path);
        
    }//prepareData
    
    
    private void generateData(String path) {
        this.ldaInputData = new ArrayList<String>();

        Scanner scan = null;
        String currLine = null;
        String processedLine = null;
        try {
            scan = new Scanner(new BufferedReader(new FileReader(path)));
            while (scan.hasNextLine()) {
                currLine = scan.nextLine();
                processedLine = _process(currLine.trim());
                //System.out.println(processedLine);
                if(processedLine!=null) {
                    this.ldaInputData.add(processedLine);
                }
            } //while
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControversialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        metadata();
    }//generateData
    
    
    private void metadata() {
        System.out.println("Num of datapoints " + ldaInputData.size());
    }
    
    
    private String _process(String input) {
        String result = null;
        //System.out.println(input);
        
        //I Luv Halloween CG Animation Preview category_animation:controversial:obscene_language
        String[] lineList = input.split(":");
        int indexOfData=0;
        String data;
        List<String> catList = new ArrayList<String>();
        
        StringBuilder sb = new StringBuilder();
        if(lineList.length==0) {
            return null;
        }
        for(int i=0;i<lineList.length;i++) {
            if (lineList[i].equals("controversial")) {
                indexOfData=i;
                break;
            }
        }
        
        for(int i=0;i<indexOfData;i++) {
            sb.append(lineList[i]);
            sb.append(" ");
        }
        
        for(int i=indexOfData+1;i<lineList.length;i++) {
            catList.add(lineList[i].toUpperCase().replace("_",""));
            //System.out.print(lineList[i].toUpperCase().replace("_",""));
        }
        //System.out.println();
        
        data = sb.toString().trim().toLowerCase();
        //System.out.println(data);
        
        
        result = BP.Utils.StringUtil.filterText(data);
        
        //NOW FILTER 
        String filteredResultByKW = filterKW(result);
        
        if(filteredResultByKW==null) return null;
        //System.out.println(filteredResultByKW);
        String filteredResultByCat = filterCat(filteredResultByKW,catList);
        //System.out.println(filteredResultByCat);
        //printCatList(catList);
        if(filteredResultByCat ==null) return null;
        return filteredResultByCat;
        
        //return null;
    } //_process
    
    
    
    private void printCatList(List<?> list_) {
        for(Object o:list_) {
            System.out.print(o.toString());
        }
        System.out.println();
    }
    
    
    private String filterKW(String input) {
        /*
         * if kwList is empty no filtering needed
         * else if the kw or kws are there filter 
         */
        boolean present = false;
        if(input==null) return input;
        if(this.kwList.size()==0) return input;
        for(String s:this.kwList) {
            //System.out.println(s);
            if(input.contains(s)) {
                //System.out.println(input);
                present = true;
            }
        }
        if(present) return input;
        return null;
    }
    
    private String filterCat(String input,List<String> catList) {
        
        if(input==null) return input;
        if(this.subCatList.size()==0) return input;
        
        String[] subCatArr = new String[subCatList.size()];
        for(int i=0;i<this.subCatList.size();i++) {
            subCatArr[i]=this.subCatList.get(i).name();
        }
        
        
        
        boolean match=false;
        for(String s1:catList) {
            for (String s2:subCatArr) {
                if(s1.equals(s2)) match = true;
            }
        }
        
        
        if(match==true) return input;
        return null;
    } 
    
    
} //ControversialData
