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
import jim.sums.common.db.ProjectKind;

/**
 *
 * @author KardousS
 */
@Stateless
public class ProjectKindFacade extends AbstractFacade<ProjectKind> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectKindFacade() {
        super(ProjectKind.class);
    }

    public List<ProjectKind> findSameName(ProjectKind k) {
        Query q = em.createNamedQuery("kind.findByKindname");
        q.setParameter("kindname", k.getKindname());
        List<ProjectKind> l = q.getResultList();
        return l;
    }

    /*
     * If there are no objects, create them
     */
    public void Initialise() {
        List<ProjectKind> psl = this.findAll();
        if (psl.isEmpty()) {
            for (ProjectKind ps : ProjectKind.getAllProjectKinds()) {
                em.persist(ps);
            }
        }
    }
}
