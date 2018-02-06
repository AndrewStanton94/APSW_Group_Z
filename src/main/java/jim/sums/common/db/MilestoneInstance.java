/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
//@Table(name = "MILESTONE_SPECIFIC")
@NamedQueries({
    @NamedQuery(name = "MilestoneInstance.findAll", query = "SELECT mss FROM MilestoneInstance mss"),
    @NamedQuery(name = "MilestoneInstance.findByPerson", query = "SELECT mss FROM MilestoneInstance mss WHERE mss.student = :student")
})
/**
 *
 * @author Adrian Earle
 */
public class MilestoneInstance implements Serializable {

    //Fields\\
    @Id
    @GeneratedValue
//    @Column(name = "mss_id")
    private Long id;
    @Version
//    @Column(name = "mss_version")
    private int version;
    @ManyToOne
//    @JoinColumn(name = "PERSON", referencedColumnName = "USERNAME")
    private Person student;
    @ManyToOne
//    @JoinColumn(name = "MILESTONEBROAD", referencedColumnName = "MSB_ID")
    private MilestoneTemplate template;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "mss_active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "mss_quality")
    private String quality;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "mss_effort")
    private String effort;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "mss_studentComment")
    private String studentComment;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "mss_supervisorComment")
    private String supervisorComment;

    //Constructors\\
    public MilestoneInstance() {
    }

    //load contact details from their id
    public MilestoneInstance(Long lID) {
        //TODO: load the milestone from the database
    }

    public MilestoneInstance(Person person, MilestoneTemplate broad) {
        id = null;
        version = 1;
        student = person;
        template = broad;
        active = false;
        effort = "N/A";
        quality = "N/A";
        studentComment = "";
        supervisorComment = "";
    }

    //--Gets & Sets--//    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {
        this.studentComment = studentComment;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    public MilestoneTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MilestoneTemplate template) {
        this.template = template;
    }
}
