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
public class ProjectideahistoryPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "IDPERSON")
	private String idperson;
	@Basic(optional = false)
    @NotNull
    @Column(name = "PROJECTIDEA")
	private int projectidea;
	@Basic(optional = false)
    @NotNull
    @Column(name = "NUMCHANGE")
	private int numchange;

	public ProjectideahistoryPK() {
	}

	public ProjectideahistoryPK(String idperson, int projectidea, int numchange) {
		this.idperson = idperson;
		this.projectidea = projectidea;
		this.numchange = numchange;
	}

	public String getIdperson() {
		return idperson;
	}

	public void setIdperson(String idperson) {
		this.idperson = idperson;
	}

	public int getProjectidea() {
		return projectidea;
	}

	public void setProjectidea(int projectidea) {
		this.projectidea = projectidea;
	}

	public int getNumchange() {
		return numchange;
	}

	public void setNumchange(int numchange) {
		this.numchange = numchange;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idperson != null ? idperson.hashCode() : 0);
		hash += (int) projectidea;
		hash += (int) numchange;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof ProjectideahistoryPK)) {
			return false;
		}
		ProjectideahistoryPK other = (ProjectideahistoryPK) object;
		if ((this.idperson == null && other.idperson != null) || (this.idperson != null && !this.idperson.equals(other.idperson))) {
			return false;
		}
		if (this.projectidea != other.projectidea) {
			return false;
		}
		if (this.numchange != other.numchange) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.ProjectideahistoryPK[ idperson=" + idperson + ", projectidea=" + projectidea + ", numchange=" + numchange + " ]";
	}
	
}
