/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.CourseLevel;
import jim.sums.common.facade.CourseLevelFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@RequestScoped
@ManagedBean(name = "gc")
public class CourseLevelController extends AbstractController<CourseLevel, CourseLevelFacade> {

    @EJB
    IdeaService is;
    ArrayList<CourseLevel> selectedGradeList;

    @Override
    public CourseLevelFacade getFacade() {
        return is.getCourseLevelFacade();
    }

    public CourseLevelController() {
        super(CourseLevel.class);
    }

    public ArrayList<CourseLevel> getSelectedGradeList() {
        return selectedGradeList;
    }

    public void setSelectedGradeList(ArrayList<CourseLevel> selectedGradeList) {
        this.selectedGradeList = selectedGradeList;
    }

    public SelectItem[] getGradeList() {

        List<CourseLevel> tmp = getFacade().findAll();

        return getSelectItems(tmp, false);
    }
}
