package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

//Captain Obvious says : this class should not be used to verify the identity of a user
@FacesValidator(value = "passwordValidator")
public class PasswordValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^(?=.*\\d).{6,32}$";
    }

    @Override
    public String getMessage() {
        return "This is not a secured password. A good password consists of at least 8 random characters and includes at least one number and one special character (32 char. max.)";
    }
}