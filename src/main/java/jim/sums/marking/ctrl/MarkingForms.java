/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.marking.ctrl;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import jim.sums.common.db.Academicyear;
import jim.sums.common.db.CategoryMark;
import jim.sums.common.db.CategoryMarksOption;
import jim.sums.common.db.TemplateCriteria;
import jim.sums.common.db.CategoryOptions;
import jim.sums.common.db.MarkCategory;
import jim.sums.common.db.TemplateCategory;
import jim.sums.common.db.TemplateMarkForm;
import jim.sums.common.db.MarkerMark;
import jim.sums.common.db.Unit;
import jim.sums.common.db.UnitInstance;
import jim.sums.common.db.UnitMarkForms;
import jim.sums.common.facade.AcademicyearFacade;
import jim.sums.common.facade.CategoryMarksOptionFacade;
import jim.sums.common.facade.TemplateCriteriaFacade;
import jim.sums.common.facade.CategoryOptionsFacade;
import jim.sums.common.facade.MarkCategoryFacade;
import jim.sums.common.facade.TemplateCategoryFacade;
import jim.sums.common.facade.TemplateMarkFormFacade;
import jim.sums.common.facade.MarkerMarkFacade;
import jim.sums.common.facade.UnitFacade;
import jim.sums.common.facade.UnitMarkFormsFacade;
import jim.sums.common.facade.UnitinstanceFacade;
import jim.sums.management.bus.UnitInstanceService;

/**
 *
 * @author Auwal
 * 
 * OLD ONE DONT USE IT
 */
@Named(value = "markingForms")
@SessionScoped
public class MarkingForms implements Serializable {

    @EJB
    private UnitMarkFormsFacade unitMarkFormsFacade;
    @EJB
    private UnitinstanceFacade unitinstanceFacade;

    @EJB
    private UnitFacade unitFacade;
    @EJB
    private AcademicyearFacade academicyearFacade;

    @EJB
    private TemplateCriteriaFacade categoryOptionCriteriaFacade;

    @EJB
    private TemplateCategoryFacade markFormCategoriesFacade;
    @EJB
    private MarkCategoryFacade markCategoriesFacade;
    @EJB
    private CategoryOptionsFacade categoryOptionsFacade;

    @EJB
    private TemplateMarkFormFacade markFormsFacade;

    private Integer selectedMarkForms;
    private Integer selectedUnitinstance;

    private CategoryOptions opt1;
    private CategoryOptions opt2;
    private CategoryOptions opt3;
    private CategoryOptions opt4;
    private CategoryOptions opt5;
    private CategoryOptions opt6;
    private CategoryOptions opt7;
    private CategoryOptions opt8;
    private CategoryOptions opt9;
    private CategoryOptions opt10;

    private Integer selectedFormID;
    private Integer academicYear;
    private MarkerMark currentMark;
    private boolean selectedOptionLabel;
    private TemplateCategory selectedFormCategories;
    private String unitCode;
    private boolean isUpdate;
    private MarkCategory markCategories;
    private List<CategoryOptions> columnLabelsList;

    private List<TemplateCategory> categories;
    private String opt1Criteria;
    private TemplateCriteria criteria1 = null;
    private String opt2Criteria;
    private TemplateCriteria criteria2 = null;
    private String opt3Criteria;
    private TemplateCriteria criteria3 = null;
    private String opt4Criteria;
    private TemplateCriteria criteria4 = null;
    private String opt5Criteria;
    private TemplateCriteria criteria5 = null;
    private String opt6Criteria;
    private TemplateCriteria criteria6 = null;
    private String opt7Criteria;
    private TemplateCriteria criteria7 = null;
    private String opt8Criteria;
    private TemplateCriteria criteria8 = null;
    private String opt9Criteria;
    private TemplateCriteria criteria9 = null;
    private String opt10Criteria;
    private TemplateCriteria criteria10 = null;

