package jim.sums.submit.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.submit.bus.SubmissionService;
import jim.sums.common.db.SubmitConfiguration;
import jim.sums.common.facade.SubmitConfigurationFacade;

/**
 * @author thomas
 */

@ViewScoped
@ManagedBean(name = "scec")
public class SubmitConfigurationEditController extends AbstractController<SubmitConfiguration, SubmitConfigurationFacade> {

    @EJB
    private SubmissionService ss;
    private String fileConf = "";
    private SubmitConfiguration originalSubmitConf;
    private boolean creationMode;
    
    public SubmitConfigurationEditController() {
        super(SubmitConfiguration.class);
    }

    @Override
    public SubmitConfigurationFacade getFacade() {
        return ss.getSubmitConfFacade();
    }
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        
        originalSubmitConf = ub.getTempSubmitConf();
        if(originalSubmitConf == null) {
            creationMode = true;
        } else {
            if(originalSubmitConf.getId() == null)
                creationMode = true;
            else
                creationMode = false;
            
            getNewItem().setId(originalSubmitConf.getId());
            newItem.setDescriptionSubmitConfig(originalSubmitConf.getDescriptionSubmitConfig());
            newItem.setUniti(originalSubmitConf.getUniti());
            newItem.setFileType(originalSubmitConf.getFileType());
            newItem.setFileSize(originalSubmitConf.getFileSize());
            newItem.setOpeningDate(originalSubmitConf.getOpeningDate());
            newItem.setNormalDeadline(originalSubmitConf.getNormalDeadline());
            newItem.setFinalDeadline(originalSubmitConf.getFinalDeadline());
            newItem.setSubmissions(originalSubmitConf.getSubmissions());
            
            setFileConf(originalSubmitConf.getFileSize() + ":" + originalSubmitConf.getFileType());
            
        }
    }

    public SubmitConfiguration getOriginalSubmitConf() {
        return originalSubmitConf;
    }    
       
    public boolean isCreationMode() {
        return creationMode;
    }
        
    public String add() {
        try {
            ss.makeNewConfig(newItem);
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return "toSubmissionConfiguration";
    }
    
    public String edit() {
        try {
            ss.updateConfig(newItem);
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return "toSubmissionConfiguration";
    }
    
    public String delete(SubmitConfiguration submitConf){
        //TODO
        return "toSubmissionConfiguration";
    }

    public String getFileConf() {
        return fileConf;
    }

    public void setFileConf(String fileConf) {
        this.fileConf = fileConf;
        if (fileConf.equals("[void]")) {
            newItem.setFileType("");
            newItem.setFileSize(0);
        } else {
            String[] parameters = fileConf.split(":");
            newItem.setFileSize(Integer.parseInt(parameters[0]));
            newItem.setFileType(parameters[1]);
        }
    }     
}