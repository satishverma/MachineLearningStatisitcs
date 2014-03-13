/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletData;

/**
 *
 * @author sverma
 */
public class Data {
    public static String malletHome = "/home/sverma/work/mallet/";
    public static String malletDir = "/home/sverma/work/mallet/mallet-2.0.7/";
    public static String malletDataDir = "data/";
    public static String malletDataBpDir = "data/bp/";
    
    
    public static String stopWordsFile = "data/stopwords.txt";
    
    
    //data files 
    public static String dataC = "data/bp/contro/controversialTitles";
    public static String dataNC = "data/bp/noncontro/nonControversial";
    public static String dataCFiltered = "data/bp/dataC.txt";
    public static String dataNCFiltered = "data/bp/dataNC.txt";
    
    
    //data per category 
    public enum CatNamesAll {
        Violence,ObsceneLanguage,Distasteful,Gambling,Sex,Accident,SubstanceAbuse,Crime, All
    }
    
    public enum CategoriesAll { 
        Controversial, NonControversial, All
    }
    
    
    //THIS IS WHAT WE ARE USING
    public enum CatNames {
      Crime
    }
    
    public enum Categories { 
        Controversial
    }
    
    public enum SourceType {
        Controversial,NonControversial
    }
    
    
    public static String[] wordsToFilter = {"null","part","video","episode","review"};
            
    
    
} //Data
