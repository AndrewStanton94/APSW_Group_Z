/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.Organisation;
import jim.sums.common.facade.OrganisationFacade;
import jim.sums.register.bus.RegisterService;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean
public class OrganisationController extends AbstractController<Organisation, OrganisationFacade> {

	@EJB
	private RegisterService registerService;
	
	public OrganisationController() {
		super(Organisation.class);
	}

	@Override
	public OrganisationFacade getFacade() {
		return registerService.getOrgaFacade();
	}
	
	@PostConstruct
	public void init(){
		if(current == null){
			String username = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("username");
			FacesContext fc = FacesContext.getCurrentInstance();
				UserBean ub = (UserBean) fc.getApplication().getELResolver().
						getValue(fc.getELContext(), null, "userBean");
			if (username != null) { // If we want to see another user's profile
				current = registerService.getPersFacade().find(username).getOrganisation();
			} else { // Currently logged user's profile*/
				current = ub.getLoggedInUser().getOrganisation();
			}
		}
	}
}
