/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.util;

import java.util.ArrayList;
import java.util.List;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;

/**
 *
 * @author BriggsJ
 */
public class FinalProjectUtil {

    static public List<Finalproject> getProjectsToFeedback(Person currUser, List<Cohort> cohortsManager, List<Finalproject> allProjects) {
        List<Finalproject> projectList = new ArrayList<Finalproject>();
        for (Finalproject fp : allProjects) {
            for (Staffprojectrelationship sp : fp.getStaffprojectrelationshipList()) {
                if (currUser.equals(sp.getPerson())) {
                    projectList.add(fp);
                }
            }
            //For each of the Cohort where the current user is Cohort coordinator s/he should can see all of the project in this cohort
            for (Cohort c : cohortsManager) {
                if (fp.getUnitinstance().getCohortList() != null && fp.getUnitinstance().getCohortList().contains(c)) {
                    if (!projectList.contains(fp)) {
                        projectList.add(fp);
                    }
                }

            }
        }
        return projectList;
    }
}
