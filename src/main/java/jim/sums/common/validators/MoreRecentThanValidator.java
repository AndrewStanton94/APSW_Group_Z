package jim.sums.common.validators;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */

@FacesValidator("moreRecentThanValidator")
public class MoreRecentThanValidator extends AbstractValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    
        if (value == null || component.getAttributes().get("moreRecentThan") == null)
        return;
    
        Date toDate   = (Date) value; 
        Date fromDate = (Date) component.getAttributes().get("moreRecentThan");
        if (fromDate.after(toDate)) {
            unvalidateCurrentField("Sorry, there is no more plutonium for the Deloreane...");
        }
    }
}
