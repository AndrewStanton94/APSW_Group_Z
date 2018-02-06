/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "ORGANISATION")
@NamedQueries({
    @NamedQuery(name = "Organisation.findAll", query = "SELECT o FROM Organisation o"),
    @NamedQuery(name = "Organisation.findByIdorganisation", query = "SELECT o FROM Organisation o WHERE o.id = :idorganisation"),
    @NamedQuery(name = "Organisation.findByNameorganisation", query = "SELECT o FROM Organisation o WHERE o.nameorganisation = :nameorganisation"),
    @NamedQuery(name = "Organisation.findByMailingaddress", query = "SELECT o FROM Organisation o WHERE o.mailingaddress = :mailingaddress"),
    @NamedQuery(name = "Organisation.findByPostcode", query = "SELECT o FROM Organisation o WHERE o.postcode = :postcode"),
    @NamedQuery(name = "Organisation.findByActivity", query = "SELECT o FROM Organisation o WHERE o.activity = :activity")})
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
//    @Column(name = "IDORGANISATION")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
//    @Column(name = "NAMEORGANISATION")
    private String nameorganisation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
//    @Column(name = "MAILINGADDRESS")
    private String mailingaddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
//    @Column(name = "POSTCODE")
    private String postcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
//    @Column(name = "ACTIVITY")
    private String activity;
    @OneToMany(mappedBy = "organisation")
    private List<Person> personList;

    public Organisation() {
    }

    public Organisation(Integer id) {
        this.id = id;
    }

    public Organisation(Integer id, String nameorganisation, String mailingaddress, String postcode, String activity) {
        this.id = id;
        this.nameorganisation = nameorganisation;
        this.mailingaddress = mailingaddress;
        this.postcode = postcode;
        this.activity = activity;
    }

    public Organisation(String nameorganisation, String mailingaddress, String postcode, String activity) {
        this.nameorganisation = nameorganisation;
        this.mailingaddress = mailingaddress;
        this.postcode = postcode;
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameorganisation() {
        return nameorganisation;
    }

    public void setNameorganisation(String nameorganisation) {
        this.nameorganisation = nameorganisation;
    }

    public String getMailingaddress() {
        return mailingaddress;
    }

    public void setMailingaddress(String mailingaddress) {
        this.mailingaddress = mailingaddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organisation)) {
            return false;
        }
        Organisation other = (Organisation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Organisation[ id=" + id + " ]";
    }
}
