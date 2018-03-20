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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
//@Table(name = "KIND")
@NamedQueries({
    @NamedQuery(name = "ProjectKind.findAll", query = "SELECT k FROM ProjectKind k"),
    @NamedQuery(name = "ProjectKind.findByIdkind", query = "SELECT k FROM ProjectKind k WHERE k.idkind = :idkind"),
    @NamedQuery(name = "ProjectKind.findByKindname", query = "SELECT k FROM ProjectKind k WHERE k.kindname = :kindname")})
public class ProjectKind implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "IDKIND")
    private Long idkind;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
//    @Column(name = "KINDNAME")
    private String kindname;
//    By BerardiD *******
    @ManyToMany(mappedBy = "kindList")
    private List<Projectidea> projectideaList;
//    *******
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitkind")
    private List<Unit> unitList;

    public ProjectKind() {
    }

    public ProjectKind(Long idkind) {
        this.idkind = idkind;
    }

    public ProjectKind(Long idkind, String kindname) {
        this.idkind = idkind;
        this.kindname = kindname;
    }

    static private final String[] initialkinds = {"Engineering", "Study","Undergraduate","Postgraduate"};
    static private List<ProjectKind> allProjectKinds = null;

    static public List<ProjectKind> getAllProjectKinds() {
        if (allProjectKinds == null) {
            allProjectKinds = new ArrayList<ProjectKind>();
            Long id = 0L;
            for (String s : initialkinds) {
                id++;
                ProjectKind k = new ProjectKind(id, s);
                allProjectKinds.add(k);
            }
        }
        return allProjectKinds;
    }

    public Long getIdkind() {
        return idkind;
    }

    public void setIdkind(Long idkind) {
        this.idkind = idkind;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public List<Projectidea> getProjectideaList() {
        return projectideaList;
    }

    private final void setProjectideaList(List<Projectidea> projectideaList) {
        /*
         * This is a dangerous method to make public because it could be used to
         * change a lot of ideas. See Jim before changing.
         */
        this.projectideaList = projectideaList;
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList) {
        this.unitList = unitList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkind != null ? idkind.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectKind)) {
            return false;
        }
        ProjectKind other = (ProjectKind) object;
        if ((this.idkind == null && other.idkind != null) || (this.idkind != null && !this.idkind.equals(other.idkind))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.ProjectKind[ idkind=" + idkind + " ]";
    }

}
