/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Pasu
 */
@Entity
public class ApprovedIdeaStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "APPROVEDCOMMENT")
    private String approvedComment;
    @Column(name = "APPROVEDSTATUS")
    private String approvedStatus;
    @ManyToOne
    @JoinColumn(name = "IDPROJECTIDEA")
    private Projectidea approvedIdeaList;
    @ManyToOne
    @JoinColumn(name = "IDPERSON")
    private Person approvedStaffList;

    public ApprovedIdeaStaff(){
    }
    
    public ApprovedIdeaStaff(Projectidea projectideaToApprove, Person ownerIdeaSend){
        this.approvedIdeaList = projectideaToApprove;
        this.approvedStaffList = ownerIdeaSend;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
    
    public String getApprovedComment() {
        return approvedComment;
    }

    public void setApprovedComment(String approvedComment) {
        this.approvedComment = approvedComment;
    }
    
    public Projectidea getApprovedIdeaList() {
        return approvedIdeaList;
    }

    public void setApprovedIdeaList(Projectidea approvedIdeaList) {
        this.approvedIdeaList = approvedIdeaList;
    }

    public Person getApprovedStaffList() {
        return approvedStaffList;
    }

    public void setApprovedStaffList(Person approvedStaffList) {
        this.approvedStaffList = approvedStaffList;
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
        if (!(object instanceof ApprovedIdeaStaff)) {
            return false;
        }
        ApprovedIdeaStaff other = (ApprovedIdeaStaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.ApprovedIdeaStaff[ id=" + id + " ]";
    }
}
