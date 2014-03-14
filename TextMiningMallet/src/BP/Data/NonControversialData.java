/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Data;

import BP.Utils.ListUtil;

/**
 *
 * @author sverma
 */
public class NonControversialData  extends Data {
    
    public NonControversialData() {
        super();
        category = CATEGORY.NONCONTROVERSIAL; 
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
        
    }
}
