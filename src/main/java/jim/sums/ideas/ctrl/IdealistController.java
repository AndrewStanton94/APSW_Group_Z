package jim.sums.ideas.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;

import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.common.db.CourseLevel;
import jim.sums.common.db.Ideachosenby;
import jim.sums.common.db.ProjectKind;
import jim.sums.common.db.Projectidea;
import jim.sums.common.facade.ProjectideaFacade;
import jim.sums.ideas.bus.IdeaService;

/**
 * @author Nicolas Dossou-Gbete
 */

@ViewScoped
@ManagedBean(name = "ilc")
public class IdealistController extends AbstractController<Projectidea, ProjectideaFacade> {

    @EJB
    IdeaService is;
    Projectidea[] selected;
    List<Projectidea> ideaList;
    public boolean selectMyIdeas;
    public String schideatitle = "";
    public String schideadesc = "";
    public String schideasub = "";
    public String schideakind = "";

    public IdealistController() {
        super(Projectidea.class);
    }

    public String getSchideadesc() {
        return schideadesc;
    }

    public void setSchideadesc(String schideadesc) {
        this.schideadesc = schideadesc;
    }

    public String getSchideakind() {
        return schideakind;
    }

    public void setSchideakind(String schideakind) {
        this.schideakind = schideakind;
    }

    public String getSchideasub() {
        return schideasub;
    }

    public void setSchideasub(String schideasub) {
        this.schideasub = schideasub;
    }

    public boolean isSelectMyIdeas() {
        return selectMyIdeas;
    }

    public void setSelectMyIdeas(boolean selectMyIdeas) {
        this.selectMyIdeas = selectMyIdeas;
    }

    public String deleteIdea(Projectidea delcurrentidea) {
        is.delete(delcurrentidea);
        return "allIdeaListRefresh";
    }

    @Override
    public ProjectideaFacade getFacade() {
        return is.getPiFacade();
    }

    public Projectidea[] getSelected() {
        return selected;
    }

    public String getSchideatitle() {
        return schideatitle;
    }

    public void setSchideatitle(String schideatitle) {
        this.schideatitle = schideatitle;
    }

    public List<Projectidea> getIdeaList() {
        if (ideaList == null) {
            resetIdeaList();
        }
        return ideaList;
    }

    public void resetIdeaList() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");


        if (ub.getLoggedInUser().isStaff() || ub.getLoggedInUser().isAdmin()) {
            ideaList = is.getPiFacade().findAll();
        } else {
            ideaList = is.getPiFacade().findByStatus(is.getIdeastatusfacade().findSameName("Approved"));
            if(! (is.getPiFacade().findByOwner(ub.getLoggedInUser()).isEmpty())) {
                ideaList.add(is.getPiFacade().findByOwner(ub.getLoggedInUser()).get(0));
            }
        }
    }

    public String getIdeaKind(Projectidea idea) {
        StringBuilder ret = new StringBuilder();
        for (ProjectKind k : idea.getKindList()) {
            if (ret.toString().isEmpty()) {
                ret.append(k.getKindname());
            } else {
                ret.append(", ");
                ret.append(k.getKindname());
            }
        }
        return ret.toString();
    }

    public String getIdeaGrade(Projectidea idea) {
        StringBuilder ret = new StringBuilder();
        for (CourseLevel g : idea.getGradeList()) {
            if (ret.toString().isEmpty()) {
                ret.append(g.getName());
            } else {
                ret.append(", ");
                ret.append(g.getName());
            }
        }
        return ret.toString();
    }

    public int getNumberOfStudents(Projectidea idea) {

        int numberOfStudents = 0;
        if (idea != null) {
            List<Ideachosenby> chosenList = idea.getIdeachosenbyList();

            for (Ideachosenby i : chosenList) {
                if (i.getPerson().isStudent()) {
                    numberOfStudents++;
                }
            }
        }
        return numberOfStudents;
    }

    public int getNumberOfSupervisors(Projectidea idea) {

        int numberOfSups = 0;
        if (idea != null) {
            List<Ideachosenby> chosenList = idea.getIdeachosenbyList();

            for (Ideachosenby i : chosenList) {
                if (i.getPerson().isStaff()) {
                    numberOfSups++;
                }
            }
        }
        return numberOfSups;
    }

    public String partDescription(Projectidea idea) {
        if (idea.getDescription().length() > 80) {
            return idea.getDescription().substring(0, 81) + "...";
        } else {
            return idea.getDescription();
        }
    }

    private void applySearchParameters() {
        ArrayList<Projectidea> tmp = new ArrayList<Projectidea>(ideaList);
        for (Projectidea idea : ideaList) {
            if (!idea.getIdeatitle().contains(schideatitle)
                    || !idea.getDescription().contains(schideadesc)
                    || !idea.getOwneridea().getUsername().contains(schideasub)
                    || !getIdeaKind(idea).contains(schideakind)) {
                tmp.remove(idea);
            }
        }
        ideaList = tmp;
    }

    private void applyCheckBoxRestrictions() {
        if (selectMyIdeas) {
            FacesContext fc = FacesContext.getCurrentInstance();
            UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");
            ideaList = new ArrayList<Projectidea>(getFacade().findByOwner(ub.getLoggedInUser()));
        } else {
            resetIdeaList();
        }
    }

    public void updateList() {
        ideaList = is.getPiFacade().searchIdeas(schideatitle.toUpperCase());
        //applyCheckBoxRestrictions();
        ///applySearchParameters();
    }

    public boolean allowedToDelete(Projectidea idea) {
//        If final project => not allowed
        return !is.isFinal(idea);
    }
}
