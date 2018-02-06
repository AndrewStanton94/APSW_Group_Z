/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Deprecated

/**
 *
 * @author Dimitar
 */
@Entity
@Table(name = "CATEGORY_MARKS_OPTION")
@NamedQueries({
    @NamedQuery(name = "CategoryMarksOption.findAll", query = "SELECT c FROM CategoryMarksOption c"),
    @NamedQuery(name = "CategoryMarksOption.findByCatMarkOptionId", query = "SELECT c FROM CategoryMarksOption c WHERE c.catMarkOptionId = :catMarkOptionId"),
    @NamedQuery(name = "CategoryMarksOption.findBySelected", query = "SELECT c FROM CategoryMarksOption c WHERE c.selected = :selected")})
public class CategoryMarksOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAT_MARK_OPTION_ID")
    private Integer catMarkOptionId;
    @Size(max = 1)
    @Column(name = "SELECTED")
    private String selected;
    @JoinColumn(name = "CAT_ID", referencedColumnName = "CATID")
    @ManyToOne(optional = false)
    private MarkCategory catId;
    @JoinColumn(name = "MARK_ID", referencedColumnName = "MARK_ID")
    @ManyToOne(optional = false)
    private MarkerMark markId;
    @JoinColumn(name = "OPTION_ID", referencedColumnName = "OPT_ID")
    @ManyToOne(optional = false)
    private CategoryOptions optionId;
    @Transient
    boolean selectedBool;

    public CategoryMarksOption() {
    }

    public CategoryMarksOption(Integer catMarkOptionId) {
        this.catMarkOptionId = catMarkOptionId;
    }
    
    public CategoryMarksOption(String selected) {
        this.selected = selected;
    }

    public Integer getCatMarkOptionId() {
        return catMarkOptionId;
    }

    public void setCatMarkOptionId(Integer catMarkOptionId) {
        this.catMarkOptionId = catMarkOptionId;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public boolean getSelectedBool() {
        return "Y".equals(selected);
    }

    public boolean isSelectedBool() {
        return "Y".equals(selected);
    }

    public void setSelectedBool(boolean selected) {
        if (selected) {
            this.selected = "Y";
        } else {
            this.selected = "N";
        }
        calculateCategoryMark();

    }

    public void calculateCategoryMark() {
     
            List<CategoryMark> current = getMarkId().getCategoryMarksList();
            List<CategoryMarksOption> currentMO = getMarkId().getCategoryMarksOptionList();
            int selectedOption = 0;
            int tmpResult = 0;
            for (CategoryMarksOption co : currentMO) {
                if (co.getCatId().getCatid().equals(getCatId().getCatid())) {
                    if ("Y".equals(co.getSelected())) {
                        tmpResult = tmpResult + co.getOptionId().getOptSelectedValue().intValue();
                        selectedOption++;
                    }
                }

            }
            for (CategoryMark cm : current) {
                if (cm.getCatId().getCatid().equals(getCatId().getCatid())) {
                    if (tmpResult > 0) {
                        cm.setMark(tmpResult / selectedOption);
                    } else {
                        cm.setMark(0);
                    }

                }
            }

    }

    public MarkCategory getCatId() {
        return catId;
    }

    public void setCatId(MarkCategory catId) {
        this.catId = catId;
    }

    public MarkerMark getMarkId() {
        return markId;
    }

    public void setMarkId(MarkerMark markId) {
        this.markId = markId;
    }

    public CategoryOptions getOptionId() {
        return optionId;
    }

    public void setOptionId(CategoryOptions optionId) {
        this.optionId = optionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catMarkOptionId != null ? catMarkOptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryMarksOption)) {
            return false;
        }
        CategoryMarksOption other = (CategoryMarksOption) object;
        if ((this.catMarkOptionId == null && other.catMarkOptionId != null) || (this.catMarkOptionId != null && !this.catMarkOptionId.equals(other.catMarkOptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.CategoryMarksOption[ catMarkOptionId=" + catMarkOptionId + " ]";
    }
}
