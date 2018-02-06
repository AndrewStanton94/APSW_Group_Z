/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.db.AppProperty;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class AppPropertyFacade extends AbstractFacade<AppProperty> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    public AppPropertyFacade() {
        super(AppProperty.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public String get(String prop) throws BusinessException {
        AppProperty a = em.find(AppProperty.class, prop);
        if (a != null) {
            return a.getPropValue();
        } else {
            throw new BusinessException("Application property " + prop + " not found");
        }
    }

    public void Initialise() {
        if (this.count() == 0) {
            AppProperty address = new AppProperty("smtp_email_address", "Jim.Briggs@port.ac.uk");
            em.persist(address);
            AppProperty user = new AppProperty("smtp_auth_user", "");
            em.persist(user);
            AppProperty pwd = new AppProperty("smtp_auth_pwd", "");
            em.persist(pwd);
            AppProperty port = new AppProperty("smtp_host_port", "25");
            em.persist(port);
            AppProperty host = new AppProperty("smtp_host_name", "smtpserver.port.ac.uk");
            em.persist(host);
        }
    }
}
