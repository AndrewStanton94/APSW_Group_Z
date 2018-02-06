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
public class PersonchosenbyPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAMECHOOSER")
	private String usernamechooser;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAMECHOSEN")
	private String usernamechosen;

	public PersonchosenbyPK() {
	}

	public PersonchosenbyPK(String usernamechooser, String usernamechosen) {
		this.usernamechooser = usernamechooser;
		this.usernamechosen = usernamechosen;
	}

	public String getUsernamechooser() {
		return usernamechooser;
	}

	public void setUsernamechooser(String usernamechooser) {
		this.usernamechooser = usernamechooser;
	}

	public String getUsernamechosen() {
		return usernamechosen;
	}

	public void setUsernamechosen(String usernamechosen) {
		this.usernamechosen = usernamechosen;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (usernamechooser != null ? usernamechooser.hashCode() : 0);
		hash += (usernamechosen != null ? usernamechosen.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof PersonchosenbyPK)) {
			return false;
		}
		PersonchosenbyPK other = (PersonchosenbyPK) object;
		if ((this.usernamechooser == null && other.usernamechooser != null) || (this.usernamechooser != null && !this.usernamechooser.equals(other.usernamechooser))) {
			return false;
		}
		if ((this.usernamechosen == null && other.usernamechosen != null) || (this.usernamechosen != null && !this.usernamechosen.equals(other.usernamechosen))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.PersonchosenbyPK[ usernamechooser=" + usernamechooser + ", usernamechosen=" + usernamechosen + " ]";
	}
	
}
