/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.topicModeling;

/**
 *
 * @author sverma
 */



import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.regex.Pattern;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class LDA {

    
    private static int numTopics = 50;
    private static int numIter = 500;
    private static int numThreads = 5;
    private static double alpha_t = 2; //defualt for the expt was 1
    private static double beta_w = 0.001;
    private ArrayList<Pipe> pipeList;
    private ParallelTopicModel model;
    
    public LDA() {
        model = new ParallelTopicModel(numTopics, alpha_t, beta_w );
        
    }
    
        private void preparePipelist(String[] args) {
        pipeList = new ArrayList<Pipe>();
        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        //pipeList.add(new TokenSequenceRemoveStopwords(new File(args[1]+"stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequenceRemoveStopwords(new File(Data.malletDir+"stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());
    }
    
    
    public void runTopicModel(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        
        preparePipelist(args); //pipeList is ready
        
        //EACH MODEL HAS ITS OWN INSTANCES AND INSTANCELIST
        InstanceList instances = new InstanceList(new SerialPipes(pipeList));
        Reader fileReader = new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8");
        instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                3, 2, 1)); // data, label, name fields
        
        int numInstances = instances.size(); //numberInstances
        System.out.println(numInstances); //basically number of documents 
        
   
        model.addInstances(instances);
        model.setNumThreads(numThreads);
        model.setNumIterations(numIter);
        model.estimate();
        
        //Analyze MODEL PROPERTIES
        
        //basic info on the alphabet
        analyzeAlphabet();
        
        
        //get top terms in the corpus 
        //getTopWords(100000);
    } 
    
    
    
    public ParallelTopicModel getTopicModel() {
        return model;
        
    }
    
    
    
    public void analyzeAlphabet() throws IOException {
        BufferedWriter bw = new BufferedWriter (new FileWriter(new File(Data.malletDataBpDir + "alphabet.txt")));
        
        Formatter formatter = new Formatter(bw, Locale.US);
        Alphabet alphabet = model.getAlphabet();
        System.out.println("Size of Alphabet " + alphabet.size());
        Object[] alphabetArr = alphabet.toArray();
        for(Object o:alphabetArr) {
            //System.out.print((String)(o)+ " ");
            formatter.format("%s,%s\n", (String)(o),"1");
        }
        //System.out.println();
        
        
        formatter.flush();
        formatter.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void getTopWords(int numWords_) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        BufferedWriter bw = new BufferedWriter (new FileWriter(new File(Data.malletDataBpDir + "rinput.txt")));
        Formatter formatter = new Formatter(bw, Locale.US);
        
        
        int numWords=numWords_;
        HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
        String s;
        Object[][] topWords= model.getTopWords(numWords);
        for(int i=0; i<topWords.length;i++) {
            System.out.println("Topic " +i+" ");
            for (int j=0;j<topWords[i].length;j++) {
                s=topWords[i][j].toString();
                System.out.println(s);
                if(wordCount.containsKey(s)) {
                    wordCount.put(s, wordCount.get(s)+1);
                } else {
                    wordCount.put(s, 1);
                }
            }
            System.out.println();
            
        }
        //print the hashmap
        LinkedHashMap<String,Integer> sortedMap =  new LinkedHashMap<String,Integer>();
        sortedMap = sortByValues(wordCount);
        Iterator it = sortedMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            //System.out.println(pairs.getKey() + "," + pairs.getValue());
            formatter.format("%s,%s\n", pairs.getKey(),pairs.getValue());
        } 
        //System.out.println("Size of the Word MAP " + sortedMap.size());
        //System.out.println(sb.toString());
        formatter.flush();
        formatter.close();
        
        
    } //getTopWords
    
    
    
    
   
    
    
    
    
    
    //Define a generic method to sort HashMap by VALUE Values can be repeated and be null while keys are unique n not-null
   public static <K extends Comparable,V extends Comparable> LinkedHashMap<K,V> sortByValues(Map<K,V> map) {
       java.util.List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
       
       Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
       
       //LinkedHashMap will keep the keys in the order they are inserted
       //which is currently sorted on natural ordering
       LinkedHashMap<K,V> sortedMap = new LinkedHashMap<K,V>();
     
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
     
        return sortedMap;
       
      
   } 
} //LDA