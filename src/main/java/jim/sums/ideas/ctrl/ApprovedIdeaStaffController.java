/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.bus.MailService;

import jim.sums.common.ctrl.AbstractController;
import jim.sums.common.ctrl.UserBean;
import jim.sums.ideas.bus.IdeaService;
import jim.sums.common.facade.ApprovedIdeaStaffFacade;
import jim.sums.common.db.ApprovedIdeaStaff;
import jim.sums.common.db.Projectidea;


/**
 *
 * @author Pasu
 */

@SessionScoped
@ManagedBean(name = "aisc")
public class ApprovedIdeaStaffController extends AbstractController<ApprovedIdeaStaff, ApprovedIdeaStaffFacade> {
    
    @EJB
    ApprovedIdeaStaffFacade aisf;
    @EJB
    IdeaService is;
    @EJB
    private MailService ms;
    private ApprovedIdeaStaff ais;
    private String commentStr;

    @PostConstruct
    public void init() {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");        
//        relationIdeaStaffList = aisf.findByStaff(ub.getLoggedInUser());
        searchApprovedIdeaStaff();
    }
    
    public String getCommentStr() {
        return commentStr;
    }

    public void setCommentStr(String commentStr) {
        this.commentStr = commentStr;
    }
    
    private List<ApprovedIdeaStaff> relationIdeaStaffList;

    public List<ApprovedIdeaStaff> getRelationIdeaStaffList() {
        return relationIdeaStaffList;
    }

    public void setRelationIdeaStaffList(List<ApprovedIdeaStaff> relationIdeaStaffList) {
        this.relationIdeaStaffList = relationIdeaStaffList;
    }
    
    private List<ApprovedIdeaStaff> relationIdeaStaffListfromIdea;

    public List<ApprovedIdeaStaff> getRelationIdeaStaffListfromIdea() {
        return relationIdeaStaffListfromIdea;
    }

    public void setRelationIdeaStaffListfromIdea(List<ApprovedIdeaStaff> relationIdeaStaffListfromIdea) {
        this.relationIdeaStaffListfromIdea = relationIdeaStaffListfromIdea;
    }

    public List<ApprovedIdeaStaff> searchRelationStaffListfromIdea(Projectidea idea) 
    {
        return aisf.findByIdea(idea);
    }
    public String updateRelationStatus(ApprovedIdeaStaff ais, String status) throws BusinessException {
        ais.setApprovedStatus(status);
        ais.setApprovedComment(commentStr);
        aisf.edit(ais);
        
        if("Approved".equals(ais.getApprovedStatus())) {
            ais.getApprovedIdeaList().setIdeastatus(is.getIdeastatusfacade().findSameName("Approved"));
        } else {
            ais.getApprovedIdeaList().setIdeastatus(is.getIdeastatusfacade().findSameName("Provisional"));
        }
        is.editProjectidea(ais.getApprovedIdeaList());
        
//        try {
//            ms.postMail(ais.getApprovedIdeaList().getOwneridea().getEmail(), "Your idea (" + ais.getApprovedIdeaList().getIdeatitle() +") has been update"
//                    , commentStr + "\n\n"
//                    + "This is comment feedback from " + ais.getApprovedStaffList().getFullname());
//        } catch (MessagingException ex) {
//            throw new BusinessException("Mail error\n" + ex.getMessage());
//        }
        
        return "toApprovedIdeas";
    }
    
    public ApprovedIdeaStaffController() {
        super(ApprovedIdeaStaff.class);
    }

    @Override
    public ApprovedIdeaStaffFacade getFacade() {
        return aisf;
    }
    
    private String searchTitle = "";
    private String searchTimePeriod = "9999";
    private String searchStatus = "All";

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getSearchTimePeriod() {
        return searchTimePeriod;
    }

    public void setSearchTimePeriod(String searchTimePeriod) {
        this.searchTimePeriod = searchTimePeriod;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }
    
    public void searchApprovedIdeaStaff() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UserBean ub = (UserBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "userBean");        
        setRelationIdeaStaffList(aisf.findBySearchCriteria(ub.getLoggedInUser(), Integer.parseInt(searchTimePeriod), searchStatus, searchTitle));
    }
}
