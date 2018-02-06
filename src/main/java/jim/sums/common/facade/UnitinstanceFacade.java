/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jim.sums.common.facade;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jim.sums.common.db.Academicyear;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitInstance;
/**
 *
 * @author Sam
 */
@Stateless
public class UnitinstanceFacade extends AbstractFacade<UnitInstance>{

	@PersistenceContext(unitName="SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UnitinstanceFacade() {
		super(UnitInstance.class);
	}

   

	public Cohort getCohort(UnitInstance ui) {
		Date today = new Date();
		for (Cohort c : ui.getCohortList()) {
			if (today.after(c.getRegisterstart()) && today.before(c.getRegisterstop())) {
				return c;
			}
		}
		return null;
	}

        //TODO -> it seems useless... In most of the cases (all ?), we can use unit.getUnitinstanceList()
        public List<UnitInstance> findUnitInstancesByUnit(Unit u) {
            Query query = em.createNamedQuery("UnitInstance.findByUnit");
            query.setParameter("unit",u);
            return query.getResultList();	
	}
       
        /**
         * This is a method that gets
         * an instance based on the unitCode and academicYear
         * @param unit  This is the Unit 
         * @param ac This is the AcademicYear
         * @return It returns an instance of UnitInstance
         */
         public UnitInstance findByUnitCodeAndAcademicYear(Unit unit,Academicyear ac){
            TypedQuery<UnitInstance> query = em.createQuery("SELECT u FROM UnitInstance u WHERE u.unit = :unit AND u.academicyear = :academicYear",UnitInstance.class);
            query.setParameter("unit", unit);
            query.setParameter("academicYear", ac);
            return query.getSingleResult();
        }
}
