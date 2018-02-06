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
import jim.sums.common.db.CourseLevel;

/**
 *
 * @author KardousS
 */
@Stateless
public class CourseLevelFacade extends AbstractFacade<CourseLevel> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseLevelFacade() {
        super(CourseLevel.class);
    }

    public List<CourseLevel> findSameName(CourseLevel g) {
        Query q = em.createNamedQuery("CourseLevel.findByName");
        q.setParameter("name", g.getName());
        List<CourseLevel> l = q.getResultList();
        return l;
    }

    /*
     * If there are no objects, create them
     */
    public void Initialise() {
        List<CourseLevel> psl = this.findAll();
        if (psl.isEmpty()) {
            for (CourseLevel ps : CourseLevel.getAllValues()) {
                em.persist(ps);
            }
        }
    }
}
