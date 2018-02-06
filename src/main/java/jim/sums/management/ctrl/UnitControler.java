package jim.sums.management.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.UnitFacade;
import jim.sums.management.bus.UnitService;

/**
 * @author thomas
 */

@ViewScoped
@ManagedBean(name = "uc")
public class UnitControler extends AbstractController<Unit, UnitFacade> {
    
    @EJB
    private UnitService us;
    private UnitInstance currentUnitInstance;
    
    public UnitControler() {
        super(Unit.class);
        currentUnitInstance = null;
    }
    
    @PostConstruct
    private void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        if (ub.getTempUnit() != null)
            current = ub.getTempUnit();
    }

    @Override
    public UnitFacade getFacade() {
        return us.getFacade();
    }

    public UnitInstance getCurrentUnitInstance() {
        return currentUnitInstance;
    }

    public void setCurrentUnitInstance(UnitInstance currentUnitInstance) {
        this.currentUnitInstance = currentUnitInstance;
    }
}