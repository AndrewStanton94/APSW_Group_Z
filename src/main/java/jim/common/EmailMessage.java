/*
 * EmailMessage.java
 *
 * Created on 24 March 2005, 15:17
 */
package jim.common;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * An email message (template) that could be sent multiple times to multiple
 * recipients.
 *
 * The message can be tailored for each recipient.
 *
 * @author briggsj
 */
public class EmailMessage extends SimpleEmail {

    static final private Log log = LogFactory.getLog(EmailMessage.class);
    private boolean testing = false;
    private Map<String, String> generalParams = new HashMap<String, String>();
    private Map<String, String> tailoredParams = new HashMap<String, String>();
    private String text;

    public EmailMessage() {
    }

    public Map<String, String> getTailoredParams() {
        return tailoredParams;
    }

    public void setTailoredParams(Map<String, String> tailoredParams) {
        this.tailoredParams = tailoredParams;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Email setMsg(String text) {
        this.setText(text);
        return this;
    }

    public Map<String, String> getGeneralParams() {
        return this.generalParams;
    }

    public void setGeneralParams(Map<String, String> generalParams) {
        this.generalParams = generalParams;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    /**
     * Display an email message as a string
     *
     * @return <CODE>String</CODE>
     */
    @Override
    public String toString() {
        String retValue;

        retValue = "\n" + "From: " + ((fromAddress != null) ? fromAddress.toString() : "no sender") + "\n"
                + "To: " + ((toList != null) ? toList.toString() : "no recipients") + "\n"
                + "Subject: " + subject + "\n"
                + "\n"
                + getFmtMessage();
        return retValue;
    }

    /**
     * Get the message text. Any tailoring will be applied.
     *
     * @return <CODE>String</CODE>
     */
    public String getFmtMessage() {
        Map<String, String> localMap = new HashMap<String, String>(generalParams);
        if (tailoredParams == null) {
            // do nothing
        } else {
            localMap.putAll(tailoredParams);
        }
        String fmtMessage = StringFormatter.apply(text, localMap);
        return fmtMessage;
    }

    @Override
    public String send() throws EmailException {
        super.setMsg(this.getFmtMessage());
        String msgid;
        if (testing) {
            msgid = "noMessageSent";
            log.info("Sent simulated email, subject[" + subject + "] to " + toList.toString());
        } else {
            msgid = super.send();
            log.info("Sent email, subject[" + subject + "] to " + toList.toString());
        }
        return msgid;
    }
}