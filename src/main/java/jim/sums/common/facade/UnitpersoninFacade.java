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
import jim.sums.common.db.Person;
import jim.sums.common.db.Unitpersonin;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class UnitpersoninFacade extends AbstractFacade<Unitpersonin> {

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UnitpersoninFacade() {
		super(Unitpersonin.class);
	}
	
	public List<Unitpersonin> findByPerson(Person p){
		Query q = em.createNamedQuery("Unitpersonin.findByPerson");
		q.setParameter("person", p.getUsername());
		List<Unitpersonin> l = q.getResultList();
		return l;
	}

	public List<Unitpersonin> findSame(int idUnitInstance, String username) {
		Query q = em.createQuery("SELECT u FROM Unitpersonin AS u "
				+ "WHERE u.unitpersoninPK.idunitinstance = '"+idUnitInstance+"'"
				+ "AND u.unitpersoninPK.person = '"+username+"'");
		List<Unitpersonin> l = q.getResultList();
		return l;
	}
}