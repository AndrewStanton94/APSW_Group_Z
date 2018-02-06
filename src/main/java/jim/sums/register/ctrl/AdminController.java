/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Person;
import jim.sums.common.db.RoleName;
import jim.sums.common.facade.PersonFacade;
import jim.sums.register.bus.AdminService;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@ViewScoped
@ManagedBean
public class AdminController extends AbstractController<Person, PersonFacade> {

    @EJB
    private AdminService as;
    private Set<Person> userList;
    private Person[] selectedPersons;
    private String searchString;
    private boolean displayadd = false;
    private SelectItem[] personStatusOption = null;

    public boolean isDisplayadd() {
        return displayadd;
    }

    public void setDisplayadd() {
        this.displayadd = true;
    }

    public AdminController() {
        super(Person.class);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public PersonFacade getFacade() {
        return as.getPersonFacade();
    }

    public AdminService getAdminService() {
        return as;
    }

    public Set<Person> getUserList() {
        userList = new HashSet<Person>();

        if (searchString == null || "".equals(searchString)) {
            userList.addAll(as.getPersonFacade().findAll());
        } else {
            userList.addAll(as.getPersonFacade().findSameUserName(searchString));
            userList.addAll(as.getPersonFacade().findSameEmail(searchString));
            RoleName searchRole;
            try {
                searchRole = as.findRoleByName(searchString); //getPsf().findByName(searchString);
                if (searchRole != null) {
                    userList.addAll(as.getPersonFacade().findByRole(searchRole));
                }
            } catch (Exception e) {
                //Doesn't matter if the search string is not a valid role name; just ignore it
            }
        }
        return userList;
    }

    @Override
    public DataModel<Person> getItems() {
        if (items == null) {
            items = new ListDataModel<Person>(new ArrayList<Person>(getUserList()));
        }
        return items;
    }

    public boolean isSuspended(Person p) {
        return as.isSuspended(p);
    }

    public String activate(Person p) {
        try {
            as.activate(p);
            as.setAccountStatus(p);
        } catch (Exception ex) {
            addError(ex.getMessage());
        }
        return "toAdminPage";
    }

    public String confirm(Person p) {
        try {
            as.confirm(p);
            as.setAccountStatus(p);
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return "toAdminPage";
    }

    public String unsuspend(Person p) {
        try {
            as.unsuspend(p);
        } catch (Exception ex) {
            addError(ex.getMessage());
        }
        return "toAdminPage";
    }

    public Person[] getSelectedPersons() {
        return selectedPersons;
    }

    public void setSelectedPersons(Person[] selectedPersons) {
        this.selectedPersons = selectedPersons;
    }

    public List<Person> getPersons() {
        return new ArrayList<Person>(getUserList());
    }

    public void doSearch() {
        userList = getUserList();
    }

    public String addUser() {
        as.adduser(newItem);
        return "admin.xhtml";
    }
}
