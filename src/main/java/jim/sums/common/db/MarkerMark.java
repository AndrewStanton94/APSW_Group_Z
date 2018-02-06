/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dimitar
 */
@Entity
// @Table(name = "MARKER_MARK")
@NamedQueries({
    @NamedQuery(name = "MarkerMark.findAll", query = "SELECT mm FROM MarkerMark mm"),
    @NamedQuery(name = "MarkerMark.findByMarkId", query = "SELECT mm FROM MarkerMark mm WHERE mm.markId = :markId"),
    @NamedQuery(name = "MarkerMark.findByMark", query = "SELECT mm FROM MarkerMark mm WHERE mm.mark = :mark"),
    @NamedQuery(name = "MarkerMark.findByProject", query = "SELECT mm FROM MarkerMark mm WHERE mm.projectId = :fp"),
    @NamedQuery(name = "MarkerMark.findByMarkDate", query = "SELECT mm FROM MarkerMark mm WHERE mm.markDate = :markDate"),
    @NamedQuery(name = "MarkerMark.findByGeneralComments", query = "SELECT mm FROM MarkerMark mm WHERE mm.generalComments = :generalComments"),
    @NamedQuery(name = "MarkerMark.findByCommentsForExaminers", query = "SELECT mm FROM MarkerMark mm WHERE mm.commentsForExaminers = :commentsForExaminers"),
    @NamedQuery(name = "MarkerMark.findByGeneralCommentsExamboard", query = "SELECT mm FROM MarkerMark mm WHERE mm.generalCommentsExamboard = :generalCommentsExamboard"),
    @NamedQuery(name = "MarkerMark.findByPlagiarismUnfairAct", query = "SELECT mm FROM MarkerMark mm WHERE mm.plagiarismUnfairAct = :plagiarismUnfairAct"),
    @NamedQuery(name = "MarkerMark.findByPlagiarismComments", query = "SELECT mm FROM MarkerMark mm WHERE mm.plagiarismComments = :plagiarismComments"),
    @NamedQuery(name = "MarkerMark.findByUnfairActNotes", query = "SELECT mm FROM MarkerMark mm WHERE mm.unfairActNotes = :unfairActNotes"),
    @NamedQuery(name = "MarkerMark.findByAdjustmentApplied", query = "SELECT mm FROM MarkerMark mm WHERE mm.adjustmentApplied = :adjustmentApplied"),
    @NamedQuery(name = "MarkerMark.findByPlagiarismSuspect", query = "SELECT mm FROM MarkerMark mm WHERE mm.plagiarismSuspect = :plagiarismSuspect"),
    @NamedQuery(name = "MarkerMark.findByMarker", query = "SELECT mm FROM MarkerMark mm WHERE mm.marker = :marker"),
    @NamedQuery(name = "Marks.findIdByMarkerAndProject", query ="SELECT mm.mark FROM MarkerMark mm WHERE mm.marker = :marker AND mm.projectId = :projectId"),
    @NamedQuery(name = "Marks.findStatusByMarkerAndProject", query ="SELECT mm.markStatus FROM MarkerMark mm WHERE mm.marker = :marker AND mm.projectId = :projectId")
})

