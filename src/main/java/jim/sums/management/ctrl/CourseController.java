
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Course;
import jim.sums.common.db.Courseinstance;
import jim.sums.common.facade.CourseFacade;
import jim.sums.management.bus.GroupsService;

/**
 *
 * @author Sam
 */
@ManagedBean(name = "couc")
@ViewScoped
public class CourseController extends AbstractController<Course, CourseFacade> {

    @EJB
    GroupsService gs;
    private Boolean opDisplayList = true;
    private Boolean opAddCourse = false;
    private Boolean opEditCourse = false;
     private Boolean opCourseDetails = false;
     private Boolean opAddCourseInstance = false;
    private Boolean opEditCourseInstance = false;

    @PostConstruct
    public void init() {
        current = getCurrent();
    }

    /** Creates a new instance of GroupsController */
    public CourseController() {
        super(Course.class);
    }

    @Override
    public CourseFacade getFacade() {
        return gs.getCourseFacade();
    }

    public void doEdit() {
        gs.editCourse(current);
    }

    public void doAdd() {
        gs.addCourse(newItem);
        items = new ListDataModel<Course>(getFacade().findAll());
    }

    public Boolean getOpAddCourse() {
        return opAddCourse;
    }

    public Boolean getOpDisplayList() {
        return opDisplayList;
    }

    public Boolean getOpEditCourse() {
        return opEditCourse;
    }

    public Boolean getOpCourseDetails() {
        return opCourseDetails;
    }

    public void addCourse(ActionEvent ae) {
        opAddCourse = true;
        opDisplayList = false;
        opEditCourse = false;
        opCourseDetails = false;
        opAddCourseInstance = false;
        opEditCourseInstance = false;
    }
   
     public void addCourseInstance(ActionEvent ae) {
        opAddCourse = false;
        opDisplayList = false;
        opEditCourse = false;
        opCourseDetails = false;
        opAddCourseInstance = true;
        opEditCourseInstance = false;
    }

    public void displayList(ActionEvent ae) {
        opAddCourse = false;
        opDisplayList = true;
        opEditCourse = false;
        opCourseDetails = false;
        opAddCourseInstance = false;
        opEditCourseInstance = false;
    }

    public void editCourse(ActionEvent ae) {
        opAddCourse = false;
        opDisplayList = false;
        opEditCourse = true;
        opCourseDetails = false;
        opAddCourseInstance = false;
        opEditCourseInstance = false;
    }
     public void editCourseInstance(ActionEvent ae) {
        opAddCourse = false;
        opDisplayList = false;
        opEditCourse = false;
        opCourseDetails = false;
        opAddCourseInstance = false;
        opEditCourseInstance = true;
    }

    public void courseDetails(ActionEvent ae) {
        opAddCourse = false;
        opDisplayList = false;
        opEditCourse = false;
        opCourseDetails = true;
        opAddCourseInstance = false;
        opEditCourseInstance = false;
    }
    
    
        public Boolean getOpAddCourseInstance() {
        return opAddCourseInstance;
    }

    public void setOpAddCourseInstance(Boolean opAddCourseInstance) {
        this.opAddCourseInstance = opAddCourseInstance;
    }

    public Boolean getOpEditCourseInstance() {
        return opEditCourseInstance;
    }

    public void setOpEditCourseInstance(Boolean opEditCourseInstance) {
        this.opEditCourseInstance = opEditCourseInstance;
    }
    
    public List<Courseinstance> getCourseInstances(){
        
        return gs.getCourseinstanceFacade().findCourseInstanceByCourse(current);
    }
}


