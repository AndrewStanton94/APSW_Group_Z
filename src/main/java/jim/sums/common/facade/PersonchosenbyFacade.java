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
import jim.sums.common.db.Personchosenby;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class PersonchosenbyFacade extends AbstractFacade<Personchosenby>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public PersonchosenbyFacade() {
		super(Personchosenby.class);
	}
	
	public List<Personchosenby> findSame(Person chooser, Person chosen){
		Query q = em.createQuery("SELECT p FROM Personchosenby AS p "
				+ "WHERE p.personchosenbyPK.usernamechooser = '"+chooser.getUsername()+"' "
				+ "AND p.personchosenbyPK.usernamechosen = '"+chosen.getUsername()+"'");
		return q.getResultList();
	}
	
	public List<Personchosenby> findByChooserUsername(Person p){
		Query q = em.createNamedQuery("Personchosenby.findByUsernamechooser");
		q.setParameter("usernamechooser",p.getUsername());
		return q.getResultList();
	}
}