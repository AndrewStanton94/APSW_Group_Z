package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

@FacesValidator(value = "PostCodeValidator")
public class PostCodeValidator extends RegularExpressionValidator{

    @Override
    public String getPattern() {
        //Old Pattern (It seems incorrect) : "^[A-Z0-9]{5,7}$"
        //Official pattern from UK Government Data Standard :
        return "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})";
    }

    @Override
    public String getMessage() {
        return "Invalid postal code.";
    }
}
