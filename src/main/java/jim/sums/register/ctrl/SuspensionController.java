package jim.sums.register.ctrl;

import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Suspension;
import jim.sums.common.facade.SuspensionFacade;

/**
 *
 * @author LepanK
 */
@ViewScoped
@ManagedBean
public class SuspensionController extends AbstractController<Suspension, SuspensionFacade> {
    
    @EJB
    private SuspensionFacade sf;
    
    public SuspensionController() {
        super(Suspension.class);
    }

    @Override
    public SuspensionFacade getFacade() {
        return sf;
    }
    
    public String add() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        newItem.setDatesuspension(new Date());
        newItem.setSuspendedperson(ub.getTempPerson());
        newItem.setInstigaterperson(ub.getLoggedInUser());
        sf.create(newItem);
//        ms.postMail(new String[]{p.getEmail()}, "SUMS - Suspension", 
//                "Your account has been suspended. The reason is :\n\" "+reason+" \"\nYou should contact "+
//                ub.getLoggedInUser().getSurname()+" "+ub.getLoggedInUser().getForename()+".");
        ub.setTempPerson(null);
        
        return "toAdminPage";
    }
}
