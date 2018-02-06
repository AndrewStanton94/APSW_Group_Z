package jim.sums.common.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */

@FacesValidator(value = "emailValidator")
public class EmailValidator extends RegularExpressionValidator {
   
    @Override
    public String getPattern() {
        return "^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})$";
    }

    @Override
    public String getMessage() {
        return "This is not a valid email.";
    }
}
