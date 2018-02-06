package jim.sums.register.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Person;
import jim.sums.common.db.Personstaffstatus;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.PersonstaffstatusFacade;
import jim.sums.common.facade.StaffstatusFacade;
import jim.sums.register.bus.AccountService;
import jim.sums.register.bus.AdminService;

/**
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean(name = "pac")
public class PersonAccountController extends AbstractController<Person, PersonFacade> {

    @EJB
    private AccountService accountService;
    @EJB
    private AdminService adminService;
    @EJB
    private UserService userService;
    @EJB
    private PersonstaffstatusFacade pssf;
    @EJB
    private StaffstatusFacade ssf;
    private String currentPassword;
    private String newPassword;
    private String email;
    private int idUnitInstanceToAdd;
    private Boolean thisIsMyProfile = null;
    private int activeStaff;

    public PersonAccountController() {
        super(Person.class);
    }

    @Override
    public PersonFacade getFacade() {
        return accountService.getPersFacade();
    }

    @PostConstruct
    public void init() {
        if (current == null) {
            String username = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("username");
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
                    getValue(fc.getELContext(), null, "userBean");
            if (username != null) { // If we want to see another user's profile
                current = getFacade().find(username);
            } else { // Currently logged user's profile*/
                current = ub.getLoggedInUser();
            }
            if (current.equals(ub.getLoggedInUser())) {
                thisIsMyProfile = true;
            }
        }
    }

    public void saveUserInfo() {
        accountService.saveUserInfo(current);
        if (current.isStaff()) {
            List<Personstaffstatus> listPss = current.getPersonstaffstatusList();

            if (listPss.isEmpty()) {
                Personstaffstatus pss = new Personstaffstatus(current.getUsername(), ssf.findByName("supervisor").getId());
                pss.setActive(activeStaff);
                pss.setStaffstatus(ssf.findByName("supervisor"));
                pssf.edit(pss);
                listPss.add(pss);
                current.setPersonstaffstatusList(listPss);
            } else {
                for (Personstaffstatus pss : listPss) {
                    if (pss.getStaffstatus().getStatusname().equalsIgnoreCase("supervisor")) {
                        pss.setActive(activeStaff);
                        pssf.edit(pss);
                        current.setPersonstaffstatusList(listPss);
                        break;
                    }
                }
            }
        }
        userService.getPersonFacade().edit(current);
        addInfo("UserGeneralInfo", "Information updated.");
    }

    public String contactPointsToHTML() {
        String cf = "";
        if (current.getContactpoints() != null) {
            String contactPoints[] = current.getContactpoints().split(" ");
            for (int i = 0; i < contactPoints.length; i++) {
                if (contactPoints[i].startsWith("http://") || contactPoints[i].startsWith("https://")) {
                    contactPoints[i] = "<a href=\"" + contactPoints[i] + "\" target=\"_blank\">" + contactPoints[i] + "</a>";
                }
                cf += contactPoints[i] + " ";
            }
            return cf.replace("\n", "<br />");
        }
        return "";
    }

    public void changePassword() {
        try {
            accountService.changePassword(currentPassword, newPassword, current);
            addInfo("Password successfully changed.");
        } catch (BusinessException ex) {
            addError("UserPass", ex.getMessage());
        }
    }

    public void changeEmail() {
        try {
            accountService.changeEmail(email, current);
            addInfo("Email address successfully changed.");
            email = null;
        } catch (BusinessException ex) {
            addError("UserEmail", ex.getMessage());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Units">
    public String addUnitInstance() {
        try {
            accountService.addUnitInstance(idUnitInstanceToAdd, getCurrent());
        } catch (BusinessException ex) {
            addError("UnitStudentIn", ex.getMessage());
        }
        return null;
    }

    public DataModel<Unitpersonin> getUnits() {
        return new ListDataModel<Unitpersonin>(accountService.getUnits(current));
    }

    public int getIdUnitInstanceToAdd() {
        return idUnitInstanceToAdd;
    }

    public void setIdUnitInstanceToAdd(int idUnitInstanceToAdd) {
        this.idUnitInstanceToAdd = idUnitInstanceToAdd;
    }

    public List<UnitInstance> getUnitInstanceList() {
        return accountService.getUnitInstanceList();
    }

    public List<Person> getPersonInUnitPersonIn() {
        return accountService.getPersonInUnitPersonIn();
    }

    public String upiStatus(Unitpersonin upi) {
        if (upi.getValidationdate() == null) {
            return "Pending";
        } else {
            return "Validated";
        }
    }

    public String confirm(Unitpersonin upi) {
        adminService.confirm(upi);
        return null;
    }

    public String delete(Unitpersonin upi) {
        adminService.delete(upi);
        return null;
    }
    //</editor-fold>

    public void generatePassword() {
        try {
            accountService.generatePassword(current);
            addInfo("Password generated and sent by email.");
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<PersonStatus> getStatusList() {
//        return adminService.getPersonStatusList();
//    }
    public boolean currentIsStudent() {
        return userService.isStudent(current.getUsername());
    }

    public boolean currentIsExternal() {
        return userService.isExternal(current.getUsername());
    }

    public boolean isMyProfile() {
        //if(thisIsMyProfile == null){
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().
                getValue(fc.getELContext(), null, "userBean");
        thisIsMyProfile = current.equals(ub.getLoggedInUser());
        //}
        return thisIsMyProfile;
    }

    public int getActiveStaff() {
        activeStaff = 0;
        if (!current.getPersonstaffstatusList().isEmpty()) {
            for (Personstaffstatus pss : current.getPersonstaffstatusList()) {
                if (pss.getStaffstatus().getStatusname().equalsIgnoreCase("supervisor")) {
                    activeStaff = pss.getActive();
                }
            }
        }
        return activeStaff;
    }

    public void setActiveStaff(int activeStaff) {
        this.activeStaff = activeStaff;
    }
}
