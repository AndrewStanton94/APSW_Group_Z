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
public class SubmitConfigurationPK implements Serializable  {
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAME")
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long configid;
    
    public SubmitConfigurationPK() {
	}
    
    public SubmitConfigurationPK(String username, Long id) {
		this.username = username;
		this.configid = id;
	}

    public Long getProjectid() {
        return configid;
    }

    public void setProjectid(Long projectid) {
        this.configid = projectid;
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
		hash += (Long) configid;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		
		if (!(object instanceof SubmissionProjectchosenbyPK)) {
			return false;
		}
		SubmitConfigurationPK other = (SubmitConfigurationPK) object;
		if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
			return false;
		}
		if (this.configid != other.configid) {
			return false;
		}
		return true;
	}
        
        @Override
	public String toString() {
		return "jim.sums.common.db.SubmitConfigurationPK[ username=" + username + ", projectid=" + configid + " ]";
	}
        
        
}
