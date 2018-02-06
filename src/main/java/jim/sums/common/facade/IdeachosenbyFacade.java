/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jim.sums.common.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Ideachosenby;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.UnitInstance;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class IdeachosenbyFacade extends AbstractFacade<Ideachosenby> {

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public IdeachosenbyFacade(){
		super(Ideachosenby.class);
	}

	public List<Ideachosenby> findSame(Projectidea i, Person p){
		Query q = em.createQuery("SELECT i FROM Ideachosenby AS i "
				+ "WHERE i.ideachosenbyPK.username = '"+p.getUsername()+"' "
				+ "AND i.ideachosenbyPK.idprojectidea = '"+i.getIdprojectidea()+"' ");
		return q.getResultList();
//		//If one can chose same idea in different units...
//		List<Ideachosenby> l = new ArrayList<Ideachosenby>();
//		for(Ideachosenby icb: (List<Ideachosenby>)q.getResultList()){
//			if(icb.getUnitinstance().equals(ui)) l.add(icb);
//		}
//		return l;
	}

	public List<Ideachosenby> findByUsername(Person p){
		Query q = em.createNamedQuery("Ideachosenby.findByUsername");
		q.setParameter("username",p.getUsername());
		return q.getResultList();
	}
}
