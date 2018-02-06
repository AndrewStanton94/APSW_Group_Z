/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.allocation.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import jim.sums.allocation.bus.AllocationService;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Projectidea;
import jim.sums.common.facade.ProjectideaFacade;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@SessionScoped
@ManagedBean(name="appc")
public class AllocationsPerProjectController extends AbstractController<Projectidea, ProjectideaFacade> {

	@EJB
	AllocationService as;

	public AllocationsPerProjectController() {
		super(Projectidea.class);
	}

	@Override
	public ProjectideaFacade getFacade() {
		return as.getProjectideaFacade();
	}
	
	@PostConstruct
	public void init(){
		//current = as.getProjectideaFacade().find(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pi"));
	}
	
	public List<AllocationService.RankSum> getAllocationsByProject(){
		try {
			return as.getAllocationsByProject(current.getIdprojectidea());
		} catch (BusinessException ex) {
			addError(ex.getMessage());
			//return new ArrayList<AllocationService.RankSum>();
			return null;
		}
	}
	
	public String confirmStudentAllocation(AllocationService.RankSum rs){
		as.addStudentToFinalProject(rs);
		return null;
	}
        
        public boolean isAllocated(AllocationService.RankSum rs) {
                return as.isAllocated(rs);
        }
}
	