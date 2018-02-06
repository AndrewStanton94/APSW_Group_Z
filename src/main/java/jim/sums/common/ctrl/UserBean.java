package jim.sums.common.ctrl;

import java.security.Principal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import jim.sums.common.bus.UserService;
import jim.sums.common.db.*;
import jim.sums.common.facade.PersonFacade;
import jim.sums.register.bus.AdminService;

/**
 * @author Nicolas Dossou-Gbete
 */

@ManagedBean
@SessionScoped
public class UserBean extends MessageController {

    @EJB
    UserService us;
    @EJB
    private PersonFacade personFacade;
    @EJB
    private AdminService adminService;
    private String username = null;
    public Projectidea tempidea = null;
    public Finalproject tempProj = null;
    private Unit tempUnit = null;
    private UnitInstance tempUnitInstance = null;
    public Person tempPerson = null;
    private SubmitConfiguration tempSubmitConf = null;

    public String getUser() {
        if (username == null) {
            // create a default user
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                username = principal.getName(); // j_username.
                us.updateLastLogin(username);
            }
        }
        return username;
    }

    public Person getLoggedInUser() {
        return personFacade.find(username);
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public boolean isAdmin() {
        return us.isAdmin(username);
    }

    public boolean isStaff() {
        return us.isStaff(username);
    }

    public boolean isStudent() {
        return us.isStudent(username);
    }

    public boolean isExternal() {
        return us.isExternal(username);
    }

    public boolean isSuspended() {
        if (adminService.isSuspended(getLoggedInUser())) {
            logout();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sorry, but you are not allowed to login because you are suspended, check your emails"));
            return true;
        }
        return false;
    }

    public boolean canEditIdeas(Projectidea currentidea) {
        if ((isStudent() || isExternal()) && (!currentidea.getOwneridea().equals(getLoggedInUser()))) {
            return false;
        } else {
            return true;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();

        final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        username = null;
        request.getSession(false).invalidate();
        return "toIndex";
    }

    public boolean hasToSelectIdeas() {
        return true;
    }

    public List<UnitInstance> unitList() {
        return us.unitInstancesSelectionOpen(username);
    }

    public boolean isEngagedSupervisor() {
        return us.isEngagedSupervisor(username) || us.isAdmin(username);
    }

    public boolean hasToSelectSupervisors() {
        Person p = getLoggedInUser();
        if (p.isStudent()) {
            return false; // is student
        }		// Put many buttons if the student has more units.
        // Test selection dates.
        return true;
    }

    /*Suggestion :
     *
     * We could create a variable 'Object tempObject' with its getter/setter.
     * With the cast method, it could replace all the tempXXXX variables.
     * Indeed, the tempXXX variables all have the same goal
     * (transmitting information from a page to another)
     * and they are never used at the same time.
     * This could help to reduce the weight of UserBean.
     */

    public void setTempidea(Projectidea currentidea) {
        this.tempidea = currentidea;
    }

    public Projectidea getTempidea() {
        return tempidea;
    }

    public Finalproject getTempProj() {
        return tempProj;
    }

    public void setTempProj(Finalproject tempProj) {
        this.tempProj = tempProj;
    }

    public Unit getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(Unit tempUnit) {
        this.tempUnit = tempUnit;
    }

    public Person getTempPerson() {
        return tempPerson;
    }

    public void setTempPerson(Person tempPerson) {
        this.tempPerson = tempPerson;
    }

    public UnitInstance getTempUnitInstance() {
        return tempUnitInstance;
    }

    public void setTempUnitInstance(UnitInstance tempUnitInstance) {
        this.tempUnitInstance = tempUnitInstance;
    }

    public SubmitConfiguration getTempSubmitConf() {
        return tempSubmitConf;
    }

    public void setTempSubmitConf(SubmitConfiguration tempSubmitConf) {
        this.tempSubmitConf = tempSubmitConf;
    }

    public Cohort getCohort() {
        if (us.cohortSelectionOpen(username).isEmpty()) {
            return null;
        }
        return us.cohortSelectionOpen(username).get(0);
    }

    /**
     * The css has to be changed when an user is logged. the difference
     * consists in one line, this way seems to be the easiest.
     * @return Margin-left required to not overlap the menu
     */
    public String alterTemplate() {
        if (isLoggedIn()) {
            return "margin-left: 180px;";
        }
        return null;
    }
}
