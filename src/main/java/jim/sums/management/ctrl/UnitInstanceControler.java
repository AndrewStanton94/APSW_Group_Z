package jim.sums.management.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.management.bus.UnitInstanceService;

/**
 * @author thomas
 */

@ViewScoped
@ManagedBean(name = "unitIc")
public class UnitInstanceControler extends AbstractController<UnitInstance, UnitinstanceFacade>{
    
    @EJB
    UnitInstanceService uis;
    
    public UnitInstanceControler() {
        super(UnitInstance.class);
    }
    
    @PostConstruct
    private void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        if (ub.getTempUnitInstance()!= null)
            current = ub.getTempUnitInstance();
    }
    
    @Override
    public UnitinstanceFacade getFacade() {
        return uis.getFacade();
    }
    
    

}
