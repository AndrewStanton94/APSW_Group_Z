package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

@FacesValidator(value = "unitCodeValidator")
public class UnitCodeValidator extends RegularExpressionValidator{

    @Override
    public String getPattern() {
        return "[a-zA-Z0-9]{6}$";
    }

    @Override
    public String getMessage() {
        return "Invalid unit code. A unit code must have 6 alphanumeric characters.";
    }
    
}
