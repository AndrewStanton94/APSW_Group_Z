/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Finalproject.findAll", query = "SELECT f FROM Finalproject f"),
    @NamedQuery(name = "Finalproject.findByIdproject", query = "SELECT f FROM Finalproject f WHERE f.idproject = :idproject"),
    @NamedQuery(name = "Finalproject.findByProjecttitle", query = "SELECT f FROM Finalproject f WHERE f.projecttitle = :projecttitle"),
    @NamedQuery(name = "Finalproject.findByPlannedsubmissiondate", query = "SELECT f FROM Finalproject f WHERE f.plannedsubmissiondate = :plannedsubmissiondate"),
    @NamedQuery(name = "Finalproject.findByActualsubmissiondate", query = "SELECT f FROM Finalproject f WHERE f.actualsubmissiondate = :actualsubmissiondate"),
    @NamedQuery(name = "Finalproject.findUnmarkedProjects", query = "SELECT f FROM Finalproject f WHERE f.finalMark IS NULL")})
public class Finalproject implements Serializable {

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date plannedsubmissiondate;
    @Temporal(TemporalType.DATE)
    private Date actualsubmissiondate;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
    private Long idproject;
    @Basic(optional = false)
    private String projecttitle;
    @OneToMany(mappedBy = "finalproject")
    private List<Staffprojectrelationship> staffprojectrelationshipList;
    // @JoinColumn(name = "UNITINSTANCE", referencedColumnName = "IDUNITINSTANCE")
    @ManyToOne(optional = false)
    private UnitInstance unitinstance;
    // @JoinColumn(name = "PROJECTIDEA", referencedColumnName = "IDPROJECTIDEA")
    @ManyToOne(optional = false)
    private Projectidea projectidea;
    // @JoinTable(name = "PERSONFINALPROJECT", joinColumns = {
    // @JoinColumn(name = "FINALPROJECTLIST_IDPROJECT", referencedColumnName = "IDPROJECT")}, inverseJoinColumns = {
    // @JoinColumn(name = "PERSON", referencedColumnName = "USERNAME")})
    @ManyToMany
    private List<Person> studentList;
    // @Column(name = "FINAL_MARK")
    int finalMark;
    // @Column(name = "FINAL_MARK_STATUS")
    String finalMarkStatus;
    @OneToOne
    Feedback feedback;
//    @Column(name = "EXTERNAL_SATISFIED", nullable=false, columnDefinition="boolean default true")
    private Boolean externalSatisfied = true;
    private String externalDate;
    @OneToOne
    private Submission submission;
    @ManyToOne
    private TemplateMarkForm tmf;
    @ManyToMany
    private List<Person> markers;
    @OneToMany(mappedBy = "projectId")
    private List<MarkerMark> marksList;
    @Column(name = "EXTERNAL_COMMENTS")
    String externalComments;
    @Column(name = "FINAL_MARK_COMMENT")
    String finalMarkComment;

    public Finalproject() {
    }

    public Finalproject(Long idproject) {
        this.idproject = idproject;
    }

    public Finalproject(Long idproject, String projecttitle, Date plannedsubmissiondate) {
        this.idproject = idproject;
        this.projecttitle = projecttitle;
        this.plannedsubmissiondate = plannedsubmissiondate;
    }

    public String getFinalMarkComment() {
        return finalMarkComment;
    }

    public void setFinalMarkComment(String finalMarkComment) {
        this.finalMarkComment = finalMarkComment;
    }

    public String getFinalMarkStatus() {
        return finalMarkStatus;
    }

    public void setFinalMarkStatus(String finalMarkStatus) {
        this.finalMarkStatus = finalMarkStatus;
    }

    public int getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(int finalMark) {
        this.finalMark = finalMark;
    }

    public List<MarkerMark> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<MarkerMark> marksList) {
        this.marksList = marksList;
    }

    public List<Person> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Person> studentList) {
        this.studentList = studentList;
    }

    public TemplateMarkForm getTmf() {
        return tmf;
    }

    public void setTmf(TemplateMarkForm tmf) {
        this.tmf = tmf;
    }

    public List<Person> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Person> markers) {
        this.markers = markers;
    }

    public String getExternalDate() {
        return externalDate;
    }

    public void setExternalDate(String externalDate) {
        this.externalDate = externalDate;
    }

    public Boolean getExternalSatisfied() {
        return externalSatisfied;
    }

    public void setExternalSatisfied(Boolean externalSatisfied) {
        this.externalSatisfied = externalSatisfied;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public String getExternalComments() {
        return externalComments;
    }

    public void setExternalComments(String externalComments) {
        this.externalComments = externalComments;
    }

    public Long getIdproject() {
        return idproject;
    }

    public void setIdproject(Long idproject) {
        this.idproject = idproject;
    }

    public String getProjecttitle() {
        return projecttitle;
    }

    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    public List<Staffprojectrelationship> getStaffprojectrelationshipList() {
        staffprojectrelationshipList.size();
        return staffprojectrelationshipList;
    }

    public void setStaffprojectrelationshipList(List<Staffprojectrelationship> staffprojectrelationshipList) {
        this.staffprojectrelationshipList = staffprojectrelationshipList;
    }

    public UnitInstance getUnitinstance() {
        return unitinstance;
    }

    public void setUnitinstance(UnitInstance unitinstance) {
        this.unitinstance = unitinstance;
    }

    public Projectidea getProjectidea() {
        return projectidea;
    }

    public void setProjectidea(Projectidea projectidea) {
        this.projectidea = projectidea;
    }

    public List<Person> getStudent() {
        return studentList;
    }

    public void setStudent(List<Person> studentList) {
        this.studentList = studentList;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproject != null ? idproject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Finalproject)) {
            return false;
        }
        Finalproject other = (Finalproject) object;
        if ((this.idproject == null && other.idproject != null) || (this.idproject != null && !this.idproject.equals(other.idproject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Finalproject[ idproject=" + idproject + " ]";
    }

    public Date getPlannedsubmissiondate() {
        return plannedsubmissiondate;
    }

    public void setPlannedsubmissiondate(Date plannedsubmissiondate) {
        this.plannedsubmissiondate = plannedsubmissiondate;
    }

    public Date getActualsubmissiondate() {
        return actualsubmissiondate;
    }

    public void setActualsubmissiondate(Date actualsubmissiondate) {
        this.actualsubmissiondate = actualsubmissiondate;
    }

    public Person getModerator() {
        for (Staffprojectrelationship spr : staffprojectrelationshipList) {
            if (spr.getStaffstatus().getStatusname().equalsIgnoreCase("moderator")) {
                return spr.getPerson();
            }
        }
        return null;
    }

    public Person getSupervisor() {
        for (Staffprojectrelationship spr : staffprojectrelationshipList) {
            if (spr.getStaffstatus().getStatusname().equalsIgnoreCase("supervisor")) {
                return spr.getPerson();
            }
        }
        return null;
    }

    public Person getCoordinator() {
        for (Staffprojectrelationship spr : staffprojectrelationshipList) {
            if (spr.getStaffstatus().getStatusname().equalsIgnoreCase("coordinator")) {
                return spr.getPerson();
            }
        }
        return null;
    }
}
