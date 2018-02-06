package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Unit;

/**
 * @author Sam
 */

@Stateless
public class UnitFacade extends AbstractFacade<Unit>{

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
            return em;
    }

    public UnitFacade() {
            super(Unit.class);
    }

    public void deleteForUnit() {
        em.createQuery("DELETE FROM SubmitConfiguration").executeUpdate();
        em.createQuery("DELETE FROM UnitInstance").executeUpdate();
        em.createQuery("DELETE FROM Unit").executeUpdate();
    }
    
    public List<Unit> findSameTitle(Unit u) {
        Query q = em.createNamedQuery("Unit.findByUnittitle");
        q.setParameter("unittitle", u.getUnittitle());
        return q.getResultList();
    }
    
    public List<Unit> findSameCourseLevel(Long clid) {
        Query q = em.createNamedQuery("Unit.findByUnitcoursel") ;
        q.setParameter("unitcl", clid) ;
        return q.getResultList();
    }
    
    public List<Unit> findSameCode(Unit u) {
        Query q = em.createNamedQuery("Unit.findByUnitcode");
        q.setParameter("unitcode", u.getUnitcode());
        return q.getResultList();
    }
    
    public Unit findByUnitCode(String unitCode) {
        Query q = em.createNamedQuery("Unit.findByUnitcode");
        q.setParameter("unitcode" , unitCode);
        return (Unit)q.getSingleResult();
    }
}
