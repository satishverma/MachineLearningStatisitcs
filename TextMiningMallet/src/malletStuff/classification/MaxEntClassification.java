/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.classification;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.types.InstanceList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author sverma
 */
public class MaxEntClassification {
    
    private InstanceList trainingInstances;
    
    public void prepareData() {
        
    } //prepareData
    
    public Classifier trainClassifier() {
        
        ClassifierTrainer trainer = new MaxEntTrainer();
        return trainer.train(trainingInstances);
    } //trainClassifier
    
    
    
    
    
    
    
    public void saveClassifier(Classifier classifier, File serializedFile) throws IOException {

        // The standard method for saving classifiers in                                                   
        //  Mallet is through Java serialization. Here we                                                  
        //  write the classifier object to the specified file.                                             
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedFile));
        oos.writeObject(classifier);
        oos.close();
    } //saveClassifier

    
    
    
    
    
    
    
    public Classifier loadClassifier(File serializedFile) throws FileNotFoundException, IOException, ClassNotFoundException {

        // The standard way to save classifiers and Mallet data                                            
        //  for repeated use is through Java serialization.                                                
        // Here we load a serialized classifier from a file.                                               
        Classifier classifier;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedFile));
        classifier = (Classifier) ois.readObject();
        ois.close();

        return classifier;
    } //loadClassifier

    
    
    
    
    
    
} //MaxEntClassification
