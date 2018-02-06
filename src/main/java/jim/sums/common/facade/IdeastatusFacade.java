/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.Ideastatus;

/**
 *
 * @author KardousS
 */
@Stateless
public class IdeastatusFacade extends AbstractFacade<Ideastatus> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdeastatusFacade() {
        super(Ideastatus.class);
    }

    public Ideastatus findSameName(String statusname) {
        /*
        TODO: it should be possible to do this in JPQL*/
        Ideastatus test = (Ideastatus) em.createNamedQuery("Ideastatus.findByIdeastatusname")
                                         .setParameter("ideastatusname", statusname)
                                         .getSingleResult();
        
        return test;
    }

    public void Initialise() {
        List<Ideastatus> isl = this.findAll();
        if (isl.isEmpty()) {
            for (Ideastatus is : Ideastatus.getAllIdeastatuses()) {               
                em.persist(is);
            }
        }
    }
}
