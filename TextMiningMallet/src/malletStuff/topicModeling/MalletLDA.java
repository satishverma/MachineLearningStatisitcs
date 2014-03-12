/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malletStuff.topicModeling;

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
import cc.mallet.types.Instance;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import malletData.Data;
import static malletStuff.topicModeling.LDA.sortByValues;

/**
 *
 * @author sverma
 */
public class MalletLDA {

    
    private static int numTopics = 50;
    private static int numIter = 500;
    private static int numThreads = 5;
    private static double alpha_t = 2; //defualt for the expt was 1
    private static double beta_w = 0.001;
    private ArrayList<Pipe> pipeList;
    private ParallelTopicModel model;
    
    public MalletLDA() {
        
    }
    
    
    
   
   

   
   
   
   
    //CODING FOR INDIVIDUAL CATEGORIES
   
   
   
   
   private  void _buildTopicModel(List<Instance> data) {
       model = new ParallelTopicModel(numTopics, alpha_t, beta_w );
       preparePipelist();
       InstanceList instances = new InstanceList(new SerialPipes(pipeList));
       
       
       //Create Instances and Add
       Instance instance = null;
       for(Instance ins:data)
           instances.addThruPipe(ins);
       
       
       
        model.addInstances(instances);
        model.setNumThreads(numThreads);
        model.setNumIterations(numIter);
        try {
            model.estimate();
        } catch (IOException ex) {
            Logger.getLogger(MalletLDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //EXPT 2
          //basic info on the alphabet
        try {
          
             analyzeAlphabet();
        } catch (IOException ex) {
            Logger.getLogger(MalletLDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        //EXPT 2
        
        try {
            //GET TOP WORDS 
            //get top terms in the corpus 
            getTopWords(100000);
        } catch (IOException ex) {
            Logger.getLogger(MalletLDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
   } //_buildTopicModel
   
   
   
   
  
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
   

    public void  trainLDA(Data.CatNames cat,Data.Categories categ, String rawConFile,String rawNonCFile ) {
        System.out.println("Training for " + cat +" " + categ);
        prepareData(cat.toString(),categ.toString(),rawConFile,rawNonCFile);
        
    } //trainLDA
    
    
      /*
    * catname violence crime etc
    * category contro or nontro
    * cFile file containing contro data
    * ncFile file containing noncontro data
    * 
    */
   private  void prepareData(String catname, String category, String cFile, String ncFile) {
       if(category.equals("Controversial")) { 
           System.out.println("We only need Controversial");
           List<Instance> data = getControversialData(catname,category,cFile);
           //Now that you have data, go one and build the TOPIC MODEL
           _buildTopicModel(data);
           
       } else if (category.equals("NonControversial")) {
            System.out.println("We only need NON Controversial");
            
       } else if (category.equals("All")) {
            
           System.out.println("We  need Both ");
       }
   } //prepareData
    
    
    
    
    
    
     private  List<Instance> getControversialData(String catname, String category,String fileName) {
       List<Instance> data = new ArrayList<>();
       DataPreparer dp = new DataPreparer();
       //open the file , read and compare with category 
       data = dp.getData(catname,category,fileName);
       //printData(data);
       return data;
   } //getControversialData
   
   
   private void printData(List<?> list) {
       //past any type of list
       for(Object o:list) {
           System.out.println(o.toString());
       }
       System.out.println();
   } // printData
    
    
    
    
     private  void preparePipelist() {
        pipeList = new ArrayList<Pipe>();
        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        //pipeList.add(new TokenSequenceRemoveStopwords(new File(args[1]+"stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequenceRemoveStopwords(new File(Data.malletDir+"stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());
    } //preparePipelist
    
    
    
    
    
    
    
    
    
     //Define a generic method to sort HashMap by VALUE Values can be repeated and be null while keys are unique n not-null
   public  <K extends Comparable,V extends Comparable> LinkedHashMap<K,V> sortByValues(Map<K,V> map) {
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
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public void runTopicModel_(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
       
        //Parameters 
        
        int numTopics = 20;
        int numIter = 750;
        int numThreads=5;
        int rankVal=20;//number of words that need to be processed
        double alpha_t = 5; //1
        double beta_w = 0.0001;
        
        // Begin by importing documents from text to feature sequences
        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        pipeList.add(new TokenSequenceRemoveStopwords(new File(args[1]+"stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());

        InstanceList instances = new InstanceList(new SerialPipes(pipeList));

        Reader fileReader = new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8");
        instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                3, 2, 1)); // data, label, name fields
        
        int numInstances = instances.size(); //numberInstances

        // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
        //  Note that the first parameter is passed as the sum over topics, while
        //  the second is the parameter for a single dimension of the Dirichlet prior.
        
        ParallelTopicModel model = new ParallelTopicModel(numTopics, alpha_t, beta_w );

        model.addInstances(instances);

        // Use two parallel samplers, which each look at one half the corpus and combine
        //  statistics after every iteration.
        model.setNumThreads(numThreads);

        // Run the model for 50 iterations and stop (this is for testing only, 
        //  for real applications, use 1000 to 2000 iterations)
        model.setNumIterations(numIter);
        model.estimate();

        // Show the words and topics in the first instance

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();
     
        
        
        
        
        
        
        //get top topic for each of the doc
        /*
         * Approach:  From model datastructure, get FeatureSequence and LabelSequence
         * For each doc, for each term, we get the best choice topic 
         * doc term1-12 term2-70 and so on 
         */
        for (int i = 0; i < numInstances; i++) {
            int docID = i;
            int topTopicWords=50000;
            FeatureSequence tokens = (FeatureSequence) model.getData().get(docID).instance.getData();
            LabelSequence topics = model.getData().get(docID).topicSequence;
            Formatter out = new Formatter(new StringBuilder(), Locale.US);
            for (int position = 0; position < tokens.getLength(); position++) {
                if(topics.getIndexAtPosition(position)<topTopicWords) {
                   out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
                } else {
                    out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), -1);
                }
            }
           // System.out.println("doc id  " + i + " " + out);
        } 

        
        
        
        
        
        
        
        
        //get prop for docs 
   
        int docID=0;
        FeatureSequence tokens = (FeatureSequence) model.getData().get(docID).instance.getData();
        LabelSequence topics = model.getData().get(docID).topicSequence;

        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        System.out.println("RESULT "+out);

        // Estimate the topic distribution of the first instance, 
        //  given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(docID);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

        // Show top few eg 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < rankVal) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
            System.out.println("Topic in Doc  " +docID+" "+out);
        }

        
        
        
        
        
        
        
        //Testing the Inference Process 
        
        
        /*
        // Create a new instance with high probability of topic 0
        int testdocid=67;
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(testdocid).iterator();

        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(instances.getPipe());
        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
        System.out.println("0\t" + testProbabilities[0]);
        
        */
        
        
        
        
        //Satish
        //Using the API of ParallelTopicModel
        //http://mallet.cs.umass.edu/api/
        
        
        /*
         * 
         * getTopoWords(int numWords)
         * get top numWords words for teach of the topics
         * 
         */
        
        
        
        int numWords=15;
        HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
        String s;
        Object[][] topWords= model.getTopWords(numWords);
        for(int i=0; i<topWords.length;i++) {
            System.out.println("Topic " +i+" ");
            for (int j=0;j<numWords;j++) {
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
        Iterator it = wordCount.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + "," + pairs.getValue());
        } 
        
    } //main
}
