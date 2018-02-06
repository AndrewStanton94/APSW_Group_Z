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
@Table(name = "PERSONSTAFFSTATUS")
@NamedQueries({
    @NamedQuery(name = "Personstaffstatus.findAll", query = "SELECT p FROM Personstaffstatus p"),
    @NamedQuery(name = "Personstaffstatus.findByUsername", query = "SELECT p FROM Personstaffstatus p WHERE p.personstaffstatusPK.username = :username"),
    @NamedQuery(name = "Personstaffstatus.findByIdstaffstatus", query = "SELECT p FROM Personstaffstatus p WHERE p.personstaffstatusPK.idstaffstatus = :idstaffstatus"),
    @NamedQuery(name = "Personstaffstatus.findByActive", query = "SELECT p FROM Personstaffstatus p WHERE p.active = :active")})
public class Personstaffstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonstaffstatusPK personstaffstatusPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVE")
    private int active;
    @JoinColumn(name = "IDSTAFFSTATUS", referencedColumnName = "IDSTAFFSTATUS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Staffstatus staffstatus;
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person;

    public Personstaffstatus() {
    }

    public Personstaffstatus(PersonstaffstatusPK personstaffstatusPK) {
        this.personstaffstatusPK = personstaffstatusPK;
    }
    

    public Personstaffstatus(PersonstaffstatusPK personstaffstatusPK, int active) {
        this.personstaffstatusPK = personstaffstatusPK;
        this.active = active;
    }

    public Personstaffstatus(String username, long idstaffstatus) {
        this.personstaffstatusPK = new PersonstaffstatusPK(username, idstaffstatus);
    }

    public PersonstaffstatusPK getPersonstaffstatusPK() {
        return personstaffstatusPK;
    }

    public void setPersonstaffstatusPK(PersonstaffstatusPK personstaffstatusPK) {
        this.personstaffstatusPK = personstaffstatusPK;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Staffstatus getStaffstatus() {
        return staffstatus;
    }

    public void setStaffstatus(Staffstatus staffstatus) {
        this.staffstatus = staffstatus;
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
        hash += (personstaffstatusPK != null ? personstaffstatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personstaffstatus)) {
            return false;
        }
        Personstaffstatus other = (Personstaffstatus) object;
        if ((this.personstaffstatusPK == null && other.personstaffstatusPK != null) || (this.personstaffstatusPK != null && !this.personstaffstatusPK.equals(other.personstaffstatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Personstaffstatus[ personstaffstatusPK=" + personstaffstatusPK + " ]";
    }
}
