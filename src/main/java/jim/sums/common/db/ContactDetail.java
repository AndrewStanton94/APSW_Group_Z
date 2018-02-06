/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
//@Table(name = "CONTACT_DETAIL")
@NamedQueries({
    @NamedQuery(name = "ContactDetail.findAll", query = "SELECT cd FROM ContactDetail cd"),
    @NamedQuery(name = "ContactDetail.findByPerson", query = "SELECT cd FROM ContactDetail cd WHERE cd.person = :person")
})
/**
 *
 * @author Adrian Earle
 */
public class ContactDetail implements Serializable {
    //Fields\\

    @Id
    @GeneratedValue
    @Basic(optional = false)
    @NotNull
//    @Column(name = "cdl_id")
    private Long id;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "cdl_type")
    private String type;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "cdl_username")
    private String username;
    @ManyToOne
    @JoinColumn(name = "PERSON", referencedColumnName = "USERNAME")
    private Person person;

    //Constructors\\
    public ContactDetail() {
    }

    public ContactDetail(Person person, String type, String username) {
        id = null;
        this.person = person;
        this.type = type;
        this.username = username;
    }
    //--End Constructors--//

    //--Gets & Sets--//
    public Long getID() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    //No set ID as ID is uniquely assigned//
    public void setPerson(Person person) {
        this.person = person;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //--End Gets & Sets--//
    public String constructMailTo() {
        if (!"email".equals(type)) {
            return "";
        } else {
            return "mailto:" + username + "?Subject=Final%20Year%20Project%20";
        }
    }
}
