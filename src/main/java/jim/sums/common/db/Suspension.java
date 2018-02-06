/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "SUSPENSION")
@NamedQueries({
	@NamedQuery(name = "Suspension.findAll", query = "SELECT s FROM Suspension s"),
	@NamedQuery(name = "Suspension.findByIdsuspension", query = "SELECT s FROM Suspension s WHERE s.idsuspension = :idsuspension"),
	@NamedQuery(name = "Suspension.findByDatesuspension", query = "SELECT s FROM Suspension s WHERE s.datesuspension = :datesuspension"),
	@NamedQuery(name = "Suspension.findByReason", query = "SELECT s FROM Suspension s WHERE s.reason = :reason")})
public class Suspension implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDSUSPENSION")
    private Integer idsuspension;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATESUSPENSION")
    @Temporal(TemporalType.DATE)
    private Date datesuspension;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "REASON")
    private String reason;
    @JoinColumn(name = "SUSPENDEDPERSON", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Person suspendedperson;
    @JoinColumn(name = "INSTIGATERPERSON", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Person instigaterperson;
    private static final long serialVersionUID = 1L;

    public Suspension() {
    }

    public Suspension(Integer idsuspension) {
            this.idsuspension = idsuspension;
    }

    public Suspension(Integer idsuspension, Date datesuspension, String reason) {
            this.idsuspension = idsuspension;
            this.datesuspension = datesuspension;
            this.reason = reason;
    }

    public Integer getIdsuspension() {
            return idsuspension;
    }

    public void setIdsuspension(Integer idsuspension) {
            this.idsuspension = idsuspension;
    }

    public String getReason() {
            return reason;
    }

    public void setReason(String reason) {
            this.reason = reason;
    }

    public Person getSuspendedperson() {
            return suspendedperson;
    }

    public void setSuspendedperson(Person suspendedperson) {
            this.suspendedperson = suspendedperson;
    }

    public Person getInstigaterperson() {
            return instigaterperson;
    }

    public void setInstigaterperson(Person instigaterperson) {
            this.instigaterperson = instigaterperson;
    }

    @Override
    public int hashCode() {
            int hash = 0;
            hash += (idsuspension != null ? idsuspension.hashCode() : 0);
            return hash;
    }

    @Override
    public boolean equals(Object object) {
            // TODO: Warning - this method won't work in the case the id fields are not set
            if (!(object instanceof Suspension)) {
                    return false;
            }
            Suspension other = (Suspension) object;
            if ((this.idsuspension == null && other.idsuspension != null) || (this.idsuspension != null && !this.idsuspension.equals(other.idsuspension))) {
                    return false;
            }
            return true;
    }

    @Override
    public String toString() {
            return "jim.sums.common.db.Suspension[ idsuspension=" + idsuspension + " ]";
    }

    public Date getDatesuspension() {
        return datesuspension;
    }

    public void setDatesuspension(Date datesuspension) {
        this.datesuspension = datesuspension;
    }
	
}
