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

/**
 *
 * @author Sam
 */
@Stateless
public class CourseFacade extends AbstractFacade<Course> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseFacade() {
        super(Course.class);
    }

    public List<Course> findSameName(Course u) {
        Query q = em.createNamedQuery("Course.findByCoursename");
        q.setParameter("coursename", u.getCoursename());
        List<Course> l = q.getResultList();
        return l;
    }

    public void deleteForCourse() {
        em.createQuery("DELETE FROM Courseinstance").executeUpdate();
        em.createQuery("DELETE FROM Course").executeUpdate();
    }
}
