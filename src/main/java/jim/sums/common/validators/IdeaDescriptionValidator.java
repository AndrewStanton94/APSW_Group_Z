package jim.sums.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * @author LepanK
 */

@FacesValidator(value = "ideaDescriptionValidator")
public class IdeaDescriptionValidator extends AbstractValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null)
            return;
        
        String currentValue = value.toString();
        
        if(currentValue.isEmpty())
            return;
        
        int wordNumber = currentValue.split("\\s+").length;
        
        if (wordNumber < 20)
            unvalidateCurrentField("The description is too short.");
        if (wordNumber > 200)
            unvalidateCurrentField("The description is too long.");
        
    }
}
