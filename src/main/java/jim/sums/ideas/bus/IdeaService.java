/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.ideas.bus;

import jim.sums.common.db.CourseLevel;
import jim.sums.common.db.ProjectKind;
import jim.sums.common.facade.CourseLevelFacade;
import jim.sums.common.facade.ProjectKindFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.db.Projectidea;
import jim.sums.common.db.Projectideahistory;
import jim.sums.common.facade.ApprovedIdeaStaffFacade;
import jim.sums.common.facade.FinalprojectFacade;
import jim.sums.common.facade.IdeastatusFacade;
import jim.sums.common.facade.ProjectideaFacade;
import jim.sums.common.facade.ProjectideahistoryFacade;

/**
 *
 * @author KardousS
 */
@Stateless
public class IdeaService extends AbstractService {

    @EJB
    private ProjectideaFacade piFacade;
    @EJB
    private ProjectKindFacade kindfacade;
    @EJB
    private IdeastatusFacade ideastatusfacade;
    @EJB
    private CourseLevelFacade courseLevelFacade;
    @EJB
    private ProjectideahistoryFacade pihFacade;
    @EJB
    private ApprovedIdeaStaffFacade aisf;
    @EJB
    private FinalprojectFacade fpf;

    public IdeastatusFacade getIdeastatusfacade() {
        return ideastatusfacade;
    }

    public CourseLevelFacade getCourseLevelFacade() {
        return courseLevelFacade;
    }

    public IdeaService() {
    }

    public ProjectideaFacade getPiFacade() {
        return piFacade;
    }

//	public EmailFacade getEmailFacade() {
//		return ;
//	}
    public Projectidea registerNewProjectidea(Projectidea pi) {

        List<Projectidea> sameNames = piFacade.findSameName(pi);
        if (sameNames.isEmpty()) {
            Date date = new Date();
            pi.setSubmissiondate(date);
            piFacade.create(pi);
        } else {
            //message
            //throw new BusinessException("Champion with name " + c.getName() + " already exists");
        }
        return pi;
    }

    public void editProjectidea(Projectidea pi) {
        piFacade.edit(pi);
    }

    public void addProjectideahistory(Projectideahistory pih) {
        pihFacade.create(pih);
    }

    public ProjectKindFacade getKindFacade() {
        return kindfacade;
    }

    public IdeastatusFacade getIdeastatusFacade() {
        return ideastatusfacade;
    }

    public void delete(Projectidea current) {
        if (!(aisf.findByIdea(current).isEmpty())) {
            aisf.remove(aisf.findByIdea(current).get(0));
        }
        piFacade.remove(current);
    }

    public List<CourseLevel> getAllCourseLevels() {
        return courseLevelFacade.findAll();
    }
    
    public CourseLevel editGrade(CourseLevel grade) {
        return courseLevelFacade.edit(grade);
    }

    public ProjectKind editKind(ProjectKind kind) {
        return kindfacade.edit(kind);
    }

    public ProjectideahistoryFacade getProjectideahistoryFacade() {
        return pihFacade;
    }
    
    public boolean isFinal(Projectidea idea) {
    //        If this idea is a final project, we can not delete the idea
        if (fpf.findByTitle(idea.getIdeatitle()).isEmpty()) {
            return false;
        }
        return true;  
    }
}
