/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.Person;
import jim.sums.common.db.Suspension;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class SuspensionFacade extends AbstractFacade<Suspension> {

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public SuspensionFacade() {
		super(Suspension.class);
	}

    public boolean isSuspended(Person p) {
        for(Suspension s: findAll()){
			if(s.getSuspendedperson().equals(p)) return true;
		}
        return false;
    }


}