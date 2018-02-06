package jim.sums.management.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jim.sums.common.db.UnitInstance;
import jim.sums.management.ctrl.UnitInstanceControler;

/**
 * @author thomas
 */

@FacesConverter(forClass = UnitInstance.class)
public class UnitInstanceConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        UnitInstanceControler uic = (UnitInstanceControler) context.getApplication()
                .getELResolver().getValue(context.getELContext(), null, "unitIc");
        return uic.getFacade().find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        
        if (!(value instanceof UnitInstance))
            throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + UnitInstance.class.getName());

        UnitInstance ui = (UnitInstance) value;
        return ui.getIdunitinstance().toString();
    }
    
}
