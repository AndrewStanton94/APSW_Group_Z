/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Deprecated

/**
 *
 * @author Dimitar
 */
@Entity
@Table(name = "CATEGORY_OPTIONS")
@NamedQueries({
    @NamedQuery(name = "CategoryOptions.findAll", query = "SELECT c FROM CategoryOptions c"),
    @NamedQuery(name = "CategoryOptions.findByOptId", query = "SELECT c FROM CategoryOptions c WHERE c.optId = :optId"),
    @NamedQuery(name = "CategoryOptions.findByOptIndex", query = "SELECT c FROM CategoryOptions c WHERE c.optIndex = :optIndex"),
    @NamedQuery(name = "CategoryOptions.findByOptLabel", query = "SELECT c FROM CategoryOptions c WHERE c.optLabel = :optLabel"),
    @NamedQuery(name = "CategoryOptions.findByOptSelectedValue", query = "SELECT c FROM CategoryOptions c WHERE c.optSelectedValue = :optSelectedValue"),
    @NamedQuery(name = "CategoryOptions.findAllOptionLable", query="SELECT c.optLabel FROM CategoryOptions c")})
public class CategoryOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OPT_ID")
    private Integer optId;
    @Column(name = "OPT_INDEX")
    private Integer optIndex;
    @Size(max = 255)
    @Column(name = "OPT_LABEL")
    private String optLabel;
    @Column(name = "OPT_SELECTED_VALUE")
    private Integer optSelectedValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catOption")
    private List<TemplateCriteria> categoryOptionCriteriaList;
    @JoinColumn(name = "OPT_GROUP", referencedColumnName = "OPT_GROUP_ID")
    @ManyToOne
    private CategoryOptionGroups optGroup;

    public CategoryOptions() {
    }

    public CategoryOptions(Integer optId) {
        this.optId = optId;
    }
        public List<TemplateCriteria> getCategoryOptionCriteriaList() {
        return categoryOptionCriteriaList;
    }

    public void setCategoryOptionCriteriaList(List<TemplateCriteria> categoryOptionCriteriaList) {
        this.categoryOptionCriteriaList = categoryOptionCriteriaList;
    }

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public Integer getOptIndex() {
        return optIndex;
    }

    public void setOptIndex(Integer optIndex) {
        this.optIndex = optIndex;
    }

    public String getOptLabel() {
        return optLabel;
    }

    public void setOptLabel(String optLabel) {
        this.optLabel = optLabel;
    }

    public Integer getOptSelectedValue() {
        return optSelectedValue;
    }

    public void setOptSelectedValue(Integer optSelectedValue) {
        this.optSelectedValue = optSelectedValue;
    }

    public CategoryOptionGroups getOptGroup() {
        return optGroup;
    }

    public void setOptGroup(CategoryOptionGroups optGroup) {
        this.optGroup = optGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optId != null ? optId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryOptions)) {
            return false;
        }
        CategoryOptions other = (CategoryOptions) object;
        if ((this.optId == null && other.optId != null) || (this.optId != null && !this.optId.equals(other.optId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.CategoryOptions[ optId=" + optId + " ]";
    }
}
