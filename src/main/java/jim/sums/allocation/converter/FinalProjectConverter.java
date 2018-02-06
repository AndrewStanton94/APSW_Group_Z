package jim.sums.allocation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jim.sums.allocation.ctrl.FinalProjectController;
import jim.sums.common.db.Finalproject;

/**
 * @author thomas
 */

@FacesConverter(forClass = Finalproject.class)
public class FinalProjectConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        FinalProjectController fpc = (FinalProjectController) context.getApplication()
                .getELResolver().getValue(context.getELContext(), null, "fpc");
        return fpc.getFacade().find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        
        if (!(value instanceof Finalproject))
            throw new IllegalArgumentException("object " + value + " is of type " + value.getClass().getName() + "; expected type: " + Finalproject.class.getName());

        Finalproject fp = (Finalproject) value;
        return fp.getIdproject().toString();
    }
    
}
