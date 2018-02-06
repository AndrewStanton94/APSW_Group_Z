/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.UserService;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Ideachosenby;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.Personchosenby;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.db.Staffstatus;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.CohortFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.common.facade.IdeachosenbyFacade;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.PersonstaffstatusFacade;
import jim.sums.common.facade.ProjectideaFacade;
import jim.sums.common.facade.PersonchosenbyFacade;
import jim.sums.common.facade.StaffprojectrelationshipFacade;
import jim.sums.common.facade.StaffstatusFacade;
import jim.sums.common.facade.UnitinstanceFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class AllocationService {

    @EJB
    private CohortFacade cohortFacade;
    // <- TODO:Super dirty inner class
    @EJB
    private ProjectideaFacade pif;
    @EJB
    private PersonFacade pf;
    @EJB
    private IdeachosenbyFacade icf;
    @EJB
    private PersonchosenbyFacade pcf;
    @EJB
    private UnitinstanceFacade uif;
    @EJB
    private UserService us;
    @EJB
    private PersonstaffstatusFacade pssf;
    @EJB
    private FinalprojectFacade fpf;
    @EJB
    private IdeastatusFacade isf;
    @EJB
    private StaffprojectrelationshipFacade sprf;
    @EJB
    private StaffstatusFacade ssf;

    public FinalprojectFacade getFpf() {
        return fpf;
    }

    public void setFpf(FinalprojectFacade fpf) {
        this.fpf = fpf;
    }

    public IdeachosenbyFacade getIcf() {
        return icf;
    }

    public void setIcf(IdeachosenbyFacade icf) {
        this.icf = icf;
    }

    public CohortFacade getCohortFacade() {
        return cohortFacade;
    }

    public void setCohortFacade(CohortFacade cohortFacade) {
        this.cohortFacade = cohortFacade;
    }

    public IdeastatusFacade getIsf() {
        return isf;
    }

    public void setIsf(IdeastatusFacade isf) {
        this.isf = isf;
    }

    public PersonchosenbyFacade getPcf() {
        return pcf;
    }

    public void setPcf(PersonchosenbyFacade pcf) {
        this.pcf = pcf;
    }

    public PersonFacade getPf() {
        return pf;
    }

    public void setPf(PersonFacade pf) {
        this.pf = pf;
    }

    public ProjectideaFacade getPif() {
        return pif;
    }

    public void setPif(ProjectideaFacade pif) {
        this.pif = pif;
    }

    public PersonstaffstatusFacade getPssf() {
        return pssf;
    }

    public void setPssf(PersonstaffstatusFacade pssf) {
        this.pssf = pssf;
    }

    public StaffprojectrelationshipFacade getSprf() {
        return sprf;
    }

    public void setSprf(StaffprojectrelationshipFacade sprf) {
        this.sprf = sprf;
    }

    public StaffstatusFacade getSsf() {
        return ssf;
    }

    public void setSsf(StaffstatusFacade ssf) {
        this.ssf = ssf;
    }

    public UnitinstanceFacade getUif() {
        return uif;
    }

    public void setUif(UnitinstanceFacade uif) {
        this.uif = uif;
    }

    public UserService getUs() {
        return us;
    }

    public void setUs(UserService us) {
        this.us = us;
    }

    public AllocationService() {
    }

    public PersonFacade getPersonFacade() {
        return pf;
    }

    public ProjectideaFacade getProjectideaFacade() {
        return pif;
    }

    public List<Ideachosenby> getChosenIdeas(Person p) {
        return icf.findByUsername(p);
    }

    public List<Personchosenby> getChosenPersons(Person chooser) {
        return pcf.findByChooserUsername(chooser);
    }

    public void removeIdea(Ideachosenby idea) {
        Person p = idea.getPerson();
        icf.remove(idea);
        List<Ideachosenby> l = getChosenIdeas(p);
        Collections.sort(l, new IdeaRankComparator());
        for (Ideachosenby i : l) {
            i.setIdearank(10 - l.lastIndexOf(i));
        }
    }

    public void rankIdea(Ideachosenby idea, int rankDiff) {
        int rank = idea.getIdearank();
        List<Ideachosenby> l = getChosenIdeas(idea.getPerson());
        for (Ideachosenby i : l) {
            if (i.getIdearank() == rank + rankDiff) {
                i.setIdearank(rank);
                icf.edit(i);
                idea.setIdearank(rank + rankDiff);
                icf.edit(idea);
                break;
            }
        }
    }

    public void removeSupervisor(Personchosenby sup) {
        Person p = sup.getPerson();
        pcf.remove(sup);
        List<Personchosenby> l = getChosenPersons(p);
        Collections.sort(l, new SupervisorRankComparator());
        for (Personchosenby i : l) {
            i.setPersonrank(10 - l.lastIndexOf(i));
        }
    }

    public void rankSupervisor(Personchosenby pcb, int rankDiff) {
        int rank = pcb.getPersonrank();
        List<Personchosenby> l = getChosenPersons(pcb.getPerson());
        for (Personchosenby p : l) {
            if (p.getPersonrank() == rank + rankDiff) {
                p.setPersonrank(rank);
                pcf.edit(p);
                pcb.setPersonrank(rank + rankDiff);
                pcf.edit(pcb);
                break;
            }
        }
    }

    public Cohort getCohort(UnitInstance ui) {
        return uif.getCohort(ui);
    }

    public void selectIdea(Projectidea i, Person p, UnitInstance ui) throws BusinessException {
        //if(uif.getCohort(ui).getIdeashortlistmaxsize() >= )
        if (!icf.findSame(i, p).isEmpty()) {
            throw new BusinessException("You have already selected this idea.");
        }
        if (ui != null) {
            if (!kindCompatible(i, p)) {
                throw new BusinessException("Incompatible kind.");
            }
            if (!gradeCompatible(i, p)) {
                throw new BusinessException("Incompatible grade.");
            }
        }
        Ideachosenby icb = new Ideachosenby(p.getUsername(), i.getIdprojectidea());
        icb.setProjectidea(i);
        icb.setPerson(p);
        icb.setIdunitinstance(ui);

        int foo = icf.findByUsername(p).size();
        if (foo >= 10) {
            icb.setIdearank(1);
        } else {
            icb.setIdearank(10 - foo);
        }

        icf.create(icb);
    }

    private boolean kindCompatible(Projectidea i, Person p) {
        for (UnitInstance ui : us.unitInstancesSelectionOpen(p.getUsername())) {
            if (i.getKindList().contains(ui.getUnit().getUnitkind())) {
                return true;
            }
        }
        return false;
    }

    private boolean gradeCompatible(Projectidea i, Person p) {
        for (UnitInstance ui : us.unitInstancesSelectionOpen(p.getUsername())) {
            if (i.getGradeList().contains(ui.getUnit().getCourseLevel())) {
                return true;
            }
        }
        return false;
    }

    public List<Person> getSupervisorList() {
        Staffstatus sts = new Staffstatus();
        for (Staffstatus ss : ssf.findAll()) {
            if (ss.getStatusname().equalsIgnoreCase("supervisor")) {
                sts = ss;
            }
        }
        return pssf.findActiveSupervisor(sts);
    }
   
    public void selectPerson(Person chooser, Person chosen, UnitInstance ui) throws BusinessException {
        if (!pcf.findSame(chooser, chosen).isEmpty()) {
            throw new BusinessException("You have already selected this person.");
        }
        Personchosenby pcb = new Personchosenby(chooser.getUsername(), chosen.getUsername());
        pcb.setPerson(chooser);
        pcb.setPerson1(chosen);
        pcb.setIdunitinstance(ui);

        int foo = pcf.findByChooserUsername(chooser).size();
        if (foo >= 10) {
            pcb.setPersonrank(1);
        } else {
            pcb.setPersonrank(10 - foo);
        }

        pcf.create(pcb);
    }

    public List<RankSum> getAllocationList() throws BusinessException {
        //Is it better to do it w/o queries, but only with list processing?
        List<RankSum> al = new ArrayList<RankSum>();
        List<RankSum> tmp = new ArrayList<RankSum>();

        for (Projectidea pi : pif.findAllOrderedByPopularity()) {
            tmp = getAllocationsByProject(pi.getIdprojectidea());
            if (!tmp.isEmpty()) {
                al.add(tmp.get(0));
            }
        }
        return al;
    }

        //*** Edit by Pasu Poonpakdee Issue: JIMUOPDEV-35
        public List<RankSum> getAllocationListByStudent(String user_name) throws BusinessException {
		//Is it better to do it w/o queries, but only with list processing?
		List<RankSum> al = new ArrayList<RankSum>();
		List<RankSum> tmp = new ArrayList<RankSum>();

		for (Projectidea pi : pif.findAllOrderedByPopularity()) {
                    if(pi.getOwneridea().getUsername().equals(user_name)) {
			tmp = getAllocationsByProject(pi.getIdprojectidea());
			if (!tmp.isEmpty()) {
                            al.add(tmp.get(0));
			}
                    }
		}
		return al;
	}

    public List<RankSum> getAllocationsByProject(int idProjectIdea) throws BusinessException {

        List<RankSum> lrs = new ArrayList<RankSum>();

        for (Object[] tab : fpf.getPreProjectAssociations(idProjectIdea)) {
            lrs.add(new RankSum((Ideachosenby) tab[0], (Personchosenby) tab[1],
                    (Ideachosenby) tab[2], (Personchosenby) tab[3]));
        }
        Collections.sort(lrs, new Comparator<RankSum>() {

            @Override
            public int compare(RankSum t, RankSum t1) {
                return new Integer(t1.getRanksum()).compareTo(t.getRanksum());
            }
        });
        return lrs;
    }

    public void createFinalProject(RankSum rs) {
        Finalproject project = new Finalproject();
        project.setProjecttitle(rs.getProjectidea().getIdeatitle());
        
        // Retrieve the cohort of the student and after that we can put the first choice
        // staff by this student as cohort co-ordinator and add to the project the good submission date
        UnitInstance retrieveUnitIntsance = pcf.findSame(rs.getStudent(), rs.getSupervisor()).get(0).getIdunitinstance();
//        Person -> (personchosenby) -> UnitInstancemust be equals with :
//        Person -> (managedby) -> Cohort -> (cohortunitin) -> UnitInstance 
        List<Cohort> cohortList = cohortFacade.findAll();
        for(Cohort cohort: cohortList) {
            if (cohort.getPersonList().contains(rs.getStudent())) {
                if (cohort.getUnits().contains(retrieveUnitIntsance)) {
                    project.setPlannedsubmissiondate(cohort.getProjectstop());
                    cohort.getPersonList1().add(rs.getSupervisor());
                    break;
                }
            }
        }
        
//         <= set student?
        List<Person> firstStudentInTheFinalProject = new ArrayList<Person>();
        firstStudentInTheFinalProject.add(rs.getStudent());
        project.setProjectidea(rs.getProjectidea());
        project.setUnitinstance(retrieveUnitIntsance);
        project.setStudent(firstStudentInTheFinalProject);
        project.setFinalMarkStatus("Not Submitted");
        fpf.create(project);
        
        Staffprojectrelationship spr = new Staffprojectrelationship();
        spr.setPerson(rs.getSupervisor());
        spr.setFinalproject(project);
        spr.setStaffstatus(ssf.findByName("coordinator"));
        sprf.create(spr);

        // What else should be disabled?
        rs.getProjectidea().setIdeastatus(isf.findSameName("Allocated"));
        pif.edit(rs.getProjectidea());
    }
    
//    Add new student to a final project
    public void addStudentToFinalProject(RankSum rs) {
        Finalproject project = fpf.findByTitle(rs.getProjectidea().getIdeatitle()).get(0);
        List<Person> studentToAdd = new ArrayList<Person>(project.getStudent());
        studentToAdd.add(rs.getStudent());
        project.setStudent(studentToAdd);
        fpf.edit(project);
    }
    
    public boolean isAllocated(RankSum rs) {
        Finalproject project = fpf.findByTitle(rs.getProjectidea().getIdeatitle()).get(0);
        if (project.getStudent().contains(rs.getStudent())) {
            return true;
        }
        return false;
    }


    public FinalprojectFacade getFinalprojectFacade() {
        return fpf;
    }

    @Deprecated
    public List<Staffprojectrelationship> getProjectStaffList(Finalproject project) {
        return project.getStaffprojectrelationshipList();
    }

    public Person getProjectSupervisor(Finalproject project) {
        for (Staffprojectrelationship spr : getProjectStaffList(project)) {
            if (spr.getStaffstatus().getId() == 3) {
                return spr.getPerson();
            }
        }
        return null;
    }

    public static class SupervisorRankComparator implements Comparator<Personchosenby> {

        @Override
        public int compare(Personchosenby o1, Personchosenby o2) {
            return new Integer(o2.getPersonrank()).compareTo(o1.getPersonrank());
        }
    }

    public static class IdeaRankComparator implements Comparator<Ideachosenby> {

        @Override
        public int compare(Ideachosenby o1, Ideachosenby o2) {
            return new Integer(o2.getIdearank()).compareTo(o1.getIdearank());
        }
    }

    /* Inner class because only used in allocation service. If it is needed 
     * elsewhere, put it in jim.sums.ideas.other
     * 
     */
    public class RankSum {

        private Projectidea projectidea;
        private Person student;
        private Person supervisor;
        private int ranksum;

        public RankSum() {
        }

        public RankSum(Projectidea projectidea, Person student, Person supervisor, int ranksum) {
            this.projectidea = projectidea;
            this.student = student;
            this.supervisor = supervisor;
            this.ranksum = ranksum;
        }

        public RankSum(Ideachosenby icbstu, Personchosenby pcbstu, Ideachosenby icbsup, Personchosenby pcbsup) throws BusinessException {
            if (!icbstu.getPerson().equals(pcbstu.getPerson()) || //student actually chose the idea 
                    !icbsup.getPerson().equals(pcbsup.getPerson()) || //supervisor actually chose the idea
                    !pcbstu.getPerson().equals(pcbsup.getPerson1()) || //student actually chose the supervisor
                    !pcbstu.getPerson1().equals(pcbsup.getPerson())) { //supervisor actually chose the student
                throw new BusinessException("The choices can't be associated");
            }
            this.projectidea = icbstu.getProjectidea();
            this.student = icbstu.getPerson();
            this.supervisor = pcbstu.getPerson1();
            this.ranksum = icbstu.getIdearank() + pcbstu.getPersonrank() + icbsup.getIdearank() + pcbsup.getPersonrank();
        }

        @Override
        public String toString() {
            return "Rank " + ranksum + ": " + student.getUsername() + " - "
                    + supervisor.getUsername() + " - " + projectidea.getIdeatitle();
        }

        public Projectidea getProjectidea() {
            return projectidea;
        }

        public void setProjectidea(Projectidea projectidea) {
            this.projectidea = projectidea;
        }

        public int getRanksum() {
            return ranksum;
        }

        public void setRanksum(int ranksum) {
            this.ranksum = ranksum;
        }

        public Person getStudent() {
            return student;
        }

        public void setStudent(Person student) {
            this.student = student;
        }

        public Person getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(Person supervisor) {
            this.supervisor = supervisor;
        }
    }
}
