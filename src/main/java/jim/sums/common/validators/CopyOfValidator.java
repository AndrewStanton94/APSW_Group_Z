package jim.sums.common.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */
@FacesValidator(value = "copyOfValidator")
public class CopyOfValidator extends AbstractValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {


        if (value == null || component.getAttributes().get("copyOf") == null) {
            return;
        }

        String confirmpassword = (String) value;
        String password = (String) component.getAttributes().get("copyOf");

        //empty test for copy
        //This test is very usefull. With it, the copy field is required automaticaly if the original field is required !
        if (confirmpassword.isEmpty() && !password.isEmpty()) {
            unvalidateCurrentField("Please retype your password here.");
        }

        //comparison original/copy
        if (!password.equals(confirmpassword)) {
            unvalidateCurrentField("The copy is not equal to the original. Please retype one or both.");
        }
    }
}
