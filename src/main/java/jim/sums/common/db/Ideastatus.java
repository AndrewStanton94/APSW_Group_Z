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
@Table(name = "IDEASTATUS")
@NamedQueries({
    @NamedQuery(name = "Ideastatus.findAll", query = "SELECT i FROM Ideastatus i"),
    @NamedQuery(name = "Ideastatus.findByIdideastatus", query = "SELECT i FROM Ideastatus i WHERE i.idideastatus = :idideastatus"),
    @NamedQuery(name = "Ideastatus.findByIdeastatusname", query = "SELECT i FROM Ideastatus i WHERE i.ideastatusname = :ideastatusname")})
public class Ideastatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDIDEASTATUS")
    private Integer idideastatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "IDEASTATUSNAME")
    private String ideastatusname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideastatus")
    private List<Projectidea> projectideaList;
//
    //Initialise
    static private final String[] initialStatuses = {"Provisional", "Approved", "Withdrawn", "Allocated"};
    static private List<Ideastatus> allIdeastatuses = null;

    static public List<Ideastatus> getAllIdeastatuses() {
        if (allIdeastatuses == null) {
            allIdeastatuses = new ArrayList<Ideastatus>();
            Integer id = 0;
            for (String s : initialStatuses) {
                id++;
                Ideastatus ps = new Ideastatus(id, s);
                allIdeastatuses.add(ps);
            }
        }
        return allIdeastatuses;
    }
    
    public Ideastatus() {
    }

    public Ideastatus(Integer idideastatus) {
        this.idideastatus = idideastatus;
    }

    public Ideastatus(Integer idideastatus, String ideastatusname) {
        this.idideastatus = idideastatus;
        this.ideastatusname = ideastatusname;
    }

    public Integer getIdideastatus() {
        return idideastatus;
    }

    public void setIdideastatus(Integer idideastatus) {
        this.idideastatus = idideastatus;
    }

    public String getIdeastatusname() {
        return ideastatusname;
    }

    public void setIdeastatusname(String ideastatusname) {
        this.ideastatusname = ideastatusname;
    }

    public List<Projectidea> getProjectideaList() {
        return projectideaList;
    }

    public void setProjectideaList(List<Projectidea> projectideaList) {
        this.projectideaList = projectideaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idideastatus != null ? idideastatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ideastatus)) {
            return false;
        }
        Ideastatus other = (Ideastatus) object;
        if ((this.idideastatus == null && other.idideastatus != null) || (this.idideastatus != null && !this.idideastatus.equals(other.idideastatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Ideastatus[ idideastatus=" + idideastatus + " ]";
    }
}
