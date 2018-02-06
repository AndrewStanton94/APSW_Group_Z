/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.ctrl;

import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.UserService;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.facade.AbstractFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean
public class AllocationController extends AbstractController<Object, AbstractFacade> {

	@EJB
	AllocationService as;
	
        @EJB
        UserService us;
        String user = "";
        
	public AllocationController() {
		super(null);
	}
        
	@Override
	public AbstractFacade getFacade() {
		return null;
	}
	
        //*** Edit by Pasu Poonpakdee Issue: JIMUOPDEV-35 
        @PostConstruct
        public void init() {
//            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
//            if (principal != null) {
//                user = principal.getName(); // j_username.
//            }
//            
//            if(us.isStudent(user)) {
//                getList();
//            }
        }
         
	public DataModel<AllocationService.RankSum> getList(){
            //addInfo("Does not follow the implementation pattern.");
            
            //*** Edit by Pasu Poonpakdee Issue: JIMUOPDEV-35 
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                user = principal.getName(); // j_username.
            }
            
            if(us.getPerson(user).getRoles().contains("Student")) {
                try {
			return new ListDataModel<AllocationService.RankSum>(as.getAllocationListByStudent(user));
		} catch (BusinessException ex) {
			addError(ex.getMessage());
			return new ListDataModel<AllocationService.RankSum>();
		}
            } else {
		try {
			return new ListDataModel<AllocationService.RankSum>(as.getAllocationList());
		} catch (BusinessException ex) {
			addError(ex.getMessage());
			return new ListDataModel<AllocationService.RankSum>();
		}
            }
	}
	
	public String confirm(AllocationService.RankSum rs){
		as.createFinalProject(rs);
		return null;
	}	
}
	