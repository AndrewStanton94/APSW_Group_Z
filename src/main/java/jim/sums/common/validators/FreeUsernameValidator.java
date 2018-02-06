package jim.sums.common.validators;

import java.text.MessageFormat;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jim.sums.common.db.Person;
import jim.sums.register.bus.RegisterService;

/**
 * @author thomas
 */

//Captain Obvious says : this class should not be used to verify the identity of a user
//WARNING : Unsecured validator !! Use this validator only to be more userfriendly. It does not remplace a real test in the business logic !!!
@ManagedBean
@RequestScoped
@FacesValidator(value = "freeUsernameValidator")
public class FreeUsernameValidator extends AbstractValidator {
    
    @EJB
    private RegisterService rs;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if(value == null)
            return;
        
        String usernameToCheck = value.toString();
        
        if(usernameToCheck.isEmpty())
            return;
        
        //availibility test
        Person p = new Person(usernameToCheck);
        if (rs.findSameUsername(p)) {
            String message = MessageFormat.format("Sorry, the username \"{0}\" is already used. Please choose another.", usernameToCheck);
            unvalidateCurrentField(message);
        }
    }
}
