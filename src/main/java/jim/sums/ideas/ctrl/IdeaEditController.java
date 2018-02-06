/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Ideastatus;
import jim.sums.common.db.Organisation;
import jim.sums.common.db.Person;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.Projectideahistory;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.AppPropertyFacade;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.common.facade.ProjectideaFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@RequestScoped
@ManagedBean(name = "iec")
public class IdeaEditController extends AbstractController<Projectidea, ProjectideaFacade> {

    @EJB
    private AppPropertyFacade propFacade;
	@EJB
	IdeaService is;
	@EJB
	AllocationService as;
	@EJB
	MailService ms;
        @EJB
        private IdeastatusFacade isf;


	String email = null;
	Boolean validKey = null;
	Organisation organisation = null;
	String confirmPassword = null;
	Person user;
	Projectidea currentidea;
        String changes;
        String reason;

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

	public IdeaEditController() {
		super(Projectidea.class);
	}

	public IdeaEditController(Class<Projectidea> entityClass) {
		super(Projectidea.class);
	}

	@Override
	public ProjectideaFacade getFacade() {
		return is.getPiFacade();
	}

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");

		if (ub.getTempidea() == null) {
			ub.setTempidea(currentidea);
		}
		setCurrentidea(ub.getTempidea());
	}

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

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

	public String doEdit() throws MessagingException, BusinessException {
		// TODO => Business
                FacesContext fc = FacesContext.getCurrentInstance();
		UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
        /*   Sent mail doesn't works
                ms.postMail(new String[]{currentidea.getOwneridea().getEmail()},
                        "Hi,", "One of your ideas on SUMS have been modified by the user "+ub.getLoggedInUser().getUsername()+" :\n"
                        + " Idea name :"+currentidea.getIdeatitle());
        */
                Projectideahistory pih = new Projectideahistory(ub.getUser(),currentidea.getIdprojectidea(),currentidea.getProjectideahistoryList().size()+1);
                pih.setPerson(ub.getLoggedInUser());
                pih.setProjectidea1(currentidea);
                pih.setChangedate(new Date());
                pih.setChange(changes);
                pih.setChangereason(reason);

                ub.getLoggedInUser().getProjectideahistoryList().add(pih);
                currentidea.getProjectideahistoryList().add(pih);

                if(!(ub.getLoggedInUser().isStaff()) && !(ub.getLoggedInUser().isAdmin())){
                    currentidea.setIdeastatus(isf.findSameName("Provisional"));
                }
                is.editProjectidea(currentidea);
		return "backDetails";
	}


	public String selectIdea(UnitInstance ui){
		FacesContext fc = FacesContext.getCurrentInstance();
		UserBean ub = (UserBean) fc.getApplication().getELResolver().
				getValue(fc.getELContext(), null, "userBean");
		try{
			as.selectIdea(currentidea, ub.getLoggedInUser(),ui);
			addInfo("Idea added to the shortlist.");
		}catch(BusinessException ex){
			addError(ex.getMessage());
		}
		return null;
	}

        public String descconvert(){

            return convertToHTML(currentidea.getDescription());
        }

        public String convertToHTML(String toconvert){
            return toconvert.replace("\n", "<br />");
        }
}
