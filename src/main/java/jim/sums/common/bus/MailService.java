/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.internet.InternetAddress;
import jim.common.EmailMessage;
import jim.sums.common.facade.AppPropertyFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class MailService extends AbstractService {

    @EJB
    private AppPropertyFacade prop;
    private static Log log = LogFactory.getLog(MailService.class);

    public MailService() {
    }

    //Factory method for SUMS EmailMessage
    public EmailMessage getNewEmailMessage() throws EmailException, BusinessException {
        EmailMessage em = new EmailMessage();
        em.setHostName(prop.get("smtp_host_name"));
        em.setSmtpPort(Integer.parseInt(prop.get("smtp_host_port")));
        String smtpAuthUser = prop.get("smtp_auth_user");
        if (smtpAuthUser != null && !smtpAuthUser.isEmpty()) {
            em.setAuthenticator(new DefaultAuthenticator(smtpAuthUser, prop.get("smtp_auth_pwd")));
        }
        em.setFrom(prop.get("smtp_email_address"), "SUMS manager");
//        em.setTLS(true);
        return em;
    }

    public EmailMessage getNewEmailMessage(String to, String subject) throws EmailException, BusinessException {
        EmailMessage email = getNewEmailMessage();
        email.addTo(to);
        email.setSubject(subject);
        return email;
    }

    public EmailMessage getNewEmailMessage(InternetAddress to, String subject) throws EmailException, BusinessException {
        EmailMessage email = getNewEmailMessage();
        email.addTo(to.getAddress(), to.getPersonal());
        email.setSubject(subject);
        return email;
    }

    @Deprecated
    public void postMail(String recipients[], String subject, String msg) throws EmailException, BusinessException {
        try {
            Email email = getNewEmailMessage();
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(recipients);
            email.send();
        } catch (EmailException ex) {
            log.error("Failed to send email message to " + recipients.toString(), ex);
            throw ex;
        }
    }

    @Deprecated
    public void postMail(String recipient, String subject, String msg) throws EmailException, BusinessException {
//        In comments because the send of email does not work'
        postMail(new String[]{recipient}, subject, msg);
    }
}
