/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import jim.sums.common.ctrl.AbstractController;
import static jim.sums.common.ctrl.AbstractController.getSelectItems;
import jim.sums.common.db.RoleName;
import jim.sums.common.facade.RoleNameFacade;

/**
 *
 * @author BriggsJ
 */
@ManagedBean
public class RoleNameController extends AbstractController<RoleName, RoleNameFacade> {

    @EJB
    private RoleNameFacade rnf;

    /**
     * Creates a new instance of RoleNameController
     */
    public RoleNameController() {
        super(RoleName.class);
    }

    @Override
    public RoleNameFacade getFacade() {
        return rnf;
    }

    public SelectItem[] getUserItemsAvailableSelectOne() {
        SelectItem[] list = getSelectItems(getFacade().findUserRoles(), false);
        return list;
    }

//    @Override
//    public SelectItem[] getItemsAvailableSelectMany() {
//        return super.getItemsAvailableSelectMany(); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public SelectItem[] getItemsAvailableSelectOne() {
//        return super.getItemsAvailableSelectOne(); //To change body of generated methods, choose Tools | Templates.
//    }


}
