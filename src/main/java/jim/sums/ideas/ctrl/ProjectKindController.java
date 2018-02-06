/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.ProjectKind;
import jim.sums.common.facade.ProjectKindFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@RequestScoped
@ManagedBean(name = "kc")
public class ProjectKindController extends AbstractController<ProjectKind, ProjectKindFacade> {

    @EJB
    IdeaService is;
    ArrayList<ProjectKind> selectedKindList;

    @Override
    public ProjectKindFacade getFacade() {
        return is.getKindFacade();
    }

    public ProjectKindController() {
        super(ProjectKind.class);
    }

    public ArrayList<ProjectKind> getSelectedKindList() {
        return selectedKindList;
    }

    public void setSelectedKindList(ArrayList<ProjectKind> selectedKindList) {
        this.selectedKindList = selectedKindList;
    }

    public SelectItem[] getKindList() {

        List<ProjectKind> tmp = getFacade().findAll();

        return getSelectItems(tmp, false);
    }
}
