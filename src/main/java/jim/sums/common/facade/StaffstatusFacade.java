/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Staffstatus;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class StaffstatusFacade extends AbstractFacade<Staffstatus> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StaffstatusFacade() {
        super(Staffstatus.class);
    }


    public Staffstatus findByName(String statusName) {
        Query q = em.createNamedQuery("Staffstatus.findByStatusname");
        q.setParameter("statusname", statusName);
        return (Staffstatus)q.getSingleResult();   
    }
    
    public void deleteForStaffstatus() {
        em.createQuery("DELETE FROM Staffstatus").executeUpdate();
    }
}
