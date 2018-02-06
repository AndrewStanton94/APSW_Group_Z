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
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;
import jim.sums.register.bus.AdminService;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean(name="uic")
public class UserInfoController extends AbstractController<Person, PersonFacade> {

	@EJB
	AdminService as;
	@EJB
	UserService us;
	String[] password;
	String newGroup;

	public UserInfoController() {
		super(Person.class);
	}

	@Override
	public PersonFacade getFacade() {
		return as.getPersonFacade();
	}


	@PostConstruct
	public void init() {
		String username = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("username");
		if(username != null)
			current = getFacade().find(username); 
	}

	public String saveUserInfo(){
		as.edit(getCurrent());
		addInfo("user edited");
		return null;
	}

	public String changePassword(){
		if(password[0].equals(password[1])){
			as.changePassword(current, password[0]);
			addInfo("Password successfully changed.");
		} else
			addError("The passwords do not match.");
		return null;
	}

	public String[] getPassword(){
		if(password == null) password = new String[2];
		return password;
	}
}
