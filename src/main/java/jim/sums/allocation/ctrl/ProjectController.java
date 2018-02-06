/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.ctrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Ideachosenby;
import jim.sums.common.db.Person;
import jim.sums.common.db.Personchosenby;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.PersonFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean
public class ProjectController extends AbstractController<Person, PersonFacade> {

	@EJB
	AllocationService as;

	public ProjectController() {
		super(Person.class);
	}

	@Override
	public PersonFacade getFacade() {
		return as.getPersonFacade();
	}

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
		current = ub.getLoggedInUser();
	}

	public DataModel<Ideachosenby> getChosenIdeas(){
		return getChosenIdeas(null);
	}
	
	public DataModel<Ideachosenby> getChosenIdeas(UnitInstance ui){
		List<Ideachosenby> l;
		if(ui != null){
			l = new ArrayList<Ideachosenby>();
			for(Ideachosenby icb: as.getChosenIdeas(current)){
				if(icb.getIdunitinstance().equals(ui)) l.add(icb);
			}
		}else{
			l = as.getChosenIdeas(current);
		}
		
		Collections.sort(l,new AllocationService.IdeaRankComparator());
		return new ListDataModel<Ideachosenby>(l);
	}
	
	public DataModel<Personchosenby> getChosenPersons(UnitInstance ui){
		List<Personchosenby> l;
		if(ui != null){
			l = new ArrayList<Personchosenby>();
			for(Personchosenby scb: as.getChosenPersons(current)){
				if(scb.getIdunitinstance().equals(ui)) l.add(scb);
			}
		}else{
			l = as.getChosenPersons(current);
		}

		Collections.sort(l, new AllocationService.SupervisorRankComparator());
		return new ListDataModel<Personchosenby>(l);
	}

	public String toPickIdeas(){
		//If idea list not full;
		return "toAllIdeas";
	}

	public String removeIdea(Ideachosenby idea){
		as.removeIdea(idea);
		addInfo("Idea removed. TODO:  confirm popup");
		return null;
	}

	public String rankIdeaUp(Ideachosenby idea){
		as.rankIdea(idea,+1);
		return null;
	}

	public String rankIdeaDown(Ideachosenby idea){
		as.rankIdea(idea,-1);
		return null;
	}
	
	public String removeSupervisor(Personchosenby sup){
		as.removeSupervisor(sup);
		addInfo("Supervisor removed. TODO:  confirm popup");
		return null;
	}

	public String rankSupervisorUp(Personchosenby sup){
		as.rankSupervisor(sup,+1);
		return null;
	}

	public String rankSupervisorDown(Personchosenby sup){
		as.rankSupervisor(sup,-1);
		return null;
	}
	
	public Cohort getCohort(UnitInstance ui){
		return as.getCohort(ui);
	}
	
	public List<Person> getSupervisorList(){
		return as.getSupervisorList();
	}
	
	public String selectSupervisor(UnitInstance ui, Person chosen){
		FacesContext fc = FacesContext.getCurrentInstance();
		UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
		try{
			as.selectPerson(ub.getLoggedInUser(),chosen,ui);
			addInfo("Supervisor added to the shortlist.");
		}catch(BusinessException ex){
			addError(ex.getMessage());
		}
		return null;
		
	}
	
	public List<Cohort> getSupervisorStudentChoices(String sup){
		return null;
	}
	
	public String styleAvailable(Projectidea pi){
		if(pi.getIdeastatus().getIdideastatus() != 2) return "color:red";
		return null;
	}
        
        public String goToAddUpdateProjectMark() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "markFormView1.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        
        public String goToEnterReconciliedMark() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "reconciliedMark.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        
        public String goToAskThirdMarker() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "askThirdMarker.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        
        public String goToAskAnotherMarker() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "askAnotherMarker.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        
        public String goToEnterProjectMark() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "enterProjectMark.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        public String goToChangeModerator() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "changeModerator.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        public String goToAuditTrail() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "auditTrail.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        public String goToFullProjectDetails() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "fullProjectDetails.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        public String goToSupervisorMark() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "supervisorMark.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        public String goToModeratorMark() {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
            if (ub != null && (ub.isAdmin() || ub.isStaff())) {
                // if it's a staff member test rights
                return "moderatorMark.xhtml";
            } else {
                return "403.xhtml";
            }
        }
        
}
