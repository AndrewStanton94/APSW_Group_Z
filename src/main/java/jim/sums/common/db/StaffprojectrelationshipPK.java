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
@Deprecated
@Embeddable
public class StaffprojectrelationshipPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Column(name = "IDPROJECT")
	private Long idproject;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "IDPERSON")
	private String idperson;
	@Basic(optional = false)
    @NotNull
    @Column(name = "IDSTAFFSTATUS")
	private int idstaffstatus;

	public StaffprojectrelationshipPK() {
	}

	public StaffprojectrelationshipPK(Long idproject, String idperson, int idstaffstatus) {
		this.idproject = idproject;
		this.idperson = idperson;
		this.idstaffstatus = idstaffstatus;
	}

	public Long getIdproject() {
		return idproject;
	}

	public void setIdproject(Long idproject) {
		this.idproject = idproject;
	}

	public String getIdperson() {
		return idperson;
	}

	public void setIdperson(String idperson) {
		this.idperson = idperson;
	}

	public int getIdstaffstatus() {
		return idstaffstatus;
	}

	public void setIdstaffstatus(int idstaffstatus) {
		this.idstaffstatus = idstaffstatus;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (Long) idproject;
		hash += (idperson != null ? idperson.hashCode() : 0);
		hash += (int) idstaffstatus;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof StaffprojectrelationshipPK)) {
			return false;
		}
		StaffprojectrelationshipPK other = (StaffprojectrelationshipPK) object;
		if (this.idproject != other.idproject) {
			return false;
		}
		if ((this.idperson == null && other.idperson != null) || (this.idperson != null && !this.idperson.equals(other.idperson))) {
			return false;
		}
		if (this.idstaffstatus != other.idstaffstatus) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.StaffprojectrelationshipPK[ idproject=" + idproject + ", idperson=" + idperson + ", idstaffstatus=" + idstaffstatus + " ]";
	}
	
}
