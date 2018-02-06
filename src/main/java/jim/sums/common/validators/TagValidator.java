package jim.sums.common.validators;

import javax.faces.validator.FacesValidator;

/**
 * @author thomas
 */

@FacesValidator(value = "tagValidator")
public class TagValidator extends RegularExpressionValidator {

    @Override
    public String getPattern() {
        return "^[a-z0-9]+(,[a-z0-9]+)*$";
    }

    @Override
    public String getMessage() {
        return "Invalid tags. Type your tags without accents, separated by commas.";
    }
    
}
