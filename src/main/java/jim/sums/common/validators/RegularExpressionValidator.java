package jim.sums.common.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */

public abstract class RegularExpressionValidator extends AbstractValidator {
    
    public abstract String getPattern();
    public abstract String getMessage();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if (value == null)
            return;
        
        String currentValue = value.toString();
        
        if(currentValue.isEmpty())
            return;
        
        Pattern p = Pattern.compile(getPattern());
        Matcher m = p.matcher(currentValue);
        if (!m.matches()) {
            unvalidateCurrentField(getMessage());
        }
    }
}
