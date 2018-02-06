/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import jim.sums.common.db.MilestoneInstance;
import jim.sums.common.db.Person;
import jim.sums.monitoring.bus.PersonEJB;

/**
 *
 * @author Adrian Earle
 */
@Named(value = "monitoringForSupervisorMB")
@Dependent
public class MonitoringForSupervisor {

    @EJB
    private PersonEJB personEJB;
    
    private List<Person> lStudents = new ArrayList<Person>();
    
    /** Creates a new instance of MonitoringForSupervisor */
    public MonitoringForSupervisor() {
    }
    
    public void doSaveChanges(){
        for (Person p : lStudents){
            p = personEJB.editPerson(p);
        }        
    }
    
    public List<Person> getStudents(){
        return lStudents;
    }
    public void setStudents(List<Person> lp){
        lStudents = lp;
    }
    public boolean addStudent(Person p){
        return lStudents.add(p);
    }
    public boolean removeStudent(Person p){
        return lStudents.remove(p);
    }
    
    public void doActivateMilestone(MilestoneInstance mi){
        mi.setActive(true);
    }
}
