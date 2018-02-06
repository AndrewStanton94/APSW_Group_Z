/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Person;
import jim.sums.common.db.Personstaffstatus;
import jim.sums.common.db.Staffstatus;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class PersonstaffstatusFacade extends AbstractFacade<Personstaffstatus> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonstaffstatusFacade() {
        super(Personstaffstatus.class);
    }

    public List<Person> findActiveSupervisor(Staffstatus staffstatus) {
        Query q = em.createNamedQuery("Personstaffstatus.findByIdstaffstatus");
        q.setParameter("staffstatus", staffstatus);
        List<Person> l = new ArrayList<Person>();
        for (Personstaffstatus pss : (List<Personstaffstatus>) q.getResultList()) {
            if (pss.getActive() == 1) {
                l.add(pss.getPerson());
            }
        }
        return l;
    }

    public List<Personstaffstatus> findByPK(String username, Long idStaffStatus) {
        Query q = em.createQuery("SELECT p FROM Personstaffstatus AS p "
                + "WHERE p.personstaffstatusPK.username = '" + username + "' "
                + "AND p.personstaffstatusPK.idstaffstatus = '" + idStaffStatus + "'");
        return q.getResultList();
    }
}