public class MarkerMark implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    // @Column(name = "MARK_ID")
    private Long markId;
    @Basic(optional = false)
    @NotNull
    // @Column(name = "MARK")
    private int mark;
    // @Column(name = "MARK_DATE")
    @Temporal(TemporalType.DATE)
    private Date markDate;
    @Size(max = 500)
    // @Column(name = "GENERAL_COMMENTS")
    private String generalComments;
    @Size(max = 500)
    // @Column(name = "COMMENTS_FOR_EXAMINERS")
    private String commentsForExaminers;
    @Size(max = 500)
    // @Column(name = "GENERAL_COMMENTS_EXAMBOARD")
    private String generalCommentsExamboard;
    @Size(max = 1)
    // @Column(name = "PLAGIARISM_UNFAIR_ACT")
    private String plagiarismUnfairAct;
    @Size(max = 500)
    // @Column(name = "PLAGIARISM_COMMENTS")
    private String plagiarismComments;
    @Size(max = 500)
    // @Column(name = "UNFAIR_ACT_NOTES")
    private String unfairActNotes;
    // @Column(name = "ADJUSTMENT_APPLIED")
    private Integer adjustmentApplied;
    @Size(max = 1)
    // @Column(name = "PLAGIARISM_SUSPECT")
    private String plagiarismSuspect;
    @Size(max = 32)
    // @Column(name = "MARKER")
    private String markerName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "markId")
    private List<CategoryMarksOption> categoryMarksOptionList;
    @OneToMany(mappedBy = "markerId")
    private List<CategoryMark> categoryMarksList;
    // @JoinColumn(name = "PROJECT_ID", referencedColumnName = "IDPROJECT")
    @ManyToOne(optional = false)
    private Finalproject projectId;
    // @Column(name = "MARK_STATUS")
    String markStatus;
    // @JoinColumn(name = "FEEDBACKID", referencedColumnName = "FEEDBACKID")
    @ManyToOne
    private Feedback feedback;
    @OneToMany(mappedBy = "markerId")
    private List<CategoryMark> categoryMarks;

    public List<CategoryMark> getCategoryMarks() {
        return categoryMarks;
    }

    public void setCategoryMarks(List<CategoryMark> categoryMarks) {
        this.categoryMarks = categoryMarks;
    }

    public Person getMarker() {
        return marker;
    }

    public void setMarker(Person marker) {
        this.marker = marker;
    }
    @ManyToOne
    private Person marker;

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public String getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(String markStatus) {
        this.markStatus = markStatus;
    }

    public void calculateOverallMark() {
        int selectedOption = 0;
        int tmpResult = 0;
        boolean specialCatUnderLimit=false;
        for (CategoryMark cm : getCategoryMarksList()) {
            //cm.getCatId().getMarkFormCategoriesList().get(0) -this is not ok!!!!
            int catWeight = cm.getCatId().getMarkFormCategoriesList().get(0).getTemplateWeightCategory().getWeightValue();
         
            if(cm.getCatId().getMarkFormCategoriesList().get(0).getCritical()!=null&& cm.getCatId().getMarkFormCategoriesList().get(0).getCritical().toString().equals("Y") && cm.getMark()<40){
               specialCatUnderLimit=true; 
                
            }
            selectedOption = selectedOption + catWeight;
            tmpResult = tmpResult + (cm.getMark() * catWeight);

        }
        tmpResult = tmpResult / selectedOption;
        if (getAdjustmentApplied() != null) {
            tmpResult = tmpResult + getAdjustmentApplied().intValue();
        }
        //If we have a category which leads to overall mark of 39 then set it
        if(specialCatUnderLimit && tmpResult>=40){
            setMark(39);
        } else {
            setMark(tmpResult);
        }

    }

    public Finalproject getProjectId() {
        return projectId;
    }

    public void setProjectId(Finalproject projectId) {
        this.projectId = projectId;
    }

    public MarkerMark() {
    }

    public MarkerMark(Long markId) {
        this.markId = markId;
    }

    public MarkerMark(Long markId, int mark) {
        this.markId = markId;
        this.mark = mark;
    }
    
    public MarkerMark(int mark, Date markDate, String generalComments, 
                 String commentsForExaminers, String generalCommentsExamboard, 
                 String plagiarismUnfairAct, String plagiarismComments, 
                 String unfairActNotes, Integer adjustmentApplied, 
                 String plagiarismSuspect, String markStatus) {
        this.mark = mark;
        this.markDate = markDate;
        this.generalComments = generalComments;
        this.commentsForExaminers = commentsForExaminers;
        this.generalCommentsExamboard = generalCommentsExamboard;
        this.plagiarismUnfairAct = plagiarismUnfairAct;
        this.plagiarismComments = plagiarismComments;
        this.unfairActNotes = unfairActNotes;
        this.adjustmentApplied = adjustmentApplied;
        this.plagiarismSuspect = plagiarismSuspect;
        this.markStatus = markStatus;
    }

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getMarkDate() {
        if (markDate == null) {
            markDate = new Date();
        }
        return markDate;
    }

    public boolean getPlagiarism() {
        return "Y".equals(plagiarismUnfairAct);
    }

    public void setPlagiarism(boolean selected) {
        if (selected) {
            this.plagiarismUnfairAct = "Y";
        } else {
            this.plagiarismUnfairAct = "N";
        }


    }

    public boolean getPlagiarismSuspectBool() {
        return "Y".equals(plagiarismSuspect);
    }

    public void setPlagiarismSuspectBool(boolean selected) {
        if (selected) {
            this.plagiarismSuspect = "Y";
        } else {
            this.plagiarismSuspect = "N";
        }
    }

    public void setMarkDate(Date markDate) {
        this.markDate = markDate;
    }

    public String getGeneralComments() {
        return generalComments;
    }

    public void setGeneralComments(String generalComments) {
        this.generalComments = generalComments;
    }

    public String getCommentsForExaminers() {
        return commentsForExaminers;
    }

    public void setCommentsForExaminers(String commentsForExaminers) {
        this.commentsForExaminers = commentsForExaminers;
    }

    public String getGeneralCommentsExamboard() {
        return generalCommentsExamboard;
    }

    public void setGeneralCommentsExamboard(String generalCommentsExamboard) {
        this.generalCommentsExamboard = generalCommentsExamboard;
    }

    public String getPlagiarismUnfairAct() {
        return plagiarismUnfairAct;
    }

    public void setPlagiarismUnfairAct(String plagiarismUnfairAct) {
        this.plagiarismUnfairAct = plagiarismUnfairAct;
    }

    public String getPlagiarismComments() {
        return plagiarismComments;
    }

    public void setPlagiarismComments(String plagiarismComments) {
        this.plagiarismComments = plagiarismComments;
    }

    public String getUnfairActNotes() {
        return unfairActNotes;
    }

    public void setUnfairActNotes(String unfairActNotes) {
        this.unfairActNotes = unfairActNotes;
    }

    public Integer getAdjustmentApplied() {
        return adjustmentApplied;
    }

    public void setAdjustmentApplied(Integer adjustmentApplied) {
        this.adjustmentApplied = adjustmentApplied;
        calculateOverallMark();
    }

    public String getPlagiarismSuspect() {
        return plagiarismSuspect;
    }

    public void setPlagiarismSuspect(String plagiarismSuspect) {
        this.plagiarismSuspect = plagiarismSuspect;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public List<CategoryMarksOption> getCategoryMarksOptionList() {
        return categoryMarksOptionList;
    }

    public void setCategoryMarksOptionList(List<CategoryMarksOption> categoryMarksOptionList) {
        this.categoryMarksOptionList = categoryMarksOptionList;
    }

    public List<CategoryMark> getCategoryMarksList() {
        return categoryMarksList;
    }

    public void setCategoryMarksList(List<CategoryMark> categoryMarksList) {
        this.categoryMarksList = categoryMarksList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (markId != null ? markId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarkerMark)) {
            return false;
        }
        MarkerMark other = (MarkerMark) object;
        if ((this.markId == null && other.markId != null) || (this.markId != null && !this.markId.equals(other.markId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.MarkerMark[ markId=" + markId + " ]";
    }
}
