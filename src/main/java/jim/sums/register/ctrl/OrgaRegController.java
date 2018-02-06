/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Organisation;
import jim.sums.common.facade.OrganisationFacade;
import jim.sums.register.bus.AccountService;
import jim.sums.register.bus.RegisterService;

/**
 *
 * @author Scilano
 */
@RequestScoped
@ManagedBean(name = "orc")
public class OrgaRegController extends AbstractController<Organisation, OrganisationFacade> {

    @EJB
    private RegisterService rs;
    @EJB
    private AccountService as;
    String email = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrgaRegController() {
        super(Organisation.class);
    }

    public OrgaRegController(Class<Organisation> entityClass) {
        super(Organisation.class);
    }

    public String doAdd() {
        rs.registerNewOrganisation(newItem);
        return "toIndex";
    }

    @Override
    public OrganisationFacade getFacade() {
        return rs.getOrgaFacade();
    }

    public boolean checkKey() {
        String confirmKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        try {
            as.confirmRegistrationKey(confirmKey);
            addInfo("ok");
            return true;
        } catch (BusinessException ex) {
            addError(ex.getMessage());
            return false;
        }
    }
}
