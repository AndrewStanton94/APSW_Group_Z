package jim.sums.common.bus;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.ctrl.SystemInfoController;

/**
 * @author thomas
 */

@Singleton
@Startup
public class SystemInfoService {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;
    
    private String projectUrl;
    private String databaseUrl;
    private String projectVersion;
    private String svnRevision;
    
    @PostConstruct
    private void init() {
        //We can not initialize projectUrl now because there is not FacesContext yet
        projectUrl = null;
        initDatabaseUrl();
        initProjectVersion();
        initSvnRevision();
    }
    
    public void initProjectUrl() {
          try {
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                  null, ext.getRequestServerName(), ext.getRequestServerPort(),
                  ext.getRequestContextPath(), null, null);
            projectUrl = uri.toASCIIString();
          } catch (URISyntaxException e) {
            throw new FacesException(e);
          }
    }

    public void initDatabaseUrl() {
        String database;
        Connection connection = em.unwrap(Connection.class);
        try {
            database = connection.getMetaData().getURL() + "/" + connection.getMetaData().getUserName();
        } catch (SQLException ex) {
            Logger.getLogger(SystemInfoController.class.getName()).log(Level.SEVERE, null, ex);
            database = null;
        }
        databaseUrl = database;
    }

    public void initProjectVersion() {
        //get current build version from buildVersion-shared.properties
        String version;
        try {
            ResourceBundle rb = ResourceBundle.getBundle("buildVersion-shared");
            version = rb.getString("buildVersion.major") + "." + rb.getString("buildVersion.minor") + "." + rb.getString("buildVersion.revision");
        } catch (Exception ex) {
            Logger.getLogger(SystemInfoController.class.getName()).log(Level.SEVERE, null, ex);
            version = null;
        }
        
        projectVersion = version;
    }
    
    public void initSvnRevision() {
        //get current SVN revision from buildVersion-local.properties
        String revision;
        try {
            ResourceBundle rb = ResourceBundle.getBundle("buildVersion-local");
            String svnCheckedRevision = rb.getString("buildVersion.SVN.revision.checked");
            if (svnCheckedRevision != null && svnCheckedRevision.equals("true"))
                revision = rb.getString("buildVersion.SVN.revision");
            else
                revision = null;
        } catch (Exception ex) {
            Logger.getLogger(SystemInfoController.class.getName()).log(Level.SEVERE, null, ex);
            revision = null;
        }
        
        svnRevision = revision;
    }

    public String getProjectUrl() {
        if (projectUrl == null)
            initProjectUrl();
        return projectUrl;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public String getSvnRevision() {
        return svnRevision;
    }
}
