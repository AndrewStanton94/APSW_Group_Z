
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.TemplateMarkForm;
import jim.sums.common.db.MarkerMark;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class TemplateMarkFormFacade extends AbstractFacade<TemplateMarkForm>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TemplateMarkFormFacade() {
		super(TemplateMarkForm.class);
	}

    public void deleteForMark() {
        em.createQuery("DELETE FROM UnitMarkForms").executeUpdate();
        em.createQuery("DELETE FROM TemplateCriteria").executeUpdate();
        em.createQuery("DELETE FROM TemplateCategory").executeUpdate();
        em.createQuery("DELETE FROM TemplateMarkForm").executeUpdate();
    }
        
        
    
}
