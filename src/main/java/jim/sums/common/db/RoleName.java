/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author BriggsJ
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "RoleName.findAll", query = "SELECT p FROM RoleName p"),
    @NamedQuery(name = "RoleName.findById", query = "SELECT p FROM RoleName p WHERE p.id = :id"),
    @NamedQuery(name = "RoleName.findByName", query = "SELECT p FROM RoleName p WHERE lower(p.id) = lower(:name)")})
@DeclareRoles(value = {"User", "Administrator", "Staff", "Student", "External"})
public class RoleName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToMany(mappedBy = "roles")
    private Set<Person> personsHavingRole;

    public RoleName() {
    }

    public RoleName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Person> getPersonsHavingRole() {
        return personsHavingRole;
    }

    public void setPersonsHavingRole(Set<Person> personsHavingRole) {
        this.personsHavingRole = personsHavingRole;
    }

    public boolean matches(String rolename) {
        return this.getId().equalsIgnoreCase(rolename);
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
        if (!(object instanceof RoleName)) {
            return false;
        }
        RoleName other = (RoleName) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role."+id;
    }
    //Initialisation section
    //TODO This list should be the same as in the DeclareRoles annotation above
    static private final String[] initialRoles = {"User", "Administrator", "Staff", "Student", "External"};
    static private List<RoleName> allRoleNames = null;

    static public List<RoleName> getAllRoleNames() {
        if (allRoleNames == null) {
            allRoleNames = new ArrayList<RoleName>();
            for (String s : initialRoles) {
                RoleName rn = new RoleName(s);
                allRoleNames.add(rn);
            }
        }
        return allRoleNames;
    }
}