    /**
     * Creates a new instance of MarkingForms
     */
    public MarkingForms() {

    }

    public Integer getSelectedMarkForms() {

        return selectedMarkForms;
    }

    public void setSelectedMarkForms(Integer selectedMarkForms) {
        this.selectedMarkForms = selectedMarkForms;
    }

    public Map<String, Long> getMarkFormsList() {
        Map<String, Long> map = new HashMap<>();
        for (TemplateMarkForm markForm : markFormsFacade.findAll()) {//.forEach((markform) -> {
            map.put(markForm.getFormname(), markForm.getFormid());
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
        unitMarkForms.setFormId(markForms);
        UnitInstance unitInstance = unitinstanceFacade.find(selectedUnitinstance);
        unitMarkForms.setUnitInstance(unitInstance);
        unitMarkForms.setUnitId(unitInstance.getUnit());
        unitMarkFormsFacade.create(unitMarkForms);

        selectedMarkForms = null;
        selectedUnitinstance = null;
        unitMarkForms = null;

        return "index.xhtml";
    }

    public Integer getSelectedUnitinstance() {
        return selectedUnitinstance;
    }

    public void setSelectedUnitinstance(Integer selectedUnitinstance) {
        this.selectedUnitinstance = selectedUnitinstance;
    }

    public Integer getSelectedFormID() {
        return selectedFormID;
    }

    public void setSelectedFormID(Integer selectedFormID) {
        this.selectedFormID = selectedFormID;
    }

    public boolean isSelectedOptionLabel() {
        return selectedOptionLabel;
    }

    public void setSelectedOptionLabel(boolean selectedOptionLabel) {
        this.selectedOptionLabel = selectedOptionLabel;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * Retrieves all the forms in the Database and gets the FormName and
     * FormID,and puts them into a Map
     *
     * @return A map that contains FormName and FormID
     */
    public Map<String, Long> getFormList() {
        List<TemplateMarkForm> list = markFormsFacade.findAll();
        Map<String, Long> listOfName = new HashMap<>();
        for (TemplateMarkForm form : list) {
            listOfName.put(form.getFormname(), form.getFormid());
        }
        return listOfName;
    }

    private TemplateMarkForm form;

    /**
     * Create a map of Categories based on UniteCode and AcademicYear
     *
     * @return returns a map of Categories
     */
    public List<TemplateCategory> getCategories() {
        UnitInstance instance = unitinstanceFacade.findByUnitCodeAndAcademicYear(new Unit(unitCode), new Academicyear(academicYear));
        UnitMarkForms unitMarkForm = unitMarkFormsFacade.findByUnitInstance(instance);
        form = unitMarkForm.getFormId();
        categories = markFormCategoriesFacade.findByForm(form);
        return categories;
    }

    public void setCategories(
            List<TemplateCategory> categories) {
        this.categories = categories;
    }

    public List<CategoryOptions> getColumnLabels() {
        return categoryOptionsFacade.findColumnLabels();
    }

    public TemplateCategory getSelectedFormCategories() {
        return selectedFormCategories;
    }

    public String getSelectedCategory(TemplateCategory opt) {
        selectedFormCategories = opt;
        return "CategoryEdit";
    }

//    private String optionCriteria;
//
//    public void setOptionCriteria(CategoryOptions catOpt) {
//        TemplateCriteria value = catOpt.getCategoryOptionCriteriaList().get(0);
//        optionCriteria = (value.getCatOption().getOptId() > 4) ? value.setPositiveCriteria() : value.setNegativeCriteria();
//    }
//
//    public String getOptionCriteria() {
//        return optionCriteria;
//    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * Create a Map from a map of AcademinYears.The Map contains
     * AcademicYearName and AcademicYearID
     *
     * @return returns the AcademicYearName and AcademicYearID
     */
    public Map<String, Integer> getAcademicYears() {
        Map<String, Integer> map = new HashMap<>();
        for (Academicyear academicYear : academicyearFacade.findAll()) { //.forEach((academicYear) -> {
            map.put(academicYear.getAcademicYearName(), academicYear.getIdacademicyear());
        };
        return map;
    }

    /**
     * Create and returns a Map Containing Unitcode,UnitTitle and UnitCode in
     * the form "UnitCode - UnitTitle" and "UnitCode"
     *
     * @return returns a Map of UnitCodes
     */
    public Map<String, String> getUnitCodes() {
        Map<String, String> map = new HashMap<>();
        for (Unit unit : unitFacade.findAll()) { //.forEach((unit) -> {
            map.put(unit.getUnitcode() + "-" + unit.getUnittitle(), unit.getUnitcode());
        };
        return map;
    }

    /**
     * Generate a list of Column Labels
     *
     * @return returns a List of CategoryOptions
     */
    public List<CategoryOptions> getColumnLabelsList() {
        if (columnLabelsList == null) {
            columnLabelsList = categoryOptionsFacade.findColumnLabels();
        }
        return columnLabelsList;
    }

    public void setColumnLabelsList(List<CategoryOptions> columnLabelsList) {
        this.columnLabelsList = columnLabelsList;
    }

    /**
     * Gets the Criteria from the Database, If isUpdate is true(ie We are trying
     * to Update an existing record) otherwise it returns the current value of
     * the field.
     *
     * @return returns a string representation of the Criteria(Positive for any
     * one with CategoryOptionID greater than 5 otherwise negative)
     *
     */
    private void generateCriteria(CategoryOptions opt, TemplateCriteria criteria, String optCriteria) {
        isIsUpdate();
        if (isUpdate) {
            criteria = categoryOptionCriteriaFacade.findCatOptionCriteriaByCatOptAndOptID(opt, selectedFormCategories.getCatid());
            optCriteria = criteria.getCatOption().getOptId() > 4 ? criteria.getPositiveCriteria() : criteria.getNegativeCriteria();
        }
    }

    public CategoryOptions getOpt1() {
        getColumnLabelsList();
        if (opt1 == null) {
            opt1 = columnLabelsList.get(0);
        }
        return opt1;
    }

    public void setOpt1(CategoryOptions opt1) {
        this.opt1 = opt1;
    }

    public String getOpt1Criteria() {
        generateCriteria(opt1, criteria1, opt1Criteria);
        return opt1Criteria;
    }

    public void setOpt1Criteria(String opt1Criteria) {
        this.opt1Criteria = opt1Criteria;
    }

    public CategoryOptions getOpt2() {
        getColumnLabelsList();
        if (opt2 == null) {
            opt2 = columnLabelsList.get(1);
        }
        return opt2;
    }

    public void setOpt2(CategoryOptions opt2) {
        this.opt2 = opt2;
    }

    public String getOpt2Criteria() {
        generateCriteria(opt2, criteria2, opt2Criteria);
        return opt2Criteria;
    }

    public void setOpt2Criteria(String opt2Criteria) {
        this.opt2Criteria = opt2Criteria;
    }

    public CategoryOptions getOpt3() {
        getColumnLabelsList();
        if (opt3 == null) {
            opt3 = columnLabelsList.get(2);
        }
        return opt3;
    }

    public void setOpt3(CategoryOptions opt3) {
        this.opt3 = opt3;
    }

    public String getOpt3Criteria() {
        generateCriteria(opt3, criteria3, opt3Criteria);
        return opt3Criteria;
    }

    public void setOpt3Criteria(String opt3Criteria) {
        this.opt3Criteria = opt3Criteria;
    }

    public CategoryOptions getOpt4() {
        getColumnLabelsList();
        if (opt4 == null) {
            opt4 = columnLabelsList.get(3);
        }
        return opt4;
    }

    public void setOpt4(CategoryOptions opt4) {
        this.opt4 = opt4;
    }

    public String getOpt4Criteria() {
        generateCriteria(opt4, criteria4, opt4Criteria);
        return opt4Criteria;
    }

    public void setOpt4Criteria(String opt4Criteria) {
        this.opt4Criteria = opt4Criteria;
    }

    public CategoryOptions getOpt5() {
        getColumnLabelsList();
        if (opt5 == null) {
            opt5 = columnLabelsList.get(4);
        }
        return opt5;
    }

    public void setOpt5(CategoryOptions opt5) {
        this.opt5 = opt5;
    }

    public String getOpt5Criteria() {
        generateCriteria(opt5, criteria5, opt5Criteria);
        return opt5Criteria;
    }

    public void setOpt5Criteria(String opt5Criteria) {
        this.opt5Criteria = opt5Criteria;
    }

    public CategoryOptions getOpt6() {
        getColumnLabelsList();
        if (opt6 == null) {
            opt6 = columnLabelsList.get(5);
        }
        return opt6;
    }

    public void setOpt6(CategoryOptions opt6) {
        this.opt6 = opt6;
    }

    public String getOpt6Criteria() {
        generateCriteria(opt6, criteria6, opt6Criteria);
        return opt6Criteria;
    }

    public void setOpt6Criteria(String opt6Criteria) {
        this.opt6Criteria = opt6Criteria;
    }

    public CategoryOptions getOpt7() {
        getColumnLabelsList();
        if (opt7 == null) {
            opt7 = columnLabelsList.get(6);
        }
        return opt7;
    }

    public void setOpt7(CategoryOptions opt7) {
        this.opt7 = opt7;
    }

    public String getOpt7Criteria() {
        generateCriteria(opt7, criteria7, opt7Criteria);
        return opt7Criteria;
    }

    public void setOpt7Criteria(String opt7Criteria) {
        this.opt7Criteria = opt7Criteria;
    }

    public CategoryOptions getOpt8() {
        getColumnLabelsList();
        if (opt8 == null) {
            opt8 = columnLabelsList.get(7);
        }
        return opt8;
    }

    public void setOpt8(CategoryOptions opt8) {
        this.opt8 = opt8;
    }

    public String getOpt8Criteria() {
        generateCriteria(opt8, criteria8, opt8Criteria);
        return opt8Criteria;
    }

    public void setOpt8Criteria(String opt8Criteria) {
        this.opt8Criteria = opt8Criteria;
    }

    public CategoryOptions getOpt9() {
        getColumnLabelsList();
        if (opt9 == null) {
            opt9 = columnLabelsList.get(8);
        }
        return opt9;
    }

    public void setOpt9(CategoryOptions opt9) {
        this.opt9 = opt9;
    }

    public String getOpt9Criteria() {
        generateCriteria(opt9, criteria9, opt9Criteria);
        return opt9Criteria;
    }

    public void setOpt9Criteria(String opt9Criteria) {
        this.opt9Criteria = opt9Criteria;
    }

    public CategoryOptions getOpt10() {
        getColumnLabelsList();
        if (opt10 == null) {
            opt10 = columnLabelsList.get(9);
        }
        return opt10;
    }

    public void setOpt10(CategoryOptions opt10) {
        this.opt10 = opt10;
    }

    public String getOpt10Criteria() {
        generateCriteria(opt10, criteria10, opt10Criteria);
        return opt10Criteria;
    }

    public void setOpt10Criteria(String opt10Criteria) {
        this.opt10Criteria = opt10Criteria;
    }

    /**
     * This handles the action of Update button. It Update MarkCategory ,
 TemplateCategory and TemplateCriteria
     *
     * @return
     */
    public String updateCriteria() {

        choosePositiveOrNegative(criteria1, opt1Criteria);
        choosePositiveOrNegative(criteria2, opt2Criteria);
        choosePositiveOrNegative(criteria3, opt3Criteria);
        choosePositiveOrNegative(criteria4, opt4Criteria);
        choosePositiveOrNegative(criteria5, opt5Criteria);
        choosePositiveOrNegative(criteria6, opt6Criteria);
        choosePositiveOrNegative(criteria7, opt7Criteria);
        choosePositiveOrNegative(criteria8, opt8Criteria);
        choosePositiveOrNegative(criteria9, opt9Criteria);
        choosePositiveOrNegative(criteria10, opt10Criteria);

        //This Update MarkCategory and TemplateCategory
        markCategoriesFacade.edit(selectedFormCategories.getCatid());
        markFormCategoriesFacade.edit(selectedFormCategories);

        //This Update TemplateCriteria table
        categoryOptionCriteriaFacade.edit(criteria1);
        categoryOptionCriteriaFacade.edit(criteria2);
        categoryOptionCriteriaFacade.edit(criteria3);
        categoryOptionCriteriaFacade.edit(criteria4);
        categoryOptionCriteriaFacade.edit(criteria5);
        categoryOptionCriteriaFacade.edit(criteria6);
        categoryOptionCriteriaFacade.edit(criteria7);
        categoryOptionCriteriaFacade.edit(criteria8);
        categoryOptionCriteriaFacade.edit(criteria9);
        categoryOptionCriteriaFacade.edit(criteria10);
        return "MarkingFormsView";
    }

    public void choosePositiveOrNegative(TemplateCriteria cat, String catOption) {
        switch (cat.getCatOption().getOptId()) {
            case 1:
            case 2:
            case 3:
            case 4:
                cat.setNegativeCriteria(catOption);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                cat.setPositiveCriteria(catOption);
                break;
        }
    }

    /**
     * This property is used to retrieve the value of a hidden form component in
     * MarkingFormsView page.This component helps us to distinguish between when
     * we are creating and Updating record.
     *
     * @return It returns true if Edit button is clicked
     */
    public boolean isIsUpdate() {
        isUpdate = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("isUpdate"));

        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    /**
     * Handles the action of new Button. It clears all the data in the
     * properties created, in other to make them ready to take fresh data. This
     * is because MarkingForms bean is a session bean and so it maintain its
     * state.
     *
     * @return returns the new Page to be navigated to.
     */
    public String newCategoryOptionForm() {
        setIsUpdate(false);
        markCategories = new MarkCategory();
        selectedFormCategories = new TemplateCategory();
        selectedFormCategories.setCatid(markCategories);
        opt1Criteria = null;
        opt2Criteria = null;
        opt3Criteria = null;
        opt4Criteria = null;
        opt5Criteria = null;
        opt6Criteria = null;
        opt7Criteria = null;
        opt8Criteria = null;
        opt9Criteria = null;
        opt10Criteria = null;

        return "CreateCategoryOptionForm";
    }

    /**
     *
     * Create new TemplateCriteria for both Positive and negative.And also
 set the Criteria value 1 to 4 are negative while 5 to 10 are positive.
     *
     * @return Return s the page to be navigated to.
     */
    public String registerNewCategoryOptionForm() {

        //Negative Criteria
        criteria1 = new TemplateCriteria();
        criteria1.setNegativeCriteria(opt2Criteria);
        criteria2 = new TemplateCriteria();
        criteria2.setNegativeCriteria(opt2Criteria);
        criteria3 = new TemplateCriteria();
        criteria3.setNegativeCriteria(opt3Criteria);
        criteria4 = new TemplateCriteria();
        criteria4.setNegativeCriteria(opt4Criteria);
        //Positive Criteria
        criteria5 = new TemplateCriteria();
        criteria5.setPositiveCriteria(opt5Criteria);
        criteria6 = new TemplateCriteria();
        criteria6.setPositiveCriteria(opt6Criteria);
        criteria7 = new TemplateCriteria();
        criteria7.setPositiveCriteria(opt7Criteria);
        criteria8 = new TemplateCriteria();
        criteria8.setPositiveCriteria(opt8Criteria);
        criteria9 = new TemplateCriteria();
        criteria9.setPositiveCriteria(opt9Criteria);
        criteria10 = new TemplateCriteria();
        criteria10.setPositiveCriteria(opt10Criteria);

        /*
         This retrieves the CategoryOptionGroup
         and Creates a new Mark Categories
         */
        markCategories.setCogId(opt1.getOptGroup());
        markCategoriesFacade.create(markCategories);

        //This section create MarkFormCategory Based on a formID and CategoryID
        selectedFormCategories.setFormid(form);//categories.get(0).getFormid());
        selectedFormCategories.setCatid(markCategories);
        markFormCategoriesFacade.create(selectedFormCategories);

        //This sets the category Options and MarkCategory
        //For each Criteria
        criteria1.setMarkCategory(markCategories);
        criteria1.setCatOption(opt1);
        criteria2.setMarkCategory(markCategories);
        criteria2.setCatOption(opt2);
        criteria3.setMarkCategory(markCategories);
        criteria3.setCatOption(opt3);
        criteria4.setMarkCategory(markCategories);
        criteria4.setCatOption(opt4);
        criteria5.setMarkCategory(markCategories);
        criteria5.setCatOption(opt5);
        criteria6.setMarkCategory(markCategories);
        criteria6.setCatOption(opt6);
        criteria7.setMarkCategory(markCategories);
        criteria7.setCatOption(opt7);
        criteria8.setMarkCategory(markCategories);
        criteria8.setCatOption(opt8);
        criteria9.setMarkCategory(markCategories);
        criteria9.setCatOption(opt9);
        criteria10.setMarkCategory(markCategories);
        criteria10.setCatOption(opt10);

        //This registers the new Criteria into the DB
        categoryOptionCriteriaFacade.create(criteria1);
        categoryOptionCriteriaFacade.create(criteria2);
        categoryOptionCriteriaFacade.create(criteria3);
        categoryOptionCriteriaFacade.create(criteria4);
        categoryOptionCriteriaFacade.create(criteria5);
        categoryOptionCriteriaFacade.create(criteria6);
        categoryOptionCriteriaFacade.create(criteria7);
        categoryOptionCriteriaFacade.create(criteria8);
        categoryOptionCriteriaFacade.create(criteria9);
        categoryOptionCriteriaFacade.create(criteria10);
        return "MarkingFormsView";
    }

    /**
     * Handles the action of the delete button on MarkingFormsView page.
     *
     * @param formCategories An Object of type TemplateCategory is needed
     * @return If it execte successfully it returns the next page to navigate
     * to.
     */
    public String deleteAction(TemplateCategory formCategories) {
        /*
         This line creates an array of CategoryOptions
         So that using a foreach loop we can loop through
         All the CategoryOptions and delete the TemplateCriteria defined
         For each of them
         */
        CategoryOptions[] myCatOpts = new CategoryOptions[]{opt1, opt2, opt3, opt4, opt5, opt6, opt7, opt8, opt9, opt10};
        for (CategoryOptions catOpt : myCatOpts) {
            List<TemplateCriteria> categoryOptionCriteriaList = categoryOptionCriteriaFacade.findCatOptionCriteriaListByCatOptAndOptID(catOpt, formCategories.getCatid());
            //Delete CategoryOptionCriterail
            for (TemplateCriteria criterion : categoryOptionCriteriaList) {//.forEach((criteria) -> {
                categoryOptionCriteriaFacade.remove(criterion);
            };
        }
        markFormCategoriesFacade.remove(new TemplateCategory(formCategories.getMfcid()));
        markCategoriesFacade.remove(formCategories.getCatid());

        //This will empty the map and reload it again
        categories = null;
        setCategories(getCategories());
        return "markingForms";
    }
}
