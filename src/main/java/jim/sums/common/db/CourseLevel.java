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
//@Table(name = "GRADE")
@NamedQueries({
    @NamedQuery(name = "CourseLevel.findAll", query = "SELECT g FROM CourseLevel g"),
    @NamedQuery(name = "CourseLevel.findById", query = "SELECT g FROM CourseLevel g WHERE g.id = :id"),
    @NamedQuery(name = "CourseLevel.findByName", query = "SELECT g FROM CourseLevel g WHERE g.name = :name")})
public class CourseLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
//    @NotNull
    private Long id;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 32)
//    @Column(name = "GRADENAME")
    private String name;
//    BerardiD *******
    @ManyToMany(mappedBy = "gradeList")
    private List<Projectidea> projectideaList = new ArrayList<>();
//    *******
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseLevel")
    private List<Unit> unitList = new ArrayList<>();

    public CourseLevel() {
    }

    public CourseLevel(Long id) {
        this.id = id;
    }

    public CourseLevel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static private final String[] levelnames = {"First Degree", "Second Degree", "Third Degree", "Masters Degree Not Mainly By Research"};
    static private List<CourseLevel> allCourseLevels = null;

    static public List<CourseLevel> getAllValues() {
        if (allCourseLevels == null) {
            allCourseLevels = new ArrayList<CourseLevel>();
            long id = 0L;
            for (String s : levelnames) {
                id++;
                CourseLevel k = new CourseLevel(id, s);
                allCourseLevels.add(k);
            }
        }
        return allCourseLevels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Projectidea> getProjectideaList() {
        return projectideaList;
    }

    public void setProjectideaList(List<Projectidea> projectideaList) {
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseLevel)) {
            return false;
        }
        CourseLevel other = (CourseLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
