/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.ContactDetail;
/**
 *
 * @author Adrian Earle
 */
@Stateless
public class ContactDetailFacade extends AbstractFacade<ContactDetail>{
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ContactDetailFacade(){
        super(ContactDetail.class);
    }
}