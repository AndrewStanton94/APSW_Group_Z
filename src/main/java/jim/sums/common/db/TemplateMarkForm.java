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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dimitar
 */
@Entity
// @Table(name = "TEMPLATE_MARK_FORM")
@NamedQueries({
    @NamedQuery(name = "TemplateMarkForm.findAll", query = "SELECT tmf FROM TemplateMarkForm tmf"),
    @NamedQuery(name = "TemplateMarkForm.findByFormid", query = "SELECT tmf FROM TemplateMarkForm tmf WHERE tmf.formid = :formid"),
    @NamedQuery(name = "TemplateMarkForm.findByFormname", query = "SELECT tmf FROM TemplateMarkForm tmf WHERE tmf.formname = :formname")
   // @NamedQuery(name = "TemplateMarkForm.findByFormNameAndAcademicYear", query = "SELECT ")
})
public class TemplateMarkForm implements Serializable {
    @OneToMany(mappedBy = "formId")
    private List<UnitMarkForms> unitMarkFormsList;
    @OneToMany(mappedBy = "formid")
    private List<TemplateCategory> markFormCategoriesList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    // @Column(name = "FORMID")
    private Long formid;
    @Size(max = 255)
    // @Column(name = "FORMNAME")
    private String formname;
    @OrderBy("mfcid")
    @OneToMany(mappedBy = "tmf")
    private List<TemplateWeightCategory> weights;
    @OneToMany(mappedBy = "tmf")
    private List<Finalproject> projects;

    public List<TemplateWeightCategory> getWeights() {
        return weights;
    }

    public void setWeights(List<TemplateWeightCategory> weights) {
        this.weights = weights;
    }

    public List<Finalproject> getProjects() {
        return projects;
    }

    public void setProjects(List<Finalproject> projects) {
        this.projects = projects;
    }

    public TemplateMarkForm() {
    }

    public TemplateMarkForm(Long formid) {
        this.formid = formid;
    }

    public TemplateMarkForm(Long formid, String formname) {
        this.formid = formid;
        this.formname = formname;
    }

    public Long getFormid() {
        return formid;
    }

    public void setFormid(Long formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formid != null ? formid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateMarkForm)) {
            return false;
        }
        TemplateMarkForm other = (TemplateMarkForm) object;
        if ((this.formid == null && other.formid != null) || (this.formid != null && !this.formid.equals(other.formid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.TemplateMarkForm[ formid=" + formid + " ]";
    }

    public List<TemplateCategory> getMarkFormCategoriesList() {
        return markFormCategoriesList;
    }

    public void setMarkFormCategoriesList(List<TemplateCategory> markFormCategoriesList) {
        this.markFormCategoriesList = markFormCategoriesList;
    }

    @XmlTransient
    public List<UnitMarkForms> getUnitMarkFormsList() {
        return unitMarkFormsList;
    }

    public void setUnitMarkFormsList(List<UnitMarkForms> unitMarkFormsList) {
        this.unitMarkFormsList = unitMarkFormsList;
    }
    
}
