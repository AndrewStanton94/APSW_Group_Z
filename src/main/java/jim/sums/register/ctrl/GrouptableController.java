/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.ctrl;

import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import jim.common.StringFormatter;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.MessageController;
import jim.sums.common.db.Person;
import jim.sums.common.db.RoleName;
import jim.sums.register.bus.AdminService;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean
public class GrouptableController extends MessageController {

    @EJB
    private AdminService as;
    private RoleName newGroup;
    private Set<RoleName> newRoles;

    public RoleName getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(RoleName newGroup) {
        this.newGroup = newGroup;
    }

    public Set<RoleName> getNewRoles() {
        return newRoles;
    }

    public void setNewRoles(Set<RoleName> newRoles) {
        this.newRoles = newRoles;
    }

    public String addGroup(Person p) {
        try {
            as.addRole(p, newGroup);
            addInfo("Group added: " + newGroup.toString());
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return null;
    }

    public String addGroups(Person p) {
        try {
            as.changeRoles(p);
            addInfo("Roles now set to: " + StringFormatter.join(p.getRoles(), ", "));
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return null;
    }

    public String removeGroup(Person p, RoleName group) {
        try {
            as.removeRole(p, group);
            addInfo("Group removed: " + group.toString());
        } catch (BusinessException ex) {
            addError(ex.getMessage());
        }
        return null;
    }
}
