package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

@FacesValidator(value = "surnameValidator")
public class SurnameValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^[a-zA-Z-]{2,32}$";
    }

    @Override
    public String getMessage() {
        return "Invalid surname. Please type your surname, with letters, without accents or ponctuation (32 char. max.).";
    }
}
