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
 * @author Nicolas Dossou-Gbete
 */
@Embeddable
public class IdeachosenbyPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPROJECTIDEA")
    private int idprojectidea;

	public IdeachosenbyPK() {
	}

	public IdeachosenbyPK(String username, int idprojectidea) {
		this.username = username;
		this.idprojectidea = idprojectidea;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIdprojectidea() {
		return idprojectidea;
	}

	public void setIdprojectidea(int idprojectidea) {
		this.idprojectidea = idprojectidea;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (username != null ? username.hashCode() : 0);
		hash += (int) idprojectidea;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof IdeachosenbyPK)) {
			return false;
		}
		IdeachosenbyPK other = (IdeachosenbyPK) object;
		if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
			return false;
		}
		if (this.idprojectidea != other.idprojectidea) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.IdeachosenbyPK[ username=" + username + ", idprojectidea=" + idprojectidea + " ]";
	}
	
}
