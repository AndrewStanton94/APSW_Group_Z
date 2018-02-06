package jim.sums.register.bus;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.common.EmailMessage;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;
import jim.sums.common.bus.SystemInfoService;
import jim.sums.common.db.Organisation;
import jim.sums.common.db.Person;
import jim.sums.common.facade.OrganisationFacade;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.util.TestFunctionUtil;
import jim.sums.register.util.PasswordUtil;
import org.apache.commons.mail.EmailException;

/**
 * @author KardousS
 */
@Stateless
public class RegisterService extends AbstractService {

    @EJB
    private PersonFacade persFacade;
    @EJB
    private OrganisationFacade orgaFacade;
    @EJB
    private MailService ms;
    @EJB
    private SystemInfoService sis;
    @EJB
    private AccountService as;

    public RegisterService() {
    }

    public PersonFacade getPersFacade() {
        return persFacade;
    }

    public Person registerNewPerson(Person p) throws BusinessException {

        List<Person> pers = persFacade.findSameUserName(p.getUsername());
        if (!pers.isEmpty()) {
            throw new BusinessException("Username " + p.getUsername() + " is already in use.");
        }
        if (!TestFunctionUtil.disableDuplicateEmailChecking && persFacade.isUsedEmail(p.getEmail())) {
            throw new BusinessException("Email address already in use.");
        }

        Date date = new Date();
        p.setCreationdate(date);
        p.setLastlogin(date);

        persFacade.create(p);
        sendActivationMail(p);

        return p;
    }

    private void informActivation(Person p, String link) throws BusinessException {
        try {
            String subject = "Welcome to SUMS";
            EmailMessage email = ms.getNewEmailMessage(p.getInternetAddress(), subject);
            email.setText(
                    "Dear " + p.getFullname() + ",\n\n"
                    + "Thank you for registering on the SUMS website.\n\n"
                    + "Please keep this email for your records. "
                    + "Your username to login is: \n\n"
                    + "---------------------------- \n"
                    + p.getUsername() + " \n"
                    + "---------------------------- \n\n"
                    + "Before we can activate your account one last step must be taken to complete your registration.\n"
                    + "Please note - you must complete this last step to become a registered member. "
                    + "You will only need to visit this url once to activate your account.\n\n"
                    + "To complete your registration, please visit this url: \n\n"
                    + link + "\n\n"
                    + "If you forgot your password, please use our password recovery page: " + "TODO:LINK" + "\n\n"
                    + "SUMS Manager");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(RegisterService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    private void sendActivationMail(Person p) throws BusinessException {
        String key = PasswordUtil.hashMD5(p.getUsername());
        String link = sis.getProjectUrl() + "/view/register/confirm.xhtml?key=" + key;
        p.setConfirmkey(key);
        persFacade.edit(p);
        informActivation(p, link);
    }

    public Organisation registerNewOrganisation(Organisation o) {
        List<Organisation> sameNames = orgaFacade.findSameName(o);
        if (sameNames.isEmpty()) {
            orgaFacade.create(o);
        } else {
            //message, exception...
        }
        return o;
    }

    public OrganisationFacade getOrgaFacade() {
        return orgaFacade;
    }

    public boolean findSameUsername(Person p) {
        List<Person> homonyms = getPersFacade().findSameUserName(p.getUsername());
        if (homonyms == null || homonyms.isEmpty()) {
            return false;
        }
        return true;
    }
}
