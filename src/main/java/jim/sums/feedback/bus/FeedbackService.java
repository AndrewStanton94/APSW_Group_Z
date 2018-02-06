/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.feedback.bus;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.common.EmailMessage;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;
import jim.sums.common.bus.SystemInfoService;
import jim.sums.common.db.*;
import jim.sums.common.facade.FeedbackFacade;
import jim.sums.marking.bus.MarkingService;
import jim.sums.register.util.PasswordUtil;
import org.apache.commons.mail.EmailException;

/**
 * Service class for feedback properties
 *
 * @author Mike
 */
@Stateless
public class FeedbackService extends AbstractService {

    @EJB
    private FeedbackFacade feedbackFacade;
    @EJB
    private MailService ms;
    @EJB
    private MarkingService markService;
    @EJB
    private SystemInfoService sis;
    private Date todayDate;
    private TemplateMarkForm markingForm;
    private List<TemplateCategory> markingFormList;

    /**
     * Returns marking service
     *
     * @return MarkingService
     */
    public MarkingService getMarkService() {
        return markService;
    }

    /**
     * Sets the marking service
     *
     * @param markService
     */
    public void setMarkService(MarkingService markService) {
        this.markService = markService;
    }

    /**
     * Returns the feedback facade
     *
     * @return FeedbackFacade
     */
    public FeedbackFacade getFeedbackFacade() {
        return feedbackFacade;
    }

    /**
     * Sets the feedbackFacade
     *
     * @param feedbackFacade
     */
    public void setFeedbackFacade(FeedbackFacade feedbackFacade) {
        this.feedbackFacade = feedbackFacade;
    }

    /**
     * Actions when Feedback has been confirmed by cohort
     *
     * @param feedback
     * @return feedback
     */
    public Feedback confirmFeedback(Feedback feedback) {
        todayDate = new Date();
        feedback.setFeedbackConfirmed(Boolean.TRUE);
        feedback = feedbackFacade.edit(feedback);
        return feedback;
    }

    /**
     * Actions when user only wants to save changes and not confirm feedback
     *
     * @param feedback
     * @return feedback
     */
    public Feedback updateFeedback(Feedback feedback) {
        todayDate = new Date();
        feedback.setModifieddate(todayDate);
        feedback = feedbackFacade.edit(feedback);
        return feedback;
    }

    /**
     * Actions when feedback has been viewed
     *
     * @param feedback
     * @return feedback
     */
    public Feedback feedbackViewed(Feedback feedback) {
        todayDate = new Date();
        feedback.setViewedDate(todayDate.toString());
        feedback = feedbackFacade.edit(feedback);
        return feedback;
    }

    /**
     * Returns the Marking Criteria form data for a project
     *
     * @param project
     * @return List
     */
    public List<TemplateCategory> getFormData(Finalproject project) {
        markingForm = (TemplateMarkForm) project.getUnitinstance().getUnit().getUnitMarkFormsList().get(0).getFormId();
        if (markingForm != null) {
            if (markingFormList == null) {
                markingFormList = markService.getFormRows(markingForm, project);

            }
            return markingFormList;
        }

        return null;
    }

    /**
     * Actions when user wants to sent email notifications to project owners who
     * have not yet read the feedback
     *
     * @param projects
     */
    public void sendConfirmedFeedback(List<Finalproject> projects) {
        for (Finalproject fp : projects) {
            //TODO: When email service is working uncomment sendConfirmationEmail call
            //try {
            String key = PasswordUtil.hashMD5(fp.getProjecttitle() + fp.getFeedback().getModifieddate());
            //sendConfirmationEmail(fp,key);
            fp.getFeedback().setFeedbackSent("Sent");
            fp.getFeedback().setConfirmkey(key);
            feedbackFacade.edit(fp.getFeedback());
            //} catch (BusinessException ex) {
            //    Logger.getLogger(FeedbackService.class.getName()).log(Level.SEVERE, null, ex);
            // }
        }
    }

    /**
     * {@Link sendConfirmedFeedback} Sends email out to specified project owner
     *
     * @param project
     * @param confirmKey
     * @throws BusinessException
     */
    private void sendConfirmationEmail(Finalproject project, String confirmKey) throws BusinessException {
        try {
            Person p = project.getStudent().get(0); // Needs modifications in the future
            String link = sis.getProjectUrl() + "/view/register/confirm.xhtml?key=" + confirmKey;

            EmailMessage email = ms.getNewEmailMessage(p.getInternetAddress(), "SUMS feedback is ready");
            email.setText(
                    "Dear " + p.getFullname() + ",\n\n"
                    + "Your feedback for the project " + project.getProjecttitle() + " is ready to be viewed.\n\n"
                    + "Please keep this email for your records. "
                    + "To view your feedback, please visit this url: \n\n"
                    + link + "\n\n"
                    + "SUMS Manager");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(FeedbackService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(ex.getMessage());
        }
    }
}
