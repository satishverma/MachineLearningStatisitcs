/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BP.Utils;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author sverma
 */
public class ListUtil {
    
    public static void printList(List<?> list) {
        Iterator<?> it = list.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        } //while
    } //printList
    
    
    
} //ListUtil
