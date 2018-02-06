/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.CategoryMark;

/**
 *
 * @author Dimitar
 */
@Stateless
public class CategoryMarkFacade extends AbstractFacade<CategoryMark> {
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryMarkFacade() {
        super(CategoryMark.class);
    }
    
}
