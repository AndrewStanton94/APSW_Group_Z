/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Deprecated

/**
 *
 * @author Dimitar
 */
@Entity
@Table(name = "CATEGORY_OPTION_GROUPS")
@NamedQueries({
    @NamedQuery(name = "CategoryOptionGroups.findAll", query = "SELECT c FROM CategoryOptionGroups c"),
    @NamedQuery(name = "CategoryOptionGroups.findByOptGroupId", query = "SELECT c FROM CategoryOptionGroups c WHERE c.optGroupId = :optGroupId"),
    @NamedQuery(name = "CategoryOptionGroups.findByOptGroupName", query = "SELECT c FROM CategoryOptionGroups c WHERE c.optGroupName = :optGroupName")})
public class CategoryOptionGroups implements Serializable {
    @OneToMany(mappedBy = "cogId")
    private List<MarkCategory> markCategoriesList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OPT_GROUP_ID")
    private Integer optGroupId;
    @Size(max = 255)
    @Column(name = "OPT_GROUP_NAME")
    private String optGroupName;
    @OneToMany(mappedBy = "optGroup")
    private List<CategoryOptions> categoryOptionsList;

    public CategoryOptionGroups() {
    }

    public CategoryOptionGroups(Integer optGroupId) {
        this.optGroupId = optGroupId;
    }

    public CategoryOptionGroups(Integer optGroupId, String optGroupName) {
        this.optGroupId = optGroupId;
        this.optGroupName = optGroupName;
    }

    public Integer getOptGroupId() {
        return optGroupId;
    }

    public void setOptGroupId(Integer optGroupId) {
        this.optGroupId = optGroupId;
    }

    public String getOptGroupName() {
        return optGroupName;
    }

    public void setOptGroupName(String optGroupName) {
        this.optGroupName = optGroupName;
    }

    public List<CategoryOptions> getCategoryOptionsList() {
        return categoryOptionsList;
    }

    public void setCategoryOptionsList(List<CategoryOptions> categoryOptionsList) {
        this.categoryOptionsList = categoryOptionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optGroupId != null ? optGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryOptionGroups)) {
            return false;
        }
        CategoryOptionGroups other = (CategoryOptionGroups) object;
        if ((this.optGroupId == null && other.optGroupId != null) || (this.optGroupId != null && !this.optGroupId.equals(other.optGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.CategoryOptionGroups[ optGroupId=" + optGroupId + " ]";
    }

    @XmlTransient
    public List<MarkCategory> getMarkCategoriesList() {
        return markCategoriesList;
    }

    public void setMarkCategoriesList(List<MarkCategory> markCategoriesList) {
        this.markCategoriesList = markCategoriesList;
    }
    
}
