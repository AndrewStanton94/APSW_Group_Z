package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Feedback database class
 *
 * @author Mike
 */
@Entity
@Table(name = "FEEDBACK")
@NamedQueries({
    @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f"),
    @NamedQuery(name = "Feedback.findByConfirmkey", query = "SELECT f FROM Feedback f WHERE f.confirmkey = :confirmkey")})
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "FEEDBACKID")
    private Integer feedbackID;
    @Basic(optional = false)
    @Column(name = "VIEWEDDATE")
    private String viewedDate;
    @Column(name = "CREATEDATE")
    @Temporal(TemporalType.DATE)
    private Date createdate;
    @Basic(optional = false)
    @Column(name = "MODIFIEDDATE")
    @Temporal(TemporalType.DATE)
    private Date modifieddate;
    @Basic(optional = false)
    @Column(name = "FEEDBACKCOMMENTS")
    private String feedbackcomments;
    @Column(name = "FEEDBACKSENT")
    private String feedbackSent;
    @OneToMany(mappedBy = "feedback")
    private List<MarkerMark> marks;
    @JoinColumn(name = "PROJECT", referencedColumnName = "IDPROJECT")
    @OneToOne
    private Finalproject project;
    @Column(name = "FEEDBACKCONFIRMED")
    private Boolean feedbackConfirmed;
    @Column(name = "CONFIRMKEY")
    private String confirmkey;

    /**
     * Returns the confirmation key generated when feedback has been confirmed
     *
     * @return confirmkey
     */
    public String getConfirmkey() {
        return confirmkey;
    }

    /**
     * Sets the confirmation key which determines the feedback to be viewed
     *
     * @param confirmkey
     */
    public void setConfirmkey(String confirmkey) {
        this.confirmkey = confirmkey;
    }

    /**
     * Returns if feedback has been confirmed
     *
     * @return feedbackConfirmed
     */
    public Boolean getFeedbackConfirmed() {
        return feedbackConfirmed;
    }

    /**
     * Sets whether feedback has been confirmed
     *
     * @param feedbackConfirmed
     */
    public void setFeedbackConfirmed(Boolean feedbackConfirmed) {
        this.feedbackConfirmed = feedbackConfirmed;
    }

    /*
     * Link to project (ProjectID)
     */
    public Finalproject getProject() {
        return project;
    }

    /*
     * Sets the project (ProjectID)
     */
    public void setProject(Finalproject project) {
        this.project = project;
    }

    /**
     * Returns the list of MarkerMark linked to the instance of feedback
     *
     * @return marks
     */
    public List<MarkerMark> getMarks() {
        return marks;
    }

    /**
     * Set the marks linked to instance of feedback
     *
     * @param marks
     */
    public void setMarks(List<MarkerMark> marks) {
        this.marks = marks;
    }

    /**
     * Returns if feedback has been sent
     *
     * @return Date in string format or "Not Sent" if null
     */
    public String getFeedbackSent() {
        if (feedbackSent == null) {
            setFeedbackSent("Not Sent");
        }
        return feedbackSent;
    }

    /**
     * Sets the feedbackSent status
     *
     * @param feedbackSent
     */
    public void setFeedbackSent(String feedbackSent) {
        this.feedbackSent = feedbackSent;
    }

    /**
     * Returns the Feedback identifier
     *
     * @return feedbackID
     */
    public Integer getFeedbackID() {
        return feedbackID;
    }

    /**
     * Sets the Feedback Identifier
     *
     * @param feedbackID
     */
    public void setFeedbackID(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    /**
     * Returns the general comments
     *
     * @return String
     */
    public String getFeedbackcomments() {
        return feedbackcomments;
    }

    /**
     * Set the general Feedback Comments
     *
     * @param feedbackcomments
     */
    public void setFeedbackcomments(String feedbackcomments) {
        this.feedbackcomments = feedbackcomments;
    }

    /**
     * Returns the date Feedback was created
     *
     * @return Date
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * Set the date Feedback was created
     *
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * Returns either "Not Read" or date viewed
     *
     * @return String
     */
    public String getViewedDate() {
        if (viewedDate == null) {
            setViewedDate("Not Read");
        }
        return viewedDate;
    }

    /**
     * Set date viewed either date.toString() or "Not Read"
     *
     * @param viewedDate
     */
    public void setViewedDate(String viewedDate) {
        this.viewedDate = viewedDate;
    }

    /**
     * Returns date feedback was last changed in anyway
     *
     * @return Date
     */
    public Date getModifieddate() {
        return modifieddate;
    }

    /**
     * Sets the date feedback was changed in any way
     *
     * @param modifieddate
     */
    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackID != null ? feedbackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.feedbackID == null && other.feedbackID != null) || (this.feedbackID != null && !this.feedbackID.equals(other.feedbackID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Feedback[ feedbackID=" + feedbackID + " ]";
    }
}
