
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jim.sums.common.db.CategoryOptionGroups;
import jim.sums.common.db.CategoryOptions;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class CategoryOptionsFacade extends AbstractFacade<CategoryOptions>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CategoryOptionsFacade() {
		super(CategoryOptions.class);
	}

	public List<CategoryOptions> findByGroup(CategoryOptionGroups cog) {
		Query q = em.createQuery("SELECT c FROM CategoryOptions AS c "
				+ "WHERE c.optGroup = :cog");
		q.setParameter("cog", cog);
		return q.getResultList();
	}
        public List<CategoryOptions> findAll2() {
            return em.createQuery("SELECT c FROM CategoryOptions c ORDER BY c.optId ASC").getResultList() ;
        }
        
        /**
         * Finds the List based on a groupId of 1.
         * It Retrieve all the Column Labels (eg 0-9,10-19)
         * @return returns a list ofCategoryOptions
         */
        public List<CategoryOptions> findColumnLabels()
        {
            TypedQuery<CategoryOptions> q = em.createQuery("SELECT c FROM CategoryOptions c WHERE c.optGroup = :optGroup ORDER BY c.optId ASC",CategoryOptions.class);
           q.setParameter("optGroup", new CategoryOptionGroups(1));
         return q.getResultList();
        }
}
