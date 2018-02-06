/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.Submission;

/**
 *
 * @author Me
 */
@Stateless
public class SubmissionFacade extends AbstractFacade<Submission> {
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SubmissionFacade() {
        super(Submission.class);
    }
    
}
