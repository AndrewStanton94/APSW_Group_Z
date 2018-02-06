
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import jim.sums.common.db.TemplateCriteria;
import jim.sums.common.db.CategoryOptionGroups;
import jim.sums.common.db.CategoryOptions;
import jim.sums.common.db.MarkCategory;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class TemplateCriteriaFacade extends AbstractFacade<TemplateCriteria>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TemplateCriteriaFacade() {
		super(TemplateCriteria.class);
	}
        
//        public List<String> getToolTip(){
//            
//        }
    
        //This is to Retrieve all the Column Labels (eg 0-9,10-19)
        
        public TemplateCriteria findCatOptionCriteriaByCatOptAndOptID(CategoryOptions opt, MarkCategory mc)
        {
            TypedQuery<TemplateCriteria> q = em.createQuery("SELECT c FROM  TemplateCriteria c WHERE c.catOption = :catOption AND c.markCategory=:markCat ",TemplateCriteria.class);
           q.setParameter("catOption", opt);
           q.setParameter("markCat", mc);
         return q.getSingleResult();
        }
        
         public List<TemplateCriteria> findCatOptionCriteriaListByCatOptAndOptID(CategoryOptions opt, MarkCategory mc)
        {
            TypedQuery<TemplateCriteria> q = em.createQuery("SELECT c FROM  TemplateCriteria c WHERE c.catOption = :catOption AND c.markCategory=:markCat ",TemplateCriteria.class);
           q.setParameter("catOption", opt);
           q.setParameter("markCat", mc);
         return q.getResultList();
        }
}
