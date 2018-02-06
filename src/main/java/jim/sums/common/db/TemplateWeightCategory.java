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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Dimitar
 */
@Entity
// @Table(name = "TEMPLATE_WEIGHT_CATEGORY")
@NamedQueries({
    @NamedQuery(name = "TemplateWeightCategory.findAll", query = "SELECT twc FROM TemplateWeightCategory twc"),
    @NamedQuery(name = "TemplateWeightCategory.findByMfcid", query = "SELECT twc FROM TemplateWeightCategory twc WHERE twc.mfcid = :mfcid"),
    @NamedQuery(name = "TemplateWeightCategory.findByCatindex", query = "SELECT twc FROM TemplateWeightCategory twc WHERE twc.catid = :catid"),
//    @NamedQuery(name = "TemplateWeightCategory.findByCatweight", query = "SELECT twc FROM TemplateWeightCategory twc WHERE twc.weightId = :weightId"),
    @NamedQuery(name = "TemplateWeightCategory.findByCritical", query = "SELECT twc FROM TemplateWeightCategory twc WHERE twc.critical = :critical"),
    @NamedQuery(name = "TemplateWeightCategory.findByOptional", query = "SELECT twc FROM TemplateWeightCategory twc WHERE twc.optional = :optional")})
public class TemplateWeightCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    // @Column(name = "MFCID")
    private Long mfcid;
    // @Column(name = "CATINDEX")
//    private Integer catindex;
    // @Column(name = "CATWEIGHT")
    private Integer weightValue;
    // @Column(name = "CRITICAL")
    private Character critical;
    // @Column(name = "OPTIONAL")
    private Character optional;
    // @JoinColumn(name = "FORMID", referencedColumnName = "FORMID")
    @ManyToOne
    private TemplateMarkForm formid;
    // @JoinColumn(name = "CATID", referencedColumnName = "CATID")
    @ManyToOne
    private MarkCategory catid;
    @ManyToOne
    private TemplateMarkForm tmf;
    @OneToMany(mappedBy = "templateWeightCategory")
    private List<TemplateCategory> categories;
    @ManyToMany
    private List<CategoryMark> categoryMarks;
    
    public TemplateWeightCategory() {};
    
//    public TemplateWeightCategory(Integer weightValue, MarkCategory catid) {
//        this.weightValue = weightValue;
//        this.catid = catid;
//    }

    public TemplateWeightCategory(Integer weightValue) {
        this.weightValue = weightValue;
    }

    public TemplateMarkForm getTmf() {
        return tmf;
    }

    public void setTmf(TemplateMarkForm tmf) {
        this.tmf = tmf;
    }

    public List<TemplateCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TemplateCategory> categories) {
        this.categories = categories;
    }

    public List<CategoryMark> getCategoryMarks() {
        return categoryMarks;
    }

    public void setCategoryMarks(List<CategoryMark> categoryMarks) {
        this.categoryMarks = categoryMarks;
    }

    public TemplateWeightCategory(Long mfcid) {
        this.mfcid = mfcid;
    }

    public Long getMfcid() {
        return mfcid;
    }

    public void setMfcid(Long mfcid) {
        this.mfcid = mfcid;
    }

//    public Integer getWeightId() {
//        return weightId;
//    }
//
//    public void setWeightId(Integer weightId) {
//        this.weightId = weightId;
//    }

    public Integer getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Integer weightValue) {
        this.weightValue = weightValue;
    }

    public Character getCritical() {
        return critical;
    }

    public void setCritical(Character critical) {
        this.critical = critical;
    }

    public Character getOptional() {
        return optional;
    }

    public void setOptional(Character optional) {
        this.optional = optional;
    }

    public TemplateMarkForm getFormid() {
        return formid;
    }

    public void setFormid(TemplateMarkForm formid) {
        this.formid = formid;
    }

    public MarkCategory getCatid() {
        return catid;
    }

    public void setCatid(MarkCategory catid) {
        this.catid = catid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mfcid != null ? mfcid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateWeightCategory)) {
            return false;
        }
        TemplateWeightCategory other = (TemplateWeightCategory) object;
        if ((this.mfcid == null && other.mfcid != null) || (this.mfcid != null && !this.mfcid.equals(other.mfcid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.TemplateWeightCategory[ mfcid=" + mfcid + " ]";
    }
    
}
