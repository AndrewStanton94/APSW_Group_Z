/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import jim.sums.common.ctrl.MessageController;
import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;

/**
 *
 * @author jamiebarr
 */
@Named(value = "temporaryLoginController")
@SessionScoped
public class TemporaryLoginController extends MessageController implements Serializable {

    public TemporaryLoginController() {
    }

    @EJB
    private PersonFacade pf;
    private Person person;
    private String username;
    private String password;

    public String userLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //request.login(this.username, this.password);
            request.login(this.username, this.username);
            person = pf.find(username);
            addInfo(username + " has logged in");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logged in OK", username));
        } catch (Exception e) {
            addError(e, password);
            context.addMessage(null, new FacesMessage("Login failed."));
            return "loginerror";
        }
        return "coordinatorDashboard.xhtml";
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
