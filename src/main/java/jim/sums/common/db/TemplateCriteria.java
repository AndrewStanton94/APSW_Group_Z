/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dimitar
 */
@Entity
// @Table(name = "TEMPLATE_CRITERIA")
@NamedQueries({
    @NamedQuery(name = "TemplateCriteria.findAll", query = "SELECT tc FROM TemplateCriteria tc"),
    @NamedQuery(name = "TemplateCriteria.findByOptCriteriaId", query = "SELECT tc FROM TemplateCriteria tc WHERE tc.optCriteriaId = :optCriteriaId"),
    @NamedQuery(name = "TemplateCriteria.findByNegativeCriteria", query = "SELECT tc FROM TemplateCriteria tc WHERE tc.negativeCriteria = :negativeCriteria"),
    @NamedQuery(name = "TemplateCriteria.findByPositiveCriteria", query = "SELECT tc FROM TemplateCriteria tc WHERE tc.positiveCriteria = :positiveCriteria"),
    @NamedQuery(name = "TemplateCriteria.findByCAT_OPT", query = "SELECT tc FROM TemplateCriteria tc WHERE tc.catOption = :catOption")
    })
public class TemplateCriteria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    // @Column(name = "OPT_CRITERIA_ID")
    private Long optCriteriaId;
    @Size(max = 255)
    // @Column(name = "NEGATIVE_CRITERIA")
    private String negativeCriteria;
    @Size(max = 255)
    // @Column(name = "POSITIVE_CRITERIA")
    private String positiveCriteria;
    // @JoinColumn(name = "MARK_CATEGORY", referencedColumnName = "CATID")
    @ManyToOne
    private MarkCategory markCategory;
    // @JoinColumn(name = "CAT_OPTION", referencedColumnName = "OPT_ID")
    @ManyToOne
    private CategoryOptions catOption;
    @ManyToOne
    private TemplateCategory category;
    @ManyToMany
    private List<CategoryMark> categoryMarks;

    public TemplateCriteria() {
    }

    public TemplateCriteria(Long optCriteriaId) {
        this.optCriteriaId = optCriteriaId;
    }

    public TemplateCategory getCategory() {
        return category;
    }

    public void setCategory(TemplateCategory category) {
        this.category = category;
    }

    public List<CategoryMark> getCategoryMarks() {
        return categoryMarks;
    }

    public void setCategoryMarks(List<CategoryMark> categoryMarks) {
        this.categoryMarks = categoryMarks;
    }

    public Long getOptCriteriaId() {
        return optCriteriaId;
    }

    public void setOptCriteriaId(Long optCriteriaId) {
        this.optCriteriaId = optCriteriaId;
    }

    public String getNegativeCriteria() {
        return negativeCriteria;
    }

    public void setNegativeCriteria(String negativeCriteria) {
        this.negativeCriteria = negativeCriteria;
    }

    public String getPositiveCriteria() {
        return positiveCriteria;
    }

    public void setPositiveCriteria(String positiveCriteria) {
        this.positiveCriteria = positiveCriteria;
    }

    public MarkCategory getMarkCategory() {
        return markCategory;
    }

    public void setMarkCategory(MarkCategory markCategory) {
        this.markCategory = markCategory;
    }

    public CategoryOptions getCatOption() {
        return catOption;
    }

    public void setCatOption(CategoryOptions catOption) {
        this.catOption = catOption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optCriteriaId != null ? optCriteriaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateCriteria)) {
            return false;
        }
        TemplateCriteria other = (TemplateCriteria) object;
        if ((this.optCriteriaId == null && other.optCriteriaId != null) || (this.optCriteriaId != null && !this.optCriteriaId.equals(other.optCriteriaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.TemplateCriteria[ optCriteriaId=" + optCriteriaId + " ]";
    }

    public String setPositiveCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String setNegativeCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
