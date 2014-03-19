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
public class NonControversialData  extends Data {
    
    public NonControversialData() {
        super();
        category = CATEGORY.NONCONTROVERSIAL; 
    }
    
    
     public void LDA() {
         List<Instance> instanceList = new ArrayList<Instance>();
        int count=0;
        Instance inst;
        for(String s:this.ldaInputData) {
            //System.out.println(s);
            //Create Instance 
            inst = new Instance(s,"NonControversial",String.valueOf(count),null);
            instanceList.add(inst);  //Add to instanceList
        }
        
        TopicModeling lda = new TopicModeling();
        lda.run(instanceList);
        
        try {
            lda.analyzeAlphabet("NC");
        } catch (IOException ex) {
            Logger.getLogger(ControversialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        lda.displayTopWords("NC");
        
        try {
            lda.getTopWords(10000,"NC");
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
    } //prepareData
    
    
    private String _process(String input) {
        String result = null;
        //System.out.println(input);
        
        //2.8 Solving for a specific variable:noncontroversial

        String[] lineList = input.split(":");
        int indexOfData=0;
        String data;
        List<String> catList = new ArrayList<String>();
        
        StringBuilder sb = new StringBuilder();
        if(lineList.length==0) {
            return null;
        }
        for(int i=0;i<lineList.length;i++) {
            if (lineList[i].equals("noncontroversial")) {
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
        //System.out.println(result);
        
        //At this time we are filtering only by KW
        String filteredResultByKW = filterKW(result);
        
        if(filteredResultByKW==null) return null;
        //System.out.println(filteredResultByKW);
        
        return filteredResultByKW;
    } //_process
    
    
    
    
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
      
      
      
    
    
}
