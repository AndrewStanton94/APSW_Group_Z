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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "UNITINSTANCE")
@NamedQueries({
    @NamedQuery(name = "UnitInstance.findAll", query = "SELECT u FROM UnitInstance u"),
    @NamedQuery(name = "UnitInstance.findByUnit", query = "SELECT u FROM UnitInstance u where u.unit=:unit"),
    @NamedQuery(name = "UnitInstance.findByIdunitinstance", query = "SELECT u FROM UnitInstance u WHERE u.idunitinstance = :idunitinstance")})
public class UnitInstance implements Serializable {
    @OneToMany(mappedBy = "unitInstance")
    private List<UnitMarkForms> unitMarkFormsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUNITINSTANCE")
    private Integer idunitinstance;
    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "unitinstanceList")
    private List<Cohort> cohortList;
    @ManyToMany(cascade = CascadeType.REMOVE,mappedBy = "unitinstances")
    private List<Courseinstance> courseinstanceList;
    @OneToMany(mappedBy = "idunitinstance")
    private List<Personchosenby> personchosenbyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitinstance")
    private List<Unitpersonin> unitpersoninList;
    @OneToMany(mappedBy = "idunitinstance")
    private List<Ideachosenby> ideachosenbyList;
    @JoinColumn(name = "UNIT", referencedColumnName = "UNITCODE")
    @ManyToOne(optional = false)
    private Unit unit;
    @JoinColumn(name = "ACADEMICYEAR", referencedColumnName = "IDACADEMICYEAR")
    @ManyToOne(optional = false)
    private Academicyear academicyear;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitinstance")
    private List<Finalproject> finalprojectList;
    @OneToMany(mappedBy = "uniti")
    private List<SubmitConfiguration> submitConfigurations;

    public UnitInstance() {
    }

    public UnitInstance(Integer idunitinstance) {
        this.idunitinstance = idunitinstance;
    }

    public UnitInstance(Unit unit, Academicyear academicyear) {
        this.unit = unit;
        this.academicyear = academicyear;
    }

    public Integer getIdunitinstance() {
        return idunitinstance;
    }

    public void setIdunitinstance(Integer idunitinstance) {
        this.idunitinstance = idunitinstance;
    }

    public List<Cohort> getCohortList() {
        return cohortList;
    }

    public void setCohortList(List<Cohort> cohortList) {
        this.cohortList = cohortList;
    }

    public List<Courseinstance> getCourseinstanceList() {
        return courseinstanceList;
    }

    public void setCourseinstanceList(List<Courseinstance> courseinstanceList) {
        this.courseinstanceList = courseinstanceList;
    }

    public List<Personchosenby> getPersonchosenbyList() {
        return personchosenbyList;
    }

    public void setPersonchosenbyList(List<Personchosenby> personchosenbyList) {
        this.personchosenbyList = personchosenbyList;
    }

    public List<Unitpersonin> getUnitpersoninList() {
        return unitpersoninList;
    }

    public void setUnitpersoninList(List<Unitpersonin> unitpersoninList) {
        this.unitpersoninList = unitpersoninList;
    }

    public List<Ideachosenby> getIdeachosenbyList() {
        return ideachosenbyList;
    }

    public void setIdeachosenbyList(List<Ideachosenby> ideachosenbyList) {
        this.ideachosenbyList = ideachosenbyList;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Academicyear getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(Academicyear academicyear) {
        this.academicyear = academicyear;
    }

    public List<Finalproject> getFinalprojectList() {
        return finalprojectList;
    }

    public void setFinalprojectList(List<Finalproject> finalprojectList) {
        this.finalprojectList = finalprojectList;
    }
    
    public List<SubmitConfiguration> getSubmitConfigurations(){
        return submitConfigurations;
    }
    
    public void setSubmitConfiguration(List<SubmitConfiguration> submitConfigurations) {
        this.submitConfigurations = submitConfigurations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idunitinstance != null ? idunitinstance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnitInstance)) {
            return false;
        }
        UnitInstance other = (UnitInstance) object;
        if ((this.idunitinstance == null && other.idunitinstance != null) || (this.idunitinstance != null && !this.idunitinstance.equals(other.idunitinstance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Unitinstance[ idunitinstance=" + idunitinstance + " ]";
    }

    public SubmitConfiguration getCorrectSubmission() {
        return null ;
    }

    @XmlTransient
    public List<UnitMarkForms> getUnitMarkFormsList() {
        return unitMarkFormsList;
    }

    public void setUnitMarkFormsList(List<UnitMarkForms> unitMarkFormsList) {
        this.unitMarkFormsList = unitMarkFormsList;
    }
}
