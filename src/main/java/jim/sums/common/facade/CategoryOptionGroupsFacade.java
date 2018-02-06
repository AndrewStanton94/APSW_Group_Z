
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.CategoryOptionGroups;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class CategoryOptionGroupsFacade extends AbstractFacade<CategoryOptionGroups>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CategoryOptionGroupsFacade() {
		super(CategoryOptionGroups.class);
	}
    
}
