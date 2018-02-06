/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.RoleName;

/**
 *
 * @author BriggsJ
 */
@Stateless
public class RoleNameFacade extends AbstractFacade<RoleName> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleNameFacade() {
        super(RoleName.class);
    }

    public void Initialise() {
        if (this.count() == 0) {
            for (RoleName rn : RoleName.getAllRoleNames()) {
                em.persist(rn);
            }
        }
    }

    public RoleName findByName(String name) {
        Query q = em.createNamedQuery("RoleName.findByName");
        q.setParameter("name", name);
        return (RoleName) q.getSingleResult();
    }

    public List<RoleName> findUserRoles() {
        List<RoleName> list = new ArrayList<RoleName>();
        list.add(staff());
        list.add(student());
        list.add(external());
        return list;
    }

    public RoleName admin() {
        return findByName("Administrator");
    }

    public RoleName staff() {
        return findByName("Staff");
    }

    public RoleName student() {
        return findByName("Student");
    }

    public RoleName external() {
        return findByName("External");
    }

    public RoleName user() {
        return findByName("User");
    }
}
