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
    public static String malletDir = "/home/sverma/work/mallet/mallet-2.0.7/";
    public static String malletDataDir = "/home/sverma/work/mallet/expt/data/";
    public static String malletDataBpDir = "/home/sverma/work/mallet/expt/data/bp/";
    public static String malletHome = "/home/sverma/work/mallet/";
    
    public static String stopWordsFile = "/home/sverma/work/hadoopStuff/data/stopwords.txt";
    
    
    //data files 
    public static String dataC = "/home/sverma/work/mallet/expt/data/bp/contro/controversialTitles";
    public static String dataNC = "/home/sverma/work/mallet/expt/data/bp/noncontro/nonControversial";
    public static String dataCFiltered = "/home/sverma/work/mallet/expt/data/bp/dataC.txt";
    public static String dataNCFiltered = "/home/sverma/work/mallet/expt/data/bp/dataNC.txt";
    
    
    //data per category 
    public enum CatNamesAll {
        Violence,ObsceneLanguage,Distasteful,Gambling,Sex,Accident,SubstanceAbuse,Crime, All
    }
    
    public enum CategoriesAll { 
        Controversial, NonControversial, All
    }
    
    public enum CatNames {
        Violence
    }
    
    public enum Categories { 
        Controversial
    }
    
} //Data
