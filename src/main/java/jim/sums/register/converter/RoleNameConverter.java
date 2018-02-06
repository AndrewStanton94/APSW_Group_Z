/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.register.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import jim.sums.common.db.RoleName;
import jim.sums.common.facade.RoleNameFacade;
import jim.sums.register.ctrl.RoleNameController;

/**
 *
 * @author briggsj
 */
@FacesConverter(forClass = RoleName.class, value = "roleNameConverter")
public class RoleNameConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        RoleNameController controller = (RoleNameController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "roleNameController");
        RoleNameFacade rnf = controller.getFacade();
        RoleName a = rnf.find(value);
        return a;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof RoleName) {
            RoleName o = (RoleName) object;
            return o.getId();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RoleName.class.getName());
        }
    }

    public Long getKey(String value) {
        Long key;
        key = Long.valueOf(value);
        return key;
    }

    public String getStringKey(Long value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }
}
