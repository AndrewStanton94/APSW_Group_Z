/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Courseinstance;
import jim.sums.common.db.Courseinstance;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.CourseinstanceFacade;
import jim.sums.management.bus.GroupsService;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Sam
 */
@ManagedBean(name = "cic")
@ViewScoped
public class CourseinstanceController extends AbstractController<Courseinstance, CourseinstanceFacade> {

    @EJB
    GroupsService gs;
    private Boolean opDisplayList = true;
    private Boolean opAddCourseInstance = false;
    private Boolean opEditCourseInstance = false;
    private Boolean opCourseInstanceDetails = false;
    private String Academicyear;

    public void setSelectedUnitInstances(UnitInstance[] selectedUnitInstances) {
        this.selectedUnitInstances = selectedUnitInstances;
    }
    private UnitInstance[] selectedUnitInstances;

    @PostConstruct
    public void init() {
        current = getCurrent();
    }

    /** Creates a new instance of GroupsController */
    public CourseinstanceController() {
        super(Courseinstance.class);
    }

    @Override
    public CourseinstanceFacade getFacade() {
        return gs.getCourseinstanceFacade();
    }

    public String getAcademicyear() {
        return Academicyear;
    }

    public void setAcademicyear(String Academicyear) {
        this.Academicyear = Academicyear;
    }

    public void doEdit() {
        gs.editCourseinstance(current);
    }

    public void doAdd() {
        newItem.setAcademicyear(gs.getAcademicyearFacade().find(Integer.parseInt(Academicyear)));
        //gs.addCourseinstance(newItem);
        List<Courseinstance> curr=   newItem.getCourse().getCourseinstanceList();
        curr.add(newItem);
        newItem.getCourse().setCourseinstanceList(curr);
        gs.getCourseFacade().edit(newItem.getCourse());
        items = new ListDataModel<Courseinstance>(getFacade().findAll());
    }

    public Boolean getOpAddCourseInstance() {
        return opAddCourseInstance;
    }

    public Boolean getOpDisplayList() {
        return opDisplayList;
    }

    public Boolean getOpEditCourseInstance() {
        return opEditCourseInstance;
    }

    public List<UnitInstance> getAllUnitInstances() {
        return gs.getUnitinstanceFacade().findAll();

    }

    public UnitInstance[] getSelectedUnitInstances() {
        return selectedUnitInstances;
    }

    public void selectInstance(ActionEvent ev) {

        List<UnitInstance> currentUnitList = current.getUnitinstances();
        for (UnitInstance i : getSelectedUnitInstances()) {
            if (!currentUnitList.contains(i)) {
                currentUnitList.add(i);
            }
        }
        current.setUnitinstances(currentUnitList);
        gs.editCourseinstance(current);


    }

    public Boolean getOpCourseInstanceDetails() {
        return opCourseInstanceDetails;
    }

    public void addCourseInstance(ActionEvent ae) {
        opAddCourseInstance = true;
        opDisplayList = false;
        opEditCourseInstance = false;
        opCourseInstanceDetails = false;
    }

    public void displayList(ActionEvent ae) {
        opAddCourseInstance = false;
        opDisplayList = true;
        opEditCourseInstance = false;
        opCourseInstanceDetails = false;
    }

    public void editCourseInstance(ActionEvent ae) {
        opAddCourseInstance = false;
        opDisplayList = false;
        opEditCourseInstance = true;
        opCourseInstanceDetails = false;
    }

    public void CourseInstanceDetails(ActionEvent ae) {
        opAddCourseInstance = false;
        opDisplayList = false;
        opEditCourseInstance = false;
        opCourseInstanceDetails = true;
    }

    /* seems useless
    public void courseInstanceSelectListener(SelectEvent ev) {

        Courseinstance obj = null;
        try {
            setCurrent((Courseinstance) ev.getObject());
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }*/
    
     //Remove unitInstance fro Cohorts
     public void remove(UnitInstance unit) {
         
        List<UnitInstance> currentUnitList= current.getUnitinstances();
        currentUnitList.remove(unit);
        current.setUnitinstances(currentUnitList);
        gs.editCourseinstance(current);
        
        
    }
}
