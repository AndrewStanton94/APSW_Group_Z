/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.bus;

import jim.sums.common.db.ContactDetail;
import jim.sums.common.facade.ContactDetailFacade;

/**
 *
 * @author Adrian Earle
 */
public class ContactDetailEJB {

    public ContactDetail createContactDetail(ContactDetail cd) {
        ContactDetailFacade cdf = null;
        cdf.create(cd);
        return cd;
    }

    public void removeContactDetail(ContactDetail cd) {
        ContactDetailFacade cdf = null;
        cdf.remove(cd);
    }

    public ContactDetail editContactDetail(ContactDetail cd) {
        ContactDetailFacade cdf = null;
        cdf.edit(cd);
        return cd;
    }
}
