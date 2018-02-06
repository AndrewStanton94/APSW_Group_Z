
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.TemplateCategory;
import jim.sums.common.db.TemplateMarkForm;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class MarkFormCategoriesFacade extends AbstractFacade<TemplateCategory>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MarkFormCategoriesFacade() {
		super(TemplateCategory.class);
	}

	public List<TemplateCategory> findByForm(TemplateMarkForm form) {
		Query q = em.createQuery("SELECT mfc FROM TemplateCategory AS mfc "
				+ "WHERE mfc.formid = :form");
		q.setParameter("form", form);
		return q.getResultList();
	}
    
        
}
