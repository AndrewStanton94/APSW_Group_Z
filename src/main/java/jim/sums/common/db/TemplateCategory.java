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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Dimitar
 */
@Entity
//@Table(name = "TEMPLATE_CATEGORY")
@NamedQueries({
    @NamedQuery(name = "TemplateCategory.findAll", query = "SELECT tc FROM TemplateCategory tc"),
    @NamedQuery(name = "TemplateCategory.findByMfcid", query = "SELECT tc FROM TemplateCategory tc WHERE tc.mfcid = :mfcid"),
    @NamedQuery(name = "TemplateCategory.findByCatindex", query = "SELECT tc FROM TemplateCategory tc WHERE tc.catindex = :catindex"),
    @NamedQuery(name = "TemplateCategory.findByCatweight", query = "SELECT tc FROM TemplateCategory tc WHERE tc.templateWeightCategory = :templateWeightCategory"),
    @NamedQuery(name = "TemplateCategory.findByCritical", query = "SELECT tc FROM TemplateCategory tc WHERE tc.critical = :critical"),
    @NamedQuery(name = "TemplateCategory.findByOptional", query = "SELECT tc FROM TemplateCategory tc WHERE tc.optional = :optional")})
public class TemplateCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
//    @NotNull
    // @Column(name = "MFCID")
    private Long mfcid;
    // @Column(name = "CATINDEX")
    private Integer catindex;
    // @Column(name = "CATWEIGHT")
    @ManyToOne
    private TemplateWeightCategory templateWeightCategory;
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
    @OneToMany(mappedBy = "category")
    @OrderBy("optCriteriaId")
    private List<TemplateCriteria> criteria;

    public TemplateCategory() {
    }

    public TemplateCategory(Long mfcid) {
        this.mfcid = mfcid;
    }

    public List<TemplateCriteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<TemplateCriteria> criteria) {
        this.criteria = criteria;
    }

    public Long getMfcid() {
        return mfcid;
    }

    public void setMfcid(Long mfcid) {
        this.mfcid = mfcid;
    }

    public Integer getCatindex() {
        return catindex;
    }

    public void setCatindex(Integer catindex) {
        this.catindex = catindex;
    }

    public TemplateWeightCategory getTemplateWeightCategory() {
        return templateWeightCategory;
    }

    public void setTemplateWeightCategory(TemplateWeightCategory templateWeightCategory) {
        this.templateWeightCategory = templateWeightCategory;
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
        if (!(object instanceof TemplateCategory)) {
            return false;
        }
        TemplateCategory other = (TemplateCategory) object;
        if ((this.mfcid == null && other.mfcid != null) || (this.mfcid != null && !this.mfcid.equals(other.mfcid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.TemplateCategory[ mfcid=" + mfcid + " ]";
    }

}
