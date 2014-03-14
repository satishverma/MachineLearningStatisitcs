/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Expt;

import BP.Data.ControversialData;
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
        
         ControversialData data = new ControversialData();
         data.setDebugOn();
         
         data.setSubCat(Data.SUBCATEGORY.CRIME);
         data.setKW("Arrested");
         
         
         data.prepareData();
         
        
    } //main
} //Driver
