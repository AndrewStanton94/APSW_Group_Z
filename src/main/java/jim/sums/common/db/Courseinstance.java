/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "COURSEINSTANCE")
@NamedQueries({
    @NamedQuery(name = "Courseinstance.findAll", query = "SELECT c FROM Courseinstance c"),
    @NamedQuery(name = "Courseinstance.findByCourse", query = "SELECT c FROM Courseinstance c where c.course=:course"),
    @NamedQuery(name = "Courseinstance.findByIdcourseinstance", query = "SELECT c FROM Courseinstance c WHERE c.idcourseinstance = :idcourseinstance"),
    @NamedQuery(name = "Courseinstance.findByCourseinstancename", query = "SELECT c FROM Courseinstance c WHERE c.courseinstancename = :courseinstancename"),
    @NamedQuery(name = "Courseinstance.findByCourselevel", query = "SELECT c FROM Courseinstance c WHERE c.courselevel = :courselevel")})
public class Courseinstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCOURSEINSTANCE")
    private Integer idcourseinstance;
    @Size(max = 64)
    @Column(name = "COURSEINSTANCENAME")
    private String courseinstancename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COURSELEVEL")
    private int courselevel;
    @JoinTable(name = "CONSISTOF", joinColumns = {
        @JoinColumn(name = "IDCOURSEINSTANCE", referencedColumnName = "IDCOURSEINSTANCE")}, inverseJoinColumns = {
        @JoinColumn(name = "IDUNITINSTANCE", referencedColumnName = "IDUNITINSTANCE")})
    @ManyToMany
    private List<UnitInstance> unitinstances;
    @JoinColumn(name = "COURSE", referencedColumnName = "COURSECODE")
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "ACADEMICYEAR", referencedColumnName = "IDACADEMICYEAR")
    @ManyToOne(optional = false)
    private Academicyear academicyear;

    public Courseinstance() {
    }

    public Courseinstance(Integer idcourseinstance) {
        this.idcourseinstance = idcourseinstance;
    }

    public Courseinstance(Integer idcourseinstance, int courselevel) {
        this.idcourseinstance = idcourseinstance;
        this.courselevel = courselevel;
    }

    public Courseinstance(int courselevel, Course course, Academicyear academicyear) {
        this.courselevel = courselevel;
        this.course = course;
        this.academicyear = academicyear;
    }

    public Integer getIdcourseinstance() {
        return idcourseinstance;
    }

    public void setIdcourseinstance(Integer idcourseinstance) {
        this.idcourseinstance = idcourseinstance;
    }

    public String getCourseinstancename() {
        return courseinstancename;
    }

    public void setCourseinstancename(String courseinstancename) {
        this.courseinstancename = courseinstancename;
    }

    public int getCourselevel() {
        return courselevel;
    }

    public void setCourselevel(int courselevel) {
        this.courselevel = courselevel;
    }

    public List<UnitInstance> getUnitinstances() {
        return unitinstances;
    }

    public void setUnitinstances(List<UnitInstance> unitinstanceList) {
        this.unitinstances = unitinstanceList;
    }

    public void addUnitInstance(UnitInstance ui) {
        if (unitinstances == null) {
            unitinstances = new ArrayList<UnitInstance>();
        }
        unitinstances.add(ui);
    }
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Academicyear getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(Academicyear academicyear) {
        this.academicyear = academicyear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcourseinstance != null ? idcourseinstance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Courseinstance)) {
            return false;
        }
        Courseinstance other = (Courseinstance) object;
        if ((this.idcourseinstance == null && other.idcourseinstance != null) || (this.idcourseinstance != null && !this.idcourseinstance.equals(other.idcourseinstance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Courseinstance[ idcourseinstance=" + idcourseinstance + " ]";
    }
}
