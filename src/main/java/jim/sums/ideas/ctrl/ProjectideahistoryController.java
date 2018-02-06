/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Projectideahistory;
import jim.sums.common.db.ProjectideahistoryPK;
import jim.sums.common.facade.ProjectideahistoryFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@RequestScoped
@ManagedBean(name = "pihc")
public class ProjectideahistoryController extends AbstractController<Projectideahistory, ProjectideahistoryFacade> {

    @EJB
    IdeaService is;

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPihNum") != null) {
            int numchange = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPihNum"));
            int projectidea = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPihProj"));
            String username = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPihPers");
            ProjectideahistoryPK pk = new ProjectideahistoryPK(username, projectidea, numchange);
            current = is.getProjectideahistoryFacade().find(pk);
        }
    }

    @Override
    public ProjectideahistoryFacade getFacade() {
        return is.getProjectideahistoryFacade();
    }

    public ProjectideahistoryController() {
        super(Projectideahistory.class);
    }

    public String convertChanges() {
        return current.getChange().replace("\n", "<br />");
    }
}
