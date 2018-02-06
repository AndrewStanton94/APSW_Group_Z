/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Ideastatus;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 *
 * @author KardousS
 */
@RequestScoped
@ManagedBean(name = "istc")
public class IdeastatusController extends AbstractController<Ideastatus, IdeastatusFacade> {

    @EJB
    IdeaService is;

     @Override
    public IdeastatusFacade getFacade() {
        return is.getIdeastatusFacade();
    }

    public IdeastatusController() {
        super(Ideastatus.class);    }

    
    

    public List<Ideastatus> getIdeaStatusList(){

              List<Ideastatus> tmp=getFacade().findAll();
              
              return tmp;
    }


}
