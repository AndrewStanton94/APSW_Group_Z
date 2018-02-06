/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "IDEACHOSENBY")
@NamedQueries({
	@NamedQuery(name = "Ideachosenby.findAll", query = "SELECT i FROM Ideachosenby i"),
	@NamedQuery(name = "Ideachosenby.findByUsername", query = "SELECT i FROM Ideachosenby i WHERE i.ideachosenbyPK.username = :username"),
	@NamedQuery(name = "Ideachosenby.findByIdprojectidea", query = "SELECT i FROM Ideachosenby i WHERE i.ideachosenbyPK.idprojectidea = :idprojectidea"),
	@NamedQuery(name = "Ideachosenby.findByIdearank", query = "SELECT i FROM Ideachosenby i WHERE i.idearank = :idearank")})
public class Ideachosenby implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected IdeachosenbyPK ideachosenbyPK;
	@Basic(optional = false)
    @NotNull
    @Column(name = "IDEARANK")
	private int idearank;
	@JoinColumn(name = "IDUNITINSTANCE", referencedColumnName = "IDUNITINSTANCE")
    @ManyToOne
	private UnitInstance idunitinstance;
	@JoinColumn(name = "IDPROJECTIDEA", referencedColumnName = "IDPROJECTIDEA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Projectidea projectidea;
	@JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person person;

	public Ideachosenby() {
	}

	public Ideachosenby(IdeachosenbyPK ideachosenbyPK) {
		this.ideachosenbyPK = ideachosenbyPK;
	}

	public Ideachosenby(IdeachosenbyPK ideachosenbyPK, int idearank) {
		this.ideachosenbyPK = ideachosenbyPK;
		this.idearank = idearank;
	}

	public Ideachosenby(String username, int idprojectidea) {
		this.ideachosenbyPK = new IdeachosenbyPK(username, idprojectidea);
	}

	public IdeachosenbyPK getIdeachosenbyPK() {
		return ideachosenbyPK;
	}

	public void setIdeachosenbyPK(IdeachosenbyPK ideachosenbyPK) {
		this.ideachosenbyPK = ideachosenbyPK;
	}

	public int getIdearank() {
		return idearank;
	}

	public void setIdearank(int idearank) {
		this.idearank = idearank;
	}

	public UnitInstance getIdunitinstance() {
		return idunitinstance;
	}

	public void setIdunitinstance(UnitInstance idunitinstance) {
		this.idunitinstance = idunitinstance;
	}

	public Projectidea getProjectidea() {
		return projectidea;
	}

	public void setProjectidea(Projectidea projectidea) {
		this.projectidea = projectidea;
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
		hash += (ideachosenbyPK != null ? ideachosenbyPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Ideachosenby)) {
			return false;
		}
		Ideachosenby other = (Ideachosenby) object;
		if ((this.ideachosenbyPK == null && other.ideachosenbyPK != null) || (this.ideachosenbyPK != null && !this.ideachosenbyPK.equals(other.ideachosenbyPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.Ideachosenby[ ideachosenbyPK=" + ideachosenbyPK + " ]";
	}
	
}
