package jim.sums.common.validators;

import javax.faces.application.FacesMessage;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author thomas
 */

public abstract class AbstractValidator implements Validator{

    protected static void unvalidateCurrentField(String reason) {
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, reason, reason));
    } 
}
