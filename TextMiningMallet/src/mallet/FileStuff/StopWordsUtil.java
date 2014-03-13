/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallet.FileStuff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import malletData.Data;

/**
 *
 * @author sverma
 */
public class StopWordsUtil {

    private Map<String, Boolean> stopwordsMap = new LinkedHashMap<String, Boolean>();
    
    
    public Map<String, Boolean> getStopWords() {
        return stopwordsMap;
    }

    public StopWordsUtil() {
        buildStopMap(Data.stopWordsFile);
        addToStopMap(Data.wordsToFilter);


    } //Constructor

    private void addToStopMap(String[] wordsToFilter) {
        for (String s : wordsToFilter) {
            stopwordsMap.put(s, true);
            //System.out.println(s);
        }
    }

    private void buildStopMap(String stop) {
        if (stop == null) {
            System.out.println("stop file not set");
            System.exit(1);
        }

        String line;
        String kw = "";

        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(stop)));
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                // process the lines
                this.stopwordsMap.put(line.trim(), true);

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
    }// buildStopMap
}
