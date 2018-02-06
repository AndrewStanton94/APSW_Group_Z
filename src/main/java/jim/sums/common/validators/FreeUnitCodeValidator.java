package jim.sums.common.validators;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jim.sums.common.db.Unit;
import jim.sums.management.bus.UnitService;

/**
 * @author thomas
 */

//WARNING : Unsecured validator !! Use this validator only to be more userfriendly. It does not remplace a real test in the business logic !!!
@ManagedBean
@RequestScoped
@FacesValidator(value = "freeUnitCodeValidator")
public class FreeUnitCodeValidator extends AbstractValidator {
    
    @EJB
    private UnitService unitService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    
        if(value == null)
            return;
        
        String unitCodeToCheck = value.toString();
        
        if(unitCodeToCheck.isEmpty())
            return;
        
        //availibility test       
        Unit unit = new Unit(unitCodeToCheck);
        if(unitService.findSameUnitCode(unit)) {
            unvalidateCurrentField("Sorry, this code is already used. Please choose another one.");
        };
    }
    
}
