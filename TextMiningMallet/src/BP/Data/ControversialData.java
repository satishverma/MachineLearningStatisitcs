/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Data;

import BP.Utils.ListUtil;
import java.util.List;

/**
 *
 * @author sverma
 */
public class ControversialData extends Data {
    
    
    
    
    
    public ControversialData() {
        super();
        category = CATEGORY.CONTROVERSIAL; 
    }
    
    @Override
    public void prepareData() {
        if(this.debugOn) {
            System.out.println("Cat Name " + category);
            ListUtil.printList(kwList);
            ListUtil.printList(this.subCatList);
        }
    }
    
    
    
}
