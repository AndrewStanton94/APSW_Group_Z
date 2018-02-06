/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.marking.ctrl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.CategoryMark;
import jim.sums.common.db.CategoryMarksOption;
import jim.sums.common.db.TemplateCriteria;
import jim.sums.common.db.CategoryOptions;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.TemplateCategory;
import jim.sums.common.db.TemplateMarkForm;
import jim.sums.common.db.MarkerMark;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.facade.TemplateMarkFormFacade;
import jim.sums.marking.bus.MarkingService;

/**
 *
 * @author Nicolas Dossou-Gbété
 */


@SessionScoped
@ManagedBean(name = "mfc")
public class MarkFormsController extends AbstractController<TemplateMarkForm, TemplateMarkFormFacade> {

    @EJB
    private UserService userService;
    @EJB
    MarkingService ms;
    @EJB
    AllocationService as;
    private Finalproject project;
    private List<TemplateCategory> formData;
    private MarkerMark currentMark;
    private String finalMarkComment;

    public AllocationService getAs() {
        return as;
    }

    public void setAs(AllocationService as) {
        this.as = as;
    }

    public String getFinalMarkComment() {
        return finalMarkComment;
    }

    public void setFinalMarkComment(String finalMarkComment) {
        this.finalMarkComment = finalMarkComment;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    private List<CategoryMark> currentCatMarks = new ArrayList<CategoryMark>();
    private List<CategoryMarksOption> marksOptionList = new ArrayList<CategoryMarksOption>();

    //Used to retrieve tooltip for each checkbox in the marking form
    public String getOptionTooltip(CategoryMarksOption cmo, CategoryMark cm) {
        if (cmo != null && cm != null) {
            List<TemplateCriteria> catOptionCriteriaList = cmo.getOptionId().getCategoryOptionCriteriaList();
            for (TemplateCriteria coc : catOptionCriteriaList) {
                if (coc.getMarkCategory().getCatid().equals(cm.getCatId().getCatid())) {
                    return (coc.getNegativeCriteria() == null) ? coc.getPositiveCriteria() : coc.getNegativeCriteria();
                }
            }
        }
        return "";
    }

    //Checking permissions of the authenticated user
    public boolean allowCreateMarks(Finalproject fp) {
        Person currUser = null;
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            currUser = userService.getPerson(principal.getName());
        }
        if (currUser != null) {
            List<Staffprojectrelationship> staffProjectList = as.getProjectStaffList(fp);
            for (Staffprojectrelationship sp : staffProjectList) {
                //Could the current user create marks for this project?
                if (currUser.getUsername().equals(sp.getPerson().getUsername())) {
                    //Check how many marks are already created from this user. Only one mark is permitted for specific user.
                    List<MarkerMark> projectMarks = getMarkingList(fp);
                    for (MarkerMark m : projectMarks) {
                        if (currUser.getUsername().equals(m.getMarkerName())) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    //This method is used to return MarkOptions for particular CategoryMark. The method is used in the table of Mark page
    public List<CategoryMarksOption> markOptionListForCategoryMark(CategoryMark cm) {
        List<CategoryMarksOption> temp = new ArrayList<CategoryMarksOption>();
        if (cm != null) {
            for (CategoryMarksOption cmo : marksOptionList) {
                if (cmo.getCatId().getCatid().equals(cm.getCatId().getCatid())) {
                    temp.add(cmo);
                }
            }
        } else {
            //I dont know but sometimes cm is null. This leads to broke the creation of the columns. This piece of code fix that one.
            int k = 0;
            for (CategoryMarksOption cmo : marksOptionList) {
                temp.add(cmo);
                if (k == 9) {
                    break;
                }
                k++;
            }
        }
        Collections.sort(temp, new Comparator<CategoryMarksOption>() {

            @Override
            public int compare(CategoryMarksOption o1, CategoryMarksOption o2) {
                return o1.getOptionId().getOptIndex().compareTo(o2.getOptionId().getOptIndex());

            }
        });
        return temp;
    }

    //This method is used when we go to see or create new marks. Depending on the parameters passed from projectDetails page this method create or open mark entity.
    public String createEditMarks() {
        finalMarkComment = null;
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("project") != null) {
            project = ms.getFinalprojectFacade().find(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("project")));

            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("markID") != null) {
                //TODO get currentMArks
                //getting the last mark form for this unit. TODO order by date. It is not normal to use older marking form...
                current = (TemplateMarkForm) project.getUnitinstance().getUnit().getUnitMarkFormsList().get(0).getFormId();
                Object markID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("markID");
                MarkerMark curr = ms.getMarksFacade().find(Integer.parseInt(markID.toString()));
                setCurrentMark(curr);
                currentCatMarks = currentMark.getCategoryMarksList();
                Collections.sort(currentCatMarks, new Comparator<CategoryMark>() {

                    @Override
                    public int compare(CategoryMark o1, CategoryMark o2) {
                        return o1.getCatId().getMarkFormCategoriesList().get(0).getCatindex().compareTo(o2.getCatId().getMarkFormCategoriesList().get(0).getCatindex());
                    }
                });
                marksOptionList = currentMark.getCategoryMarksOptionList();

            } else {
                MarkerMark m = new MarkerMark();

                m.setProjectId(project);
                m.setMarkStatus("New");

                Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
                if (principal != null) {
                    m.setMarkerName(principal.getName());
                }

                ms.getMarksFacade().create(m);
                setCurrentMark(m);
                currentCatMarks = currentMark.getCategoryMarksList();
                Collections.sort(currentCatMarks, new Comparator<CategoryMark>() {

                    @Override
                    public int compare(CategoryMark o1, CategoryMark o2) {
                        return o1.getCatId().getMarkFormCategoriesList().get(0).getCatindex().compareTo(o2.getCatId().getMarkFormCategoriesList().get(0).getCatindex());
                    }
                });

                marksOptionList = currentMark.getCategoryMarksOptionList();
                for (TemplateCategory mfc : getFormData()) {
                    CategoryMark cm = new CategoryMark();
                    cm.setCatId(mfc.getCatid());
                    cm.setMarkerId(m);
                    ms.getCategoryMarksFacade().create(cm);

                    currentCatMarks.add(cm);
                    for (CategoryOptions co : getOptions(mfc)) {
                        CategoryMarksOption curr = new CategoryMarksOption();
                        curr.setCatId(mfc.getCatid());
                        curr.setMarkId(m);
                        curr.setOptionId(co);

                        ms.getCategoryMarksOptionFacade().create(curr);
                        marksOptionList.add(curr);
                    }

                }
                m.setCategoryMarksList(currentCatMarks);
                m.setCategoryMarksOptionList(marksOptionList);
                ms.getMarksFacade().edit(m);

            }
        }
        return "toMarkingForm";

    }
    //This method is used in the Mark table to get the new CategoryMark each time when any of the checkboxes is clicked.

    public int marks(CategoryMark cm) {
        if (cm != null) {
            currentCatMarks = currentMark.getCategoryMarksList();
            for (CategoryMark co : currentCatMarks) {
                if (co.getCatMarkId().equals(cm.getCatMarkId())) {
                    return co.getMark();
                }

            }
        }
        return 0;
    }
    //Return Person object for the marker attribute of any Mark entity.

    public Person getUserObject() {

        return userService.getPerson(currentMark.getMarkerName());

    }

    public Person getCurrentUser() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            return userService.getPerson(principal.getName());
        }
        return null;

    }

