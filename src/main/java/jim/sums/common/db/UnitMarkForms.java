/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cemen
 */
@Entity
//@Table(name = "UNIT_MARK_FORMS")
@NamedQueries({
    @NamedQuery(name = "UnitMarkForms.findAll", query = "SELECT u FROM UnitMarkForms u"),
    @NamedQuery(name = "UnitMarkForms.findByUmfid", query = "SELECT u FROM UnitMarkForms u WHERE u.umfid = :umfid")})
public class UnitMarkForms implements Serializable {
//    @JoinColumn(name = "UNIT_INS_ID", referencedColumnName = "IDUNITINSTANCE")
    @ManyToOne
    private UnitInstance unitInstance;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
//    @Column(name = "UMFID")
    private Integer umfid;
//    @JoinColumn(name = "UNIT_ID", referencedColumnName = "UNITCODE")
    @ManyToOne(optional = false)
    private Unit unitId;
//    @JoinColumn(name = "FORM_ID", referencedColumnName = "FORMID")
    @ManyToOne(optional = false)
    private TemplateMarkForm formId;

    public UnitMarkForms() {
    }

    public UnitMarkForms(Integer umfid) {
        this.umfid = umfid;
    }

    public Integer getUmfid() {
        return umfid;
    }

    public void setUmfid(Integer umfid) {
        this.umfid = umfid;
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
    }

    public TemplateMarkForm getFormId() {
        return formId;
    }

    public void setFormId(TemplateMarkForm formId) {
        this.formId = formId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (umfid != null ? umfid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnitMarkForms)) {
            return false;
        }
        UnitMarkForms other = (UnitMarkForms) object;
        if ((this.umfid == null && other.umfid != null) || (this.umfid != null && !this.umfid.equals(other.umfid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.UnitMarkForms[ umfid=" + umfid + " ]";
    }

    public UnitInstance getUnitInstance() {
        return unitInstance;
    }

    public void setUnitInstance(UnitInstance unitInsId) {
        this.unitInstance = unitInsId;
    }

}
