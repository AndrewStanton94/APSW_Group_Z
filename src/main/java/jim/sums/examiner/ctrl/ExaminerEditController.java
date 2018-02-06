/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.examiner.ctrl;

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
import jim.sums.common.db.Finalproject;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.examiner.bus.ExternalExaminerService;
import jim.sums.feedback.bus.FeedbackService;

/**
 * Controller class actions when an external examiner is examining project
 *
 * @author Mike Wareham
 */
@SessionScoped
@ManagedBean
public class ExaminerEditController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    FeedbackService fb;
    @EJB
    ExternalExaminerService ees;
    Finalproject currentProject;
    String satisfiedFields;
    String satisfiedComments;

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
     * Returns the allocationService
     *
     * @return AllocationService
     */
    public AllocationService getAs() {
        return as;
    }

    /**
     * Sets the allocationService
     *
     * @param as
     */
    public void setAs(AllocationService as) {
        this.as = as;
    }

    /**
     * Returns feedbackService object
     *
     * @return FeedbackService
     */
    public FeedbackService getFb() {
        return fb;
    }

    /**
     * Sets the current feedbackService
     *
     * @param fb
     */
    public void setFb(FeedbackService fb) {
        this.fb = fb;
    }

    /**
     * Returns the ExternalExaminerService
     *
     * @return ees
     */
    public ExternalExaminerService getEes() {
        return ees;
    }

    /**
     * Sets the current ExternalExaminerService
     *
     * @param ees
     */
    public void setEes(ExternalExaminerService ees) {
        this.ees = ees;
    }

    /**
     * Returns the current FinalProject
     *
     * @return Finalproject
     */
    public Finalproject getCurrentProject() {
        return currentProject;
    }

    /**
     * Sets the current FinalProject
     *
     * @param currentProject
     */
    public void setCurrentProject(Finalproject currentProject) {
        this.currentProject = currentProject;
    }

    public ExaminerEditController() {
        super(Finalproject.class);
    }

    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    /**
     * Returns the projects external examiners comments
     *
     * @return String
     */
    public String getSatisfiedComments() {
        return currentProject.getExternalComments();
    }

    /**
     * Sets the projects external examiners comments
     *
     * @param satisfiedComments
     */
    public void setSatisfiedComments(String satisfiedComments) {
        this.satisfiedComments = satisfiedComments;
    }

    /**
     * Saves the external comment fields {@link ExternalExaminerService}
     *
     * @return String
     * @throws MessagingException
     * @throws BusinessException
     */
    public String doSave() throws MessagingException, BusinessException {
        ees.saveReview(currentProject, satisfiedFields, satisfiedComments);
        return "/view/externalexaminer/examinerview.xhtml";
    }

    /**
     * Returns the satisfied fields and converts to String dependant on db
     * value.
     *
     * @return String
     */
    public String getSatisfiedFields() {
        if (currentProject.getExternalSatisfied() == true) {
            return "Satisfied";
        }
        return "Dissatisfied";
    }

    /**
     * Sets whether the external examiner was satisfied with the marking
     *
     * @param satisfiedFields
     */
    public void setSatisfiedFields(String satisfiedFields) {
        this.satisfiedFields = satisfiedFields;
    }
}
