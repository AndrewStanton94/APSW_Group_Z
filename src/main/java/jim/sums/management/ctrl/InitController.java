/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import jim.sums.management.bus.InitService;

/**
 *
 * @author briggsj
 */
@ManagedBean(name = "InitController")
public class InitController {

    @EJB
    private InitService initService;
    
    public String initdb() {
        initService.initdb();
        return "";
    }
    
}
