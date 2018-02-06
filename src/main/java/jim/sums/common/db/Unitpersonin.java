/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "UNITPERSONIN")
@NamedQueries({
	@NamedQuery(name = "Unitpersonin.findAll", query = "SELECT u FROM Unitpersonin u"),
	@NamedQuery(name = "Unitpersonin.findByPerson", query = "SELECT u FROM Unitpersonin u WHERE u.unitpersoninPK.person = :person"),
	@NamedQuery(name = "Unitpersonin.findByIdunitinstance", query = "SELECT u FROM Unitpersonin u WHERE u.unitpersoninPK.idunitinstance = :idunitinstance"),
	@NamedQuery(name = "Unitpersonin.findByValidationdate", query = "SELECT u FROM Unitpersonin u WHERE u.validationdate = :validationdate")})
public class Unitpersonin implements Serializable {
    @Column(name = "VALIDATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date validationdate;
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected UnitpersoninPK unitpersoninPK;
	@JoinColumn(name = "IDUNITINSTANCE", referencedColumnName = "IDUNITINSTANCE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private UnitInstance unitinstance;
	@JoinColumn(name = "PERSON", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person chosen;

	public Unitpersonin() {
	}

	public Unitpersonin(UnitpersoninPK unitpersoninPK) {
		this.unitpersoninPK = unitpersoninPK;
	}

	public Unitpersonin(String person, int idunitinstance) {
		this.unitpersoninPK = new UnitpersoninPK(person, idunitinstance);
	}

	public UnitpersoninPK getUnitpersoninPK() {
		return unitpersoninPK;
	}

	public void setUnitpersoninPK(UnitpersoninPK unitpersoninPK) {
		this.unitpersoninPK = unitpersoninPK;
	}

	public UnitInstance getUnitinstance() {
		return unitinstance;
	}

	public void setUnitinstance(UnitInstance unitinstance) {
		this.unitinstance = unitinstance;
	}

	public Person getChosen() {
		return chosen;
	}

	public void setChosen(Person person1) {
		this.chosen = person1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (unitpersoninPK != null ? unitpersoninPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Unitpersonin)) {
			return false;
		}
		Unitpersonin other = (Unitpersonin) object;
		if ((this.unitpersoninPK == null && other.unitpersoninPK != null) || (this.unitpersoninPK != null && !this.unitpersoninPK.equals(other.unitpersoninPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.Unitpersonin[ unitpersoninPK=" + unitpersoninPK + " ]";
	}

    public Date getValidationdate() {
        return validationdate;
    }

    public void setValidationdate(Date validationdate) {
        this.validationdate = validationdate;
    }
	
}
