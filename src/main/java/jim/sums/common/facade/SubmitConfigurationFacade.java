package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.SubmitConfiguration;

/**
 * @author Me
 */

@Stateless
public class SubmitConfigurationFacade extends AbstractFacade<SubmitConfiguration> {
    
    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubmitConfigurationFacade() {
        super(SubmitConfiguration.class);
    }
    
}
