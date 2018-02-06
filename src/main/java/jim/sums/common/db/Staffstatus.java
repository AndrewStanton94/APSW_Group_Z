/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "STAFFSTATUS")
@NamedQueries({
    @NamedQuery(name = "Staffstatus.findAll", query = "SELECT s FROM Staffstatus s"),
    @NamedQuery(name = "Staffstatus.findByIdstaffstatus", query = "SELECT s FROM Staffstatus s WHERE s.id = :id"),
    @NamedQuery(name = "Staffstatus.findByStatusname", query = "SELECT s FROM Staffstatus s WHERE lower(s.statusname) = lower(:statusname)")})
public class Staffstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSTAFFSTATUS")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    private String statusname;
    //
    //Relations
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffstatus")
    private List<Staffprojectrelationship> staffprojectrelationshipList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffstatus")
//    private List<Personstaffstatus> personstaffstatusList;
//    @OneToMany(mappedBy = "personstaffstatusList")
//    private List<Person> personsWithThisStatus;

    public Staffstatus() {
    }

//    public Staffstatus(Integer id) {
//        this.id = id;
//    }
    
    public Staffstatus(String statusname) {
        this.statusname = statusname;
    }

    public Staffstatus(Long id, String statusname) {
        this.id = id;
        this.statusname = statusname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public List<Staffprojectrelationship> getStaffprojectrelationshipList() {
        return staffprojectrelationshipList;
    }

    public void setStaffprojectrelationshipList(List<Staffprojectrelationship> staffprojectrelationshipList) {
        this.staffprojectrelationshipList = staffprojectrelationshipList;
    }

//    public List<Personstaffstatus> getPersonstaffstatusList() {
//        return personstaffstatusList;
//    }
//
//    public void setPersonstaffstatusList(List<Personstaffstatus> personstaffstatusList) {
//        this.personstaffstatusList = personstaffstatusList;
//    }

//    public List<Person> getPersonsWithThisStatus() {
//        return personsWithThisStatus;
//    }
//
//    public void setPersonsWithThisStatus(List<Person> personsWithThisStatus) {
//        this.personsWithThisStatus = personsWithThisStatus;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staffstatus)) {
            return false;
        }
        Staffstatus other = (Staffstatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Staffstatus[ idstaffstatus=" + id + " ]";
    }
}