    public boolean getDisabledFields() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            return !(currentMark.getMarkerName().equals(principal.getName()));
        }
        return false;
    }

    //The method is used to persist the changes of any mark entity.
    public void saveMarks(ActionEvent e) {
        currentMark.setCategoryMarksList(currentCatMarks);
        currentMark.setCategoryMarksOptionList(marksOptionList);

        ms.getMarksFacade().edit(currentMark);
        for (CategoryMarksOption cmo : marksOptionList) {
            ms.getCategoryMarksOptionFacade().edit(cmo);
        }

    }

    public void submitFinalMark(ActionEvent e) {
        //Save before processing
        saveMarks(e);
        //Set every marks for this project to provisonal
        for (int i = 0; i < getProject().getMarksList().size(); i++) {
            getProject().getMarksList().get(i).setMarkStatus("Provisional");
        }
        currentMark.setMarkStatus("Provisional");
        ms.getMarksFacade().edit(currentMark);
        //Set the final mark to the current mark
        getProject().setFinalMarkStatus("Provisional");
        getProject().setFinalMarkComment(finalMarkComment);
        getProject().setFinalMark(currentMark.getMark());
        ms.getFinalprojectFacade().edit(project);
    }

    public void calculateFinalMark(ActionEvent e) {

        if (currentMark.getMarkStatus().equals("Need reconcile")) {
            currentMark.setMarkStatus("Reconciled");
            ms.getMarksFacade().edit(currentMark);
        }
        if (currentMark.getMarkStatus().equals("New")) {
            currentMark.setMarkStatus("Submitted");
            ms.getMarksFacade().edit(currentMark);
        }
        saveMarks(e);
        List<MarkerMark> markList = ms.getMarksFacade().findByFinalProject(project);
        int tmpFinalResult = 0;
        int firstMark = 0;
        int secondMark = 0;
        for (MarkerMark m : markList) {
            tmpFinalResult = tmpFinalResult + m.getMark();

        }
        tmpFinalResult = tmpFinalResult / markList.size();
        if (markList.size() == 1) {
            getProject().setFinalMarkStatus("Only 1 mark is submitted");
        }

        //If we have two marks
        if (markList.size() == 2) {
            firstMark = markList.get(0).getMark();
            secondMark = markList.get(1).getMark();
            //if the difference is more than 10 percent
            //if one of the marker's mark is lower than 40 and the other is higher or equal than 40 percent
            project = ms.getFinalprojectFacade().find(project.getIdproject());
            if ((Math.abs(firstMark - secondMark) > 10) || (firstMark < 40 && secondMark >= 40) || (firstMark >= 40 && secondMark < 40)) {

                getProject().setFinalMarkStatus("Need reconcile");
                if (!getProject().getMarksList().get(0).getMarkStatus().equals("Reconciled")) {
                    getProject().getMarksList().get(0).setMarkStatus("Need reconcile");
                }
                if (!getProject().getMarksList().get(1).getMarkStatus().equals("Reconciled")) {
                    getProject().getMarksList().get(1).setMarkStatus("Need reconcile");
                }
                //if the markers already reconciled then the status is 'third marker needed'
                if (getProject().getFinalMarkStatus().equals("Need reconcile") && getProject().getMarksList().get(0).getMarkStatus().equals("Reconciled")
                        && getProject().getMarksList().get(1).getMarkStatus().equals("Reconciled")) {
                    getProject().setFinalMarkStatus("Third marker will be allocated");
                }
            } else {
                getProject().setFinalMarkStatus("Provisonal");
            }
        }
        //if we have three marks, then we know that a third marker is alloacated. In that situation get only the third mark
        if (markList.size() == 3) {
            List<Staffprojectrelationship> staffProjectList = as.getProjectStaffList(project);
            Person thirdMarker = null;

            for (Staffprojectrelationship sp : staffProjectList) {

                if (sp.getStaffstatus().getStatusname().equals("Third Marker")) {
                    thirdMarker = sp.getPerson();
                    break;
                }
            }
            for (MarkerMark m : getProject().getMarksList()) {
                if (m.getMarkerName().equals(thirdMarker.getUsername())) {
                    tmpFinalResult = m.getMark();
                    getProject().setFinalMarkStatus("Provisonal");
                    break;
                }
            }

        }

        getProject().setFinalMark(tmpFinalResult);
        ms.getFinalprojectFacade().edit(project);
        currentMark = ms.getMarksFacade().find(currentMark.getMarkId());

    }

    ///The method is used to get the TemplateCategory for pariticular MarkForm for the Unit where the Project is located.
    ///We use the first MarkForm. However, we should put functionality to activate specific MarkForm for Unit and Academic Year. Otherwise, we need to
    // create functionality for the user to choose which MarkForm wants to fill!!!!!!!!
    public List<TemplateCategory> getFormData() {
        current = (TemplateMarkForm) project.getUnitinstance().getUnit().getUnitMarkFormsList().get(0).getFormId();
        if (formData == null) {
            formData = ms.getFormRows(current, project);

        }
        return formData;
    }
    //The method is used to get CategoryOptions for particular TemplateCategory. Depending on that we create several CategoryMarksOption entities to
    // store the checkbox selections.

    public List<CategoryOptions> getOptions(TemplateCategory mfc) {

        if (mfc == null) {
            mfc = getFormData().get(0);
        }
        return ms.getOptionList(mfc.getCatid().getCogId());
    }

    public List<CategoryMarksOption> getMarksOptionList() {
        return marksOptionList;
    }

    public void setMarksOptionList(List<CategoryMarksOption> marksOptionList) {
        this.marksOptionList = marksOptionList;
    }

    public List<CategoryMark> getCurrentCatMarks() {
        return currentCatMarks;
    }

    public void setCurrentCatMarks(List<CategoryMark> currentCatMarks) {
        this.currentCatMarks = currentCatMarks;
    }

    public MarkerMark getCurrentMark() {
        return currentMark;
    }

    public List<MarkerMark> getMarkingList(Finalproject f) {
        for (Staffprojectrelationship spr : f.getStaffprojectrelationshipList()) {
            if (spr.getPerson().equals(getCurrentUser())) {
                if (spr.getStaffstatus().getStatusname().equalsIgnoreCase("coordinator")) {
                    return ms.getMarksFacade().findByFinalProject(f);
                }
            }
        }

        //If the current user is related to the project as a moderator or supervisor then s/he should not see marks from the other relative people of this project
        Person currUser = null;
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            currUser = userService.getPerson(principal.getName());
        }
        if (currUser != null) {
            List<Staffprojectrelationship> staffProjectList = as.getProjectStaffList(f);
            for (Staffprojectrelationship sp : staffProjectList) {
                //Could the current user create marks for this project?
                if (currUser.getUsername().equals(sp.getPerson().getUsername())) {
                    //Check how many marks are already created from this user. Only one mark is permitted for specific user.
                    List<MarkerMark> projectMarks = new ArrayList<MarkerMark>();
                    for (MarkerMark m : ms.getMarksFacade().findByFinalProject(f)) {
                        if (currUser.getUsername().equals(m.getMarkerName())) {
                            projectMarks.add(m);
                        }
                    }
                    return projectMarks;
                }
            }

        }

        return ms.getMarksFacade().findByFinalProject(f);
    }

    public void setCurrentMark(MarkerMark currentMark) {
        this.currentMark = currentMark;
    }

    public MarkingService getMs() {
        return ms;
    }

    public void setMs(MarkingService ms) {
        this.ms = ms;
    }

    public MarkFormsController() {
        super(TemplateMarkForm.class);
    }

    public int getAdj() {
        return currentMark.getAdjustmentApplied() == null ? 0 : currentMark.getAdjustmentApplied().intValue();
    }

    public void setAdj(int number2) {
        currentMark.setAdjustmentApplied(number2);
    }

    @Override
    public TemplateMarkFormFacade getFacade() {
        return ms.getMarkFormsFacade();
    }

    public Finalproject getProject() {
        return project;
    }

    public void setProject(Finalproject project) {
        this.project = project;
    }

    public boolean isCurrentSet() {
        return current != null;
    }

    public String getMarkSupervisorByProjectAndMarker(Person supervisor, Person coordinator, Finalproject fp, UserBean userBean) {
        Person user = userBean.getLoggedInUser();
        String statusSupervisor = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, supervisor);
        String statusCoordinator = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, coordinator);
        if (statusSupervisor.equals(statusCoordinator) && statusSupervisor.equals("Done") || fp.getSupervisor().getUsername().equals(user.getUsername()) || user.isAdmin()) {
            return ms.getMarksFacade().getMarkFindByProjectAndMarker(fp, supervisor);
        } else {
            return "";
        }
    }

    public String getMarkDateByProjectAndMarker(Person supervisor, Person coordinator, Finalproject fp, UserBean userBean) {
        Person user = userBean.getLoggedInUser();
        String statusSupervisor = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, supervisor);
        String statusCoordinator = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, coordinator);
        if (statusSupervisor.equals(statusCoordinator) && statusSupervisor.equals("Done") || fp.getSupervisor().getUsername().equals(user.getUsername()) || user.isAdmin()) {
            return ms.getMarksFacade().getMarkFindByProjectAndMarker(fp, supervisor);
        } else {
            return "";
        }
    }

    public String getStatusSupervisorByProjectAndMarker(Person supervisor, Person coordinator, Finalproject fp, UserBean userBean) {
        Person user = userBean.getLoggedInUser();
        String statusSupervisor = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, supervisor);
        String statusCoordinator = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, coordinator);
        if (statusSupervisor.equals(statusCoordinator) && statusSupervisor.equals("Done") || fp.getSupervisor().getUsername().equals(user.getUsername()) || user.isAdmin()) {
            return statusSupervisor;
        } else {
            return "Unavailable";
        }
    }

    public String getMarkCoordinatorByProjectAndMarker(Person supervisor, Person coordinator, Finalproject fp, UserBean userBean) {
        Person user = userBean.getLoggedInUser();
        String statusSupervisor = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, supervisor);
        String statusCoordinator = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, coordinator);
        if (statusSupervisor.equals(statusCoordinator) && statusSupervisor.equals("Done") || fp.getCoordinator().getUsername().equals(user.getUsername()) || user.isAdmin()) {
            return ms.getMarksFacade().getMarkFindByProjectAndMarker(fp, coordinator);
        } else {
            return "";
        }
    }

    public String getStatusCoordinatorByProjectAndMarker(Person supervisor, Person coordinator, Finalproject fp, UserBean userBean) {
        Person user = userBean.getLoggedInUser();
        String statusSupervisor = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, supervisor);
        String statusCoordinator = ms.getMarksFacade().getStatusFindByProjectAndMarker(fp, coordinator);
        if (statusSupervisor.equals(statusCoordinator) && statusSupervisor.equals("Done") || fp.getCoordinator().getUsername().equals(user.getUsername()) || user.isAdmin()) {
            return statusCoordinator;
        } else {
            return "Unavailable";
        }
    }

}
