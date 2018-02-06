package jim.sums.submit.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Finalproject;
import jim.sums.submit.bus.SubmissionService;
import jim.sums.common.db.SubmitConfiguration;
import jim.sums.common.facade.SubmitConfigurationFacade;

/**
 * @author Bogdan Andreescu
 */

@ViewScoped
@ManagedBean(name = "scc")
public class SubmitConfigurationController extends AbstractController<SubmitConfiguration, SubmitConfigurationFacade> {

    @EJB
    private SubmissionService ss;
    private Finalproject currentProject;
    private SubmitConfiguration currentSubmitConf;
    
    public SubmitConfigurationController() {
        super(SubmitConfiguration.class);
    }

    @Override
    public SubmitConfigurationFacade getFacade() {
        return ss.getSubmitConfFacade();
    }

    public Finalproject getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Finalproject currentProject) {
        this.currentProject = currentProject;
    }

    public SubmitConfiguration getCurrentSubmitConf() {
        return currentSubmitConf;
    }

    public void setCurrentSubmitConf(SubmitConfiguration currentSubmitConf) {
        this.currentSubmitConf = currentSubmitConf;
    }
    
    public void GenerateRaport(){
        //TODO
    }
}