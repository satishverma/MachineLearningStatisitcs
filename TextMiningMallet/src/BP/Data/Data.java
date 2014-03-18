/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sverma
 */
public abstract class Data {

    protected enum CATEGORY {

        CONTROVERSIAL, NONCONTROVERSIAL , ALL, NONE
    };

    public enum SUBCATEGORY {

        VIOLENCE, OBSCENELANGUAGE, DISTASTEFUL, GAMBLING, SEX, ACCIDENT, SUBSTANCEABUSE, CRIME, ALL, NONE
    };
    
    //Data type can have keywrods and subcats
    protected List<String> kwList;
    protected List<SUBCATEGORY> subCatList;
    protected CATEGORY category; 
    protected boolean debugOn = false;
    protected List<String> ldaInputData;

    public Data() {
        debugOn=false;
        kwList = new ArrayList<String>();
        subCatList = new ArrayList<SUBCATEGORY>();
    }
    
    //SET THE PARAMETERS FOR THIS DATA TYPE
    public void setSubCat(Data.SUBCATEGORY subcat) {
        this.subCatList.add(subcat);
    }
    
    public void setKW(String kw) {
        this.kwList.add(kw.toLowerCase());
    }
    
    public void setDebugOn() {
        this.debugOn=true;
    }
    
    public void setDebugOff() {
        this.debugOn=false;
    }
    
    
    //call this func to read and populate
    public abstract void prepareData(String path);
    
    public abstract void LDA();
}
