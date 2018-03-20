/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.management.Descriptor;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dimitar
 */
@Entity
// @Table(name = "CATEGORY_MARKS")
@NamedQueries({
    @NamedQuery(name = "CategoryMark.findAll", query = "SELECT cm FROM CategoryMark cm"),
    @NamedQuery(name = "CategoryMark.findByCatMarkId", query = "SELECT cm FROM CategoryMark cm WHERE cm.catMarkId = :catMarkId"),
    @NamedQuery(name = "CategoryMark.findByMark", query = "SELECT cm FROM CategoryMark cm WHERE cm.mark = :mark"),
    @NamedQuery(name = "CategoryMark.findByCatComment", query = "SELECT cm FROM CategoryMark cm WHERE cm.catComment = :catComment")})
public class CategoryMark implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Basic(optional = false)
//    @NotNull
    // @Column(name = "CAT_MARK_ID")
    private Long catMarkId;
//    @Basic(optional = false)
//    @NotNull
    // @Column(name = "MARK")
    private int mark = 0;
    @Size(max = 500)
    // @Column(name = "CAT_COMMENT")
    private String catComment;
    // @JoinColumn(name = "CAT_ID", referencedColumnName = "CATID")
    @ManyToOne(optional = false)
    private MarkCategory catId;
    // @JoinColumn(name = "MARK_ID", referencedColumnName = "MARK_ID")
    @ManyToOne(optional = false)
    private MarkerMark markerId;
    @ManyToMany
    private List<TemplateCriteria> criteria;
    @ManyToMany
    private List<TemplateWeightCategory> weights;

    public CategoryMark() {
    }

    public CategoryMark(Long catMarkId) {
        this.catMarkId = catMarkId;
    }

    public CategoryMark(Long catMarkId, int mark) {
        this.catMarkId = catMarkId;
        this.mark = mark;
    }

    public List<TemplateCriteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<TemplateCriteria> criteria) {
        this.criteria = criteria;
    }

    public List<TemplateWeightCategory> getWeights() {
        return weights;
    }

    public void setWeights(List<TemplateWeightCategory> weights) {
        this.weights = weights;
    }

    public CategoryMark(int mark, String catComment) {
        this.mark = mark;
        this.catComment = catComment;
    }

    public Long getCatMarkId() {
        return catMarkId;
    }

    public void setCatMarkId(Long catMarkId) {
        this.catMarkId = catMarkId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
        getMarkerId().calculateOverallMark();
    }

    public String getCatComment() {
        return catComment;
    }

    public void setCatComment(String catComment) {
        this.catComment = catComment;
    }

    public MarkCategory getCatId() {
        return catId;
    }

    public void setCatId(MarkCategory catId) {
        this.catId = catId;
    }

    public MarkerMark getMarkerId() {
        return markerId;
    }

    public void setMarkerId(MarkerMark markerId) {
        this.markerId = markerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catMarkId != null ? catMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryMark)) {
            return false;
        }
        CategoryMark other = (CategoryMark) object;
        if ((this.catMarkId == null && other.catMarkId != null) || (this.catMarkId != null && !this.catMarkId.equals(other.catMarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.CategoryMark[ catMarkId=" + catMarkId + " ]";
    }

}
