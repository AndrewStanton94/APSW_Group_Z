package jim.sums.management.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Person;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.management.bus.GroupsService;
import jim.sums.register.bus.AdminService;

/**
 * @author Sam
 */

@ManagedBean(name = "oldUnitic")
@ViewScoped
public class OldUnitinstanceController extends AbstractController<UnitInstance, UnitinstanceFacade> {

    @EJB
    GroupsService gs;
    @EJB
    AdminService as;
    List<Unitpersonin> listStudentToConfirm;
    List<Unitpersonin> listStudentAlreadyConfirm;
    Unitpersonin upiToConfirm;
    Unitpersonin upiToDelete;
    String color;
    private Boolean opDisplayList = true;
    private Boolean opAddUnitInstance = false;
    private Boolean opEditUnitInstance = false;
    private Boolean opUnitInstanceDetails = false;
    private String Academicyear;
    public Unitpersonin[] selectedPerson;

    @PostConstruct
    public void init() {
        current = getCurrent();
    }

    /** Creates a new instance of GroupsController */
    public OldUnitinstanceController() {
        super(UnitInstance.class);
    }

    @Override
    public UnitinstanceFacade getFacade() {
        return gs.getUnitinstanceFacade();
    }

    public Unitpersonin getUpiToDelete() {
        System.out.println("jpasse par delete !!");
        return upiToDelete;
    }

    public void setUpiToDelete(Unitpersonin upiToDelete) {
        System.out.println("jpasse par delete22 !!");
        this.upiToDelete = upiToDelete;
        if (upiToDelete != null) {
            upiToDelete.getChosen().getUnitpersoninList().remove(upiToDelete);
            gs.getPersonFacade().edit(upiToDelete.getChosen());
            upiToDelete.getUnitinstance().getUnitpersoninList().remove(upiToDelete);
            gs.getUnitinstanceFacade().edit(upiToDelete.getUnitinstance());
            gs.getUnitPersonInFacade().remove(upiToDelete);
        }
    }

    public String studentsToConfirm(UnitInstance ui) {
        Person p;
        ArrayList<Unitpersonin> list = new ArrayList<Unitpersonin>();
        for (int i = 0; i < ui.getUnitpersoninList().size(); i++) {
            p = ui.getUnitpersoninList().get(i).getChosen();
            if (ui.getUnitpersoninList().get(i).getValidationdate() == null) {
                list.add(ui.getUnitpersoninList().get(i));
            }
        }
        if (list.size() > 0) {
            color = "color:red";
            return "Yes : "+list.size()+" to confirm";
        }
        color = "color:green";
        return "No";
    }

    public String getColor() {
        return color;
    }

    public String getAcademicyear() {
        return Academicyear;
    }

    public void setAcademicyear(String Academicyear) {
        this.Academicyear = Academicyear;
    }

    public Unitpersonin getUpiToConfirm() {
        System.out.println("jpasse par confiirm !!");
        return upiToConfirm;
    }

    public void setUpiToConfirm(Unitpersonin upi) {
        System.out.println("jpasse par confiirm22 !!");
        this.upiToConfirm = upi;
        if (upi != null) {
            upi.setValidationdate(new Date());
            gs.getUnitPersonInFacade().edit(upi);
        }
    }

    public List<Unitpersonin> getListStudentToConfirm() {
        listStudentToConfirm = new ArrayList<Unitpersonin>();
        for (Unitpersonin upi : getCurrent().getUnitpersoninList()) {
            if (upi.getValidationdate() == null) {
                listStudentToConfirm.add(upi);
            }
        }
        return listStudentToConfirm;
    }
    
    public List<Unitpersonin> getListStudentAlreadyConfirm() {
        listStudentAlreadyConfirm = new ArrayList<Unitpersonin>();
        for (Unitpersonin upi : getCurrent().getUnitpersoninList()) {
            if (upi.getValidationdate() != null) {
                listStudentAlreadyConfirm.add(upi);
            }
        }
        return listStudentAlreadyConfirm;
    }

    public void confirm(Unitpersonin upi) {
        as.confirm(upi);
    }

    public void doEdit() {
        gs.editUnitinstance(current);
    }

    @Override
    public UnitInstance getNewItem() {
        return super.getNewItem();
    }

    public void doAdd() {
        newItem.setAcademicyear(gs.getAcademicyearFacade().find(Integer.parseInt(Academicyear)));
       // gs.addUnitinstance(newItem);
        List<UnitInstance> currList=newItem.getUnit().getUnitinstanceList();
        currList.add(newItem);
        newItem.getUnit().setUnitinstanceList(currList);
        gs.getUnitFacade().edit(newItem.getUnit());
        items = new ListDataModel<UnitInstance>(getFacade().findAll());
    }

    public Boolean getOpAddUnitInstance() {
        return opAddUnitInstance;
    }

    public Boolean getOpDisplayList() {
        return opDisplayList;
    }

    public Boolean getOpEditUnitInstance() {
        return opEditUnitInstance;
    }

    public Boolean getOpUnitInstanceDetails() {
        return opUnitInstanceDetails;
    }

    public void addUnitInstance(ActionEvent ae) {
        opAddUnitInstance = true;
        opDisplayList = false;
        opEditUnitInstance = false;
        opUnitInstanceDetails = false;
    }

    public void displayList(ActionEvent ae) {
        opAddUnitInstance = false;
        opDisplayList = true;
        opEditUnitInstance = false;
        opUnitInstanceDetails = false;
    }

    public void editUnitInstance(ActionEvent ae) {
        opAddUnitInstance = false;
        opDisplayList = false;
        opEditUnitInstance = true;
        opUnitInstanceDetails = false;
    }

    public void UnitInstanceDetails(ActionEvent ae) {
        opAddUnitInstance = false;
        opDisplayList = false;
        opEditUnitInstance = false;
        opUnitInstanceDetails = true;
    }

    public Unitpersonin[] getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Unitpersonin[] selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
    
    public void unitPersonInSeveralDelete() {
        for(Unitpersonin upi : getSelectedPerson()) {
            setUpiToDelete(upi);
        }
    }
    
    public void unitPersonInSeveralConfirm() {
        for(Unitpersonin upi : getSelectedPerson()) {
            setUpiToConfirm(upi);
        }
    }

}
