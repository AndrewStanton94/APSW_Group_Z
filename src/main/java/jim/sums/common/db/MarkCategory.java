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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dimitar
 */
@Entity
@Table(name = "MARK_CATEGORY")
@NamedQueries({
    @NamedQuery(name = "MarkCategory.findAll", query = "SELECT mc FROM MarkCategory mc"),
    @NamedQuery(name = "MarkCategory.findByCatid", query = "SELECT mc FROM MarkCategory mc WHERE mc.catid = :catid"),
    @NamedQuery(name = "MarkCategory.findByCatdescription", query = "SELECT mc FROM MarkCategory mc WHERE mc.catdescription = :catdescription"),
    @NamedQuery(name = "MarkCategory.findByCatlongname", query = "SELECT mc FROM MarkCategory mc WHERE mc.catlongname = :catlongname"),
    @NamedQuery(name = "MarkCategory.findByCatname", query = "SELECT mc FROM MarkCategory mc WHERE mc.catname = :catname"),
    @NamedQuery(name = "MarkCategory.findByOptionType", query = "SELECT mc FROM MarkCategory mc WHERE mc.optionType = :optionType")})
public class MarkCategory implements Serializable {
//    @JoinColumn(name = "COG_ID", referencedColumnName = "OPT_GROUP_ID")
    @ManyToOne
    private CategoryOptionGroups cogId;
    @OneToMany(mappedBy = "catid")
    private List<TemplateCategory> markFormCategoriesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    //@NotNull
    @Column(name = "CATID")
    private Long catid;
    @Size(max = 255)
    @Column(name = "CATDESCRIPTION")
    private String catdescription;
    @Size(max = 255)
    @Column(name = "CATLONGNAME")
    private String catlongname;
    @Size(max = 255)
    @Column(name = "CATNAME")
    private String catname;
    @Column(name = "OPTION_TYPE")
    private Character optionType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catId")
    private List<CategoryMarksOption> categoryMarksOptionList;
    @OneToMany(mappedBy = "markCategory")
    private List<TemplateCriteria> categoryOptionCriteriaList;
    @OneToMany(mappedBy = "catId")
    private List<CategoryMark> categoryMarksList;

    public MarkCategory() {
    }

    public MarkCategory(Long catid) {
        this.catid = catid;
    }

    public Long getCatid() {
        return catid;
    }

    public void setCatid(Long catid) {
        this.catid = catid;
    }

    public String getCatdescription() {
        return catdescription;
    }

    public void setCatdescription(String catdescription) {
        this.catdescription = catdescription;
    }

    public String getCatlongname() {
        return catlongname;
    }

    public void setCatlongname(String catlongname) {
        this.catlongname = catlongname;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Character getOptionType() {
        return optionType;
    }

    public void setOptionType(Character optionType) {
        this.optionType = optionType;
    }

    public List<CategoryMarksOption> getCategoryMarksOptionList() {
        return categoryMarksOptionList;
    }

    public void setCategoryMarksOptionList(List<CategoryMarksOption> categoryMarksOptionList) {
        this.categoryMarksOptionList = categoryMarksOptionList;
    }

    public List<TemplateCriteria> getCategoryOptionCriteriaList() {
        return categoryOptionCriteriaList;
    }

    public void setCategoryOptionCriteriaList(List<TemplateCriteria> categoryOptionCriteriaList) {
        this.categoryOptionCriteriaList = categoryOptionCriteriaList;
    }

    public List<CategoryMark> getCategoryMarksList() {
        return categoryMarksList;
    }

    public void setCategoryMarksList(List<CategoryMark> categoryMarksList) {
        this.categoryMarksList = categoryMarksList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catid != null ? catid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarkCategory)) {
            return false;
        }
        MarkCategory other = (MarkCategory) object;
        if ((this.catid == null && other.catid != null) || (this.catid != null && !this.catid.equals(other.catid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.MarkCategory[ catid=" + catid + " ]";
    }

    public CategoryOptionGroups getCogId() {
        return cogId;
    }

    public void setCogId(CategoryOptionGroups cogId) {
        this.cogId = cogId;
    }

    public List<TemplateCategory> getMarkFormCategoriesList() {
        return markFormCategoriesList;
    }

    public void setMarkFormCategoriesList(List<TemplateCategory> markFormCategoriesList) {
        this.markFormCategoriesList = markFormCategoriesList;
    }
    
}
