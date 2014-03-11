/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.classification;

import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.SvmLight2FeatureVectorAndLabel;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.types.InstanceList;
import java.io.File;

/**
 *
 * @author sverma
 */
public class SVMLightClassifier {
    
    public void classify(String[] paths) {
        
        String trainFile = paths[0];
        String testFile = paths[1];
        
         Pipe instancePipe = new SerialPipes (new Pipe[] {
             new SvmLight2FeatureVectorAndLabel() 
         });
        
        // Create an empty list of the training instances
        InstanceList ilist1 = new InstanceList (instancePipe);
        InstanceList ilist2 = new InstanceList (instancePipe);
        ilist1.addThruPipe(new FileIterator (trainFile, FileIterator.STARTING_DIRECTORIES));
        //ilist2.addThruPipe(new FileIterator(new File(testFile)));
        
    } //classify
}
