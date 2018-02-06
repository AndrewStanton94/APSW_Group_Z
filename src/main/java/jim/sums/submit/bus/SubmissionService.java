package jim.sums.submit.bus;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Submission;
import jim.sums.common.db.SubmitConfiguration;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.SubmissionFacade;
import jim.sums.common.facade.SubmitConfigurationFacade;

/**
 * * Service for SubmissionController and SubmitConfigurationController
 *
 * @author Bogdan Andreescu
 */

@Stateless
@LocalBean
public class SubmissionService extends AbstractService {

    @EJB
    private SubmitConfigurationFacade scf;
    @EJB
    private SubmissionFacade sf;
    
    public SubmitConfigurationFacade getSubmitConfFacade() {
        return scf;
    }

    public SubmissionFacade getSubmissionFacade() {
        return sf;
    }
 
    public void makeNewConfig(SubmitConfiguration conf) throws BusinessException {
        //TODO check that the user can do this
        if (true) {
            scf.create(conf);
        } else {
            throw new BusinessException("You do not have the privilege level required to create a new session of submissions");
        }
    }

    public void updateConfig(SubmitConfiguration conf) throws BusinessException {
        //TODO check
        if (true) {
            scf.edit(conf);
        } else {
            throw new BusinessException("You do not have the privilege level required to modify this session of submissions");
        }
    }

    public SubmitConfiguration findConfig(Finalproject fp) {
        SubmitConfiguration conf;
        UnitInstance ui = fp.getUnitinstance();
        conf = ui.getCorrectSubmission();
        return conf;
    }

    public Submission makeNewSubmission(Submission s, Finalproject fp, SubmitConfiguration sc) throws BusinessException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        
        if (!(ub.isAdmin() || ub.isStaff() || ub.isStudent()) || ub.isSuspended())
            throw new BusinessException("You do not have the privilege level required to submit a file");
        //TODO : test link between user <-> project <-> submitConf
        
        s.setSubmissionTime(new Date());
        s.setPerson(ub.getLoggedInUser());
        s.setProject(fp);
        s.setConfig(sc);
        
        fp.setSubmission(s);
        sc.getSubmissions().add(s);
        
        sf.create(s);
        return s;
    }

}
//    public void setSubmitConfFacade(SubmitConfigurationFacade submitConf) {
//        this.scf = submitConf;
//    }
//    @EJB
//    private UnitinstanceFacade unitinstanceFacade;
//    @EJB
//    private ProjectideaFacade projectideaFacade;
//
//    @EJB
//    private SubmissionFacade submitFinalProj;
//    @EJB
//    UserService us;
//    @EJB
//    UserBean ub;
//    @EJB
//    private Submission submission;
//    private Person curruser;
//
//    /**
//     * Returns the projectideaFacade
//     *
//     * @return FinalprojectFacade
//     */
//    public ProjectideaFacade getProjectideaFacade() {
//        return projectideaFacade;
//    }
//
//    /**
//     * Sets the projectideaFacade
//     *
//     * @param FinalprojectFacade
//     */
//    public void setProjectideaFacade(ProjectideaFacade projectideaFacade) {
//        this.projectideaFacade = projectideaFacade;
//    }
//
//    /**
//     * Returns the SubmissionFacade
//     *
//     * @return SubmissionFacade
//     */
//    public SubmissionFacade getSubmitFinalProj() {
//        return submitFinalProj;
//    }
//
//    /**
//     * Sets the SubmitFinalProj
//     *
//     * @param submitFinalProj
//     */
//    public void setSubmitFinalProj(SubmissionFacade submitFinalProj) {
//        this.submitFinalProj = submitFinalProj;
//    }
//
//    /**
//     * Change filetype 
//     * 
//     *
//     * @param filetype
//     */
//    public void changeFileType(SubmitConfiguration filetype) {
//        submitConf.edit(filetype);
//    }
//
//    /**
//     * check if current user is staff
//     * 
//     *
//     * @return Boolean
//     */// 
//    public boolean checkIsStaff() {
//        return us.isStaff(ub.getUser());
//    }
//
//    /**
//     * check if current user is Student
//     * 
//     *
//     * @return Boolean
//     *///  
//    public boolean checkIsStudent() {
//        return us.isStudent(ub.getUser());
//    }
//
//    /**
//     * check if current user is Enrolled in a unit
//     * 
//     *
//     * @return Boolean
//     *///  
//    public boolean isEnrolled(String username) {
//        List<Unitpersonin> enrolled = us.getPerson(ub.getUser()).getUnitpersoninList();
//        if (enrolled == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     *Introduce the Submission data into the system
//     * 
//     *
//     * @return Boolean
//     *///  
//    public Submission submit(Submission submission, Person user ) throws BusinessException {
//        submission.setPerson(user);
//     //   submission.setIdunitinstance(unitinstance);
//       // submission.setUploadFile(currfile);
//        submission = submitFinalProj.edit(submission);
//
//        //check that the operation is OK
//        //set up relationships between submission and other objects and vice versa
//        return submission;
//    }
//
//    /**
//     *Submit the changeSubmitConfig into the system
//     * 
//     *
//     * @return Boolean
//     *///  
//    public void changeSubmitConfig(SubmitConfiguration sc) {
//
//        submitConf.edit(sc);
//
//    }