package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

//Captain Obvious says : this class should not be used to verify the identity of a user
@FacesValidator(value = "usernameValidator")
public class UsernameValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^[a-zA-Z0-9_]{1,32}$";
    }

    @Override
    public String getMessage() {
        return "Invalid username. Please use only letters and numbers.";
    }
}
