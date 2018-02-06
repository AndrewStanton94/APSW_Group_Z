/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;
import jim.sums.common.db.Person;
import jim.sums.common.db.RoleName;
import jim.sums.common.db.Suspension;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.RoleNameFacade;
import jim.sums.common.facade.SuspensionFacade;
import jim.sums.common.facade.UnitpersoninFacade;
import jim.sums.register.util.PasswordUtil;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class AdminService implements Serializable {

    @EJB
    SuspensionFacade sf;
    @EJB
    PersonFacade pf;
    @EJB
    RoleNameFacade rnf;
    @EJB
    UnitpersoninFacade upif;
    @EJB
    MailService ms;
    Suspension newSuspension;

    public AdminService() {
    }

    public PersonFacade getPersonFacade() {
        return pf;
    }

    public SuspensionFacade getSuspensionFacade() {
        return sf;
    }

    public boolean isSuspended(Person p) {
        return sf.isSuspended(p);
    }

    public void activate(Person p) throws MessagingException, BusinessException {
        RoleName userRole = rnf.user();
        p.setActivationdate(new Date());
        pf.edit(p);
        if (!p.isUser()) {
            addRole(p, userRole);
        }
//        ms.postMail(new String[]{p.getEmail()}, "SUMS - Activation of your account",
//                "Your account has been activated. You can now login and start "
//                + "using the application.");
    }

    public void confirm(Person p) throws BusinessException {
        RoleName staffRole = rnf.staff();
        p.setConfirmationdate(new Date());
        pf.edit(p);
        if (!p.isStaff()) {
            addRole(p, staffRole);
        }
//        ms.postMail(new String[]{p.getEmail()}, "SUMS - Confirmation of your account",
//                "Your account has been confirmed. You can now login and start "
//                + "using the application.");
    }

    public Suspension getNewSuspension() {
        return newSuspension;
    }

    public void unsuspend(Person p) throws MessagingException, BusinessException {
        for (Suspension suspension : sf.findAll()) {
            if (suspension.getSuspendedperson().equals(p)) {
                sf.remove(suspension);
//                ms.postMail(new String[]{p.getEmail()}, "SUMS - Your account is available",
//                        "Your suspension is removed, so you can log in to your SUMS account again.");
            }
        }
    }

    public void changeRoles(Person p) throws BusinessException {
        p = pf.edit(p);
        for (RoleName r : rnf.findAll()) {
            Set<Person> holders = r.getPersonsHavingRole();
            if (p.hasRole(r)) {
                if (!holders.contains(p)) {
                    holders.add(p);
                }
            } else {
                if (holders.contains(p)) {
                    holders.remove(p);
                }
            }
        }
    }

    @Deprecated
    public void addRole(Person p, String rolename) throws BusinessException {
        RoleName role = rnf.findByName(rolename);
        addRole(p, role);
    }

    public void addRole(Person p, RoleName role) throws BusinessException {
        if (p == null) {
            throw new BusinessException("No user specified");
        }
        if (role == null) {
            throw new BusinessException("No role specified");
        }
        role = rnf.edit(role);
        p = pf.edit(p);
        if (p.hasRole(role)) {
            throw new BusinessException("User " + p.getUsername() + " already has the role " + role + ".");
        }
        p.addRole(role);
        role.getPersonsHavingRole().add(p);
    }

    public RoleName findRoleByName(String name) {
        return rnf.findByName(name);
    }

    public void edit(Person p) {
        pf.edit(p);
    }

    public void changePassword(Person p, String pwd) {
        p.setPassword(PasswordUtil.hashMD5(pwd));
        pf.edit(p);
    }

    public void removeRole(Person p, RoleName role) throws BusinessException {
        if (p.hasRole(role)) {
            p.getRoles().remove(role);
            role.getPersonsHavingRole().remove(p);
        }
    }

    public List<Unitpersonin> getUnits(Person p) {
        return upif.findByPerson(p);
    }

    public void confirm(Unitpersonin upi) {
        upi.setValidationdate(new Date());
        upif.edit(upi);
    }

    public void delete(Unitpersonin upi) {
        upif.remove(upi);
    }

    public void setAccountStatus(Person p) {
        if (p.getActivationdate() == null
                || (p.isStaff() && p.getConfirmationdate() == null)
                || (p.isStudent() && hasUnitToValidate(p))) {
            p.setAccountStatus("Inactive");
        } else {
            p.setAccountStatus("Active");
        }
    }

    private boolean hasUnitToValidate(Person p) {
        List<Unitpersonin> upiList = upif.findByPerson(p);
        List<UnitInstance> l = new ArrayList<UnitInstance>();
        for (Unitpersonin upi : upiList) {
            if (upi.getValidationdate() == null) {
                return true;
            }
        }
        return false;
    }

    public Person adduser(Person p) {
        p.setCreationdate(new Date());
        p.setLastlogin(null);
        for (RoleName r : p.getRoles()) {
            r = rnf.edit(r);
            if (!r.getPersonsHavingRole().contains(p)) {
                r.getPersonsHavingRole().add(p);
            }
        }
        pf.create(p);
        return p;
    }
}
