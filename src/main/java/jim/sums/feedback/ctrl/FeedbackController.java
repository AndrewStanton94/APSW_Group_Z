/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.feedback.ctrl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.facade.FeedbackFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.common.util.FinalProjectUtil;
import jim.sums.feedback.bus.FeedbackService;

/**
 * Controller to show all feedback linked to all feedback
 *
 * @author Mike Wareham
 */
@ViewScoped
@ManagedBean
//TODO: this is the staff view
public class FeedbackController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    UserService us;
    @EJB
    FeedbackService fb;
    List<Finalproject> allProjects;

    public FeedbackController() {
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
     * Returns list of all FinalProject and filters result dependant on user
     * status
     *
     * @return List
     */
    public List<Finalproject> getList() {
//        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        UserBean ub = (UserBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "ub");
        Person currUser = ub.getLoggedInUser();

//        if (principal != null) {
//            currUser = us.getPerson(principal.getName());
//        }
        if (allProjects == null) {
            allProjects = getFacade().findAllWithFeedback();
        }

        //If the current user is Admin then return the whole allProjects
        if (currUser != null && currUser.isAdmin()) {
            return allProjects;
        } else {
            List<Cohort> cohortsManager = getListOfCohort();
            //If it is staff, then check whether the user is supervisor or moderator to each of the project
            if (currUser != null && currUser.isStaff()) {
                return FinalProjectUtil.getProjectsToFeedback(currUser, cohortsManager, allProjects);
            }
        }
        return null;
    }

    /**
     * Returns a list of all confirmed Feedback
     *
     * @param allProjects
     * @return List
     */
    private List<Finalproject> confirmedFeedbackList(List<Finalproject> allProjects) {
        List<Finalproject> confirmedList = new ArrayList<Finalproject>();
        for (Finalproject fp : allProjects) {
            if (fp.getFeedback().getFeedbackConfirmed() == true && fp.getFeedback().getViewedDate().equalsIgnoreCase("not read")) {
                confirmedList.add(fp);
            }
        }
        return confirmedList;
    }

    /**
     * Call to feedback for all projects with confirmed feedback
     *
     * @throws BusinessException
     */
    public void sendConfirmedFeedback() throws BusinessException {
        fb.sendConfirmedFeedback(confirmedFeedbackList(allProjects));
    }

    /**
     * Returns list of cohorts and filters by user status
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
}
