/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.examiner.ctrl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.facade.FeedbackFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.examiner.bus.ExternalExaminerService;
import jim.sums.feedback.bus.FeedbackService;

/**
 * Controller for the external examiner to overview all projects
 *
 * @author Mike Wareham
 */
@ViewScoped
@ManagedBean
public class ExternalExaminerController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    UserService us;
    @EJB
    FeedbackService fb;
    @EJB
    ExternalExaminerService ees;
    List<Finalproject> allProjects;

    /**
     * Returns the current allocation service
     *
     * @return as
     */
    public AllocationService getAs() {
        return as;
    }

    /**
     * Sets the current allocation service
     *
     * @param as
     */
    public void setAs(AllocationService as) {
        this.as = as;
    }

    /**
     * Returns the current external examiner service
     *
     * @return ees
     */
    public ExternalExaminerService getEes() {
        return ees;
    }

    /**
     * Sets the current external examiner service
     *
     * @param ees
     */
    public void setEes(ExternalExaminerService ees) {
        this.ees = ees;
    }

    public ExternalExaminerController() {
        super(Finalproject.class);
    }

    public FeedbackFacade getFb() {
        return fb.getFeedbackFacade();
    }

    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    /**
     * Returns the list of FinalProjects
     *
     * @return List
     */
    public List<Finalproject> getList() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        Person currUser = null;

        if (principal != null) {
            currUser = us.getPerson(principal.getName());
        }
        if (allProjects == null) {
            allProjects = getFacade().findAll();
        }

        //If the current user is Admin then return the whole allProjects
        if (currUser != null && currUser.isAdmin()) {
            return feedbackList(allProjects);
        } else {
            List<Cohort> cohortsManager = getListOfCohort();
            //If it is staff, then check whether the user is supervisor or moderator to each of the project
            if (currUser != null && currUser.isStaff()) {
                List<Finalproject> staffList = new ArrayList<Finalproject>();
                for (Finalproject fp : feedbackList(allProjects)) {
                    List<Staffprojectrelationship> staffProjectList = as.getProjectStaffList(fp);
                    for (Staffprojectrelationship sp : staffProjectList) {
                        if (sp.getPerson().getUsername().equals(currUser.getUsername())) {
                            staffList.add(fp);
                        }
                    }
                    //For each of the Cohort where the current user is Cohort coordinator s/he should can see all of the project in this cohort
                    for (Cohort c : cohortsManager) {
                        if (fp.getUnitinstance().getCohortList() != null && fp.getUnitinstance().getCohortList().contains(c)) {
                            if (!staffList.contains(fp)) {
                                staffList.add(fp);
                            }
                        }

                    }
                }

                return staffList;
            }
        }
        return null;
    }

    /**
     * Returns a list containing all feedback objects
     *
     * @param allProject
     * @return feedbackList
     */
    private List<Finalproject> feedbackList(List<Finalproject> allProject) {
        List<Finalproject> feedbackList = new ArrayList<Finalproject>();
        for (Finalproject fp : allProject) {
            if (fp.getFeedback() != null) {
                feedbackList.add(fp);
            }
        }
        return feedbackList;
    }

    /**
     * Returns List of all Cohorts
     *
     * @return List
     */
    public List<Cohort> getListOfCohort() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        Person currUser = null;
        if (principal != null) {
            currUser = us.getPerson(principal.getName());
        }
        List<Cohort> listCohort = as.getCohortFacade().findAll();
        List<Cohort> newListCohort = new ArrayList<Cohort>();
        for (Cohort c : listCohort) {
            if (c.getPersonList().contains(currUser)) {
                newListCohort.add(c);
            }
        }
        return newListCohort;
    }

    /**
     * Determines if project has been reviewed through {@link ExternalExaminerService}
     *
     * @param finProject
     * @return Boolean
     */
    public Boolean hasReviewed(Finalproject finProject) {
        return ees.hasBeenReviewed(finProject);
    }

    /**
     * Determines if external examiner is satisfied with project through {@link ExternalExaminerService}
     *
     * @param project
     * @return String
     */
    public String externalSatisfied(Finalproject project) {
        return ees.externalSatisfied(project);
    }
}
