package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

@FacesValidator(value = "forenameValidator")
public class FornameValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^[a-zA-Z-]{2,32}$";
    }

    @Override
    public String getMessage() {
        return "Invalid forename. Please type a forename, with letters, without accents or ponctuation (32 char. max.).";
    }
}
