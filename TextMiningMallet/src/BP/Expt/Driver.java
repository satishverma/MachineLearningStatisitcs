/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Expt;

import BP.Data.ControversialData;
import BP.Data.Definitions;
import BP.Data.NonControversialData;
import BP.Data.Data;

/**
 *
 * @author sverma
 */
public class Driver {
    
    public static void main(String[] args) {
        
        /*
         * 1. Set properties of Data 
         * 2. Set output path for model information
         * 3. Run LDA or Topic Modeling
         */
        
        /*
         * Data Properties 
         *   Input Path, Cat, SubCat, KWs 
         *   
         */
        
        //Setup StopWords
         BP.Utils.StringUtil.buildStopMap(Definitions.stopWordsFile);
        
         
         //CONTROVERSIAL STUFF
         Data dataC = new ControversialData();
         dataC.setDebugOn();   
         dataC.setSubCat(Data.SUBCATEGORY.CRIME);
         dataC.setKW("Arrested");
         dataC.prepareData(Definitions.dataC);
         dataC.LDA();
         
         //NONCONTROVERSIAL STUFF
         Data dataNC = new NonControversialData();
         dataNC.setDebugOn();
         dataNC.setSubCat(Data.SUBCATEGORY.CRIME);
         dataNC.setKW("Arrested");
         dataNC.prepareData(Definitions.dataNC);
         dataNC.LDA();
         
        
    } //main
} //Driver
