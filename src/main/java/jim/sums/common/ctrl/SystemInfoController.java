package jim.sums.common.ctrl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import jim.sums.common.bus.SystemInfoService;

/**
 * @author thomas
 */

@Stateless
@ManagedBean(name = "sysInfo")
public class SystemInfoController {

    @EJB
    private SystemInfoService sis;
    
    public String getVersion() {
        String version = sis.getProjectVersion();
        if (version == null)
            version = "Unknown";
        return version;
    }

    public String getSvnRevision() {
        String svnRevision = sis.getSvnRevision();
        if (svnRevision == null)
            svnRevision = "Unknown";
        return svnRevision;
    }

    public String getDatabase() {
        String database = sis.getDatabaseUrl();
        if (database == null)
            database = "No database";
        return database;
    }
}
