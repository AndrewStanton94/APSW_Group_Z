/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;
import jim.sums.register.bus.AccountService;

/**
 *
 * @author Nicolas Dossou-Gbété
 */
@RequestScoped
@ManagedBean
public class LostPassController extends AbstractController<Person, PersonFacade> {

	@EJB
	AccountService as;
	String username = null;

	public LostPassController() {
		super(Person.class);
	}
	
	@Override
	public PersonFacade getFacade() {
		return as.getPersFacade();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void requestNewPass(){
		try {
			as.requestNewPass(username);
			addInfo("Please check your emails for your new password.");
		} catch (BusinessException ex) {
			addError(ex.getMessage());
		}
	}
	
	public String redirect(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/register/lostPass.xhtml");
		} catch (IOException ex) {
			Logger.getLogger(LostPassController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	
	
	

	
}
