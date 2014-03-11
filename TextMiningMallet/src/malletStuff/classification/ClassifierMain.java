/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.classification;

import malletData.Data;

/**
 *
 * @author sverma
 */
public class ClassifierMain {
    
    
    public static void main(String[] args) {
        //DocumentClassifier dc = new DocumentClassifier();
        //String[] dirs = {Data.malletDir+"sample-data/web/de",Data.malletDir+"sample-data/web/en"};
        //dc.classify(dirs);
        
        
        
        SVMLightClassifier svmC = new SVMLightClassifier();
        String[] dirs_ ={Data.malletHome+"expt/svm/trainDir/",Data.malletHome+"expt/svm/testDir/"};
        svmC.classify(dirs_);
    }
}
