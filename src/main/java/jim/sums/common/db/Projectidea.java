/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "PROJECTIDEA")
@NamedQueries({
    @NamedQuery(name = "Projectidea.findAll", query = "SELECT p FROM Projectidea p"),
    @NamedQuery(name = "Projectidea.findByIdprojectidea", query = "SELECT p FROM Projectidea p WHERE p.idprojectidea = :idprojectidea"),
    @NamedQuery(name = "Projectidea.findByIdeatitle", query = "SELECT p FROM Projectidea p WHERE p.ideatitle = :ideatitle"),
    @NamedQuery(name = "Projectidea.findByDescription", query = "SELECT p FROM Projectidea p WHERE p.description = :description"),
    @NamedQuery(name = "Projectidea.findByAims", query = "SELECT p FROM Projectidea p WHERE p.aims = :aims"),
    @NamedQuery(name = "Projectidea.findByAcademicquestion", query = "SELECT p FROM Projectidea p WHERE p.academicquestion = :academicquestion"),
    @NamedQuery(name = "Projectidea.findBySubmissiondate", query = "SELECT p FROM Projectidea p WHERE p.submissiondate = :submissiondate")})
public class Projectidea implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBMISSIONDATE")
    @Temporal(TemporalType.DATE)
    private Date submissiondate;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPROJECTIDEA")
    private Integer idprojectidea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "IDEATITLE")
    private String ideatitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000, message = "Description may not be empty or more than 5000 chars long")
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "AIMS")
    private String aims;
    @Size(max = 64)
    @Column(name = "ACADEMICQUESTION")
    private String academicquestion;
    
//    By BerardiD *******
    
    @JoinTable(name = "COURSELEVEL_PROJECTIDEA", joinColumns = {
        @JoinColumn(name = "PROJECTIDEALIST_IDPROJECTIDEA", referencedColumnName = "IDPROJECTIDEA")}, inverseJoinColumns = {
        @JoinColumn(name = "GRADELIST_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<CourseLevel> gradeList;
    
    @JoinTable(name = "IDEAKIND", joinColumns = {
        @JoinColumn(name = "IDPROJECTIDEA", referencedColumnName = "IDPROJECTIDEA")}, inverseJoinColumns = {
        @JoinColumn(name = "IDIDEAKIND", referencedColumnName = "IDKIND")})
    @ManyToMany
    private List<ProjectKind> kindList;
    
//   *******
    
    @JoinColumn(name = "OWNERIDEA", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Person owneridea;
    @JoinColumn(name = "IDEASTATUS", referencedColumnName = "IDIDEASTATUS")
    @ManyToOne(optional = false)
    private Ideastatus ideastatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectidea")
    private List<Ideachosenby> ideachosenbyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectidea1")
    private List<Projectideahistory> projectideahistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectidea")
    private List<Finalproject> finalprojectList;

    public Projectidea() {
    }

    public Projectidea(Integer idprojectidea) {
        this.idprojectidea = idprojectidea;
    }

    public Projectidea(Integer idprojectidea, String ideatitle, String description, String aims, Date submissiondate) {
        this.idprojectidea = idprojectidea;
        this.ideatitle = ideatitle;
        this.description = description;
        this.aims = aims;
        this.submissiondate = submissiondate;
    }

    public Integer getIdprojectidea() {
        return idprojectidea;
    }

    public void setIdprojectidea(Integer idprojectidea) {
        this.idprojectidea = idprojectidea;
    }

    public String getIdeatitle() {
        return ideatitle;
    }

    public void setIdeatitle(String ideatitle) {
        this.ideatitle = ideatitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAims() {
        return aims;
    }

    public void setAims(String aims) {
        this.aims = aims;
    }

    public String getAcademicquestion() {
        return academicquestion;
    }

    public void setAcademicquestion(String academicquestion) {
        this.academicquestion = academicquestion;
    }

    public List<CourseLevel> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<CourseLevel> gradeList) {
        this.gradeList = gradeList;
    }

    public List<ProjectKind> getKindList() {
        return kindList;
    }

    public void setKindList(List<ProjectKind> kindList) {
        this.kindList = kindList;
    }

    public Person getOwneridea() {
        return owneridea;
    }

    public void setOwneridea(Person owneridea) {
        this.owneridea = owneridea;
    }

    public Ideastatus getIdeastatus() {
        return ideastatus;
    }

    public void setIdeastatus(Ideastatus ideastatus) {
        this.ideastatus = ideastatus;
    }

    public List<Ideachosenby> getIdeachosenbyList() {
        return ideachosenbyList;
    }

    public void setIdeachosenbyList(List<Ideachosenby> ideachosenbyList) {
        this.ideachosenbyList = ideachosenbyList;
    }

    public List<Projectideahistory> getProjectideahistoryList() {
        return projectideahistoryList;
    }

    public void setProjectideahistoryList(List<Projectideahistory> projectideahistoryList) {
        this.projectideahistoryList = projectideahistoryList;
    }

    public List<Finalproject> getFinalprojectList() {
        return finalprojectList;
    }

    public void setFinalprojectList(List<Finalproject> finalprojectList) {
        this.finalprojectList = finalprojectList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprojectidea != null ? idprojectidea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projectidea)) {
            return false;
        }
        Projectidea other = (Projectidea) object;
        if ((this.idprojectidea == null && other.idprojectidea != null) || (this.idprojectidea != null && !this.idprojectidea.equals(other.idprojectidea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Projectidea[ idprojectidea=" + idprojectidea + " ]";
    }

    public Date getSubmissiondate() {
        return submissiondate;
    }

    public void setSubmissiondate(Date submissiondate) {
        this.submissiondate = submissiondate;
    }
}
