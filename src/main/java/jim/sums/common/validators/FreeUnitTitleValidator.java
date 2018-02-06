package jim.sums.common.validators;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import jim.sums.common.db.Unit;
import static jim.sums.common.validators.AbstractValidator.unvalidateCurrentField;
import jim.sums.management.bus.UnitService;

/**
 * @author thomas
 */

//WARNING : Unsecured validator !! Use this validator only to be more userfriendly. It does not remplace a real test in the business logic !!!
@ManagedBean
@RequestScoped
@FacesValidator(value = "freeUnitCodeValidator")
public class FreeUnitTitleValidator extends AbstractValidator {
    
    @EJB
    private UnitService unitService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    
        if (value == null || component.getAttributes().get("unitCode") == null)
            return;
    
        String unitTitle = (String) value; 
        String unitCode = (String) component.getAttributes().get("unitCode");

        if (unitTitle.isEmpty())
            return;
        
        Unit unit = new Unit();
        
        //availibility test
        if (unitCode.isEmpty())
            unit.setUnitcode(null);
        else
            unit.setUnitcode(unitCode);
        unit.setUnittitle(unitTitle);
        if(unitService.findSameUnitTitleWithoutSameCode(unit)) {
            unvalidateCurrentField("Sorry, another unit already uses this title. Please choose another one.");
        };
    }
    
}
