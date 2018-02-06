/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "PROJECTIDEAHISTORY")
@NamedQueries({
	@NamedQuery(name = "Projectideahistory.findAll", query = "SELECT p FROM Projectideahistory p"),
	@NamedQuery(name = "Projectideahistory.findByIdperson", query = "SELECT p FROM Projectideahistory p WHERE p.projectideahistoryPK.idperson = :idperson"),
	@NamedQuery(name = "Projectideahistory.findByProjectidea", query = "SELECT p FROM Projectideahistory p WHERE p.projectideahistoryPK.projectidea = :projectidea"),
	@NamedQuery(name = "Projectideahistory.findByNumchange", query = "SELECT p FROM Projectideahistory p WHERE p.projectideahistoryPK.numchange = :numchange"),
	@NamedQuery(name = "Projectideahistory.findByChangedate", query = "SELECT p FROM Projectideahistory p WHERE p.changedate = :changedate"),
	@NamedQuery(name = "Projectideahistory.findByChange", query = "SELECT p FROM Projectideahistory p WHERE p.change = :change"),
	@NamedQuery(name = "Projectideahistory.findByChangereason", query = "SELECT p FROM Projectideahistory p WHERE p.changereason = :changereason")})
public class Projectideahistory implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANGEDATE")
    @Temporal(TemporalType.DATE)
    private Date changedate;
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected ProjectideahistoryPK projectideahistoryPK;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "CHANGE")
	private String change;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "CHANGEREASON")
	private String changereason;
	@JoinColumn(name = "PROJECTIDEA", referencedColumnName = "IDPROJECTIDEA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Projectidea projectidea1;
	@JoinColumn(name = "IDPERSON", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person person;

	public Projectideahistory() {
	}

	public Projectideahistory(ProjectideahistoryPK projectideahistoryPK) {
		this.projectideahistoryPK = projectideahistoryPK;
	}

	public Projectideahistory(ProjectideahistoryPK projectideahistoryPK, Date changedate, String change, String changereason) {
		this.projectideahistoryPK = projectideahistoryPK;
		this.changedate = changedate;
		this.change = change;
		this.changereason = changereason;
	}

	public Projectideahistory(String idperson, int projectidea, int numchange) {
		this.projectideahistoryPK = new ProjectideahistoryPK(idperson, projectidea, numchange);
	}

	public ProjectideahistoryPK getProjectideahistoryPK() {
		return projectideahistoryPK;
	}

	public void setProjectideahistoryPK(ProjectideahistoryPK projectideahistoryPK) {
		this.projectideahistoryPK = projectideahistoryPK;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getChangereason() {
		return changereason;
	}

	public void setChangereason(String changereason) {
		this.changereason = changereason;
	}

	public Projectidea getProjectidea1() {
		return projectidea1;
	}

	public void setProjectidea1(Projectidea projectidea1) {
		this.projectidea1 = projectidea1;
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
		hash += (projectideahistoryPK != null ? projectideahistoryPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Projectideahistory)) {
			return false;
		}
		Projectideahistory other = (Projectideahistory) object;
		if ((this.projectideahistoryPK == null && other.projectideahistoryPK != null) || (this.projectideahistoryPK != null && !this.projectideahistoryPK.equals(other.projectideahistoryPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.Projectideahistory[ projectideahistoryPK=" + projectideahistoryPK + " ]";
	}

    public Date getChangedate() {
        return changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }
	
}
