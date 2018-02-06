package jim.sums.register.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import jim.common.EmailMessage;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;
import jim.sums.common.bus.SystemInfoService;
import jim.sums.common.db.Person;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.Unitpersonin;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.RoleNameFacade;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.common.facade.UnitpersoninFacade;
import jim.sums.common.util.TestFunctionUtil;
import jim.sums.register.util.PasswordUtil;
import org.apache.commons.mail.EmailException;

/**
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class AccountService extends AbstractService {

    @EJB
    private PersonFacade persFacade;
    @EJB
    private UnitinstanceFacade uiFacade;
    @EJB
    private UnitpersoninFacade upiFacade;
    @EJB
    private MailService ms;
    @EJB
    private SystemInfoService sis;
    @EJB
    private RoleNameFacade rnf;
    @EJB
    private AdminService as;

    public AccountService() {
    }

    public PersonFacade getPersFacade() {
        return persFacade;
    }

    public void changePassword(String currentPassword, String newPassword, Person p) throws BusinessException {
        if (PasswordUtil.hashMD5(currentPassword).equals(p.getPasswordEncrypted())) {
            p.setPassword(newPassword);
            persFacade.edit(p);
        } else {
            throw new BusinessException("Your current password is incorrect.");
        }
    }

    public void saveUserInfo(Person p) {
        persFacade.edit(p);
    }

    private void informChangeEmail(String newAddress, String oldAddress, String name) throws BusinessException {
        try {
            InternetAddress ia = new InternetAddress(newAddress, name);
            EmailMessage email = ms.getNewEmailMessage(ia, "SUMS email address change");
            email.addTo(oldAddress, name);
            email.setText("Your email address has been successfully changed from " + oldAddress + " to " + newAddress);
            email.send();
        } catch (Exception ex) {
            throw new BusinessException("Email not sent", ex);
        }
    }

    public void changeEmail(String address, Person p) throws BusinessException {
        if (!TestFunctionUtil.disableDuplicateEmailChecking && persFacade.isUsedEmail(address)) {
            throw new BusinessException("Email address already in use.");
        }
        informChangeEmail(address, p.getEmail(), p.getFullname());
        p.setEmail(address);
        persFacade.edit(p);
        sendActivationMail(p);


    }
//I have added an activation function just like in the register service

    private void informActivation(String key, Person p) throws BusinessException {
        try {
            String link = sis.getProjectUrl() + "/view/register/confirm.xhtml?key=" + key;
            EmailMessage email = ms.getNewEmailMessage(p.getEmail(), "Welcome to SUMS");
            email.setText("Dear " + p.getFullname() + ",\n\n"
                    + "This is a verification email.\n\n"
                    + "To complete your change please visit this url once to activate your new email.\n\n"
                    + link + "\n\n"
                    + "If you forgot your password, please use our password recovery page: " + "TODO:LINK" + "\n\n"
                    + "SUMS Administrator");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(ex.getMessage());
        }
    }

    private void sendActivationMail(Person p) throws BusinessException {
        String key = PasswordUtil.hashMD5(p.getUsername());
        p.setConfirmkey(key);
        persFacade.edit(p);
        informActivation(key, p);
    }

    public void confirmRegistrationKey(String key) throws BusinessException {
        List<Person> sameKey = persFacade.findByConfirmKey(key);
        if (sameKey.size() == 1) {
            Person p = sameKey.get(0);
            if (p.getActivationdate() == null) {
                try {
                    as.activate(p);
                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BusinessException("Cannot send email", ex);
                }
            } else {
                throw new BusinessException("Confirmation key already used: " + p.getConfirmationdate());
            }
        } else {
            if (sameKey.isEmpty()) {
                throw new BusinessException("Confirmation key not found");
            }
            if (sameKey.size() > 1) {
                throw new BusinessException("Confirmation key not unique");
            }
        }
    }

    public void addUnitInstance(int idUnitInstance, Person p) throws BusinessException {
        if (!upiFacade.findSame(idUnitInstance, p.getUsername()).isEmpty()) {
            throw new BusinessException("You have already registered this unit.");
        }
        Unitpersonin upi = new Unitpersonin(p.getUsername(), idUnitInstance);
        upi.setUnitinstance(uiFacade.find(idUnitInstance));
        upi.setChosen(p);
        upiFacade.create(upi);
    }

    public List<UnitInstance> getUnitInstanceList() {
        return uiFacade.findAll();
    }

    public List<Unitpersonin> getUnits(Person p) {
        return upiFacade.findByPerson(p);
    }

    public void informNewPassword(String newpass, Person p) throws BusinessException {
        try {
            EmailMessage email = ms.getNewEmailMessage(p.getInternetAddress(), "Your SUMS password has been changed");
            email.setText(
                    "Dear " + p.getFullname() + ",\n\n"
                    + "Please take note of your new password:\n"
                    + "---------------------------- \n"
                    + newpass + " \n"
                    + "---------------------------- \n\n"
                    + "You can modify your password in your SUMS account panel.\n\n"
                    + "SUMS Manager");
            email.send();
        } catch (EmailException ex) {
            throw new BusinessException("Mail error\n" + ex.getMessage(), ex);
        }
    }

    public void generatePassword(Person p) throws BusinessException {
        String newpass = PasswordUtil.generate(8);
        p.setPassword(newpass);
        persFacade.edit(p);
        informNewPassword(newpass, p);
    }

    public void requestNewPass(String username) throws BusinessException {
        Person p = persFacade.find(username);
        if (p == null) {
            throw new BusinessException("User not found");
        }
        generatePassword(p);
    }

    public List<Person> getPersonInUnitPersonIn() {
        List<Person> studentList = new ArrayList<Person>();
        for (UnitInstance ui : uiFacade.findAll()) {
                for (Unitpersonin upi : ui.getUnitpersoninList()) {
                    studentList.add(upi.getChosen());
                }
            }
        return studentList;
    }
}
