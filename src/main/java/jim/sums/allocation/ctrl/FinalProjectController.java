/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.ctrl;

import static java.lang.System.in;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.Person;
import jim.sums.common.db.Staffprojectrelationship;
import jim.sums.common.db.StaffprojectrelationshipPK;
import jim.sums.common.db.Staffstatus;
import jim.sums.common.facade.FinalprojectFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@SessionScoped
@ManagedBean(name = "fpc")
public class FinalProjectController extends AbstractController<Finalproject, FinalprojectFacade> {

    @EJB
    private UserService userService;
    @EJB
    AllocationService as;
    private String newModThirdMarker;
    private String newStaffStatus;

    public FinalProjectController() {
        super(Finalproject.class);
    }

    @Override
    public FinalprojectFacade getFacade() {
        return as.getFinalprojectFacade();
    }

    public boolean getAddModeratorThirdMarker() {
        Person currPerson = userObject(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        //If it is administrator, he can do all
        if(currPerson != null && currPerson.isAdmin()) {
            return true;
        }
        //Only Supervisors could add moderator
        if (isSupervisorOfCurrentFinalProject()) {
            return true;
        }
        //Check whether the current user is CohortManager
        if (isCohortCoordinatorOfFinalProject()) {
            return true;
        }

        return false;
    }

    public String deleteProjectStaff(Staffprojectrelationship sp) {
        Person currPerson = userObject(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        if(currPerson.isAdmin()) {
            as.getSprf().remove(sp);
            current.getStaffprojectrelationshipList().remove(sp);
            as.getFinalprojectFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully removed the record."));

            return "";
        }

        if (isCohortCoordinatorOfFinalProject()) {
            if (sp != null && (sp.getStaffstatus().getStatusname().equalsIgnoreCase("Moderator") || sp.getStaffstatus().getStatusname().equalsIgnoreCase("Third Marker"))) {
                as.getSprf().remove(sp);
                current.getStaffprojectrelationshipList().remove(sp);
                as.getFinalprojectFacade().edit(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully removed the record."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You could not remove supervisor records."));
            }
            return "";
        }

         // Supervisors could only delete moderator
        if (isSupervisorOfCurrentFinalProject()) {
            if (sp != null && (sp.getStaffstatus().getStatusname().equalsIgnoreCase("Moderator"))) {
                as.getSprf().remove(sp);
                current.getStaffprojectrelationshipList().remove(sp);
                as.getFinalprojectFacade().edit(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully removed the record."));
            } else {
               if (sp != null && (sp.getStaffstatus().getStatusname().equalsIgnoreCase("Third Marker"))){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You could not remove third Marker records. Only Cohort Manager can."));
               }
            }
        }

        return "";
    }

    public String getNewModThirdMarker() {
        return newModThirdMarker;
    }

    public void setNewModThirdMarker(String newModThirdMarker) {
        this.newModThirdMarker = newModThirdMarker;
    }

    public String getNewStaffStatus() {
        return newStaffStatus;
    }

    public void setNewStaffStatus(String newStaffStatus) {
        this.newStaffStatus = newStaffStatus;
    }

    public Person userObject(String userName) {
        if (userName != null) {
            return userService.getPerson(userName);
        }
        return null;
    }

    public List<Staffprojectrelationship> getStaffList(Finalproject project) {
        return project.getStaffprojectrelationshipList();
    }

    public List<Person> getStudentList(Finalproject project) {
        return project.getStudent();
    }

    public Person getProjectSupervisor(Finalproject project) {
        return as.getProjectSupervisor(project);
    }

    public int getFinalMark() {
        current = as.getFinalprojectFacade().find(current.getIdproject());
        return current.getFinalMark();
    }

    public void addModeratorThirdMarker(ActionEvent e) {
        if (getNewModThirdMarker() != null && getNewStaffStatus() != null) {

            Staffprojectrelationship newStaffProjectRow = new Staffprojectrelationship();
            List<Staffprojectrelationship> staffProjectList = current.getStaffprojectrelationshipList();
            for (Staffprojectrelationship sp : staffProjectList) {
                if (sp.getPerson().getUsername().equals(getNewModThirdMarker())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The user is already in the list!"));
                    return;
                }
                if (sp.getStaffstatus().getId() == Integer.parseInt(getNewStaffStatus())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is already an user with the same role for this project!"));
                    return;
                }
            }
            newStaffProjectRow.setFinalproject(current);
            newStaffProjectRow.setPerson(as.getPersonFacade().find(getNewModThirdMarker()));
            newStaffProjectRow.setStaffstatus(as.getSsf().find(Long.parseLong(getNewStaffStatus())));
            staffProjectList.add(newStaffProjectRow);
            as.getSprf().edit(newStaffProjectRow);
            current.setStaffprojectrelationshipList(staffProjectList);
            as.getFinalprojectFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully added the record."));
        }
    }

    public javax.faces.model.SelectItem[] getStaffCode() {
        SelectItem[] options = null;
        List<Person> owners = as.getSupervisorList();
        if (owners != null && owners.size() > 0) {
            int i = 0;
            options = new SelectItem[owners.size()];
            for (Person dc : owners) {
                options[i++] = new SelectItem(dc.getUsername(), dc.getForename() + " " + dc.getSurname());
            }
        }
        return options;
    }

    public javax.faces.model.SelectItem[] getStaffStatusList() {
        SelectItem[] options = null;
        List<Staffstatus> statuses = as.getSsf().findAll();
        if (statuses != null && statuses.size() > 0) {
            int i = 0;
            Person currPerson = userObject(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

            // Check if it's the administrator then he could add all that he want
            if(currPerson.isAdmin()) {
                options = new SelectItem[4];
                for(Staffstatus ss : statuses) {
                    options[i++] = new SelectItem(ss.getId().toString(), ss.getStatusname());
                }
                return options;
            }

            // Supervisors could only add moderator
            if (isSupervisorOfCurrentFinalProject()) {
                options = new SelectItem[1];
                for (Staffstatus dc : statuses) {
                    if (dc.getStatusname().equalsIgnoreCase("Moderator")) {
                        options[i++] = new SelectItem(dc.getId().toString(), dc.getStatusname());
                    }
                }
                return options;
            }

            //Check whether the current user is CohortManager, then he/she could add Supervisor, Moderator and Third User
            if (isCohortCoordinatorOfFinalProject()) {
                options = new SelectItem[3];
                for (Staffstatus dc : statuses) {
                    if (dc.getStatusname().equalsIgnoreCase("Moderator") || dc.getStatusname().equalsIgnoreCase("Third Marker") || dc.getStatusname().equalsIgnoreCase("Supervisor")) {
                        options[i++] = new SelectItem(dc.getId().toString(), dc.getStatusname());
                    }
                }
                return options;
            }

        }

        return options;
    }

    //Check whether the current user is CohortManager for least of repetition code
    public boolean isCohortCoordinatorOfFinalProject() {
        List<Staffprojectrelationship> staffProjectList = getStaffList(current);
        Person currPerson = userObject(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        List<Cohort> listCohorts = getCurrent().getUnitinstance().getCohortList();
        for (Cohort c : listCohorts) {
            List<Person> cohortManagerList = c.getPersonList1();
            for (Person p : cohortManagerList) {
                if (p.getUsername().equals(currPerson.getUsername())) {
                    for (Staffprojectrelationship sp : staffProjectList) {
                        Long idss = Long.parseLong(String.valueOf(sp.getStaffstatus().getId()));
                        String statusName = as.getSsf().find(idss).getStatusname();
                        if (statusName.equalsIgnoreCase("Coordinator") && currPerson != null && currPerson.getUsername().equals(sp.getPerson().getUsername())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //Check whether the current user is Supervisor for the current final project
    public boolean isSupervisorOfCurrentFinalProject() {
        List<Staffprojectrelationship> staffProjectList = getStaffList(current);
        Person currPerson = userObject(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        for (Staffprojectrelationship sp : staffProjectList) {
            Long idss = Long.parseLong(String.valueOf(sp.getStaffstatus().getId()));
            String statusName = as.getSsf().find(idss).getStatusname();
            if (statusName.equalsIgnoreCase("Supervisor") && currPerson != null && currPerson.getUsername().equals(sp.getPerson().getUsername())) {
                return true;
            }
        }
        return false;
    }
    
    public Person personSupervisor() {
        List<Staffprojectrelationship> list = getStaffList(current);
        for(Staffprojectrelationship spr : list) {
            if (spr.getStaffstatus().getStatusname().equals("supervisor")) {
               return spr.getPerson();
            }
        }
        return null;
    }
    
    public Person personCoordinator() {
        List<Staffprojectrelationship> list = getStaffList(current);
        for(Staffprojectrelationship spr : list) {
            if (spr.getStaffstatus().getStatusname().equals("coordinator")) {
               return spr.getPerson();
            }
        }
        return null;
    }
}
