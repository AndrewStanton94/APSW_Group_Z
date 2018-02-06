
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Cohort;

/**
 *
 * @author Sam
 */
@Stateless
public class CohortFacade extends AbstractFacade<Cohort>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CohortFacade() {
		super(Cohort.class);
	}

    public List<Cohort> findSameName(Cohort c) {
        Query q = em.createNamedQuery("Organisation.Cohort.findByCohortlabel");
        q.setParameter("cohortlabel", c.getCohortlabel());
        List<Cohort> l = q.getResultList();
        return l;
    }
    public void deleteForCohort() {
        em.createQuery("DELETE FROM Cohort").executeUpdate();
    }
    
}
