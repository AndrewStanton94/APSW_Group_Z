/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "ACADEMICYEAR")
@NamedQueries({
    @NamedQuery(name = "Academicyear.findAll", query = "SELECT a FROM Academicyear a"),
    @NamedQuery(name = "Academicyear.findByIdacademicyear", query = "SELECT a FROM Academicyear a WHERE a.idacademicyear = :idacademicyear"),
    @NamedQuery(name = "Academicyear.findByStartdate", query = "SELECT a FROM Academicyear a WHERE a.startdate = :startdate"),
    @NamedQuery(name = "Academicyear.findByEnddate", query = "SELECT a FROM Academicyear a WHERE a.enddate = :enddate")})
public class Academicyear implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDACADEMICYEAR")
    private Integer idacademicyear;
    @Basic(optional = false)
    @NotNull
    @Size(min=0, max=32)
    @Column(name = "ACADEMICYEARNAME")
    private String academicYearName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicyear")
    private List<Courseinstance> courseinstanceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicyear")
    private List<UnitInstance> unitinstanceList;

    public Academicyear() {
    }

    public Academicyear(Integer idacademicyear) {
        this.idacademicyear = idacademicyear;
    }

    public Academicyear(Integer idacademicyear, Date startdate, Date enddate) {
        this.idacademicyear = idacademicyear;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Academicyear(Date startdate, Date enddate) {
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Academicyear(String name, int startYear) {
        this.academicYearName = name;
        this.startdate = new GregorianCalendar(startYear, Calendar.AUGUST, 1, 0, 0, 0).getTime();
        this.enddate = new GregorianCalendar(startYear+1, Calendar.JULY, 31, 23, 59, 59).getTime();
    }

    public Integer getIdacademicyear() {
        return idacademicyear;
    }

    public void setIdacademicyear(Integer idacademicyear) {
        this.idacademicyear = idacademicyear;
    }

    public String getAcademicYearName() {
        return academicYearName;
    }

    public void setAcademicYearName(String academicYearName) {
        this.academicYearName = academicYearName;
    }

    public List<Courseinstance> getCourseinstanceList() {
        return courseinstanceList;
    }

    public void setCourseinstanceList(List<Courseinstance> courseinstanceList) {
        this.courseinstanceList = courseinstanceList;
    }

    public List<UnitInstance> getUnitinstanceList() {
        return unitinstanceList;
    }

    public void setUnitinstanceList(List<UnitInstance> unitinstanceList) {
        this.unitinstanceList = unitinstanceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacademicyear != null ? idacademicyear.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Academicyear)) {
            return false;
        }
        Academicyear other = (Academicyear) object;
        if ((this.idacademicyear == null && other.idacademicyear != null) || (this.idacademicyear != null && !this.idacademicyear.equals(other.idacademicyear))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Academicyear[ idacademicyear=" + idacademicyear + " ]";
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public boolean isDateInAcademicYear(Date date) {
        //TODO: needs to handle date = start or end
        return (date.after(this.startdate) && date.before(this.enddate));
    }

    static private List<Date> startingDateList = new ArrayList<Date>();
    protected void addStartingDate(){
        startingDateList.add(new GregorianCalendar(2012, 8, 1).getTime());
        startingDateList.add(new GregorianCalendar(2013, 8, 1).getTime());
    }
    static private List<Date> endingDateList = new ArrayList<Date>();
    protected void addEndingDate(){
        endingDateList.add(new GregorianCalendar(2013, 7, 31).getTime());
        endingDateList.add(new GregorianCalendar(2014, 7, 31).getTime());
    }

    static private List<Academicyear> allAcademicYears = null;
    static public List<Academicyear> getAllAcademicYears() {
        if (allAcademicYears == null) {
            allAcademicYears = new ArrayList<Academicyear>();
            for (int id = 0; id < startingDateList.size(); id++) {
                Academicyear academicYear = new Academicyear(startingDateList.get(id),endingDateList.get(id));
                allAcademicYears.add(academicYear);
            }
        }
        return allAcademicYears;
    }

    public String getName() {
        //if (name == null) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startdate);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(enddate);
        String first = Integer.toString(startCalendar.get(Calendar.YEAR));
        String second = Integer.toString(endCalendar.get(Calendar.YEAR));
        return first + "-" + second;
        // } else {
        //    return name;
        // }
    }

}
