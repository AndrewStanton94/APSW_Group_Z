package jim.sums.common.validators;

import java.text.MessageFormat;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.util.TestFunctionUtil;

/**
 * @author thomas
 */
//WARNING : Unsecured validator !! Use this validator only to be more userfriendly. It does not remplace a real test in the business logic !!!

@FacesValidator(value = "freeEmailValidator")
public class FreeEmailValidator extends AbstractValidator {

    @EJB
    private PersonFacade persFacade;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (TestFunctionUtil.disableDuplicateEmailChecking) {
            return;
        }
        if (value == null) {
            return;
        }

        String emailToCheck = value.toString();

        if (emailToCheck.isEmpty()) {
            return;
        }

        //availibility test
        if (persFacade.isUsedEmail(emailToCheck)) {
            String message = MessageFormat.format("Sorry, the email \"{0}\" is already used. You may be already registered. Please try to login.", emailToCheck);
            // TODO restore this line in order to reinstate the test for duplicate users with the same email; removed for testing purposes
//            unvalidateCurrentField(message);
        }
    }
}
