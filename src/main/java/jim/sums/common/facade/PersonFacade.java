/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jim.sums.common.db.Ideachosenby;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.RoleName;

/**
 *
 * @author KardousS
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }

    public List<Person> findSameEmail(String email) {
        Query q = em.createNamedQuery("Person.findByEmail");
        q.setParameter("email", email);
        return q.getResultList();
    }

    public List<Person> findSameUserName(String username) {
        Query q = em.createNamedQuery("Person.findByUsername");
        q.setParameter("username", username);
        return q.getResultList();
    }

    public void deleteForPerson() {
        em.createQuery("DELETE FROM ApprovedIdeaStaff").executeUpdate();
        em.createQuery("DELETE FROM Projectideahistory").executeUpdate();
        em.createQuery("DELETE FROM Ideachosenby").executeUpdate();
        em.createQuery("DELETE FROM Submission").executeUpdate();
        em.createQuery("DELETE FROM MarkerMark").executeUpdate();
//        em.createQuery("DELETE FROM Marks").executeUpdate();
        em.createQuery("DELETE FROM Staffprojectrelationship").executeUpdate();
        em.createQuery("DELETE FROM Finalproject").executeUpdate();
        em.createQuery("DELETE FROM Projectidea").executeUpdate();
//        em.createQuery("DELETE FROM Grouptable").executeUpdate();
        em.createQuery("DELETE FROM Unitpersonin").executeUpdate();
        em.createQuery("DELETE FROM Personchosenby").executeUpdate();
        em.createQuery("DELETE FROM Personstaffstatus").executeUpdate();
        em.createQuery("DELETE FROM Suspension").executeUpdate();
        em.createQuery("DELETE FROM Person").executeUpdate();
        em.createQuery("DELETE FROM Personstaffstatus").executeUpdate();
        em.createQuery("DELETE FROM Staffstatus").executeUpdate();
    }

    public List<Person> findByConfirmKey(String s) {
        Query q = em.createNamedQuery("Person.findByConfirmkey");
        q.setParameter("confirmkey", s);
        List<Person> l = q.getResultList();
        return l;
    }

    public boolean isUsedEmail(String s) {
        Query q = em.createNamedQuery("Person.findByEmail");
        q.setParameter("email", s);
        return !q.getResultList().isEmpty();
    }

    public List<Projectidea> getProjectIdeas(Person p) {
        p.getProjectideaList().size();
        return p.getProjectideaList();
    }

    public List<Ideachosenby> getChosenIdeas(Person p) {
        List<Ideachosenby> l = p.getIdeachosenbyList();
        l.size();
        return l;
    }

    public List<Person> findByRole(RoleName role) {
        Query q = em.createNamedQuery("Person.findByRole");
        q.setParameter("role", role);
        return q.getResultList();
    }
}
