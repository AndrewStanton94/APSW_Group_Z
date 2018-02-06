package jim.sums.submit.ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Finalproject;
import jim.sums.submit.bus.SubmissionService;
import jim.sums.common.db.Submission;
import jim.sums.common.db.SubmitConfiguration;
import jim.sums.common.facade.SubmissionFacade;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author Me
 */

@ViewScoped
@ManagedBean(name = "sfpc")
public class SubmissionEditController extends AbstractController<Submission, SubmissionFacade> {

    @EJB
    private SubmissionService ss;
    
    private Finalproject project;
    private SubmitConfiguration config;
    private UploadedFile file;

    public SubmissionEditController() {
        super(Submission.class);
        file = null;
    }
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        
        project = ub.getTempProj();
        config = ub.getTempSubmitConf();
    }

    @Override
    public SubmissionFacade getFacade() {
        return ss.getSubmissionFacade();
    }

    public Finalproject getProject() {
        return project;
    }

    public void setProject(Finalproject project) {
        this.project = project;
    }
    
    public SubmitConfiguration getConfig() {
        return config;
    }

    public void setConfig(SubmitConfiguration config) {
        this.config = config;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void handleFileUpload (FileUploadEvent event) {  
        file = event.getFile();
    }
    
    public String getFileExtension() {
        if (file == null)
            return null;
        String fileName = file.getFileName();
        return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
    }

    public String getRegExConfigFileType() {
        String types = config.getFileType().replace(',', '|');
        return "/(\\.|\\/)(" + types + ")$/";
    }
    
    public String doSubmission() {
        Submission submission = new Submission();
        submission.setUploadfile(file.getContents());
        try {
            ss.makeNewSubmission(submission, project, config);
        } catch (BusinessException ex) {
            Logger.getLogger(SubmissionEditController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return "toIndex";
    }
    
    
}