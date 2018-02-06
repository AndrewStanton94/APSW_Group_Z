package jim.sums.common.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */

@FacesValidator(value = "userStatusMenuValidator")
public class UserStatusMenuValidator extends AbstractValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        boolean statusIsUnvalid;
        
        if(value == null) {
            statusIsUnvalid = true;
        } else {
            try {
                String test = (String) value;
                statusIsUnvalid = true;
            } catch(ClassCastException e) {
                statusIsUnvalid = false;
            }
        }
        if(statusIsUnvalid) {
            unvalidateCurrentField("You must specify a status");
        }
    } 
}
