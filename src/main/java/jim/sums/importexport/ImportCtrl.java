
package jim.sums.importexport;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.common.db.Cohort;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "import")
@ViewScoped
public class ImportCtrl{
    
    @EJB
    private ImportEJB importejb ;
    private Cohort cohort ;

    public ImportCtrl() {
    }

    
    public Cohort getCohort() {
        return cohort;
    }

    public void setCohort(Cohort cohort) {
        this.cohort = cohort;
    }
    public void execute(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful",  event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
}
