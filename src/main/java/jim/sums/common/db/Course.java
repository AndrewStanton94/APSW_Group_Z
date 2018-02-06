/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "COURSE")
@NamedQueries({
	@NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
	@NamedQuery(name = "Course.findByCoursecode", query = "SELECT c FROM Course c WHERE c.coursecode = :coursecode"),
	@NamedQuery(name = "Course.findByCoursename", query = "SELECT c FROM Course c WHERE c.coursename = :coursename")})
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "COURSECODE")
	private String coursecode;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "COURSENAME")
	private String coursename;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Courseinstance> courseinstanceList;

	public Course() {
	}

	public Course(String coursecode) {
		this.coursecode = coursecode;
	}

	public Course(String coursecode, String coursename) {
		this.coursecode = coursecode;
		this.coursename = coursename;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public List<Courseinstance> getCourseinstanceList() {
		return courseinstanceList;
	}

	public void setCourseinstanceList(List<Courseinstance> courseinstanceList) {
		this.courseinstanceList = courseinstanceList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (coursecode != null ? coursecode.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Course)) {
			return false;
		}
		Course other = (Course) object;
		if ((this.coursecode == null && other.coursecode != null) || (this.coursecode != null && !this.coursecode.equals(other.coursecode))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "jim.sums.common.db.Course[ coursecode=" + coursecode + " ]";
	}
	
}
