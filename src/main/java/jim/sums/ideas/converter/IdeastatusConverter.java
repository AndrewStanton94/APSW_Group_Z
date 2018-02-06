/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import jim.sums.common.db.Ideastatus;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.ideas.ctrl.IdeastatusController;

/**
 *
 * @author KardousS
 */
@FacesConverter(forClass = Ideastatus.class)
public class IdeastatusConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        IdeastatusController controller = (IdeastatusController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "istc");
        IdeastatusFacade af = controller.getFacade();
        Ideastatus a = af.find(Integer.parseInt(value));
        return a;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Ideastatus) {
            Ideastatus o = (Ideastatus) object;
            return getStringKey(o.getIdideastatus());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + IdeastatusConverter.class.getName());
        }
    }

    public Long getKey(String value) {
        Long key;
        key = Long.valueOf(value);
        return key;
    }

    public String getStringKey(Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }
}
