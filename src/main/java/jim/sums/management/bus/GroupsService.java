/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.bus;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.db.Academicyear;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Course;
import jim.sums.common.db.Courseinstance;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.AcademicyearFacade;
import jim.sums.common.facade.CohortFacade;
import jim.sums.common.facade.CourseFacade;
import jim.sums.common.facade.CourseinstanceFacade;
import jim.sums.common.facade.CourseLevelFacade;
import jim.sums.common.facade.ProjectKindFacade;
import jim.sums.common.facade.PersonFacade;
import jim.sums.common.facade.UnitFacade;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.common.facade.UnitpersoninFacade;

/**
 *
 * @author Sam
 */
@Stateless
public class GroupsService implements Serializable {

    @EJB
    UnitFacade uf;
    @EJB
    UnitinstanceFacade uif;
    @EJB
    CourseFacade cf;
    @EJB
    CourseinstanceFacade cif;
    @EJB
    CohortFacade cof;
    @EJB
    ProjectKindFacade kf;
    @EJB
    CourseLevelFacade gf;
    @EJB
    UnitpersoninFacade upif;
    @EJB
    PersonFacade pf;
    @EJB
    AcademicyearFacade ayf;

    public PersonFacade getPersonFacade() {
        return pf;
    }

    public UnitpersoninFacade getUnitPersonInFacade() {
        return upif;
    }

    public UnitFacade getUnitFacade() {
        return uf;
    }

    public CourseFacade getCourseFacade() {
        return cf;
    }

    public CourseinstanceFacade getCourseinstanceFacade() {
        return cif;
    }

    public CohortFacade getCohortFacade() {
        return cof;
    }

    public UnitinstanceFacade getUnitinstanceFacade() {
        return uif;
    }

    public ProjectKindFacade getKindFacade() {
        return kf;
    }

    public CourseLevelFacade getGradeFacade() {
        return gf;
    }

    public void editUnit(Unit current) {
        uf.edit(current);
    }

    public void editCourse(Course current) {
        cf.edit(current);
    }

    public void editUnitinstance(UnitInstance current) {
        uif.edit(current);
    }

    public void addUnitinstance(UnitInstance current) {
        uif.create(current);
    }

    public void addCourse(Course current) {
        cf.create(current);
    }

    public AcademicyearFacade getAcademicyearFacade() {
        return ayf;
    }

    public void addUnit(Unit newItem) throws BusinessException {
        if (uf.find(newItem.getUnitcode()) == null) {
            uf.create(newItem);
        } else {
            throw new BusinessException("This code is already used");
        }
    }

    public void editCourseinstance(Courseinstance current) {
        cif.edit(current);
    }

    public void addCourseinstance(Courseinstance newItem) {
        cif.create(newItem);
    }

    public void editCohort(Cohort current) {
        cof.edit(current);
    }

    public void addCohort(Cohort newItem) {
        cof.create(newItem);
    }

    public void addAcademicYear(Academicyear newItem) {
        ayf.create(newItem);
    }

    public void editAcademicYear(Academicyear current) {
        ayf.edit(current);
    }
}
