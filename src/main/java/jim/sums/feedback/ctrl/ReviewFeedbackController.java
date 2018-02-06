/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.feedback.ctrl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Feedback;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.TemplateCategory;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.feedback.bus.FeedbackService;

/**
 * Controller for the reviewing of feedback
 *
 * @author Mike Wareham
 */
@SessionScoped
@ManagedBean
public class ReviewFeedbackController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    FeedbackService fb;
    Finalproject currentProject;
    Feedback currentFeedback;

    @PostConstruct
    public void init() {
        FacesContext fc =
                FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null,
                "userBean");

        if (ub.getTempidea() == null) {
            ub.setTempProj(currentProject);
        }
        setCurrentProject(ub.getTempProj());
    }

    /**
     * Returns list of Mark categories
     *
     * @return List
     */
    public List<TemplateCategory> getList() {
        try {
            List<TemplateCategory> markingList = fb.getFormData(currentProject);
            return markingList;
        } catch (Exception e) {
            Logger.getLogger(ReviewFeedbackController.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * Returns the current Feedback
     *
     * @return Feedback
     */
    public Feedback getCurrentFeedback() {
        return currentFeedback;
    }

    /**
     * Sets the current Feedback
     *
     * @param currentFeedback
     */
    public void setCurrentFeedback(Feedback currentFeedback) {
        this.currentFeedback = currentFeedback;
    }

    /**
     * Returns the allocation service
     *
     * @return as
     */
    public AllocationService getAs() {
        return as;
    }

    /**
     * Sets the allocation service
     *
     * @param as
     */
    public void setAs(AllocationService as) {
        this.as = as;
    }

    /**
     * Returns the feedback service
     *
     * @return FeedbackService
     */
    public FeedbackService getFb() {
        return fb;
    }

    /**
     * Sets the feedback service
     *
     * @param fb
     */
    public void setFb(FeedbackService fb) {
        this.fb = fb;
    }

    /**
     * Returns the current final project object
     *
     * @return Finalproject
     */
    public Finalproject getCurrentProject() {
        return currentProject;
    }

    /**
     * Sets the current final project object
     *
     * @param currentProject
     */
    public void setCurrentProject(Finalproject currentProject) {
        this.currentProject = currentProject;
    }

    public ReviewFeedbackController() {
        super(Finalproject.class);
    }

    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    /**
     * Calls the Service update Feedback method for feedback
     *
     * @return String
     * @throws MessagingException
     * @throws BusinessException
     */
    public String updateFeedback() throws MessagingException, BusinessException {
        fb.updateFeedback(currentProject.getFeedback());
        return "/view/feedback/feedbackedit.xhtml";
    }

    /**
     * Call the service confirm Feedback method for feedback
     *
     * @return String
     * @throws MessagingException
     * @throws BusinessException
     */
    public String confirmFeedback() throws MessagingException, BusinessException {
        fb.confirmFeedback(currentProject.getFeedback());
        return "/view/feedback/allfeedback.xhtml";
    }

    /**
     * Call the service update general comment method for feedback
     *
     * @return String
     * @throws MessagingException
     * @throws BusinessException
     */
    public String updateGeneralComment() throws MessagingException, BusinessException {
        fb.updateFeedback(currentProject.getFeedback());
        return "/view/feedback/closeComments.xhtml";
    }
}
