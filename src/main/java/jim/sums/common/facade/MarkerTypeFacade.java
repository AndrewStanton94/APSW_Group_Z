
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.MarkerType;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class MarkerTypeFacade extends AbstractFacade<MarkerType>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MarkerTypeFacade() {
		super(MarkerType.class);
	}
    
}
