/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.util;

import java.util.Date;
import java.util.List;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;

/**
 *
 * @author Adrian Earle
 */
public class UnitPersonInUtil {
    public static UnitInstance findCurrent(List<Unitpersonin> lupi){
        Date today = new Date();
        for (Unitpersonin upi : lupi){
            if(upi.getUnitinstance().getAcademicyear().isDateInAcademicYear(today)){
                return upi.getUnitinstance();
            }
        }
        return null;
    }
}
