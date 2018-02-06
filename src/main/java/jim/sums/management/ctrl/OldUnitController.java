/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.UnitFacade;
import jim.sums.management.bus.GroupsService;
import org.primefaces.event.SelectEvent;

/**
 * @author Sam
 */
@ManagedBean(name = "OldUc")
@ViewScoped
public class OldUnitController extends AbstractController<Unit, UnitFacade> {

    @EJB
    GroupsService gs;
    public int grade = 1;
    public int kind = 1;
    private Boolean opDisplayList = true;
    private Boolean opAddUnit = false;
    private Boolean opEditUnit = false;
    private Boolean opAddUnitInstance = false;
    private String academicyear;

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public Boolean getOpAddUnitInstance() {
        return opAddUnitInstance;
    }

    public void setOpAddUnitInstance(Boolean opAddUnitInstance) {
        this.opAddUnitInstance = opAddUnitInstance;
    }

    public Boolean getOpEditUnitInstance() {
        return opEditUnitInstance;
    }

    public void setOpEditUnitInstance(Boolean opEditUnitInstance) {
        this.opEditUnitInstance = opEditUnitInstance;
    }
    private Boolean opEditUnitInstance = false;
    private Boolean opUnitDetails = false;
    private HtmlInputText code;

    public HtmlInputText getCode() {
        return code;
    }

    public void setCode(HtmlInputText code) {
        this.code = code;
    }
    private static final String UNIT_CODE_REGEXP = "[a-zA-Z0-9]{6}$";

    @PostConstruct
    public void init() {
        current = getCurrent();
    }

    public void unitSelectListener(SelectEvent ev) {

        Unit obj = null;
        try {
            setCurrent((Unit) ev.getObject());


        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    @Override
    public DataModel<Unit> getItems() {
        return super.getItems();
    }

    public OldUnitController() {
        super(Unit.class);
    }

    public void validateCode(ValueChangeEvent ev) throws ValidatorException {

        //Validate the regex
        String code = ev.getNewValue().toString();
        if (code != null) {
            validateCode(code);
        }


    }

    public boolean validateCode(String code) {
        Pattern mask = null;
        mask = Pattern.compile(UNIT_CODE_REGEXP);
        Matcher matcher = mask.matcher(code);

        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Please enter a valid Code. Must have 6 alphanumeric characters.");
            message.setSummary("Code not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }
        //Validate the existence of the records with the same code
        List<Unit> currentUnits = gs.getUnitFacade().findAll();
        for (Unit u : currentUnits) {
            if (u.getUnitcode().equals(code)) {
                FacesMessage message1 = new FacesMessage();
                message1.setDetail("Unit with the same code already exists. Please write another code.");
                message1.setSummary("Unit with the same code already exists. Please write another code.");
                message1.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message1);
                return false;

            }
        }
        return true;
    }

    public void validateTitle(ValueChangeEvent ev) throws ValidatorException {

        //Validate the regex
        String title = ev.getNewValue().toString();
        if (title != null) {
            validateTitle(title);
        }


    }

    public boolean validateTitle(String title) {

        //Validate the existence of the records with the same code
        List<Unit> currentUnits = gs.getUnitFacade().findAll();
        for (Unit u : currentUnits) {
            if (u.getUnittitle().equals(title)) {
                FacesMessage message1 = new FacesMessage();
                message1.setDetail("Unit with the same title already exists. Please write another title.");
                message1.setSummary("Unit with the same title already exists. Please write another title.");
                message1.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message1);
                return false;
            }
        }
        return true;
    }

    @Override
    public UnitFacade getFacade() {
        return gs.getUnitFacade();
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public List<UnitInstance> getUnitInstancesList() {

        return gs.getUnitinstanceFacade().findUnitInstancesByUnit(current);
    }

    public void doEdit() {
        current.setUnitkind(gs.getKindFacade().find(kind));
        current.setCourseLevel(gs.getGradeFacade().find(grade));
        gs.editUnit(current);
    }

    public void doAdd() {

        newItem.setUnitkind(gs.getKindFacade().find(new Long(kind)));
        //  newItem.setCourseLevel(gs.getGradeFacade().find(grade));
        if (!validateCode(newItem.getUnitcode())) {
            return;
        }
        if (!validateTitle(newItem.getUnittitle())) {
            return;
        }

        try {
            gs.addUnit(newItem);
            items = new ListDataModel<Unit>(getFacade().findAll());
        } catch (BusinessException ex) {
            addError("Prob");
        }

        displayList(null);

    }

    public Boolean getOpAddUnit() {
        return opAddUnit;
    }

    public Boolean getOpDisplayList() {
        return opDisplayList;
    }

    public Boolean getOpEditUnit() {
        return opEditUnit;
    }

    public Boolean getOpUnitDetails() {
        return opUnitDetails;
    }

    public void addUnit(ActionEvent ae) {
        opAddUnit = true;
        opDisplayList = false;
        opEditUnit = false;
        opUnitDetails = false;
        opEditUnitInstance = false;
        opAddUnitInstance = false;
    }

    public void addUnitInstance(ActionEvent ae) {
        opAddUnit = false;
        opDisplayList = false;
        opEditUnit = false;
        opUnitDetails = false;
        opEditUnitInstance = false;
        opAddUnitInstance = true;
    }

    public void displayList(ActionEvent ae) {
        opAddUnit = false;
        opDisplayList = true;
        opEditUnit = false;
        opUnitDetails = false;
        opEditUnitInstance = false;
        opAddUnitInstance = false;
    }

    public void editUnit(ActionEvent ae) {
        opAddUnit = false;
        opDisplayList = false;
        opEditUnit = true;
        opEditUnitInstance = false;
        opAddUnitInstance = false;
        opUnitDetails = false;
    }

    public void editUnitInstance(ActionEvent ae) {
        opAddUnit = false;
        opDisplayList = false;
        opEditUnit = false;
        opEditUnitInstance = true;
        opAddUnitInstance = false;
        opUnitDetails = false;
    }

    public void unitDetails(ActionEvent ae) {
        opAddUnit = false;
        opDisplayList = false;
        opEditUnit = false;
        opEditUnitInstance = false;
        opAddUnitInstance = false;
        opUnitDetails = true;
    }
}
