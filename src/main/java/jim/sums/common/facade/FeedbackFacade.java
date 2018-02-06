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
import jim.sums.common.db.Feedback;
import jim.sums.common.db.MarkerMark;

/**
 *
 * @author Mike
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<Feedback> {
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackFacade() {
        super(Feedback.class);
    }
    
    public List<Feedback> findByConfirmKey(String s) {
        Query q = em.createNamedQuery("Feedback.findByConfirmkey");
        q.setParameter("confirmkey", s);
        List<Feedback> l = q.getResultList();
        return l;
    }
}
