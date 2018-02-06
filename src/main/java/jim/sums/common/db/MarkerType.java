/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
@Table(name = "MARKER_TYPE")
@NamedQueries({
    @NamedQuery(name = "MarkerType.findAll", query = "SELECT m FROM MarkerType m"),
    @NamedQuery(name = "MarkerType.findByMarkerTypeId", query = "SELECT m FROM MarkerType m WHERE m.markerTypeId = :markerTypeId"),
    @NamedQuery(name = "MarkerType.findByMarkerDescription", query = "SELECT m FROM MarkerType m WHERE m.markerDescription = :markerDescription")})
public class MarkerType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKER_TYPE_ID")
    private Integer markerTypeId;
    @Size(max = 32)
    @Column(name = "MARKER_DESCRIPTION")
    private String markerDescription;

    public MarkerType() {
    }

    public MarkerType(Integer markerTypeId) {
        this.markerTypeId = markerTypeId;
    }

    public MarkerType(Integer markerTypeId, String markerDescription) {
        this.markerTypeId = markerTypeId;
        this.markerDescription = markerDescription;
    }

    public Integer getMarkerTypeId() {
        return markerTypeId;
    }

    public void setMarkerTypeId(Integer markerTypeId) {
        this.markerTypeId = markerTypeId;
    }

    public String getMarkerDescription() {
        return markerDescription;
    }

    public void setMarkerDescription(String markerDescription) {
        this.markerDescription = markerDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (markerTypeId != null ? markerTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarkerType)) {
            return false;
        }
        MarkerType other = (MarkerType) object;
        if ((this.markerTypeId == null && other.markerTypeId != null) || (this.markerTypeId != null && !this.markerTypeId.equals(other.markerTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.MarkerType[ markerTypeId=" + markerTypeId + " ]";
    }
}
