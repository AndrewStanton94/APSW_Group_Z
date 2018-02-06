/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import jim.sums.common.db.ProjectKind;
import jim.sums.common.facade.ProjectKindFacade;
import jim.sums.ideas.ctrl.ProjectKindController;

/**
 *
 * @author KardousS
 */
@FacesConverter(forClass = ProjectKind.class)
public class IdeakindConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        ProjectKindController controller = (ProjectKindController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "kc");
        ProjectKindFacade af = controller.getFacade();
        ProjectKind a = af.find(Long.parseLong(value));
        return a;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ProjectKind) {
            ProjectKind o = (ProjectKind) object;
            return getStringKey(o.getIdkind());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + IdeakindConverter.class.getName());
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
