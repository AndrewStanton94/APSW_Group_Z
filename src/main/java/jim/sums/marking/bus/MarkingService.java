/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.marking.bus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.sums.common.db.CategoryOptionGroups;
import jim.sums.common.db.CategoryOptions;
import jim.sums.common.db.Finalproject;
import jim.sums.common.db.TemplateCategory;
import jim.sums.common.db.TemplateMarkForm;
import jim.sums.common.facade.CategoryMarkFacade;
import jim.sums.common.facade.CategoryMarksOptionFacade;
import jim.sums.common.facade.TemplateCriteriaFacade;
import jim.sums.common.facade.CategoryOptionGroupsFacade;
import jim.sums.common.facade.CategoryOptionsFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.common.facade.MarkCategoryFacade;
import jim.sums.common.facade.TemplateCategoryFacade;
import jim.sums.common.facade.TemplateMarkFormFacade;
import jim.sums.common.facade.MarkerTypeFacade;
import jim.sums.common.facade.MarkerMarkFacade;
import jim.sums.common.facade.UnitMarkFormsFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Stateless
public class MarkingService {
    @EJB
    private CategoryMarksOptionFacade categoryMarksOptionFacade;

    public CategoryMarksOptionFacade getCategoryMarksOptionFacade() {
        return categoryMarksOptionFacade;
    }

    public void setCategoryMarksOptionFacade(CategoryMarksOptionFacade categoryMarksOptionFacade) {
        this.categoryMarksOptionFacade = categoryMarksOptionFacade;
    }

    @EJB
    private MarkerMarkFacade marksFacade;
    @EJB
    private CategoryMarkFacade categoryMarksFacade;

    public CategoryMarkFacade getCategoryMarksFacade() {
        return categoryMarksFacade;
    }

    public void setCategoryMarksFacade(CategoryMarkFacade categoryMarksFacade) {
        this.categoryMarksFacade = categoryMarksFacade;
    }

    public TemplateCriteriaFacade getCocf() {
        return cocf;
    }

    public void setCocf(TemplateCriteriaFacade cocf) {
        this.cocf = cocf;
    }

    public CategoryOptionsFacade getCof() {
        return cof;
    }

    public void setCof(CategoryOptionsFacade cof) {
        this.cof = cof;
    }

    public CategoryOptionGroupsFacade getCogf() {
        return cogf;
    }

    public void setCogf(CategoryOptionGroupsFacade cogf) {
        this.cogf = cogf;
    }

    public FinalprojectFacade getFpf() {
        return fpf;
    }

    public void setFpf(FinalprojectFacade fpf) {
        this.fpf = fpf;
    }

    public MarkerMarkFacade getMarksFacade() {
        return marksFacade;
    }

    public void setMarksFacade(MarkerMarkFacade marksFacade) {
        this.marksFacade = marksFacade;
    }

    public MarkCategoryFacade getMcf() {
        return mcf;
    }

    public void setMcf(MarkCategoryFacade mcf) {
        this.mcf = mcf;
    }

    public TemplateCategoryFacade getMfcf() {
        return mfcf;
    }

    public void setMfcf(TemplateCategoryFacade mfcf) {
        this.mfcf = mfcf;
    }

    public TemplateMarkFormFacade getMff() {
        return mff;
    }

    public void setMff(TemplateMarkFormFacade mff) {
        this.mff = mff;
    }

    public MarkerTypeFacade getMtf() {
        return mtf;
    }

    public void setMtf(MarkerTypeFacade mtf) {
        this.mtf = mtf;
    }

    public UnitMarkFormsFacade getUmff() {
        return umff;
    }

    public void setUmff(UnitMarkFormsFacade umff) {
        this.umff = umff;
    }
    @EJB
    private TemplateMarkFormFacade mff;
    @EJB
    private UnitMarkFormsFacade umff;
    @EJB
    private FinalprojectFacade fpf;
    @EJB
    private TemplateCategoryFacade mfcf;
    @EJB
    private CategoryOptionGroupsFacade cogf;
    @EJB
    private TemplateCriteriaFacade cocf;
    @EJB
    private MarkerTypeFacade mtf;
    @EJB
    private CategoryOptionsFacade cof;
    @EJB
    private MarkCategoryFacade mcf;

    public MarkingService() {
    }

    public TemplateMarkFormFacade getMarkFormsFacade() {
        return mff;
    }

    public FinalprojectFacade getFinalprojectFacade() {
        return fpf;
    }

    public List<TemplateCategory> getFormRows(TemplateMarkForm form, Finalproject project) {
        List<TemplateCategory> rows = mfcf.findByForm(form);

        Collections.sort(rows, new Comparator<TemplateCategory>() {

            @Override
            public int compare(TemplateCategory t, TemplateCategory t1) {
                return t.getCatindex().compareTo(t1.getCatindex());
            }
        });


        for (TemplateCategory mfc : rows) {
            //incomplete
        }
        return rows;
    }

    public List<CategoryOptions> getOptionList(CategoryOptionGroups cog) {
        return cof.findByGroup(cog);
    }
}