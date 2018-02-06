package jim.sums.management.ctrl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Academicyear;
import jim.sums.common.db.Cohort;
import jim.sums.common.db.Courseinstance;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.facade.AcademicyearFacade;
import jim.sums.management.bus.GroupsService;

/**
 * @author Sam
 */

@ManagedBean(name = "ayc")
@ViewScoped
public class AcademicyearController extends AbstractController<Academicyear, AcademicyearFacade> {

    @EJB
    GroupsService gs;
    private boolean createComponents;
    private Academicyear ayToCopy;
    private boolean copyAll = true;
    private boolean copyCourseInstances = true;
    private boolean copyCohorts = true;
    private boolean copyUnitInstances = true;

    public AcademicyearController() {
        super(Academicyear.class);
    }

    @Override
    public AcademicyearFacade getFacade() {
        return gs.getAcademicyearFacade();
    }

    @Override
    public Academicyear getNewItem() {
        if (newItem == null) {
            try {
                newItem = Academicyear.class.newInstance();

                newItem.setStartdate(getNextAugust().get(0));
                newItem.setEnddate(getNextAugust().get(1));
            } catch (InstantiationException ex) {
                Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newItem;
    }

    public List<Date> getNextAugust() {

        Calendar nextAugust = Calendar.getInstance();

        int day = nextAugust.get(Calendar.DATE);
        int month = nextAugust.get(Calendar.MONTH) + 1;
        int year = nextAugust.get(Calendar.YEAR);

        //In the case where the currrent date is more than 1st August
        if (month >= 8 && day > 1) {
            year++;
        }
        day = 1;
        month = 7;
        List<Date> dateList = new ArrayList<Date>();

        nextAugust.set(year, month, day);

        dateList.add(nextAugust.getTime());
        //Set next 31st of July
        nextAugust.set(year + 1, 6, 21);
        dateList.add(nextAugust.getTime());

        return dateList;
    }

    public boolean isCreateComponents() {
        return createComponents;
    }

    public void setCreateComponents(boolean createComponents) {
        this.createComponents = createComponents;
    }

    public void doAdd(ActionEvent ev) throws BusinessException {
        
        gs.addAcademicYear(getNewItem());
        if (createComponents) {
            try {
                if (copyAll) {
                    HashMap<UnitInstance, UnitInstance> newOldUnitInstances = null;
                    HashMap<Cohort, Cohort> newOldCohorts = null;
                    HashMap<Courseinstance, Courseinstance> newOldCourseInstances = null;
                    if (ayToCopy.getUnitinstanceList() != null && ayToCopy.getUnitinstanceList().size() > 0) {
                        newOldUnitInstances = createListUnitinstances();
                    }

                    newOldCohorts = createListCohorts();

                    if (ayToCopy.getCourseinstanceList() != null && ayToCopy.getCourseinstanceList().size() > 0) {
                        newOldCourseInstances = createListCourseinstances();
                    }
                    ///copying the cohortUnitIn relationship;
                    if (newOldUnitInstances != null && newOldCohorts != null && newOldUnitInstances.size() > 0 && newOldCohorts.size() > 0) {
                        createCohortUnitInRelationship(newOldCohorts, newOldUnitInstances);
                    }
                    ///copying the 'consistOf' relationship
                    if (newOldUnitInstances != null && newOldCourseInstances != null && newOldUnitInstances.size() > 0 && newOldCourseInstances.size() > 0) {
                        createConsisOfRelationship(newOldCourseInstances, newOldUnitInstances);
                    }
                } else {

                    if (copyUnitInstances && ayToCopy.getUnitinstanceList() != null) {
                        createListUnitinstances();
                    }
                    if (copyCohorts) {
                        createListCohorts();
                    }
                    if (copyCourseInstances && ayToCopy.getCourseinstanceList() != null) {
                        createListCourseinstances();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            gs.editAcademicYear(newItem);

        }
        if (createComponents) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully created a new academic year and all of the related cohort, unit and course instances."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have successfully created a new academic year record."));
        }
        items = null;
    }

    public HashMap<UnitInstance, UnitInstance> createListUnitinstances() {
        Iterator<UnitInstance> iter = ayToCopy.getUnitinstanceList().iterator();
        ArrayList<UnitInstance> listUnit = new ArrayList<UnitInstance>();
        HashMap<UnitInstance, UnitInstance> OldAndNewUnit = new HashMap<UnitInstance, UnitInstance>();
        UnitInstance newUnit, currentUnit;
        while (iter.hasNext()) {
            currentUnit = iter.next();
            newUnit = new UnitInstance();
            newUnit.setAcademicyear(newItem);
            //newUnit.setUnitinstancetitle(currentUnit.getUnitinstancetitle());
            newUnit.setUnit(currentUnit.getUnit());
            gs.addUnitinstance(newUnit);

            OldAndNewUnit.put(currentUnit, newUnit);

            listUnit.add(newUnit);

        }
        newItem.setUnitinstanceList(listUnit);

        return OldAndNewUnit;
    }

    public void createConsisOfRelationship(HashMap<Courseinstance, Courseinstance> newOldCourseInstances, HashMap<UnitInstance, UnitInstance> newOldUnitInstances) {

        for (Map.Entry<Courseinstance, Courseinstance> entry : newOldCourseInstances.entrySet()) {

            Courseinstance oldCourseinstance = entry.getKey();
            Courseinstance newCourseinstance = entry.getValue();
            List<UnitInstance> unitInstancesList = oldCourseinstance.getUnitinstances();
            List<UnitInstance> newUnitInstancesList = new ArrayList<UnitInstance>();

            if (unitInstancesList != null && unitInstancesList.size() > 0) {
                for (UnitInstance c : unitInstancesList) {

                    if (newOldUnitInstances.get(c) != null) {
                        newUnitInstancesList.add(newOldUnitInstances.get(c));
                    }
                }
                newCourseinstance.setUnitinstances(newUnitInstancesList);
                gs.editCourseinstance(newCourseinstance);
            }

        }


    }

    public void createCohortUnitInRelationship(HashMap<Cohort, Cohort> newOldCohorts, HashMap<UnitInstance, UnitInstance> newOldUnitInstances) {

        for (Map.Entry<UnitInstance, UnitInstance> entry : newOldUnitInstances.entrySet()) {
            UnitInstance oldUnitinstance = entry.getKey();
            UnitInstance newUnitinstance = entry.getValue();
            List<Cohort> unitCohort = oldUnitinstance.getCohortList();
            List<Cohort> newUnitCohort = new ArrayList<Cohort>();
            List<UnitInstance> currUnitinstanceList;
            if (unitCohort != null && unitCohort.size() > 0) {
                for (Cohort c : unitCohort) {
                    if (newOldCohorts.get(c) != null) {
                        newUnitCohort.add(newOldCohorts.get(c));
                    }
                }
                for (Cohort c : newUnitCohort) {
                    currUnitinstanceList = c.getUnits();
                    currUnitinstanceList.add(newUnitinstance);
                    c.setUnits(currUnitinstanceList);
                    gs.editCohort(c);
                }
            }
        }

    }

    public HashMap<Courseinstance, Courseinstance> createListCourseinstances() {
        Iterator<Courseinstance> iter = ayToCopy.getCourseinstanceList().iterator();
        ArrayList<Courseinstance> listCourse = new ArrayList<Courseinstance>();
        Courseinstance newCourse, currentCourse;
        HashMap<Courseinstance, Courseinstance> OldAndNewCourse = new HashMap<Courseinstance, Courseinstance>();
        while (iter.hasNext()) {
            currentCourse = iter.next();
            newCourse = new Courseinstance();
            newCourse.setAcademicyear(newItem);
            newCourse.setCourseinstancename(currentCourse.getCourseinstancename());
            newCourse.setCourse(currentCourse.getCourse());
            gs.addCourseinstance(newCourse);

            OldAndNewCourse.put(currentCourse, newCourse);

            listCourse.add(newCourse);

        }
        newItem.setCourseinstanceList(listCourse);
        return OldAndNewCourse;
    }

    private HashMap<Cohort, Cohort> createListCohorts() {
        HashMap<Cohort, Cohort> OldAndNewCohort = new HashMap<Cohort, Cohort>();

        if (ayToCopy.getUnitinstanceList() != null) {
            Iterator<UnitInstance> iterUnit = ayToCopy.getUnitinstanceList().iterator();
            while (iterUnit.hasNext()) {
                UnitInstance ui = iterUnit.next();
                Iterator<Cohort> iterCohort = ui.getCohortList().iterator();
                while (iterCohort.hasNext()) {
                    Cohort c = iterCohort.next();
                    if (!OldAndNewCohort.containsKey(c)) {
                        Cohort newCohort = new Cohort();
                        newCohort.setCohortlabel(c.getCohortlabel());
                        newCohort.setIdeashortlistmaxsize(c.getIdeashortlistmaxsize());
                        newCohort.setProjectstart(c.getProjectstart());
                        newCohort.setProjectstop(c.getProjectstop());
                        newCohort.setRegisterstart(c.getRegisterstart());
                        newCohort.setRegisterstop(c.getRegisterstop());
                        newCohort.setSupervisorshortlistmaxsize(c.getSupervisorshortlistmaxsize());
                        gs.addCohort(newCohort);
                        OldAndNewCohort.put(c, newCohort);
                    }
                }
            }
        }

        return OldAndNewCohort;
    }
    
    public Academicyear getAyToCopy() {
        return ayToCopy;
    }

    public void setAyToCopy(Academicyear ayToCopy) {
        this.ayToCopy = ayToCopy;
    }

    public boolean isCopyAll() {
        return copyAll;
    }

    public void setCopyAll(boolean copyAll) {
        this.copyAll = copyAll;
    }

    public boolean isCopyCohorts() {
        return copyCohorts;
    }

    public void setCopyCohorts(boolean copyCohorts) {
        this.copyCohorts = copyCohorts;
    }

    public boolean isCopyCourseInstances() {
        return copyCourseInstances;
    }

    public void setCopyCourseInstances(boolean copyCourseInstances) {
        this.copyCourseInstances = copyCourseInstances;
    }

    public boolean isCopyUnitInstances() {
        return copyUnitInstances;
    }

    public void setCopyUnitInstances(boolean copyUnitInstances) {
        this.copyUnitInstances = copyUnitInstances;
    }
}
