/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import jim.sums.common.db.CourseLevel;
import jim.sums.common.facade.CourseLevelFacade;
import jim.sums.ideas.ctrl.CourseLevelController;

/**
 *
 * @author KardousS
 * TODO: remove?
 */
@FacesConverter(forClass = CourseLevel.class)
public class CourseLevelConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        CourseLevelController controller = (CourseLevelController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "gc");
        CourseLevelFacade af = controller.getFacade();
        CourseLevel a = af.find(Long.parseLong(value));
        return a;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof CourseLevel) {
            CourseLevel o = (CourseLevel) object;
            return getStringKey(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CourseLevel.class.getName());
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
