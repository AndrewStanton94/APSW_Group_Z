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
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.MarkerMark;
import jim.sums.common.db.Person;

/**
 *
 * @author Dimitar
 */
@Stateless
public class MarkerMarkFacade extends AbstractFacade<MarkerMark> {
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MarkerMarkFacade() {
        super(MarkerMark.class);
    }
     public List<MarkerMark> findByFinalProject(Finalproject fp) {
        Query q = em.createNamedQuery("MarkerMark.findByProject");
        q.setParameter("fp", fp);
        return q.getResultList();
    }
         
    public void deleteForMarks() {
        em.createQuery("DELETE FROM MarkerMark").executeUpdate();
    }

    public String getMarkFindByProjectAndMarker(Finalproject fp, Person marker) {
        Query q = em.createNamedQuery("Marks.findIdByMarkerAndProject");
        q.setParameter("projectId", fp);
        q.setParameter("marker", marker.getUsername());
        String str;
        try {
            str = "" +(int) q.getSingleResult();
        }
        catch (javax.persistence.NoResultException e) {str = "Unmarked";}
        return str; 
    }
    
    public String getMarkDateFindByProjectAndMarker(Finalproject fp, Person marker) {
        Query q = em.createNamedQuery("Marks.findIdByMarkerAndProject");
        q.setParameter("projectId", fp);
        q.setParameter("marker", marker.getUsername());
        String str;
        try {
            str = "" +(int) q.getSingleResult();
        }
        catch (javax.persistence.NoResultException e) {str = "Unmarked";}
        return str; 
    }
    
    public String getStatusFindByProjectAndMarker(Finalproject fp, Person marker) {
        Query q = em.createNamedQuery("Marks.findStatusByMarkerAndProject");
        q.setParameter("projectId", fp);
        q.setParameter("marker", marker.getUsername());
        String str;
        try {
            str = ""+q.getSingleResult();
        }
        catch (javax.persistence.NoResultException e) {str = "No status";}
        return str; 
    }
    
}
