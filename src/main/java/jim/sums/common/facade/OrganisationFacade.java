/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Organisation;

/**
 *
 * @author KardousS
 */
@Stateless
public class OrganisationFacade extends AbstractFacade<Organisation> {

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public OrganisationFacade() {
		super(Organisation.class);
	}

    public List<Organisation> findSameName(Organisation o) {
        Query q = em.createNamedQuery("Organisation.findByNameorganisation");
        q.setParameter("nameorganisation", o.getNameorganisation());
        List<Organisation> l = q.getResultList();
        return l;
    }

	
}
