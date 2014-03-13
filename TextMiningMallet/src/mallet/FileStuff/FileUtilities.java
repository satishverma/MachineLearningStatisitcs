/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallet.FileStuff;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sverma
 */
public class FileUtilities {  
    
    private List<String> data;
    
    
    public List<String> readFile(String in) {
        data = new ArrayList<String>();
        try {

            Scanner scan = new Scanner(new BufferedReader(new FileReader(in)));
            while (scan.hasNextLine()) {
                data.add(scan.nextLine());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    } //readFile
     
    
}
