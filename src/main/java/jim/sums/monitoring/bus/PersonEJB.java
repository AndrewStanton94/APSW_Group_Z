/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.bus;

import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;

/**
 *
 * @author Adrian Earle
 */
public class PersonEJB {

    public Person createPerson(Person p) {
        PersonFacade pf = null;
        pf.create(p);
        return p;
    }

    public void removePerson(Person p) {
        PersonFacade pf = null;
        pf.remove(p);
    }

    public Person editPerson(Person p) {
        PersonFacade pf = null;
        pf.edit(p);
        return p;
    }
}
