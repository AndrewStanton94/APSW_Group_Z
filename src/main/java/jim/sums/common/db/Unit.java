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

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "UNIT")
@NamedQueries({
    @NamedQuery(name = "Unit.findAll", query = "SELECT u FROM Unit u"),
    @NamedQuery(name = "Unit.findByUnitcode", query = "SELECT u FROM Unit u WHERE u.unitcode = :unitcode"),
    @NamedQuery(name = "Unit.findByUnittitle", query = "SELECT u FROM Unit u WHERE u.unittitle = :unittitle"),
    @NamedQuery(name = "Unit.findByUnitcoursel", query = "SELECT u FROM Unit u, CourseLevel cl WHERE cl.id = :unitcl AND u.courseLevel = cl")})
public class Unit implements Serializable {
//    @JoinColumn(name = "COURSELEVEL_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private CourseLevel courselevelId;
//    @JoinColumn(name = "UNITKIND_IDKIND", referencedColumnName = "IDKIND")
//    @ManyToOne
//    private ProjectKind unitkindIdkind;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitId")
    private List<UnitMarkForms> unitMarkFormsList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "UNITCODE")
    private String unitcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "UNITTITLE")
    private String unittitle;
//    @JoinColumn(name = "UNITKIND", referencedColumnName = "IDKIND")
    @ManyToOne(optional = false)
    private ProjectKind unitkind;
//    @JoinColumn(name = "UNITGRADE", referencedColumnName = "IDGRADE")
    @ManyToOne(optional = false)
    private CourseLevel courseLevel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit")
    private List<UnitInstance> unitinstanceList;

    public Unit() {
    }

    public Unit(String unitcode) {
        this.unitcode = unitcode;
    }

    public Unit(String unitcode, String unittitle) {
        this.unitcode = unitcode;
        this.unittitle = unittitle;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getUnittitle() {
        return unittitle;
    }

    public void setUnittitle(String unittitle) {
        this.unittitle = unittitle;
    }

    public ProjectKind getUnitkind() {
        return unitkind;
    }

    public void setUnitkind(ProjectKind unitkind) {
        this.unitkind = unitkind;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevel courselevel) {
        this.courseLevel = courselevel;
    }

    public List<UnitInstance> getUnitinstanceList() {
        return unitinstanceList;
    }

    public void setUnitinstanceList(List<UnitInstance> unitinstanceList) {
        this.unitinstanceList = unitinstanceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unitcode != null ? unitcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unit)) {
            return false;
        }
        Unit other = (Unit) object;
        if ((this.unitcode == null && other.unitcode != null) || (this.unitcode != null && !this.unitcode.equals(other.unitcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Unit[ unitcode=" + unitcode + " ]";
    }

    public List<UnitMarkForms> getUnitMarkFormsList() {
        return unitMarkFormsList;
    }

    public void setUnitMarkFormsList(List<UnitMarkForms> unitMarkFormsList) {
        this.unitMarkFormsList = unitMarkFormsList;
    }

//    public CourseLevel getCourselevelId() {
//        return courselevelId;
//    }
//
//    public void setCourselevelId(CourseLevel courselevelId) {
//        this.courselevelId = courselevelId;
//    }
//
//    public ProjectKind getUnitkindIdkind() {
//        return unitkindIdkind;
//    }
//
//    public void setUnitkindIdkind(ProjectKind unitkindIdkind) {
//        this.unitkindIdkind = unitkindIdkind;
//    }
}
