
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Finalproject;

/**
 *
 * @author Sam
 */
@Stateless
public class FinalprojectFacade extends AbstractFacade<Finalproject>{
    
	@PersistenceContext(unitName = "SUMS2011PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public FinalprojectFacade() {
		super(Finalproject.class);
	}
	
	public List<Object[]> getPreProjectAssociations(int idProjectIdea){
		Query q = em.createQuery("SELECT icbstu, pcbstu, icbsup, pcbsup "
					+ "FROM Personchosenby AS pcbstu, Ideachosenby AS icbstu, "
						+ "Personchosenby AS pcbsup, Ideachosenby AS icbsup "
					+ "WHERE pcbstu.personchosenbyPK.usernamechooser = icbstu.ideachosenbyPK.username "
					+ "AND icbstu.ideachosenbyPK.idprojectidea = :idPi "
					+ "AND icbstu.projectidea.ideastatus.ideastatusname = \"Approved\" " // Idea available (approved status)
					+ "AND pcbsup.personchosenbyPK.usernamechooser = icbsup.ideachosenbyPK.username "
					+ "AND icbsup.ideachosenbyPK.idprojectidea = icbstu.ideachosenbyPK.idprojectidea "
					+ "AND icbsup.idunitinstance IS NULL "
					+ "AND pcbsup.personchosenbyPK.usernamechooser = pcbstu.personchosenbyPK.usernamechosen "
					+ "AND pcbsup.personchosenbyPK.usernamechosen = pcbstu.personchosenbyPK.usernamechooser "
					+ "");
		q.setParameter("idPi",idProjectIdea);
		return q.getResultList();
	}
	
	public List<Finalproject> findByTitle(String title){
		Query q = em.createNamedQuery("Finalproject.findByProjecttitle");
		q.setParameter("projecttitle", title);
		return q.getResultList();
	}
       

    public List<Finalproject> findAllUnmarkedProjects() {
        Query q = em.createNamedQuery("Finalproject.findUnmarkedProjects");
        return q.getResultList();
    }
    
    public List<Finalproject> feedbackhasBeenViewed() {
        Query q = em.createNamedQuery("Finalproject.feedbackViewed");
        return q.getResultList();
    }

    public List<Finalproject> findAllWithFeedback() {
        Query q = em.createQuery("SELECT p FROM Finalproject WHERE p.feedback != null", Finalproject.class);
        return q.getResultList();
    }
}
