package jim.sums.management.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Unit;
import jim.sums.common.facade.UnitFacade;

/**
 * @author thomas
 */

@Stateless
public class UnitService {
    
    @EJB
    private UnitFacade unitFacade;
    
    public UnitService(){
    }
    
    private UserBean getUserBean() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
    }
    
    public UnitFacade getFacade() {
        return unitFacade;
    }
    
    public void add(Unit unit) throws BusinessException{
        UserBean ub = getUserBean();
        if (!ub.isAdmin() || ub.isSuspended())
            throw new BusinessException("you do not have sufficient rights for this operation");
        if (findSameUnitCode(unit))
            throw new BusinessException("Sorry, this unit code is already used. Please choose another one.");
        if (findSameUnitTitleWithoutSameCode(unit))
            throw new BusinessException("Sorry, another unit already uses this title. Please choose another one.");
        unitFacade.create(unit);
    }
    
    public void edit(Unit unit) throws BusinessException {
        UserBean ub = getUserBean();
        if (!ub.isAdmin() || ub.isSuspended()) {
            throw new BusinessException("you do not have sufficient rights for this operation");
        }
        if (findSameUnitTitleWithoutSameCode(unit))
            throw new BusinessException("Sorry, another unit already uses this title. Please choose another one.");
        
        unitFacade.edit(unit);
    }
    
    public boolean findSameUnitCode(Unit unit) {
        List<Unit> unitsWithSameCode = unitFacade.findSameCode(unit);
        if (unitsWithSameCode == null || unitsWithSameCode.isEmpty()) {            
            return false;
        }
        return true;       
    }
    
    public boolean findSameUnitTitleWithoutSameCode(Unit unit) {
        List<Unit> unitsWithSameTitle = unitFacade.findSameTitle(unit);
        if (unitsWithSameTitle == null || unitsWithSameTitle.isEmpty() || (unit.getUnitcode() != null && unit.getUnitcode().equals(unitsWithSameTitle.get(0).getUnitcode()))) {
            return false;
        }
        return true;       
    }
}
