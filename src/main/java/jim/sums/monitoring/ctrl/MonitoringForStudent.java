/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.ctrl;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import jim.sums.common.db.ContactDetail;
import jim.sums.common.db.Person;
import jim.sums.monitoring.bus.PersonEJB;

/**
 *
 * @author Adrian Earle
 */
@Named(value = "monitoringForStudentMB")
@Dependent
public class MonitoringForStudent {

    @EJB
    private PersonEJB personEJB;
    private Person oStudent = new Person();

    /** Creates a new instance of MonitoringForStudent */
    public MonitoringForStudent() {
    }

    public void doSaveChanges() {
        oStudent = personEJB.editPerson(oStudent);
    }

    public void doAddContact(Boolean bIsEmail) {
        ContactDetail cd = new ContactDetail();
        if (bIsEmail) {
            cd.setType("email");
        }
        oStudent.addContactDetail(cd);
    }

    public Person getPerson() {
        return oStudent;
    }

    public void setPerson(Person p) {
        oStudent = p;
    }
}
