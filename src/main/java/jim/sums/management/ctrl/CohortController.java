/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.CohortFacade;
import jim.sums.management.bus.GroupsService;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Sam
 */
@ManagedBean(name = "cohc")
@ViewScoped
public class CohortController extends AbstractController<Cohort, CohortFacade> {

    @EJB
    GroupsService gs;
    private Boolean opDisplayList = true;
    private Boolean opAddCohort = false;
    private Boolean opEditCohort = false;
    private Boolean opCohortDetails = false;
    private DualListModel<String> unitInstances;
    private UnitInstance[] selectedUnitInstances;

    

   

    /** Creates a new instance of GroupsController */
    public CohortController() {
        super(Cohort.class);
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cohortId") != null) {
            current = gs.getCohortFacade().find(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cohortId")));
        } else {
            current = getCurrent();
        }
    }

    @Override
    public CohortFacade getFacade() {
        return gs.getCohortFacade();
    }

    public boolean isPaged() {
        if (current.getUnits().size() > 10) {
            return true;
        }
        return false;
    }

    public void saveDates(ActionEvent e) {
        gs.getCohortFacade().edit(current);
        //addInfo("dates", "Dates changed");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dates changed"));
        
    }

    public void doEdit() {
        gs.editCohort(current);
    }

    public void doAdd() {
        gs.addCohort(newItem);
        items = new ListDataModel<Cohort>(getFacade().findAll());
    }

    public Boolean getOpAddCohort() {
        return opAddCohort;
    }

    public Boolean getOpDisplayList() {
        return opDisplayList;
    }

    public Boolean getOpEditCohort() {
        return opEditCohort;
    }

    public Boolean getOpCohortDetails() {
        return opCohortDetails;
    }

    public void addCohort(ActionEvent ae) {
        opAddCohort = true;
        opDisplayList = false;
        opEditCohort = false;
        opCohortDetails = false;
    }

    public void displayList(ActionEvent ae) {
        opAddCohort = false;
        opDisplayList = true;
        opEditCohort = false;
        opCohortDetails = false;
    }

    public void editCohort(ActionEvent ae) {
        opAddCohort = false;
        opDisplayList = false;
        opEditCohort = true;
        opCohortDetails = false;

    }

    public void cohortDetails(ActionEvent ae) {
        opAddCohort = false;
        opDisplayList = false;
        opEditCohort = false;
        opCohortDetails = true;
        List<String> unitInstanceSource = new ArrayList<String>();
        List<String> unitInstanceTarget = new ArrayList<String>();

    }

    /* useless
     * public void cohortSelectListener(SelectEvent ev) {

        Cohort obj = null;
        try {
            setCurrent((Cohort) ev.getObject());
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }*/

    public DualListModel<String> getUnitInstances() {
        return unitInstances;
    }

    public void setUnitInstances(DualListModel<String> unitInstances) {
        this.unitInstances = unitInstances;
    }

    public List<UnitInstance> getAllUnitInstances() {
        return gs.getUnitinstanceFacade().findAll();
    }

    
    public UnitInstance[] getSelectedUnitInstances() {
        return selectedUnitInstances;
    }

    public void setSelectedUnitInstances(UnitInstance[] selectedUnitInstances) {
        this.selectedUnitInstances = selectedUnitInstances;
    }
    
    public void selectInstance(ActionEvent ev) {

        List<UnitInstance> currentUnitList= current.getUnits();
        for(UnitInstance i : getSelectedUnitInstances()){
            if(!currentUnitList.contains(i)){
                currentUnitList.add(i);
            }
        }

       current.setUnits(currentUnitList);
       gs.editCohort(current);
    }
     
     //Remove unitInstance from Cohorts
    public void remove(UnitInstance unit) {
        List<UnitInstance> currentUnitList= current.getUnits();
        currentUnitList.remove(unit);
        current.setUnits(currentUnitList);
        gs.editCohort(current);
    }
     
    public List<Cohort> getList() {
        return getFacade().findAll() ;
    }
    
    public List<Cohort> getCohortList() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        if (ub.isAdmin()) {
            return getList();
        }
        return ub.getLoggedInUser().getCohortList1();
    }
}
