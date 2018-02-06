package jim.sums.management.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Unit;
import jim.sums.common.facade.UnitFacade;
import jim.sums.management.bus.UnitService;

/**
 * @author thomas
 */

@ViewScoped
@ManagedBean(name = "uec")
public class UnitEditControler extends AbstractController<Unit, UnitFacade> {
    
    @EJB
    private UnitService us;
    private boolean creationMode;
    private Unit originalUnit;
    
    public UnitEditControler() {
        super(Unit.class);
    }
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        
        originalUnit = ub.getTempUnit();
        current = new Unit();
        if(originalUnit == null) {
            creationMode = true;
        } else {
            
            if (originalUnit.getUnitcode() == null) {
                creationMode = true;
            } else {
                creationMode = false;
                
                current.setUnitcode(originalUnit.getUnitcode());
                current.setUnitinstanceList(originalUnit.getUnitinstanceList());
            }
            
            current.setUnittitle(originalUnit.getUnittitle());
            current.setUnitkind(originalUnit.getUnitkind());
            current.setCourseLevel(originalUnit.getCourseLevel());
        }
    }

    @Override
    public UnitFacade getFacade() {
        return us.getFacade();
    }

    public boolean isCreationMode() {
        return creationMode;
    }
    
    public Unit getOriginalUnit() {
        return originalUnit;
    }
    
    public String edit() {
        try {
            us.edit(current);
        } catch (BusinessException ex) {
            addError("unitDetailsForm", ex.getMessage());
            return null;
        }
        return "toUnitDetails";
    }
    
    public String add() {
        try {
            us.add(current);
        } catch (BusinessException ex) {
            addError("unitDetailsForm", ex.getMessage());
            return null;
        }
        return "toUnitDetails";
    }
    
    public String delete(Unit unitToDelete) {
        //TODO implement a way to deactivate a unit (it must can be reactivated later)
        return null;
    }
}