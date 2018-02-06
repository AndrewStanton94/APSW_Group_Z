package jim.sums.management.ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.management.bus.UnitInstanceService;

/**
 * @author thomas
 */

@ViewScoped
@ManagedBean(name = "uiec")
public class UnitInstanceEditController extends AbstractController<UnitInstance, UnitinstanceFacade> {

    @EJB
    UnitInstanceService uis;
    private boolean creationMode;
    private UnitInstance originalUnitInstance;
    
    public UnitInstanceEditController(){
        super(UnitInstance.class);
    }
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        
        originalUnitInstance = ub.getTempUnitInstance();
        current = new UnitInstance();
        if(originalUnitInstance == null) {
            creationMode = true;
        } else {
            current.setIdunitinstance(originalUnitInstance.getIdunitinstance());
            current.setCohortList(originalUnitInstance.getCohortList());
            current.setAcademicyear(originalUnitInstance.getAcademicyear());
            current.setCourseinstanceList(originalUnitInstance.getCourseinstanceList());
            current.setFinalprojectList(originalUnitInstance.getFinalprojectList());
            current.setIdeachosenbyList(originalUnitInstance.getIdeachosenbyList());
            current.setPersonchosenbyList(originalUnitInstance.getPersonchosenbyList());
            current.setUnit(originalUnitInstance.getUnit());
            current.setUnitpersoninList(originalUnitInstance.getUnitpersoninList());
            creationMode = false;
        }
    }
    
    @Override
    public UnitinstanceFacade getFacade() {
        return uis.getFacade();
    }
    
    public boolean isCreationMode() {
        return creationMode;
    }
    
    public UnitInstance getOriginalUnitInstance() {
        return originalUnitInstance;
    }
    
    public String edit() {
        try {
            uis.edit(current);
        } catch (BusinessException ex) {
            addError("UnitInstanceDetailsForm", ex.getMessage());
            return null;
        }
        return "toUnitInstanceDetails";
    }
    
    public String add() {
        try {
            uis.add(current);
        } catch (BusinessException ex) {
            addError("UnitInstanceDetailsForm", ex.getMessage());
            return null;
        }
        return "toUnitInstanceDetails";
    }
    
    public String delete() {
        //TODO
        return null;
    }
    
}
