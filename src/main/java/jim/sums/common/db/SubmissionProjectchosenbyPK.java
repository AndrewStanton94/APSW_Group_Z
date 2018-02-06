/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Bogdan Andreescu
 */
@Embeddable
public class SubmissionProjectchosenbyPK implements Serializable  {
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAME")
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long projectid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUNITINSTANCE")
    private UnitInstance idunitinstance;
    
    public SubmissionProjectchosenbyPK() {
	}
    
    public SubmissionProjectchosenbyPK(String username, Long id) {
		this.username = username;
		this.projectid = id;
	}

     public SubmissionProjectchosenbyPK(String username, UnitInstance idunitinstance) {
		this.username = username;
		this.idunitinstance = idunitinstance;
	}
     
     
    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public String getUsername() {
        return username;
    }

  

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
	public int hashCode() {
		int hash = 0;
		hash += (username != null ? username.hashCode() : 0);
		hash += (Long) projectid;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		
		if (!(object instanceof SubmissionProjectchosenbyPK)) {
			return false;
		}
		SubmissionProjectchosenbyPK other = (SubmissionProjectchosenbyPK) object;
		if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
			return false;
		}
		if (this.projectid != other.projectid) {
			return false;
		}
		return true;
	}
        
        @Override
	public String toString() {
		return "jim.sums.common.db.SubmissionProjectchosenbyPK[ username=" + username + ", projectid=" + projectid + " ]";
	}
        
        
}
