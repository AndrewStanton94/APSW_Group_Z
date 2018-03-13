package jim.sums.common.facade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.db.Academicyear;
import static jim.sums.common.db.Academicyear.getAllAcademicYears;

/**
 *
 * @author Sam
 */
@Stateless
public class AcademicyearFacade extends AbstractFacade<Academicyear> {

    @PersistenceContext(unitName = "SUMS2011PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Academicyear entity) {
        genAcademicYearName(entity);
        super.create(entity);
    }

    @Override
    public Academicyear edit(Academicyear entity) {
        genAcademicYearName(entity);
        return super.edit(entity);
    }

    public void genAcademicYearName(Academicyear entity) {
        if (entity.getAcademicYearName() != null && !entity.getAcademicYearName().isEmpty()) {
            return;
        }
        String start, end;
        SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
        if (entity.getStartdate() != null) {
            start = formatNowYear.format(entity.getStartdate().getTime());
        } else {
            start = "";
        }
        if (entity.getEnddate() != null) {
            end = formatNowYear.format(entity.getEnddate().getTime());
        } else {
            end = "";
        }

        entity.setAcademicYearName(start + "-" + end);
    }

    public AcademicyearFacade() {
        super(Academicyear.class);
    }

    public void deleteForAcademicYear() {
        em.createQuery("DELETE FROM Academicyear").executeUpdate();
    }

    public void Initialise() {
        List<Academicyear> ayl = this.findAll();
        if (ayl.size() < 3) {
//                deleteForAcademicYear();
            for (Academicyear ay : Academicyear.getAllAcademicYears()) {
                em.persist(ay);
            }
        }
    }


}
