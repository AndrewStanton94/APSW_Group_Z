package jim.sums.register.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Organisation;
import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;
import jim.sums.register.bus.RegisterService;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlSelectOneMenu;
import jim.sums.common.db.RoleName;
import jim.sums.register.bus.AccountService;

/**
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean(name = "prc")
public class PersonRegController extends AbstractController<Person, PersonFacade> {

    @EJB
    private RegisterService rs;
    @EJB
    private AccountService as;
    Boolean validKey = null;
    String confirmPassword;
    Organisation organisation = null;
    String availability = null;
    String hemis = null;
    Boolean displayExternal;
    Boolean displayStudent;

    public PersonRegController() {
        super(Person.class);
    }

    public String doAdd() {
        String groupid;
        if (newItem.isExternal()) { // External
            rs.registerNewOrganisation(organisation);
            organisation.getPersonList().add(newItem);
            newItem.setOrganisation(rs.getOrgaFacade().findSameName(organisation).get(0));
            groupid = "External";
        } else if (newItem.isStudent()) { // Student
            newItem.setHemis(hemis);
            groupid = "Student";
        } else if (newItem.isAdmin()) { //Admin
            groupid = "Administrator";
        } else { //Staff
            groupid = "Staff";
        }
        try {
            Person per = rs.registerNewPerson(newItem);
            addInfo("Please check your emails to activate your account.");
        } catch (BusinessException ex) {
            addError(ex.getMessage());
            return null;
        }
        return "/index.xhtml";
    }

    public boolean isValidKey() {
        if (validKey == null) {
            String confirmKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
            try {
                as.confirmRegistrationKey(confirmKey);
                validKey = true;
            } catch (BusinessException ex) {
                addError(ex.getMessage());
                validKey = false;
            }
        }
        return validKey;
    }

    @Override
    public PersonFacade getFacade() {
        return rs.getPersFacade();
    }

    public Organisation getOrganisation() {
        if (organisation == null) {
            organisation = new Organisation();
        }
        return organisation;
    }

    public String getHemis() {
        return hemis;
    }

    public void setHemis(String hemis) {
        this.hemis = hemis;
    }

    public Boolean getDisplayExternal() {
        return newItem.isExternal();
        /*
        javax.faces.component.UISelectOne select = (javax.faces.component.UISelectOne) FacesContext.getCurrentInstance().getViewRoot().findComponent("Register:UserStatus");
        try {
            if (select.getLocalValue() != null) {
                if (((RoleName) select.getLocalValue()).matches("External")) {
                    return true;
                }
            }
            if (select.getValue() != null) {
                if (((RoleName) select.getValue()).matches("External")) {
                    return true;
                }
            }
            if (select.getSubmittedValue() != null) {
                if ((Integer.parseInt((String) select.getSubmittedValue())) == 3) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            //ignore;
        }
        return false;
        * */
    }

    public Boolean getDisplayStudent() {
        return newItem.isStudent();
        /*
        HtmlSelectOneMenu select = (HtmlSelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("Register:UserStatus");
        try {
            if (select.getLocalValue() != null) {
                if (((RoleName) select.getLocalValue()).matches("Student")) {
                    return true;
                }
            }
            if (select.getValue() != null) {
                if (((RoleName) select.getValue()).matches("Student")) {
                    return true;
                }
            }
            if (select.getSubmittedValue() != null) {
                if ((Integer.parseInt((String) select.getSubmittedValue())) == 1) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            //ignore;
        }
        return false;
        * */
    }
}
