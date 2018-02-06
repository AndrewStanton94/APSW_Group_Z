package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

//TODO : there is an inconsistency ! The RE pattern validates only digits and the error message speak about letters...
@FacesValidator(value = "HemisNumberValidator")
public class HemisNumberValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^[0-9]{6,10}$";
    }

    @Override
    public String getMessage() {
        return "Invalid HEMIS number. HEMIS number must contain only letters and numbers. Its length should be between 6 and 10 characters.";
    }
}
