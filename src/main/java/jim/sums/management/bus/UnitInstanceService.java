package jim.sums.management.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.UnitinstanceFacade;

/**
 * @author thomas
 */

@Stateless
public class UnitInstanceService {
    @EJB
    private UnitinstanceFacade unitinstanceFacade;
    
    private UserBean getUserBean() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
    }
    
    public UnitinstanceFacade getFacade() {
        return unitinstanceFacade;
    }
    
    public void add(UnitInstance unitInstance) throws BusinessException {
        UserBean ub = getUserBean();
        if (!ub.isAdmin() || ub.isSuspended()) {
            throw new BusinessException("you do not have sufficient rights for this operation");
        }
        unitinstanceFacade.create(unitInstance);
    }
    
    public void edit(UnitInstance unitInstance) throws BusinessException{
        UserBean ub = getUserBean();
        if (!ub.isAdmin() || ub.isSuspended()) {
            throw new BusinessException("you do not have sufficient rights for this operation");
        }
        unitinstanceFacade.edit(unitInstance);
    }
}
