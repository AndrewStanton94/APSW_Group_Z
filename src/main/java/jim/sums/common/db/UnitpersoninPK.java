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
public class UnitpersoninPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PERSON")
	private String person;
	@Basic(optional = false)
    @NotNull
    @Column(name = "IDUNITINSTANCE")
	private int idunitinstance;

	public UnitpersoninPK() {
	}

	public UnitpersoninPK(String person, int idunitinstance) {
		this.person = person;
		this.idunitinstance = idunitinstance;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getIdunitinstance() {
		return idunitinstance;
	}

	public void setIdunitinstance(int idunitinstance) {
		this.idunitinstance = idunitinstance;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (person != null ? person.hashCode() : 0);
		hash += (int) idunitinstance;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof UnitpersoninPK)) {
			return false;
		}
		UnitpersoninPK other = (UnitpersoninPK) object;
		if ((this.person == null && other.person != null) || (this.person != null && !this.person.equals(other.person))) {
			return false;
		}
		if (this.idunitinstance != other.idunitinstance) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.UnitpersoninPK[ person=" + person + ", idunitinstance=" + idunitinstance + " ]";
	}
	
}
