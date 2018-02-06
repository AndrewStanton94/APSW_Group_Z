package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Bogdan Andreescu
 */

@Entity
@NamedQueries({
    @NamedQuery(name = "Submission.findAll", query = "SELECT s FROM Submission s")})
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] uploadfile = null;
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionTime = null;
    @ManyToOne
    private SubmitConfiguration config = null;
    @OneToOne
    private Finalproject project = null;
    @Size(min = 0)
    private int counter = 0;
    @ManyToOne(optional = false)
    private Person person;

    public Submission() {
    }

    public SubmitConfiguration getConfig() {
        return config;
    }

    public void setConfig(SubmitConfiguration config) {
        this.config = config;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Finalproject getProject() {
        return project;
    }

    public void setProject(Finalproject project) {
        this.project = project;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    public byte[] getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(byte[] uploadfile) {
        this.uploadfile = uploadfile;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submission)) {
            return false;
        }
        Submission other = (Submission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.SubmitFinalProject[ id=" + id + " ]";
    }
}
