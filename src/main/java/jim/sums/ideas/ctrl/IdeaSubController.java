/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.ApprovedIdeaStaff;
import jim.sums.common.db.CourseLevel;
import jim.sums.common.db.Ideastatus;
import jim.sums.common.db.ProjectKind;
import jim.sums.common.db.Organisation;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import jim.sums.common.facade.ApprovedIdeaStaffFacade;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.ProjectideaFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@SessionScoped
//TODO - This probably should not be session scoped in the long run.
@ManagedBean(name = "isc")
public class IdeaSubController extends AbstractController<Projectidea, ProjectideaFacade> {

    @EJB
    private IdeaService is;
    @EJB
    private UserService us;
    @EJB
    private PersonFacade pf;
    @EJB
    private IdeastatusFacade isf;
    @EJB
    private ApprovedIdeaStaffFacade aisf;
    //Properties
    String email = null;
    Boolean validKey = null;
    Organisation organisation = null;
    String confirmPassword = null;
    Person user;
    Projectidea currentidea;
    public CourseLevel[] selectedCourseLevel;
    public ProjectKind[] selectedKind;
    List<CourseLevel> courseLevelList;
    List<ProjectKind> kindList;
    List<Person> userList;
    String supervisorUsername;
    Ideastatus staffFirstIdeaStatus;

    public IdeaSubController() {
        super(Projectidea.class);
    }

    public List<CourseLevel> getCourseLevelList() {
        courseLevelList = is.getAllCourseLevels();
        return courseLevelList;
    }

//    public void setCourseLevelList(ArrayList<CourseLevel> courseLevelList) {
//        this.courseLevelList = courseLevelList;
//    }
    public List<ProjectKind> getKindList() {
        kindList = is.getKindFacade().findAll();
        return kindList;
    }

    public void setKindList(ArrayList<ProjectKind> kindList) {
        this.kindList = kindList;
    }

    public List<Person> getUserList() {
        userList = us.getAllCurrentStaff();
        return userList;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getSupervisorUsername() {
        return supervisorUsername;
    }

    public void setSupervisorUsername(String supervisorUsername) {
        this.supervisorUsername = supervisorUsername;
    }

    public Ideastatus getStaffFirstIdeaStatus() {
        return staffFirstIdeaStatus;
    }

    public void setStaffFirstIdeaStatus(Ideastatus staffFirstIdeaStatus) {
        this.staffFirstIdeaStatus = staffFirstIdeaStatus;
    }

    public void setUserList(List<Person> userList) {
        this.userList = userList;
    }

    public CourseLevel[] getSelectedCourseLevel() {
        return selectedCourseLevel;
    }

    public void setSelectedCourseLevel(CourseLevel[] courseLevel) {
        this.selectedCourseLevel = courseLevel;
    }

    public ProjectKind[] getSelectedKind() {
        return selectedKind;

    }

    public void setSelectedKind(ProjectKind[] selectedKind) {
        this.selectedKind = selectedKind;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IdeaSubController(Class<Projectidea> entityClass) {
        super(Projectidea.class);
    }

    public Person getCurrentUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        return ub.getLoggedInUser();
    }

    public String doAdd() {
        //TODO This is a controller calling persistence logic - it should go through a business operation
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        Map requestMap = fc.getExternalContext().getRequestParameterMap();
        Projectidea addedItem = getNewItem();

        addedItem.setOwneridea(ub.getLoggedInUser());

        addedItem.setGradeList(new ArrayList<CourseLevel>(Arrays.asList(selectedCourseLevel)));
        addedItem.setKindList(new ArrayList<ProjectKind>(Arrays.asList(selectedKind)));

        // When Students sent a new idea, we set this ideaStatus with "Provisional"
        if (ub.getLoggedInUser().isStaff() || ub.getLoggedInUser().isAdmin()) {
            addedItem.setIdeastatus(staffFirstIdeaStatus);
        } else {
            addedItem.setIdeastatus(isf.findSameName("Provisional"));
        }

        is.registerNewProjectidea(addedItem);

        for (int i = 0; i < selectedCourseLevel.length; i++) {
            selectedCourseLevel[i].getProjectideaList().add(addedItem);
            is.editGrade(selectedCourseLevel[i]);
        }
        for (int j = 0; j < selectedKind.length; j++) {
            selectedKind[j].getProjectideaList().add(addedItem);
            is.editKind(selectedKind[j]);
        }

        ApprovedIdeaStaff ais = new ApprovedIdeaStaff(addedItem, pf.findSameUserName(supervisorUsername).get(0));
        aisf.create(ais);

        return "toAllIdeas";
    }

    @Override
    public ProjectideaFacade getFacade() {
        return is.getPiFacade();
    }

    @Deprecated
    //Duplicate code
    public String getCurrentUserStatus() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");

        return ub.getLoggedInUser().getRoleNames();
    }

    public String toDetails() {
        currentidea = getCurrent();
        addInfo(getCurrent().toString());
        return "toDetails";
    }

    public Projectidea getCurrentidea() {
        return currentidea;
    }

    public void setCurrentidea(Projectidea currentidea) {
        this.currentidea = currentidea;
    }

    public String doEdit() {
        is.editProjectidea(currentidea);
        return "backDetails";
    }

    public String supervisorPage() {
        return "ideaApprovedIdeaPerson";
    }
//    public boolean isValidKey() {
//        if (validKey == null) {
//            addInfo("validKey");
//            String confirmKey = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
//            try {
//                rs.confirmRegistrationKey(confirmKey);
//                addInfo("ok");
//                validKey = true;
//            } catch (BusinessException ex) {
//                addError(ex.getMessage());
//                validKey = false;
//            }
//
//
//
//        }
//        return validKey;
//
//    }
//
//    public Organisation getOrganisation() {
//        if (organisation == null) {
//            organisation = new Organisation();
//        }
//        return organisation;
//    }
}
