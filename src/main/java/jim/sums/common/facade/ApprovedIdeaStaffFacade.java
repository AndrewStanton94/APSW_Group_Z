/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.db.ApprovedIdeaStaff;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import java.util.List;
import javax.persistence.Query;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author Pasu
 */
@Stateless
public class ApprovedIdeaStaffFacade extends AbstractFacade<ApprovedIdeaStaff>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ApprovedIdeaStaffFacade() {
		super(ApprovedIdeaStaff.class);
	}
        
        @SuppressWarnings(value = "unchecked")
        public List<ApprovedIdeaStaff> findByStaff(Person staff) {
            Query q = em.createQuery("SELECT p FROM ApprovedIdeaStaff p "
				+ "WHERE p.approvedStaffList = :staff");
            q.setParameter("staff" , staff);            
            return (List<ApprovedIdeaStaff>)q.getResultList();
        }
        
        @SuppressWarnings(value = "unchecked")
        public List<ApprovedIdeaStaff> findBySearchCriteria(Person staff, int month, String idea_status, String title) {
            Calendar now = Calendar.getInstance();
            Date dateNow = now.getTime();
            now.add(Calendar.MONTH, -month);
            Query q = em.createQuery("SELECT p FROM ApprovedIdeaStaff p "
				+ "WHERE (p.approvedStaffList = :staff)"
                                + " AND (p.approvedStatus = :status OR :status = 'All')"
                                + " AND (UPPER(p.approvedIdeaList.ideatitle) LIKE :title)"
                                + " AND (p.approvedIdeaList.submissiondate between :start_date and :end_date or :month_count = 9999)");
            q.setParameter("staff"       , staff);
            q.setParameter("start_date"  , now.getTime());
            q.setParameter("end_date"    , dateNow);
            q.setParameter("month_count" , month);
            q.setParameter("status"      , idea_status);
            q.setParameter("title"       , "%" + title.toUpperCase() + "%");
            return (List<ApprovedIdeaStaff>)q.getResultList();
        }
        
        @SuppressWarnings(value = "unchecked")
        public List<ApprovedIdeaStaff> findByIdea(Projectidea idea) {
            Query q = em.createQuery("SELECT p FROM ApprovedIdeaStaff p "
				+ "WHERE p.approvedIdeaList = :idea");
            q.setParameter("idea" , idea);          
            return (List<ApprovedIdeaStaff>)q.getResultList();
        }
    
}
