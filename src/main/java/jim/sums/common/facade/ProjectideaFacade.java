/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Ideastatus;
import jim.sums.common.db.Person;
import jim.sums.common.db.ProjectKind;
import jim.sums.common.db.Projectidea;

/**
 *
 * @author KardousS
 */
@Stateless
public class ProjectideaFacade extends AbstractFacade<Projectidea> {

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ProjectideaFacade() {
		super(Projectidea.class);
	}

	public List<Projectidea> findSameName(Projectidea pi) {
		Query q = em.createNamedQuery("Projectidea.findByIdeatitle");
		q.setParameter("ideatitle", pi.getIdeatitle());
		List<Projectidea> l = q.getResultList();
		return l;
	}

	public List<Projectidea> findByOwner(Person userObject) {
		//Query q = em.createQuery("select p from Projectidea p where p.person='"+userObject+"'");
		List<Projectidea> l = new ArrayList();
		Projectidea idea;
		List<Projectidea> t = findAll();
		Iterator<Projectidea> it = t.iterator();

		while (it.hasNext()) {
			idea = it.next();

			if (idea.getOwneridea().equals(userObject)) {
				l.add(idea);
			}
		}

		return l;
	}

	public List<Projectidea> findByTitle(String ideaTitle) {

		//Query q = em.createQuery("select p from Projectidea p where p.person='"+userObject+"'");
		List<Projectidea> l = new ArrayList();
		Projectidea idea;
		List<Projectidea> t = findAll();
		Iterator<Projectidea> it = t.iterator();

		while (it.hasNext()) {
			idea = it.next();

			if (idea.getIdeatitle().indexOf(ideaTitle) > -1) {
				l.add(idea);
			}
		}

		return l;
	}

	public List<Projectidea> findByStatus(Ideastatus ideaStatus) {

		//Query q = em.createQuery("select p from Projectidea p where p.person='"+userObject+"'");
		List<Projectidea> l = new ArrayList();
		Projectidea idea;
		List<Projectidea> t = findAll();
		Iterator<Projectidea> it = t.iterator();

		while (it.hasNext()) {// browsing all the ideas
			idea = it.next();

                        try {
                            if (idea.getIdeastatus().equals(ideaStatus)) {
                                    l.add(idea);
                            }
                        } catch (Exception ex) {
                            // Mean that column IDEASTATUS of ProjectIdea is NULL => error
                            // So no add to the list of ProjectIdea to show
                        }

		}

		return l;
	}

	public List<Projectidea> findAllOrderedByPopularity() {
		Query q = em.createQuery(""
				+ "SELECT icb.projectidea AS popularity "
				+ "FROM Ideachosenby AS icb "
				+ "GROUP BY icb.projectidea "
				+ "ORDER BY SUM(icb.idearank) DESC");
		return q.getResultList();
	}
        
        
        
   /**
     * Method used for Project Ideas searching
     */
  public List<Projectidea> searchIdeas(String s) {
      
        Query q1=em.createQuery("SELECT k.kindname FROM ProjectKind k WHERE UPPER(k.kindname) like :searchField");
        q1.setParameter("searchField", "%"+s+"%");  
        List<String> kdList = (List<String>) q1.getResultList();
        String kindQuery="";
        if(kdList.size()==1){
           kindQuery="OR  :kindList IN  (SELECT pr.kindname  from p.kindList pr)";           
        }
       
        Query q = em.createQuery("SELECT p FROM Projectidea p WHERE UPPER(p.ideatitle) like :searchField OR "
                + "UPPER(p.description) like :searchField OR UPPER(p.aims) like :searchField OR "
                + "UPPER(p.ideastatus.ideastatusname) like :searchField " + kindQuery);
        q.setParameter("searchField", "%"+s+"%");
        if(kdList.size()==1){
            q.setParameter("kindList", kdList);
        }

        List<Projectidea> stList = q.getResultList();
        return stList;
    }
        
        
}
