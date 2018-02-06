/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.db.Person;
import jim.sums.common.facade.PersonFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.UnitInstance;
import jim.sums.management.bus.GroupsService;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@RequestScoped
@ManagedBean(name = "mc")
public class ManageController extends AbstractController<Person, PersonFacade> {

    @EJB
    GroupsService gs;
    List<UnitInstance> listUnit;
    List<Person> listStudent;
    UnitInstance unitSelected;

    public ManageController() {
        super(Person.class);
    }

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
        current = ub.getLoggedInUser();
    }

    @Override
    public PersonFacade getFacade() {
        return null;
    }

    public void setListUnit(List<UnitInstance> listUnit) {
        this.listUnit = listUnit;
    }

    public List<UnitInstance> getListUnit() {
        return listUnit;
    }

//    public void onRowSelect(SelectEvent event) {
//        listStudent = new ArrayList<Person>();
//        for (int j = 0; j < ((UnitInstance)event.getObject()).getUnitpersoninList().size(); j++) {
//            listStudent.add(((UnitInstance)event.getObject()).getUnitpersoninList().get(j).getChosen());
//        }
//    }

//    public void setListStudent(List<Unitpersonin> listStudent) {
////        for(int i=0;i<listAssociation.size();i++)
////        this.listStudent = listStudent;
//    }

    public List<Person> getListStudent() {
        if(unitSelected!=null){
            listStudent = new ArrayList<Person>();
            for(int i=0;i<unitSelected.getUnitpersoninList().size();i++){
                listStudent.add(unitSelected.getUnitpersoninList().get(i).getChosen());
            }
            return listStudent;
        }
            
        return null;
    }

    public UnitInstance getUnitSelected() {
        return unitSelected;
    }

    public void setUnitSelected(UnitInstance unitSelected) {
        this.unitSelected = unitSelected;
    }
}
