/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Person;
import jim.sums.common.db.Personstaffstatus;
import jim.sums.common.db.RoleName;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.PersonstaffstatusFacade;
import jim.sums.common.facade.RoleNameFacade;
import jim.sums.common.facade.StaffstatusFacade;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.common.facade.UnitpersoninFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class UserService extends AbstractService {

    @EJB
    private PersonFacade personFacade;
    @EJB
    private UnitinstanceFacade uiFacade;
    @EJB
    private UnitpersoninFacade upiFacade;
    @EJB
    private PersonstaffstatusFacade pssFacade;
    @EJB
    private RoleNameFacade rnf;
    @EJB
    private StaffstatusFacade ssFacade;

    public UserService() {
    }

    public PersonFacade getPersonFacade() {
        return personFacade;
    }

    public List<UnitInstance> unitInstancesSelectionOpen(String username) {
        List<UnitInstance> l = new ArrayList<UnitInstance>();
        if (isStudent(username)) {
            Person p = getPerson(username);
            List<Unitpersonin> upiList = upiFacade.findByPerson(p);

            Date today = new Date();
            UnitInstance ui;
            for (Unitpersonin upi : upiList) {
                if (upi.getValidationdate() != null) { // belonging to that unit confirmed.
                    //TODO: is it the way it is supposed to work?
                    ui = upi.getUnitinstance();
                    ui.getCohortList().size();
                    for (Cohort c : ui.getCohortList()) {
                        if (today.after(c.getRegisterstart()) && today.before(c.getRegisterstop())) {
                            l.add(ui);
                            break;
                        }
                    }
                }
            }
        }
        return l;
    }

    public List<Cohort> cohortSelectionOpen(String supervisor) {
        Person p = getPerson(supervisor);
        List<Cohort> l = new ArrayList<Cohort>();
        if (!p.getCohortList1().isEmpty()) {
            Date today = new Date();
            for (Cohort c : p.getCohortList1()) {
                if (today.after(c.getRegisterstart()) && today.before(c.getRegisterstop())) {
                    l.add(c);
                }
            }
        }
        return l;
    }

    /**
     * Tests if the user is a supervisor registered in a cohort in project
     * allocation process.
     *
     * @param username
     * @return
     */
    public boolean isEngagedSupervisor(String username) {
        if (!getPerson(username).isStaff()) {
            return false;
        }

        List<Personstaffstatus> l = pssFacade.findByPK(username, ssFacade.findByName("supervisor").getId());
        if (!(l.size() > 0 && l.get(0).getActive() == 1)) { //is active supervisor
            return false;
        }
//        This if : check if username is cohort supervisor, so cohort manager, it should be not appear here.
//        if (cohortSelectionOpen(username).isEmpty()) {
//            return false;
//        }

        return true;
    }

    public boolean isAdmin(String username) {
        Person p = getPerson(username);
        return p.isAdmin();
    }

    public boolean isStaff(String username) {
        Person p = getPerson(username);
        return p.isStaff();
    }

    public boolean isStudent(String username) {
        Person p = getPerson(username);
        return p.isStudent();
    }

    public boolean isExternal(String username) {
        Person p = getPerson(username);
        return p.isExternal();
    }

    
    public Person getPerson(String username) {
        return personFacade.find(username);
    }

    public void updateLastLogin(String user) {
        Person p = personFacade.find(user);
        p.setLastlogin(new Date());
        personFacade.edit(p);
    }

    public List<Person> getAllCurrentStaff() {
        RoleName role = rnf.staff();
        List<Person> allStaff = personFacade.findByRole(role);
        return allStaff;
    }
}