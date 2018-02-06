/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.ctrl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.facade.FinalprojectFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@ViewScoped
@ManagedBean
public class ProjectListController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    AllocationService as;
    @EJB
    private UserService userService;
    List<Finalproject> list = new ArrayList<Finalproject>();
    
    /*@ManagedProperty(value="#{param.month}")
    private String month;*/
    
    public ProjectListController() {
        super(Finalproject.class);
    }
    
    
    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    public List<Finalproject> getList() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        Person currUser = null;
        List<Cohort> cohortsManager = getListOfCohort();
        if (principal != null) {
            currUser = userService.getPersonFacade().findSameUserName(principal.getName()).get(0) ;
        }
        list.clear();

        // If the current user is Admin then return the whole list
        if (currUser != null && currUser.isAdmin()) {
            list = getFacade().findAll();
        }

        // If it is student, then return those to which the student is allocated
        else if (currUser != null && currUser.isStudent()) {
            for (Finalproject fp : getFacade().findAll()) {
                if (fp.getStudent().contains(currUser)) {
                    list.add(fp);
                }
            }
        }
        // If it is staff, then check whether the user is supervisor or moderator to each of the project
        else if (currUser != null && currUser.isStaff()) {
            for (Finalproject fp : getFacade().findAll()) {
                List<Staffprojectrelationship> staffProjectList = as.getProjectStaffList(fp);
                for (Staffprojectrelationship sp : staffProjectList) {
                    if (sp.getPerson().getUsername().equals(currUser.getUsername())) {
                        list.add(fp);
                    }
                }
                // For each of the Cohort where the current user is Cohort coordinator s/he should can see all of the project in this cohort
                /*for (Cohort c : cohortsManager) {
                    if (fp.getUnitinstance().getCohortList() != null && fp.getUnitinstance().getCohortList().contains(c)) {
                        if (!list.contains(fp)) {
                            list.add(fp);
                        }
                    }

                }*/
            }
        }
        return list;
    }

    public List<Cohort> getListOfCohort() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        Person currUser = null;
        if (principal != null) {
            currUser = userService.getPerson(principal.getName());
        }
        List<Cohort> listCohort = as.getCohortFacade().findAll();
        List<Cohort> newListCohort = new ArrayList<Cohort>();
        for (Cohort c : listCohort) {
            if (c.getPersonList1().contains(currUser)) {
                newListCohort.add(c);
            }
        }
        return newListCohort;
    }
    
    public String goToForm() {
      /*if(month == null){
         return "projectList.xhtml";
      }
        switch (month) {
            case "3":
                return "projectList.xhtml";
            case "6":
                return "projectList.xhtml";
            case "12":
                return "projectList.xhtml";
            case "18":
                return "projectList.xhtml";
            case "ever":
                return "projectList.xhtml";
            default:
                return "projectList.xhtml";
        }*/
      return "projectList.xhtml";
    }
}
