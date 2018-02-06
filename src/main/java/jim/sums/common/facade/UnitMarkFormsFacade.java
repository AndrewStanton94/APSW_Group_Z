
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import jim.sums.common.db.Academicyear;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitMarkForms;
import jim.sums.common.db.UnitInstance;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@Stateless
public class UnitMarkFormsFacade extends AbstractFacade<UnitMarkForms>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UnitMarkFormsFacade() {
		super(UnitMarkForms.class);
	}
    
        /**
         * This is the method that gets the UnitMarkForms based on UnitInstance
         * @param ui is the UnitInstance based on search criteria
         * @return returns a Unit Mark Form
         */
        public UnitMarkForms findByUnitInstance(UnitInstance ui){
            TypedQuery<UnitMarkForms> query = em.createQuery("SELECT u FROM UnitMarkForms u WHERE u.unitInstance = :unitInst",UnitMarkForms.class);
            query.setParameter("unitInst", ui);
            return query.getSingleResult();
        }
}
