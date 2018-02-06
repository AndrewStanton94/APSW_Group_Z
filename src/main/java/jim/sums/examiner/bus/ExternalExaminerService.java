package jim.sums.examiner.bus;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jim.sums.common.bus.AbstractService;
import jim.sums.common.db.Finalproject;
import jim.sums.common.facade.FinalprojectFacade;

/**
 * Service for External Examiner related methods
 *
 * @author Mike Wareham
 */
@Stateless
@LocalBean
public class ExternalExaminerService extends AbstractService {

    @EJB
    private FinalprojectFacade finalProject;

    /**
     * Returns the finalProjectFacade
     *
     * @return FinalprojectFacade
     */
    public FinalprojectFacade getFinalProject() {
        return finalProject;
    }

    /**
     * Sets the current finalProject
     *
     * @param finalProject
     */
    public void setFinalProject(FinalprojectFacade finalProject) {
        this.finalProject = finalProject;
    }

    /**
     * Determines if project has been reviewed by an external examiner
     *
     * @param project
     * @return Boolean
     */
    public Boolean hasBeenReviewed(Finalproject project) {
        if (project.getExternalDate() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns whether external examiner is satisfied or dissatisfied with
     * project
     *
     * @param project
     * @return String
     */
    public String externalSatisfied(Finalproject project) {
        if (project.getExternalSatisfied() == true) {
            return "Satisfied";
        } else {
            return "Dissatisfied";
        }
    }

    /**
     * Saves result of external examiners examination
     *
     * @param project
     * @param satisfiedFields
     * @param satisfiedComments
     * @return Finalproject
     */
    public Finalproject saveReview(Finalproject project, String satisfiedFields, String satisfiedComments) {
        Boolean satisfiedValue;
        Date today = new Date();

        if (satisfiedFields.equalsIgnoreCase("Satisfied")) {
            satisfiedValue = true;
        } else {
            satisfiedValue = false;
        }

        project.setExternalComments(satisfiedComments);
        project.setExternalSatisfied(satisfiedValue);
        project.setExternalDate(today.toString());
        project = finalProject.edit(project);
        //check that the operation is OK
        //set up relationships between submission and other objects and vice versa
        return project;
    }
}
