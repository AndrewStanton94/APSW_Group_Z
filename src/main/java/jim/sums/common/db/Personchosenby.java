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
@Table(name = "PERSONCHOSENBY")
@NamedQueries({
	@NamedQuery(name = "Personchosenby.findAll", query = "SELECT p FROM Personchosenby p"),
	@NamedQuery(name = "Personchosenby.findByUsernamechooser", query = "SELECT p FROM Personchosenby p WHERE p.personchosenbyPK.usernamechooser = :usernamechooser"),
	@NamedQuery(name = "Personchosenby.findByUsernamechosen", query = "SELECT p FROM Personchosenby p WHERE p.personchosenbyPK.usernamechosen = :usernamechosen"),
	@NamedQuery(name = "Personchosenby.findByPersonrank", query = "SELECT p FROM Personchosenby p WHERE p.personrank = :personrank")})
public class Personchosenby implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected PersonchosenbyPK personchosenbyPK;
	@Basic(optional = false)
    @NotNull
    @Column(name = "PERSONRANK")
	private int personrank;
	@JoinColumn(name = "IDUNITINSTANCE", referencedColumnName = "IDUNITINSTANCE")
    @ManyToOne
	private UnitInstance idunitinstance;
	@JoinColumn(name = "USERNAMECHOOSER", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person person;
	@JoinColumn(name = "USERNAMECHOSEN", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person person1;

	public Personchosenby() {
	}

	public Personchosenby(PersonchosenbyPK personchosenbyPK) {
		this.personchosenbyPK = personchosenbyPK;
	}

	public Personchosenby(PersonchosenbyPK personchosenbyPK, int personrank) {
		this.personchosenbyPK = personchosenbyPK;
		this.personrank = personrank;
	}

	public Personchosenby(String usernamechooser, String usernamechosen) {
		this.personchosenbyPK = new PersonchosenbyPK(usernamechooser, usernamechosen);
	}

	public PersonchosenbyPK getPersonchosenbyPK() {
		return personchosenbyPK;
	}

	public void setPersonchosenbyPK(PersonchosenbyPK personchosenbyPK) {
		this.personchosenbyPK = personchosenbyPK;
	}

	public int getPersonrank() {
		return personrank;
	}

	public void setPersonrank(int personrank) {
		this.personrank = personrank;
	}

	public UnitInstance getIdunitinstance() {
		return idunitinstance;
	}

	public void setIdunitinstance(UnitInstance idunitinstance) {
		this.idunitinstance = idunitinstance;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson1() {
		return person1;
	}

	public void setPerson1(Person person1) {
		this.person1 = person1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (personchosenbyPK != null ? personchosenbyPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Personchosenby)) {
			return false;
		}
		Personchosenby other = (Personchosenby) object;
		if ((this.personchosenbyPK == null && other.personchosenbyPK != null) || (this.personchosenbyPK != null && !this.personchosenbyPK.equals(other.personchosenbyPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PersonChosenBy: " + person.getUsername() + " (chooser), " + person1.getUsername() + " (chosen), " + personrank ;
	}
	
}
