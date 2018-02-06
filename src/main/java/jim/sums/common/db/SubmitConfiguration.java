package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Bogdan Andreescu
 */

@Entity
//@NamedQueries({
//    @NamedQuery(name = "SubmitConfiguration.findAll", query = "SELECT sc FROM SubmitConfiguration sc"),
//    @NamedQuery(name = "SubmitConfiguration.findByUsername", query = "SELECT s FROM SubmitConfiguration s WHERE s.submitconfigurationPK.username = :username"),
//    @NamedQuery(name = "SubmitConfiguration.findByOpenSubmission", query = "SELECT s FROM SubmitConfiguration s WHERE s.opensubmission = :opensubmission"),
//    @NamedQuery(name = "SubmitConfiguration.findByFirstDeadline", query = "SELECT s FROM SubmitConfiguration s WHERE s.firstdeadline = :firstdeadline"),
//    @NamedQuery(name = "SubmitConfiguration.findByFinalDeadline", query = "SELECT s FROM SubmitConfiguration s WHERE s.finaldeadline = :finaldeadline"),})
public class SubmitConfiguration implements Serializable {
    @OneToMany(mappedBy = "config")
    private List<Submission> submissions;
    @ManyToOne
    private UnitInstance uniti;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDate;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date normalDeadline;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalDeadline;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String fileType;
    private int fileSize = 64000000;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max=2000)
    private String descriptionSubmitConfig;
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Date getFinalDeadline() {
        return finalDeadline;
    }

    public void setFinalDeadline(Date finalDeadline) {
        this.finalDeadline = finalDeadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getNormalDeadline() {
        return normalDeadline;
    }

    public void setNormalDeadline(Date normalDeadline) {
        this.normalDeadline = normalDeadline;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
    
    public String getDescriptionSubmitConfig() {
        return descriptionSubmitConfig;
    }
    
    public void setDescriptionSubmitConfig(String descriptionSubmitConfig) {
        this.descriptionSubmitConfig = descriptionSubmitConfig;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public UnitInstance getUniti() {
        return uniti;
    }

    public void setUniti(UnitInstance uniti) {
        this.uniti = uniti;
    }
    
    public String getFileType(){
        return fileType;
    }
    
    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubmitConfiguration)) {
            return false;
        }
        SubmitConfiguration other = (SubmitConfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.SubmitConfiguration[ id=" + id + " ]";
    }
}
