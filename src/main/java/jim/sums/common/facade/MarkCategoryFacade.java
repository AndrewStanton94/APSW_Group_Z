
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.MarkCategory;
import jim.sums.common.db.TemplateCategory;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class MarkCategoryFacade extends AbstractFacade<MarkCategory>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MarkCategoryFacade() {
		super(MarkCategory.class);
	}
}
