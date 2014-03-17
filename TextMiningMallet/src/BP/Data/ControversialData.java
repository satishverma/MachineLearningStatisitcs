/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Data;

import BP.Utils.ListUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sverma
 */
public class ControversialData extends Data {
    
    
    
    
    
    public ControversialData() {
        super();
        category = CATEGORY.CONTROVERSIAL; 
    }
    
    
    public void prepareData(String path) {
        if(this.debugOn) {
            System.out.println("Cat Name " + category);
            ListUtil.printList(kwList);
            ListUtil.printList(this.subCatList);
        }
        
        //What should this do ?? 
        //Prepare data based on criterion 
        //first filter based on SubCat and then on KW if both are set 
        // if only KW is set then filter based on KW 
        //if only SubCat is set then filter based on SubCat
        generateData(path);
        
    }//prepareData
    
    
    private void generateData(String path) {
        this.ldaInputData = new ArrayList<String>();

        Scanner scan = null;
        String currLine = null;
        String processedLine = null;
        try {
            scan = new Scanner(new BufferedReader(new FileReader(path)));
            while (scan.hasNextLine()) {
                currLine = scan.nextLine();
                processedLine = _process(currLine.trim());
                if(processedLine!=null) {
                    this.ldaInputData.add(processedLine);
                }
            } //while
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControversialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//generateData
    
    
    
    private String _process(String input) {
        String result = null;
        result = BP.Utils.StringUtil.filterText(input);
        return result;
    } //_process
    
    
} //ControversialData
