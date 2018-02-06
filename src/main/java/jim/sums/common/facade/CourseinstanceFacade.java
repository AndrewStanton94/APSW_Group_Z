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
import jim.sums.common.db.Course;
import jim.sums.common.db.Courseinstance;
/**
 *
 * @author Sam
 */
@Stateless
public class CourseinstanceFacade extends AbstractFacade<Courseinstance>{

	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CourseinstanceFacade() {
		super(Courseinstance.class);
	}

    public List<Courseinstance> findSameName(Courseinstance c) {
        Query q = em.createNamedQuery("Courseinstance.findByCourseinstancename");
        q.setParameter("courseinstancename", c.getCourseinstancename());
        List<Courseinstance> l = q.getResultList();
        return l;
    }
    //Doing for refreshing
      public List<Courseinstance> findCourseInstanceByCourse(Course u) {
         Query query = em.createNamedQuery("Courseinstance.findByCourse");
        query.setParameter("course",u);
        List<Courseinstance> stList = query.getResultList();
        return stList;
		
	}

}
