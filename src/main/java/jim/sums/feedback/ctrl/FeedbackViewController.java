/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.feedback.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Feedback;
import jim.sums.common.db.Finalproject;
import jim.sums.common.facade.FeedbackFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.feedback.bus.FeedbackService;

/**
 * Controller for the final viewing of feedback objects
 *
 * @author Mike Wareham
 */
@ViewScoped
@ManagedBean
//this is the student view
public class FeedbackViewController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    UserService us;
    @EJB
    FeedbackService fb;
    @EJB
    private FeedbackFacade feedFacade;
    Feedback currentFeedback;
    Finalproject currentProject;
    Boolean validKey = null;
    String confirmKey;

    @PostConstruct
    public void feedbackViewed() {
        if (validKey == null) {
            isValidKey();
        }
        if (currentFeedback == null) {
            getFeedbackbyKey(confirmKey);
        }
        fb.feedbackViewed(currentFeedback);
    }

    public FeedbackFacade getFeedFacade() {
        return feedFacade;
    }

    public void setFeedFacade(FeedbackFacade feedFacade) {
        this.feedFacade = feedFacade;
    }

    public FeedbackViewController() {
        super(Finalproject.class);
    }

    /**
     * Returns the feedbackFacade
     *
     * @return FeedbackFacade
     */
    public FeedbackFacade getFb() {
        return fb.getFeedbackFacade();
    }

    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    /**
     * Determines whether given key through end parameter "key" is valid through
     * the confirmkey feedback property
     *
     * @return boolean
     */
    public boolean isValidKey() {
        if (validKey == null) {
            confirmKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
            getFeedbackbyKey(confirmKey);
            validKey = true;
        }
        return validKey;
    }

    /**
     * Returns a feedback object using the confirmkey value
     *
     * @param confirmKey
     * @return Feedback
     */
    private Feedback getFeedbackbyKey(String confirmKey) {
        List<Feedback> sameKey = feedFacade.findByConfirmKey(confirmKey);
        if (sameKey.size() == 1) {
            setCurrentFeedback(sameKey.get(0));
        }
        return currentFeedback;
    }

    /**
     * returns the current feedback
     *
     * @return Feedback
     */
    public Feedback getCurrentFeedback() {
        return currentFeedback;
    }

    /**
     * Sets the current feedback
     *
     * @param currentFeedback
     */
    public void setCurrentFeedback(Feedback currentFeedback) {
        this.currentFeedback = currentFeedback;
    }

    /**
     * Returns the current project
     *
     * @return Finalproject
     */
    public Finalproject getCurrentProject() {
        if (validKey != null) {
            setCurrentProject(currentFeedback.getProject());
        } else {
            isValidKey();
            setCurrentProject(currentFeedback.getProject());
        }
        return currentProject;
    }

    /**
     * Sets the current project
     *
     * @param currentProject
     */
    public void setCurrentProject(Finalproject currentProject) {
        this.currentProject = currentProject;
    }
}
