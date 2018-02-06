/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
//@Table(name = "APP_PROPERTIES")
@NamedQueries({
    @NamedQuery(name = "AppProperty.findAll", query = "SELECT a FROM AppProperty a"),
    @NamedQuery(name = "AppProperty.findByProperty", query = "SELECT a FROM AppProperty a WHERE a.propKey = :property"),
    @NamedQuery(name = "AppProperty.findByValue", query = "SELECT a FROM AppProperty a WHERE a.propValue = :value")})
public class AppProperty implements Serializable {

    private static final long serialVersionUID = 1L;
//	@EmbeddedId
//	protected AppPropertyPK AppPropertyPK;
    @Id
    protected String propKey;
    protected String propValue;

    public AppProperty() {
    }

    public AppProperty(String key, String value) {
        this.propKey = key;
        this.propValue = value;
    }

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

//    public AppPropertyPK getAppPropertyPK() {
//        return AppPropertyPK;
//    }
//
//    public void setAppPropertyPK(AppPropertyPK AppPropertyPK) {
//        this.AppPropertyPK = AppPropertyPK;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
//        hash += (AppPropertyPK != null ? AppPropertyPK.hashCode() : 0);
        hash = propKey.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppProperty)) {
            return false;
        }
        AppProperty other = (AppProperty) object;
        if ((this.propKey == null && other.propKey != null) || (this.propKey != null && !this.propKey.equals(other.propKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.AppProperty[ propKey=" + propKey + " ]";
    }
}
