/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.util;

import java.util.Date;
import java.util.List;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.db.Academicyear;

/**
 *
 * @author BriggsJ
 */
public class AcademicYearUtil {

    static private Academicyear currentYear = null;
    static private Date lastChecked = null;

    public static Academicyear findCurrentYear(List<Academicyear> allYears) throws BusinessException {
        Date now = new Date();
        //TODO
        //calculate whether currentYear needs to be refreshed
        if (lastChecked == null || currentYear == null) {
            for (Academicyear ay : allYears) {
                if (now.after(ay.getStartdate()) && now.before(ay.getEnddate())) {
                    currentYear = ay;
                    lastChecked = now;
                    return ay;
                }
            }
            throw new BusinessException("Cannot find current academic year");
        } else {
            return currentYear;
        }
    }

    public static Academicyear getCurrentYear() throws BusinessException {
        if (currentYear == null) {
            throw new BusinessException("Current academic year not set");
        }
        return currentYear;
    }

    public static void setCurrentYear(Academicyear ay) {
        currentYear = ay;
    }
}
