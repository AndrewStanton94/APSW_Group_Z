/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.management.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jim.sums.common.db.*;
import jim.sums.common.facade.*;

/**
 *
 * @author BriggsJ
 */
@Stateless
@LocalBean
public class InitService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    TemplateMarkFormFacade mf;
    @EJB
    CategoryOptionsFacade co;
    @EJB
    CategoryOptionGroupsFacade cog;
    @EJB
    TemplateCategoryFacade mfc;
    @EJB
    MarkCategoryFacade mc;
    @EJB
    UnitMarkFormsFacade umf;
    @EJB
    TemplateCriteriaFacade coc;
    @EJB
    MarkerTypeFacade mt;
    @EJB
    ProjectKindFacade pk;
    @EJB
    CourseLevelFacade cl;
//    @EJB
//    RoleNameFacade ps;
    @EJB
    RoleNameFacade rnf;
    @EJB
    PersonFacade pf;
    @EJB
    AppPropertyFacade apf;
    @EJB
    StaffstatusFacade ssf;
    @EJB
    OrganisationFacade orgf;
    @EJB
    IdeastatusFacade isf;
    @EJB
    AcademicyearFacade ayf;
    @EJB
    CohortFacade cohortf;
    @EJB
    UnitFacade uf;
    @EJB
    UnitinstanceFacade uif;
    @EJB
    CourseFacade cf;
    @EJB
    CourseinstanceFacade cif;
    @EJB
    ProjectideaFacade pi;
    @EJB
    FinalprojectFacade fp;
    @EJB
    UnitpersoninFacade upif;
    @EJB
    PersonstaffstatusFacade pssf;
    @EJB
    SubmitConfigurationFacade scf;
    @EJB
    SubmissionFacade sf;
    @EJB
    MarkerMarkFacade marksf;
    @EJB
    CategoryMarkFacade cmf;
    @EJB
    CategoryMarksOptionFacade cmof;
    @EJB
    StaffprojectrelationshipFacade sprf;

    public String initdb() {
        cohortf.deleteForCohort();
        pf.deleteForPerson();
        mf.deleteForMark();
        uf.deleteForUnit();
        cf.deleteForCourse();
        marksf.deleteForMarks();

        pk.Initialise();
        cl.Initialise();
        rnf.Initialise();
        apf.Initialise();
        isf.Initialise();

        makeTestData();
        return "toLogin";
    }

    public String makeTestData() {
        Random random = new Random();

        RoleName adminRole = rnf.admin();
        RoleName studentRole = rnf.student();
        RoleName staffRole = rnf.staff();
        RoleName externalRole = rnf.external();

        List<String> listOfStudent = new ArrayList<String>();
        List<String> listOfStaff = new ArrayList<String>();
        Person adminPerson;
        Person studentPerson;
        Person staffPerson = null;
        Person studentPerson2;
        Person staffPerson2 = null;
        Person studentPerson3;
        Person staffPerson3 = null;
        Person externPerson;
        Person studentPerson4;
        Person studentPerson5;
        Person studentPerson6;
        Person studentPerson7;
        Person studentPerson8;
        Person studentPerson9;
        Person studentPerson0;

        if (pf.count() < 20) {
            adminPerson = new Person("admin", "Admin", "User", "admin", new Date(), "jim.briggs@port.ac.uk", null);
            studentPerson = new Person("student", "Ladalle", "Pascal", "student", new Date(), "haha@port.ac.uk", null);
            staffPerson = new Person("staff", "Staff", "Tester", "staff", new Date(), "jim.briggs@port.ac.uk", null);
            studentPerson2 = new Person("student2", "Bosphore", "Tohmaa", "student2", new Date(), "ddbosf@port.ac.uk", null);
            staffPerson2 = new Person("staff2", "Nedalkolo", "Bulg", "staff2", new Date(), "nedned@port.ac.uk", null);
            studentPerson3 = new Person("student3", "Haha", "Hoho", "student3", new Date(), "hahhohh@port.ac.uk", null);
            staffPerson3 = new Person("staff3", "Bob", "Bob", "staff3", new Date(), "bob.bob@port.ac.uk", null);
            externPerson = new Person("extern", "Peace", "Love", "extern", new Date(), "hipster@port.ac.uk", null);
            studentPerson4 = new Person("student4", "Student", "Four", "student4", new Date(), "student4@port.ac.uk", null);
            studentPerson5 = new Person("student5", "Student", "Five", "student5", new Date(), "student5@port.ac.uk", null);
            studentPerson6 = new Person("student6", "Student", "Six", "student6", new Date(), "student6@port.ac.uk", null);
            studentPerson7 = new Person("student7", "Student", "Seven", "student7", new Date(), "student7@port.ac.uk", null);
            studentPerson8 = new Person("student8", "Student", "Eight", "student8", new Date(), "student8@port.ac.uk", null);
            studentPerson9 = new Person("student9", "Student", "Nine", "student9", new Date(), "student9@port.ac.uk", null);
            studentPerson0 = new Person("student0", "Student", "Zero", "student0", new Date(), "student0@port.ac.uk", null);

            listOfStudent.add(studentPerson.getUsername());
            listOfStudent.add(studentPerson2.getUsername());
            listOfStudent.add(studentPerson3.getUsername());
            listOfStudent.add(studentPerson4.getUsername());
            listOfStudent.add(studentPerson5.getUsername());
            listOfStudent.add(studentPerson6.getUsername());
            listOfStudent.add(studentPerson7.getUsername());
            listOfStudent.add(studentPerson8.getUsername());
            listOfStudent.add(studentPerson9.getUsername());
            listOfStudent.add(studentPerson0.getUsername());

            listOfStaff.add(staffPerson.getUsername());
            listOfStaff.add(staffPerson2.getUsername());
            listOfStaff.add(staffPerson3.getUsername());

            adminPerson.addRole(adminRole);
            studentPerson.addRole(studentRole);
            studentPerson2.addRole(studentRole);
            studentPerson3.addRole(studentRole);
            staffPerson.addRole(staffRole);
            staffPerson2.addRole(staffRole);
            staffPerson3.addRole(staffRole);
            externPerson.addRole(externalRole);
            studentPerson4.addRole(studentRole);
            studentPerson5.addRole(studentRole);
            studentPerson6.addRole(studentRole);
            studentPerson7.addRole(studentRole);
            studentPerson8.addRole(studentRole);
            studentPerson9.addRole(studentRole);
            studentPerson0.addRole(studentRole);

            studentPerson.setHemis("000001");
            studentPerson2.setHemis("000002");
            studentPerson3.setHemis("000003");
            studentPerson4.setHemis("000004");
            studentPerson5.setHemis("000005");
            studentPerson6.setHemis("000006");
            studentPerson7.setHemis("000007");
            studentPerson8.setHemis("000008");
            studentPerson9.setHemis("000009");
            studentPerson0.setHemis("000010");

            adminPerson.setActivationdate(new Date());
            studentPerson.setActivationdate(new Date());
            studentPerson2.setActivationdate(new Date());
            studentPerson3.setActivationdate(new Date());
            studentPerson4.setActivationdate(new Date());
            studentPerson5.setActivationdate(new Date());
            studentPerson6.setActivationdate(new Date());
            studentPerson7.setActivationdate(new Date());
            studentPerson8.setActivationdate(new Date());
            studentPerson9.setActivationdate(new Date());
            studentPerson0.setActivationdate(new Date());
            staffPerson.setActivationdate(new Date());
            staffPerson2.setActivationdate(new Date());
            staffPerson3.setActivationdate(new Date());
            externPerson.setActivationdate(new Date());

            staffPerson.setConfirmationdate(new Date());
            staffPerson2.setConfirmationdate(new Date());
            staffPerson2.setConfirmationdate(new Date());

            pf.deleteForPerson();

            pf.create(adminPerson);
            pf.create(studentPerson);
            pf.create(staffPerson);
            pf.create(studentPerson2);
            pf.create(staffPerson2);
            pf.create(studentPerson3);
            pf.create(staffPerson3);
            pf.create(externPerson);
            pf.create(studentPerson4);
            pf.create(studentPerson5);
            pf.create(studentPerson6);
            pf.create(studentPerson7);
            pf.create(studentPerson8);
            pf.create(studentPerson9);
            pf.create(studentPerson0);
        }
        if (orgf.count() == 0) {
            Organisation uop = new Organisation("University of Portsmouth", "jim.briggs@port.ac.uk", "PO1 2UP", "Education");
            orgf.create(uop);
        }

        int i;

        Date startingDate = new GregorianCalendar(2012, 8, 1).getTime();
        Date endingDate = new GregorianCalendar(2013, 7, 31).getTime();

        List<Date> startingDateList = new ArrayList<Date>();
        startingDateList.add(new GregorianCalendar(2012, 8, 1).getTime());
        startingDateList.add(new GregorianCalendar(2013, 8, 1).getTime());
        List<Date> endingDateList = new ArrayList<Date>();
        endingDateList.add(new GregorianCalendar(2013, 7, 31).getTime());
        endingDateList.add(new GregorianCalendar(2014, 7, 31).getTime());
        List<String> academicYearNameList = new ArrayList<String>();
        academicYearNameList.add("2012-2013");
        academicYearNameList.add("2013-2014");

        if (ayf.count() != 2) {
            ayf.deleteForAcademicYear();
            Academicyear academicYear;
            for (i = 0; i < 2; i++) {
                academicYear = new Academicyear(startingDateList.get(i), endingDateList.get(i));
                academicYear.setAcademicYearName(academicYearNameList.get(i));
                ayf.create(academicYear);
            }
        }

        int x, k;
        Long j;
        int tab[] = {0, 4};
        Unit u;
        List<String> unitShortCodeList = new ArrayList<String>();
        unitShortCodeList.add("PJS60P");
        unitShortCodeList.add("PJE60P");
        unitShortCodeList.add("PJS40");
        unitShortCodeList.add("PJE40");
        List<String> unitCodeList = new ArrayList<String>();
        unitCodeList.add("U22245");
        unitCodeList.add("U22244");
        unitCodeList.add("U22508");
        unitCodeList.add("U21287");
        List<Unit> lu = new ArrayList<Unit>();

        if (uf.count() < 4) {
            uf.deleteForUnit();
            for (i = 0; i < 4; i++) {
                u = new Unit(unitShortCodeList.get(i), unitCodeList.get(i));
                u.setUnitkind(pk.findAll().get(i));
                u.setCourseLevel(cl.find((long) random.nextInt(cl.findAll().size() - 1) + 1));
                uf.create(u);
                lu.add(u);
            }
        } else {
            lu = uf.findRange(tab);
        }

        UnitInstance ui;
        List<UnitInstance> lui = new ArrayList<UnitInstance>();
        if (uif.count() < 8) {
            for (i = 0; i < ayf.count(); i++) {
                for (k = 0; k < 4; k++) {
                    ui = new UnitInstance(lu.get(k), ayf.findAll().get(i));
                    uif.create(ui);
                    lui.add(ui);
                }
            }
        } else {
            lui = uif.findRange(tab);
        }

        // Initialization of Cohort                 by BernardiD
        List<UnitInstance> unitWithPFirstYear = new ArrayList<UnitInstance>();
        unitWithPFirstYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJS60P")).get(0));
        unitWithPFirstYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJE60P")).get(0));
        List<UnitInstance> unitWithPSecondYear = new ArrayList<UnitInstance>();
        unitWithPSecondYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJS60P")).get(1));
        unitWithPSecondYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJE60P")).get(1));
        List<UnitInstance> unitWithoutPFirstYear = new ArrayList<UnitInstance>();
        unitWithoutPFirstYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJS40")).get(0));
        unitWithoutPFirstYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJE40")).get(0));
        List<UnitInstance> unitWithoutPSecondYear = new ArrayList<UnitInstance>();
        unitWithoutPSecondYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJS40")).get(1));
        unitWithoutPSecondYear.add(uif.findUnitInstancesByUnit(uf.findByUnitCode("PJE40")).get(1));

        Cohort cohort;
        if (cohortf.count() < 4) {
            for (i = 0; i < 4; i++) {
                cohort = new Cohort("Test cohort " + i, 5, 6);
                cohort.setProjectstart(startingDate);
                cohort.setProjectstop(endingDate);
                cohort.setRegisterstart(startingDate);
                cohort.setRegisterstop(endingDate);
                cohort.setPersonList(pf.findByRole(studentRole));
                cohort.setPersonList1(pf.findByRole(staffRole));
                switch (i) {
                    case 0:
                        cohort.setUnits(unitWithPFirstYear);
                        cohortf.create(cohort);
                        break;
                    case 1:
                        cohort.setUnits(unitWithoutPFirstYear);
                        cohortf.create(cohort);
                        break;
                    case 2:
                        cohort.setUnits(unitWithPSecondYear);
                        cohortf.create(cohort);
                        break;
                    default:
                        cohort.setUnits(unitWithoutPSecondYear);
                        cohortf.create(cohort);
                        break;

                }
            }
        }

        // Initialization of Course
        Course course;
        List<Course> lc = new ArrayList<Course>();
        if (cf.count() < 3) {
            cf.deleteForCourse();
            course = new Course("C0056S", "BSC (HONS) COMPUTER SCIENCE");
            cf.create(course);
            lc.add(course);
            course = new Course("C0058S", "BSC (HONS) BUSINESS INFORMATION SYSTEMS");
            cf.create(course);
            lc.add(course);
            course = new Course("C1707F", "MSC SOFTWARE ENGINEERING");
            cf.create(course);
            lc.add(course);
            course = new Course("C1841F", "MSC FORENSIC INFORMATION TECHNOLOGY");
            cf.create(course);
            lc.add(course);
        } else {
            lc = cf.findAll();
        }

        // Initialization of Courseinstance         by BerardiD
        Courseinstance ci;
        x = 0;
        List<Academicyear> ayl = ayf.findAll();
        if (cif.count() < 8) {
            for (Academicyear ay : ayl) {
                for (j = 0L; j < 4L; j++) {
                    List<UnitInstance> tlui = new ArrayList<UnitInstance>();
                    for (UnitInstance tui : lui) {
                        if (tui.getUnit().getCourseLevel() == cl.find(j)) {
                            tlui.add(tui);
                        }
                    }
                    Course c = lc.get(x % lc.size());
                    ci = new Courseinstance(x, c, ay);
                    ci.setUnitinstances(tlui);
                    ci.setCourseinstancename(c.getCoursename() + " " + ay.getName()); //.courseInstance"+(j+1));
                    cif.create(ci);
                    x++;
                }
            }
        }

        // Initialization of TemplateMarkForm
        List<TemplateMarkForm> lm = new ArrayList<TemplateMarkForm>();
        if (mf.count() < 3) {
            mf.deleteForMark();

            TemplateMarkForm mf1 = new TemplateMarkForm(1L, "Masters Engineering Project rev2006");
            TemplateMarkForm mf2 = new TemplateMarkForm(2L, "SBIT Study Project");
            TemplateMarkForm mf3 = new TemplateMarkForm(3L, "PJE30 final year engineering project");
            lm.add(mf3);
            lm.add(mf2);
            lm.add(mf1);
            mf.create(mf1);
            mf.create(mf2);
            mf.create(mf3);
        } else {
            lm = mf.findAll();
        }

        // Initializaion of UnitMarkForms
        if (umf.count() < 5) {

            for (i = 1; i < 6; i++) {

                UnitMarkForms umf1 = new UnitMarkForms(i);
                umf1.setUnitId(lu.get(i % (lu.size())));
                umf1.setFormId(lm.get(i % lm.size()));
                umf.create(umf1);
            }
        }

        //Initialization of CategoryOptionGroup
        List<CategoryOptionGroups> lcog = new ArrayList<CategoryOptionGroups>();
        if (cog.count() < 2) {
            CategoryOptionGroups cog1 = new CategoryOptionGroups(1, "100% - 10 Bracket");
            lcog.add(cog1);
            cog.create(cog1);
            CategoryOptionGroups cog2 = new CategoryOptionGroups(2, "Yes/No");
            lcog.add(cog2);
            cog.create(cog2);
        } else {
            lcog = cog.findAll();
        }

        // Initialization of MarkCategory
        List<MarkCategory> lmc = new ArrayList<MarkCategory>();
        if (mc.count() < 4) {
            MarkCategory mc1 = new MarkCategory(1L);
            mc1.setCatdescription("How challenging is the problem demonstrated to be?");
            mc1.setCatlongname("Statement of project''s context, aims and objectives");
            mc1.setCatname("Context");
            mc1.setCogId(lcog.get(0));
            mc1.setOptionType('C');
            lmc.add(mc1);
            mc.create(mc1);

            MarkCategory mc2 = new MarkCategory(2L);
            mc2.setCatdescription("Does the review incorporate all the relevant literature? Is irrelevant material left out?");
            mc2.setCatlongname("Critical review of relevant literature");
            mc2.setCatname("Lit Review");
            mc2.setCogId(lcog.get(0));
            mc2.setOptionType('C');
            lmc.add(mc2);
            mc.create(mc2);

            MarkCategory mc3 = new MarkCategory(3L);
            mc3.setCatdescription("How well does the report describe and justify appropriate for deployment?");
            mc3.setCatlongname("Methodological approach");
            mc3.setCatname("Methodology");
            mc3.setCogId(lcog.get(0));
            mc3.setOptionType('C');
            lmc.add(mc3);
            mc.create(mc3);

            MarkCategory mc4 = new MarkCategory(4L);
            mc4.setCatdescription("Has the client approved them (implicitly or explicitly)?");
            mc4.setCatlongname("Specification and discussion of the requirements");
            mc4.setCatname("Requirements");
            mc4.setCogId(lcog.get(0));
            mc4.setOptionType('C');
            lmc.add(mc4);
            mc.create(mc4);
        } else {
            lmc = mc.findAll();
        }

        // Initialization of CategoryOptions
        List<CategoryOptions> lco = new ArrayList<CategoryOptions>();
        if (co.count() < 12) {
            for (i = 0; i < 12; i++) {
                int begin = i * 10, end = i * 10 + 9, selectedvalue = i * 10 + 5;
                String label = begin + "-" + end;
                if (i == 9) {
                    label = "90-100";
                }
                if (i == 10) {
                    label = "Yes";
                    selectedvalue = 50;
                }
                if (i == 11) {
                    label = "No";
                    selectedvalue = 0;
                }
                CategoryOptions co1 = new CategoryOptions(i + 1);
                if (i < 10) {
                    co1.setOptGroup(lcog.get(0));
                } else {
                    co1.setOptGroup(lcog.get(1));
                }
                co1.setOptIndex((i + 1) % 11);
                co1.setOptLabel(label);
                co1.setOptSelectedValue(selectedvalue);

                lco.add(i, co1);
                co.create(co1);
            }
        } else {
            lco = co.findAll2();
        }

        // Initialization of TemplateCriteria
        List<String> lgood = new ArrayList<String>(), lbad = new ArrayList<String>();
        lgood.add("Description of testing and debugging carried out");
        lgood.add("Some justification of approaches used for testing and debugging");
        lgood.add("Good justification of approaches used in most areas");
        lgood.add("Shows that tests were well planned and fully carried out. Critical discussion of results and remedial actions taken");
        lbad.add("Little/no evidence of evaluation having been planned or carried out");
        lbad.add("Little attempt at evaluation");
        lbad.add("No planning evident");

        if (coc.count() < 40) {
            TemplateCriteria coc1;
            int m = 1;
            for (i = 0; i < 4; i++) {
                for (k = 0; k < 10; k++) {
                    coc1 = new TemplateCriteria(Long.valueOf(m));
                    coc1.setMarkCategory(lmc.get(i));
//                    coc1.setCatOption(lco.get(k));

                    if (k < 4) {
                        coc1.setNegativeCriteria(lbad.get(k % lbad.size()));
                    } else {
                        coc1.setPositiveCriteria(lgood.get(k % lgood.size()));
                    }

                    coc.create(coc1);
                    m++;
                }
            }
        }

        // Initialization of TemplateCategory
        if (mfc.count() < 12) {
            for (i = 0; i < 3; i++) {
                for (k = 0; k < 4; k++) {
                    TemplateCategory mfc1 = new TemplateCategory();//Long.valueOf(i * 10 + k)
                    mfc1.setOptional('N');
                    mfc1.setCatindex(k);
//                    TemplateWeightCategory twc = new TemplateWeightCategory(Long.valueOf((i * k) % 3) + 1);
//                    twc.setWeightValue(((i * k) % 3) + 1);
//                    mfc1.setTemplateWeightCategory(twc);
                    System.out.println("" + i + "" + k);
                    mfc1.setFormid(lm.get(i));
                    mfc1.setCatid(lmc.get(k));
                    if ((k + i) % 4 == 0) {
                        mfc1.setCritical('Y');
                    }

                    mfc.create(mfc1);
                }
            }
        }

        // Initialization of MarkerType
        if (mt.count() < 4) {
            MarkerType mt1 = new MarkerType(1, "Supervisor");
            MarkerType mt2 = new MarkerType(2, "Moderator");
            MarkerType mt3 = new MarkerType(3, "Third");
            MarkerType mt4 = new MarkerType(4, "Examiner");
            mt.create(mt1);
            mt.create(mt2);
            mt.create(mt3);
            mt.create(mt4);
        }

        // Initialization of ProjectIdea
        List<Projectidea> lpi = new ArrayList<Projectidea>();
        List<String> title = new ArrayList<String>();
        List<Person> per = pf.findByRole(staffRole);
        title.add("haha");
        title.add("hoho");
        title.add("qsd");
        title.add("pers");

        List<String> academicQuestionList = new ArrayList();
        academicQuestionList.add("How can we test a program with efficiency ?");
        academicQuestionList.add("What are the best ways to secure a web page ?");
        academicQuestionList.add("Chaos theory with computer science");
        academicQuestionList.add("Learning computer science");
        if (pi.count() < 4) {
            Projectidea pi1;

            for (i = 0; i < 4; i++) {
                pi1 = new Projectidea(i, title.get(i % title.size()), lbad.get(i % lbad.size()), lgood.get(i % lgood.size()), new Date());

                j = 0L;
                pi1.setIdeastatus(isf.findAll().get(1));
                pi1.setGradeList(cl.findAll());
                pi1.setKindList(pk.findAll());
                pi1.setOwneridea(per.get(i % pf.findByRole(staffRole).size()));
                pi1.setAcademicquestion(academicQuestionList.get(i % academicQuestionList.size()));
                lpi.add(pi1);
                pi.create(pi1);
                j++;
            }
        } else {
            lpi = pi.findAll();
        }

        // Initialization of FinalProject
        List<Finalproject> finalProjectList = new ArrayList<Finalproject>();
        List<Person> personsInFinalProject = new ArrayList<Person>();

        if (fp.count() < 3) {
            Finalproject fp1;
            for (i = 0; i < 3; i++) {
                // line causing the exception: personsInFinalProject.add(pf.findByRole(studentRole).get(i));
                fp1 = new Finalproject(Long.valueOf(i), title.get(i), new Date());

                fp1.setProjectidea(lpi.get(i));
                fp1.setUnitinstance(lui.get(i));
                fp1.setStudent(personsInFinalProject);
                System.out.println("loop #" + i + ": PersonsInFinalProject ----- " + personsInFinalProject);

                finalProjectList.add(fp1);

                fp.create(fp1);
            }
        }

        // Initialization of Unitpersonin           by BerardiD
        List<String> listOfStudent2 = new ArrayList<String>();

        if (upif.count() < 25) {
            Unitpersonin upi;
            int numberOfStudentAvailable;
            int y, z;
            while (!listOfStudent.isEmpty()) {
                for (i = 0; i < 8; i++) {
                    // Because, currently, there is 8 Units in the database
                    y = random.nextInt(4);
                    // We only take 0 to 3 students, to avoid the case where every students are in the same unit
                    for (k = 0; k < y; k++) {
                        numberOfStudentAvailable = listOfStudent.size();
                        if (numberOfStudentAvailable > 0) {
                            z = random.nextInt(numberOfStudentAvailable);
                            // We take a student randomly in the list
                            upi = new Unitpersonin(listOfStudent.get(z), lui.get(i % lui.size()).getIdunitinstance());

                            listOfStudent2.add(listOfStudent.get(z));
                            listOfStudent.remove(z);
                            // We remove the student from the list, because a student can be in only one unit

                            upi.setValidationdate(startingDate);

                            upif.create(upi);
                        }
                    }
                }
            }
        }

        // Initialization of Staffstatus            by BerardiD
        List<String> staffStatusList = new ArrayList<String>();
        staffStatusList.add("coordinator");
        staffStatusList.add("supervisor");
        staffStatusList.add("moderator");
        staffStatusList.add("third marker");

        if (ssf.count() < 4) {
            ssf.deleteForStaffstatus();
            Staffstatus ss;
            for (i = 0; i < 4; i++) {
                ss = new Staffstatus(staffStatusList.get(i));
                ssf.create(ss);
            }
        }

        // Initialization of Personstaffstatus      by BerardiD
        if (pssf.count() < 2) {
            Personstaffstatus pss;
            boolean active = true;
            for (i = 0; i < listOfStaff.size(); i++) {
                pss = new Personstaffstatus(listOfStaff.get(i), ssf.findByName("coordinator").getId());

                if (active == true) {
                    pss.setActive(1);
                    active = false;
                } else {
                    pss.setActive(0);
                    active = true;
                }

                pssf.create(pss);
            }
        }

        // Initialization of SubmitConfiguration    by BerardiD
        if (scf.count() < 3) {
            SubmitConfiguration sc;
            for (i = 0; i < 3; i++) {
                sc = new SubmitConfiguration();

                sc.setFileSize((i + 1) * 1000 + (i + 54) * 100);
                sc.setFileType("rar");
                sc.setFinalDeadline(endingDate);
                sc.setNormalDeadline(endingDate);
                sc.setOpeningDate(startingDate);
                sc.setDescriptionSubmitConfig("Something which describes the configuration.");
                sc.setUniti(lui.get(i));

                scf.create(sc);
            }
        }

//        // Initialization of Submission             by BerardiD
        if (sf.count() < 3) {
            Submission submit;
            for (i = 0; i < 3; i++) {
                submit = new Submission();

                submit.setCounter(i + 1);
                submit.setSubmissionTime(startingDate);
                submit.setConfig(scf.findAll().get(i % scf.findAll().size()));
                submit.setProject(fp.findAll().get(i % fp.findAll().size()));
                submit.setPerson(pf.findByRole(studentRole).get(i % listOfStudent2.size()));

                sf.create(submit);
            }
        }

        // Initialization of CategoryMark          by BerardiD
        List<CategoryMark> CategoryMarksList = new ArrayList<CategoryMark>();
        if (cmf.count() < 2) {
            CategoryMark cm1 = new CategoryMark(50, "No comment");
            CategoryMarksList.add(cm1);
            cmf.create(cm1);
            CategoryMark cm2 = new CategoryMark(75, "Great!");
            CategoryMarksList.add(cm2);
            cmf.create(cm2);
        }

        // Initialization of CategoryMarksOption    by BerardiD
        List<CategoryMarksOption> CategoryMarksOptionList = new ArrayList<CategoryMarksOption>();
        if (cmof.count() < 2) {
            CategoryMarksOption cmo1 = new CategoryMarksOption("Y");
            CategoryMarksOptionList.add(cmo1);
            cmof.create(cmo1);
            CategoryMarksOption cmo2 = new CategoryMarksOption("N");
            CategoryMarksOptionList.add(cmo2);
            cmof.create(cmo2);
        }

        // Initialization of MarkerMark                  by BerardiD
        List<String> comments = new ArrayList<String>();
        comments.add("Not bad");
        comments.add("Seems great");
        comments.add("No comments");

        if (marksf.count() < 3) {
            MarkerMark marks;
            for (i = 0; i < 3; i++) {
                marks = new MarkerMark(((i + 3) * 10) + 5, new Date(), comments.get(i % comments.size()),
                        comments.get((i + 1) % comments.size()), comments.get((i + 2) % comments.size()),
                        "", "", "No", i, "N", "Done");
                marks.setCategoryMarksList(CategoryMarksList);
                marks.setCategoryMarksOptionList(CategoryMarksOptionList);
                marks.setProjectId(finalProjectList.get(i % finalProjectList.size()));
                marks.setMarkerName(listOfStaff.get(i % listOfStaff.size()));
                marksf.create(marks);
            }
        }

        // Initialisation of Staffprojectrelationship by Corentin S.        
        Staffprojectrelationship spr = new Staffprojectrelationship(1L, ssf.findByName("supervisor"), staffPerson, finalProjectList.get(0));
        sprf.create(spr);
        spr = new Staffprojectrelationship(2L, ssf.findByName("supervisor"), staffPerson3, finalProjectList.get(1));
        sprf.create(spr);
        spr = new Staffprojectrelationship(3L, ssf.findByName("supervisor"), staffPerson2, finalProjectList.get(2));
        sprf.create(spr);
        spr = new Staffprojectrelationship(4L, ssf.findByName("coordinator"), staffPerson3, finalProjectList.get(0));
        sprf.create(spr);
        spr = new Staffprojectrelationship(5L, ssf.findByName("coordinator"), staffPerson2, finalProjectList.get(1));
        sprf.create(spr);
        spr = new Staffprojectrelationship(6L, ssf.findByName("coordinator"), staffPerson, finalProjectList.get(2));
        sprf.create(spr);

        return "";
    }
}
