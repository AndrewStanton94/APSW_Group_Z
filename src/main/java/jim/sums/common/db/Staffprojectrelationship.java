/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "STAFFPROJECTRELATIONSHIP")
@NamedQueries({
	@NamedQuery(name = "Staffprojectrelationship.findAll", query = "SELECT s FROM Staffprojectrelationship s"),
	@NamedQuery(name = "Staffprojectrelationship.findByIdproject", query = "SELECT s FROM Staffprojectrelationship s WHERE s.finalproject = :finalproject"),
//	@NamedQuery(name = "Staffprojectrelationship.findByIdperson", query = "SELECT s FROM Staffprojectrelationship s WHERE s.staffprojectrelationshipPK.idperson = :idperson"),
	@NamedQuery(name = "Staffprojectrelationship.findByIdstaffstatus", query = "SELECT s FROM Staffprojectrelationship s WHERE s.staffstatus = :staffstatus")})
public class Staffprojectrelationship implements Serializable {

   
	private static final long serialVersionUID = 1L;
//	@EmbeddedId
//	protected StaffprojectrelationshipPK staffprojectrelationshipPK;
        @Id
        private Long id;
        //@JoinColumn(name = "IDSTAFFSTATUS", referencedColumnName = "IDSTAFFSTATUS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Staffstatus staffstatus;
	//@JoinColumn(name = "IDPERSON", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Person person;       
	//@JoinColumn(name = "IDPROJECT", referencedColumnName = "IDPROJECT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Finalproject finalproject;

	public Staffprojectrelationship() {
	}
        
         public Staffprojectrelationship(Long id, Staffstatus staffstatus, Person person, Finalproject finalproject) {
            this.id = id;
            this.staffstatus = staffstatus;
            this.person = person;
            this.finalproject = finalproject;
        }
//	public Staffprojectrelationship(StaffprojectrelationshipPK staffprojectrelationshipPK) {
//		this.staffprojectrelationshipPK = staffprojectrelationshipPK;
//	}
//
//	public Staffprojectrelationship(int idproject, String idperson, int idstaffstatus) {
//		this.staffprojectrelationshipPK = new StaffprojectrelationshipPK(idproject, idperson, idstaffstatus);
//	}
//
//	public StaffprojectrelationshipPK getStaffprojectrelationshipPK() {
//		return staffprojectrelationshipPK;
//	}
//
//	public void setStaffprojectrelationshipPK(StaffprojectrelationshipPK staffprojectrelationshipPK) {
//		this.staffprojectrelationshipPK = staffprojectrelationshipPK;
//	}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

	public Finalproject getFinalproject() {
		return finalproject;
	}

	public void setFinalproject(Finalproject finalproject) {
		this.finalproject = finalproject;
	}

//	@Override
//	public int hashCode() {
//		int hash = 0;
//		hash += (staffprojectrelationshipPK != null ? staffprojectrelationshipPK.hashCode() : 0);
//		return hash;
//	}

//	@Override
//	public boolean equals(Object object) {
//		// TODO: Warning - this method won't work in the case the id fields are not set
//		if (!(object instanceof Staffprojectrelationship)) {
//			return false;
//		}
//		Staffprojectrelationship other = (Staffprojectrelationship) object;
//		if ((this.staffprojectrelationshipPK == null && other.staffprojectrelationshipPK != null) || (this.staffprojectrelationshipPK != null && !this.staffprojectrelationshipPK.equals(other.staffprojectrelationshipPK))) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "jim.sums.common.db.Staffprojectrelationship[ staffprojectrelationshipPK=" + staffprojectrelationshipPK + " ]";
//	}
	
}
