/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "COHORT")
@NamedQueries({
    @NamedQuery(name = "Cohort.findAll", query = "SELECT c FROM Cohort c"),
    @NamedQuery(name = "Cohort.findByIdcohort", query = "SELECT c FROM Cohort c WHERE c.idcohort = :idcohort"),
    @NamedQuery(name = "Cohort.findByCohortlabel", query = "SELECT c FROM Cohort c WHERE c.cohortlabel = :cohortlabel"),
    @NamedQuery(name = "Cohort.findByRegisterstart", query = "SELECT c FROM Cohort c WHERE c.registerstart = :registerstart"),
    @NamedQuery(name = "Cohort.findByRegisterstop", query = "SELECT c FROM Cohort c WHERE c.registerstop = :registerstop"),
    @NamedQuery(name = "Cohort.findByProjectstart", query = "SELECT c FROM Cohort c WHERE c.projectstart = :projectstart"),
    @NamedQuery(name = "Cohort.findByProjectstop", query = "SELECT c FROM Cohort c WHERE c.projectstop = :projectstop"),
    @NamedQuery(name = "Cohort.findByIdeashortlistmaxsize", query = "SELECT c FROM Cohort c WHERE c.ideashortlistmaxsize = :ideashortlistmaxsize"),
    @NamedQuery(name = "Cohort.findBySupervisorshortlistmaxsize", query = "SELECT c FROM Cohort c WHERE c.supervisorshortlistmaxsize = :supervisorshortlistmaxsize")})
public class Cohort implements Serializable {

    @Column(name = "REGISTERSTART")
    @Temporal(TemporalType.DATE)
    private Date registerstart;
    @Column(name = "REGISTERSTOP")
    @Temporal(TemporalType.DATE)
    private Date registerstop;
    @Column(name = "PROJECTSTART")
    @Temporal(TemporalType.DATE)
    private Date projectstart;
    @Column(name = "PROJECTSTOP")
    @Temporal(TemporalType.DATE)
    private Date projectstop;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCOHORT")
    private Integer idcohort;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "COHORTLABEL")
    private String cohortlabel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDEASHORTLISTMAXSIZE")
    private int ideashortlistmaxsize;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUPERVISORSHORTLISTMAXSIZE")
    private int supervisorshortlistmaxsize;
    @JoinTable(name = "MANAGEDBY", joinColumns = {
        @JoinColumn(name = "IDCOHORT", referencedColumnName = "IDCOHORT")}, inverseJoinColumns = {
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")})
    @ManyToMany
    private List<Person> personList;
    @JoinTable(name = "COHORTUNITIN", joinColumns = {
        @JoinColumn(name = "IDCOHORT", referencedColumnName = "IDCOHORT")}, inverseJoinColumns = {
        @JoinColumn(name = "IDUNITINSTANCE", referencedColumnName = "IDUNITINSTANCE")})
    @ManyToMany
    private List<UnitInstance> unitinstanceList;
    @JoinTable(name = "SUPERVISORIN", joinColumns = {
        @JoinColumn(name = "IDCOHORT", referencedColumnName = "IDCOHORT")}, inverseJoinColumns = {
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")})
    @ManyToMany
    private List<Person> personList1;
    @Transient
    private List<Person> studentList;
    @OneToMany(mappedBy = "cohort")
    private List<MilestoneTemplate> milestoneTemplates;

    public Cohort() {
    }

    public Cohort(Integer idcohort) {
        this.idcohort = idcohort;
    }

    public Cohort(Integer idcohort, String cohortlabel, int ideashortlistmaxsize, int supervisorshortlistmaxsize) {
        this.idcohort = idcohort;
        this.cohortlabel = cohortlabel;
        this.ideashortlistmaxsize = ideashortlistmaxsize;
        this.supervisorshortlistmaxsize = supervisorshortlistmaxsize;
    }

    public Cohort(String cohortlabel, int ideashortlistmaxsize, int supervisorshortlistmaxsize) {
        this.cohortlabel = cohortlabel;
        this.ideashortlistmaxsize = ideashortlistmaxsize;
        this.supervisorshortlistmaxsize = supervisorshortlistmaxsize;
    }

    public Integer getIdcohort() {
        return idcohort;
    }

    public void setIdcohort(Integer idcohort) {
        this.idcohort = idcohort;
    }

    public String getCohortlabel() {
        return cohortlabel;
    }

    public void setCohortlabel(String cohortlabel) {
        this.cohortlabel = cohortlabel;
    }

    public int getIdeashortlistmaxsize() {
        return ideashortlistmaxsize;
    }

    public void setIdeashortlistmaxsize(int ideashortlistmaxsize) {
        this.ideashortlistmaxsize = ideashortlistmaxsize;
    }

    public int getSupervisorshortlistmaxsize() {
        return supervisorshortlistmaxsize;
    }

    public void setSupervisorshortlistmaxsize(int supervisorshortlistmaxsize) {
        this.supervisorshortlistmaxsize = supervisorshortlistmaxsize;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<UnitInstance> getUnits() {
        return unitinstanceList;
    }

    public void setUnits(List<UnitInstance> unitinstanceList) {
        this.unitinstanceList = unitinstanceList;
    }

    public void addUnitInstance(UnitInstance ui) {
        if (unitinstanceList == null) {
            unitinstanceList = new ArrayList<UnitInstance>();
        }
        unitinstanceList.add(ui);
    }

    public List<Person> getPersonList1() {
        return personList1;
    }

    public void setPersonList1(List<Person> personList1) {
        this.personList1 = personList1;
    }

    public List<Person> getStudentList() {
        if (studentList == null) {
            studentList = new ArrayList<Person>();
            for (UnitInstance ui : unitinstanceList) {
                for (Unitpersonin upi : ui.getUnitpersoninList()) {
                    studentList.add(upi.getChosen());
                }
            }
        }
        return studentList;
    }

    public void setStudentList(List<Person> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcohort != null ? idcohort.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cohort)) {
            return false;
        }
        Cohort other = (Cohort) object;
        if ((this.idcohort == null && other.idcohort != null) || (this.idcohort != null && !this.idcohort.equals(other.idcohort))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Cohort[ idcohort=" + idcohort + " ]";
    }

    public Date getRegisterstart() {
        return registerstart;
    }

    public void setRegisterstart(Date registerstart) {
        this.registerstart = registerstart;
    }

    public Date getRegisterstop() {
        return registerstop;
    }

    public void setRegisterstop(Date registerstop) {
        this.registerstop = registerstop;
    }

    public Date getProjectstart() {
        return projectstart;
    }

    public void setProjectstart(Date projectstart) {
        this.projectstart = projectstart;
    }

    public Date getProjectstop() {
        return projectstop;
    }

    public void setProjectstop(Date projectstop) {
        this.projectstop = projectstop;
    }

    public void setBroadMilestones(List<MilestoneTemplate> lmt) {
        milestoneTemplates = lmt;
    }

    public boolean addBroadMilestone(MilestoneTemplate mt) {
        return milestoneTemplates.add(mt);
    }

    public boolean removeBroadMilestone(MilestoneTemplate mt) {
        return milestoneTemplates.remove(mt);
    }

    public List<MilestoneTemplate> getBroadMilestones() {
        return milestoneTemplates;
    }
}
