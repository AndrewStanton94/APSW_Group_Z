/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.marking.ctrl;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jim.sums.common.db.TemplateMarkForm;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.UnitMarkForms;
import jim.sums.common.facade.AcademicyearFacade;
import jim.sums.common.facade.TemplateMarkFormFacade;
import jim.sums.common.facade.UnitFacade;
import jim.sums.common.facade.UnitMarkFormsFacade;
import jim.sums.common.facade.UnitinstanceFacade;

/**
 *
 * @author Auwal
 */
@Named(value = "newMarkFormBean")
@RequestScoped
public class NewMarkFormBean {
    @EJB
    private TemplateMarkFormFacade markFormsFacade;
    @EJB
    private UnitMarkFormsFacade unitMarkFormsFacade;
    @EJB
    private UnitinstanceFacade unitinstanceFacade;
    @EJB
    private UnitFacade unitFacade;
    @EJB
    private AcademicyearFacade academicyearFacade;

    private Integer selectedUnitinstance;
    private Integer selectedMarkForms;
    
    /**
     * Creates a new instance of NewMarkFormBean
     */
    public NewMarkFormBean() {
    }

    public Integer getSelectedUnitinstance() {
        return selectedUnitinstance;
    }

    public void setSelectedUnitinstance(Integer selectedUnitinstance) {
        this.selectedUnitinstance = selectedUnitinstance;
    }

    public Integer getSelectedMarkForms() {
        return selectedMarkForms;
    }

    public void setSelectedMarkForms(Integer selectedMarkForms) {
        this.selectedMarkForms = selectedMarkForms;
    }

     public Map<String, Long> getMarkFormsList() {
        Map<String, Long> map = new HashMap<>();
        for (TemplateMarkForm markform : markFormsFacade.findAll()) { //.forEach((markform) -> {
            map.put(markform.getFormname(), markform.getFormid());
        };
        return map;
    }

    /**
     * Get a list of UnitInstances that have not been assigned Forms
     *
     * @return Return a Map<String,UnitInstance>
     */
    public Map<String, Integer> getUnitInstanceList() {
        Map<String, Integer> map = new HashMap<>();
        for (UnitInstance unitInst : unitinstanceFacade.findAll()) { //.forEach((unitInst) -> {
            if (unitInst.getUnitMarkFormsList().isEmpty()) {
                map.put(unitInst.getAcademicyear().getAcademicYearName()+"--"+unitInst.getUnit().getUnitcode(), unitInst.getIdunitinstance());
            }
        };
        return map;
    }
    public String createNewForm() {
        UnitMarkForms unitMarkForms = new UnitMarkForms();
        TemplateMarkForm markForms = new TemplateMarkForm(Long.valueOf(selectedMarkForms));
        System.out.println(Long.valueOf(selectedMarkForms));
        unitMarkForms.setFormId(markForms);
        UnitInstance unitInstance = unitinstanceFacade.find(selectedUnitinstance);
        unitMarkForms.setUnitInstance(unitInstance);
        unitMarkForms.setUnitId(unitInstance.getUnit());
        unitMarkFormsFacade.create(unitMarkForms);
        selectedMarkForms = null;
        selectedUnitinstance = null;
        unitMarkForms = null;

        return "";
    }
}
