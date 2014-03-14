/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.topicModeling;

import cc.mallet.util.*;
import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import malletData.Data;

/**
 *
 * @author sverma
 */

/*
 * 
 * Use this class as an entry point 
 * 
 * /home/sverma/work/mallet/expt/data/bp  BP TITLE DATA 
 */
public class TopicModelDriver {
    
    public static void main(String[] args) throws Exception {
        try {
            runAP();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TopicModelDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TopicModelDriver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TopicModelDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    } //main
    
    
    
    
    
    
    private static void runAP() throws UnsupportedEncodingException, FileNotFoundException, IOException, Exception {
        
        
        //Data Prep, StringFilterer
        String[] filePathsContro ={Data.dataC,Data.dataCFiltered};
        //StringFilterer.main(filePathsContro);
        String[] filePathsNContro ={Data.dataNC,Data.dataNCFiltered};
        //StringFilterer.main(filePathsNContro);

        //Run Topic Model using ap.txt 
        String[] argsC = {Data.malletDataBpDir + "dataC.txt", Data.malletDir};
        String[] argsNC = {Data.malletDataBpDir + "dataNC.txt", Data.malletDir};

        /*
         * EXPT 1
         * 
         * Run for Controversial Stuff
         * 
         * 
         */
        //LDA apC = new LDA();
        //System.out.println("Running for Contro Stuff");
        //apC.runTopicModel(argsC); //create model for controversial stuff
        
        
        /*
         * EXPT 2
         * 
         * Run for NonControversial Stuff
         * 
         */
        //LDA apNC = new LDA();
        //System.out.println("Running for NON Contro Stuff");
        //apNC.runTopicModel(argsNC); //create model for non-controversial stuff 
        
        
        /*
         * EXPT 3 
         * We want to Run the Expt for Each Category 
         * 
         * 
         */
         Data.CatNames catName;
         Data.Categories category;
         
         String rawConFile = Data.malletDataBpDir+"contro/controversialTitles";
         String rawNonCFile = Data.malletDataBpDir +"noncontro/nonControversial";
         
         MalletLDA mLDA = new MalletLDA();
         
         for (Data.CatNames cat: Data.CatNames.values()) {
             for (Data.Categories categ: Data.Categories.values()) {
                //System.out.println(cat + " " + categ); 
                mLDA.trainLDA(cat,categ, rawConFile, rawNonCFile );
             }
         } 
        

    } //main
    
    
    
} //TopicModel
