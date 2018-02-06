/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Adrian Earle
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "MILESTONE_BROAD")
@NamedQueries({
    @NamedQuery(name = "MilestoneTemplate.findAll", query = "SELECT msb FROM MilestoneTemplate msb"),
    @NamedQuery(name = "MilestoneTemplate.findByCohort", query = "SELECT msb FROM MilestoneTemplate msb WHERE msb.cohort = :cohort")
})
public class MilestoneTemplate implements Serializable {

    //Fields\\
    @Id
    @GeneratedValue
//    @Column(name = "msb_id")
    private Long id;
    @ManyToOne
//    @JoinColumn(name = "COHORT", referencedColumnName = "IDCOHORT")
    private Cohort cohort;
    @Basic(optional = false)
    @NotNull
//    @Column(name = "msb_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
//    @Column(name = "msb_fromDate")
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
//    @Column(name = "msb_toDate")
    private Date endDate;
    @OneToMany(mappedBy = "template")
    private List<MilestoneInstance> specifics;

    //Constructors\\
    public MilestoneTemplate() {
    }

    public MilestoneTemplate(Long id) {
    }

    public MilestoneTemplate(Cohort cohort, String name, Date from, Date to, List<MilestoneInstance> lMsS) {
        id = null;
        this.cohort = cohort;
        this.name = name;
        startDate = from;
        endDate = to;
        specifics = lMsS;
    }

    public void setCohort(Cohort cohort) {
        this.cohort = cohort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date from) {
        startDate = from;
    }

    public void setEndDate(Date to) {
        endDate = to;
    }

    public Long getID() {
        return id;
    }

    public Cohort getCohort() {
        return cohort;
    }

    public String getName() {
        return name;
    }

    public Date getFromDate() {
        return startDate;
    }

    public Date getToDate() {
        return endDate;
    }

    public List<MilestoneInstance> getMSpecifics() {
        return specifics;
    }

    public boolean addMSpecific(MilestoneInstance ms) {
        return specifics.add(ms);
    }

    public boolean removeMSpecific(MilestoneInstance ms) {
        return specifics.remove(ms);
    }

    public void setMSpecific(List<MilestoneInstance> lms) {
        specifics = lms;
    }

    public boolean isActive() {
        Date now = new Date();
        if (now.after(startDate) && now.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }
}
