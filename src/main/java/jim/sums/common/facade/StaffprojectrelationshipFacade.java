
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Staffprojectrelationship;

/**
 *
 * @author Sam
 */
@Stateless
public class StaffprojectrelationshipFacade extends AbstractFacade<Staffprojectrelationship>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public StaffprojectrelationshipFacade() {
		super(Staffprojectrelationship.class);
	}

	public List<Staffprojectrelationship> findByProject(Finalproject project) {
		Query q = em.createNamedQuery("Staffprojectrelationship.findByIdproject");
		q.setParameter("finalproject", project);
		return q.getResultList();
	}
}
