/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.common.db;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jim.common.StringFormatter;
import jim.sums.common.bus.BusinessException;
import jim.sums.common.util.UnitPersonInUtil;
import jim.sums.register.util.PasswordUtil;

/**
 *
 * @author Nicolas Dossou-Gbete
 */
@Entity
// @Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT m FROM Person m"),
    @NamedQuery(name = "Person.findByUsername", query = "SELECT m FROM Person m WHERE m.username = :username"),
    @NamedQuery(name = "Person.findByForename", query = "SELECT m FROM Person m WHERE m.forename = :forename"),
    @NamedQuery(name = "Person.findBySurname", query = "SELECT m FROM Person m WHERE m.surname = :surname"),
//    @NamedQuery(name = "Person.findByPassword", query = "SELECT m FROM Person m WHERE m.password = :password"),
    @NamedQuery(name = "Person.findByCreationdate", query = "SELECT m FROM Person m WHERE m.creationdate = :creationdate"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT m FROM Person m WHERE m.email = :email"),
    @NamedQuery(name = "Person.findByActivationdate", query = "SELECT m FROM Person m WHERE m.activationdate = :activationdate"),
    @NamedQuery(name = "Person.findByConfirmationdate", query = "SELECT m FROM Person m WHERE m.confirmationdate = :confirmationdate"),
    @NamedQuery(name = "Person.findByLastlogin", query = "SELECT m FROM Person m WHERE m.lastlogin = :lastlogin"),
    @NamedQuery(name = "Person.findByContactpoints", query = "SELECT m FROM Person m WHERE m.contactpoints = :contactpoints"),
    @NamedQuery(name = "Person.findByHemis", query = "SELECT m FROM Person m WHERE m.hemis = :hemis"),
    @NamedQuery(name = "Person.findByRole", query = "SELECT m FROM Person m WHERE :role MEMBER OF m.roles"),
    @NamedQuery(name = "Person.findByConfirmkey", query = "SELECT m FROM Person m WHERE m.confirmkey = :confirmkey")})
public class Person implements Serializable {

    @Basic(optional = false)
    @NotNull
    // @Column(name = "CREATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date creationdate;
    // @Column(name = "ACTIVATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date activationdate;
    // @Column(name = "CONFIRMATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date confirmationdate;
    @Basic(optional = false)
    @NotNull
    // @Column(name = "LASTLOGIN")
    @Temporal(TemporalType.DATE)
    private Date lastlogin;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    // @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    // @Column(name = "FORENAME")
    private String forename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    // @Column(name = "SURNAME")
    private String surname;
    // @Basic(optional = false)
    // @NotNull
    // @Size(min = 1, max = 32)
    // @Column(name = "PASSWORD")
    @Transient
    private String password;
    private String passwordEncrypted;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    // @Column(name = "EMAIL")
    private String email;
    @Size(max = 512)
    // @Column(name = "CONTACTPOINTS")
    private String contactpoints;
    @Size(max = 16)
    // @Column(name = "HEMIS")
    private String hemis;
    @Size(max = 32)
    // @Column(name = "CONFIRMKEY")
    private String confirmkey;
    @ManyToMany(mappedBy = "personList")
    private List<Cohort> cohortList; //ManagedBy
    @ManyToMany(mappedBy = "personList1")
    private List<Cohort> cohortList1; //SupervisorIn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Personchosenby> personchosenbyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person1")
    private List<Personchosenby> personchosenbyList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Staffprojectrelationship> staffprojectrelationshipList;
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    // private List<Grouptable> grouptableList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chosen")
    private List<Unitpersonin> unitpersoninList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owneridea")
    private List<Projectidea> projectideaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Ideachosenby> ideachosenbyList;
    @ManyToMany
    // @JoinTable(name = "GROUPTABLE",
    // joinColumns =
    // @JoinColumn(name = "USERNAME"),
    // inverseJoinColumns =
    // @JoinColumn(name = "ROLENAME"))
    private List<RoleName> roles = new ArrayList<RoleName>();
    // @JoinColumn(name = "ORGANISATION", referencedColumnName = "IDORGANISATION")
    @ManyToOne
    private Organisation organisation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suspendedperson")
    private List<Suspension> suspensionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instigaterperson")
    private List<Suspension> suspensionList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Projectideahistory> projectideahistoryList;
    @ManyToMany
    private List<Finalproject> finalprojectList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Personstaffstatus> personstaffstatusList;
    @Transient
    String accountStatus;
    @OneToMany(mappedBy = "person")
    private List<ContactDetail> contactDetails;
    @OneToMany(mappedBy = "student")
    private List<MilestoneInstance> milestoneInstances;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Submission> submissionList;
    @OneToMany(mappedBy = "marker")
    private List<MarkerMark> marks;

    public Person() {
    }

    public Person(String username) {
        this.username = username;
    }

    public Person(String username, String forename, String surname, String password, Date creationdate, String email, Date lastlogin) {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.setPassword(password);
//        this.password = password;
        this.creationdate = creationdate;
        this.email = email;
        this.lastlogin = lastlogin;
    }

    public List<MarkerMark> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkerMark> marks) {
        this.marks = marks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = formatUsername(username);
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = formatForename(forename);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = formatSurname(surname);
    }

    public String getFullname() {
        return this.forename + " " + this.surname;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
        this.setPasswordEncrypted(encryptPassword(password));
    }

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncypted) {
        this.passwordEncrypted = passwordEncypted;
    }

    private String encryptPassword(String password) {
        return PasswordUtil.hashMD5(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactpoints() {
        return contactpoints;
    }

    public void setContactpoints(String contactpoints) {
        this.contactpoints = contactpoints;
    }

    public String getHemis() {
        return hemis;
    }

    public void setHemis(String hemis) {
        this.hemis = hemis;
    }

    public String getConfirmkey() {
        return confirmkey;
    }

    public void setConfirmkey(String confirmkey) {
        this.confirmkey = confirmkey;
    }

    public List<Cohort> getCohortList() {
        return cohortList;
    }

    public void setCohortList(List<Cohort> cohortList) {
        this.cohortList = cohortList;
    }

    public List<Cohort> getCohortList1() {
        return cohortList1;
    }

    public void setCohortList1(List<Cohort> cohortList1) {
        this.cohortList1 = cohortList1;
    }

    public List<Personchosenby> getPersonchosenbyList() {
        return personchosenbyList;
    }

    public void setPersonchosenbyList(List<Personchosenby> personchosenbyList) {
        this.personchosenbyList = personchosenbyList;
    }

    public List<Personchosenby> getPersonchosenbyList1() {
        return personchosenbyList1;
    }

    public void setPersonchosenbyList1(List<Personchosenby> personchosenbyList1) {
        this.personchosenbyList1 = personchosenbyList1;
    }

    public List<Staffprojectrelationship> getStaffprojectrelationshipList() {
        return staffprojectrelationshipList;
    }

    public void setStaffprojectrelationshipList(List<Staffprojectrelationship> staffprojectrelationshipList) {
        this.staffprojectrelationshipList = staffprojectrelationshipList;
    }

//    public List<Grouptable> getGrouptableList() {
//        return grouptableList;
//    }
//
//    public void setGrouptableList(List<Grouptable> grouptableList) {
//        this.grouptableList = grouptableList;
//    }
    public List<Unitpersonin> getUnitpersoninList() {
        return unitpersoninList;
    }

    public void setUnitpersoninList(List<Unitpersonin> unitpersoninList) {
        this.unitpersoninList = unitpersoninList;
    }

    public List<Projectidea> getProjectideaList() {
        return projectideaList;
    }

    public void setProjectideaList(List<Projectidea> projectideaList) {
        this.projectideaList = projectideaList;
    }

    public List<Ideachosenby> getIdeachosenbyList() {
        return ideachosenbyList;
    }

    public void setIdeachosenbyList(List<Ideachosenby> ideachosenbyList) {
        this.ideachosenbyList = ideachosenbyList;
    }

//    @Deprecated
//    public PersonStatus getPersonStatus() {
//        return personStatus;
//    }
//
//    @Deprecated
//    public void setPersonStatus(PersonStatus personstatus) {
//        this.personStatus = personstatus;
//    }
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public List<Suspension> getSuspensionList() {
        return suspensionList;
    }

    public void setSuspensionList(List<Suspension> suspensionList) {
        this.suspensionList = suspensionList;
    }

    public List<Suspension> getSuspensionList1() {
        return suspensionList1;
    }

    public void setSuspensionList1(List<Suspension> suspensionList1) {
        this.suspensionList1 = suspensionList1;
    }

    public List<Projectideahistory> getProjectideahistoryList() {
        return projectideahistoryList;
    }

    public void setProjectideahistoryList(List<Projectideahistory> projectideahistoryList) {
        this.projectideahistoryList = projectideahistoryList;
    }

    public List<Finalproject> getFinalprojectList() {
        return finalprojectList;
    }

    public void setFinalprojectList(List<Finalproject> finalprojectList) {
        this.finalprojectList = finalprojectList;
    }

    public List<Personstaffstatus> getPersonstaffstatusList() {
        return personstaffstatusList;
    }

    public void setPersonstaffstatusList(List<Personstaffstatus> personstaffstatusList) {
        this.personstaffstatusList = personstaffstatusList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "jim.sums.common.db.Person[ username=" + username + " ]";
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Date getActivationdate() {
        return activationdate;
    }

    public void setActivationdate(Date activationdate) {
        this.activationdate = activationdate;
    }

    public Date getConfirmationdate() {
        return confirmationdate;
    }

    public void setConfirmationdate(Date confirmationdate) {
        this.confirmationdate = confirmationdate;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public List<Submission> getSubmission() {
        return submissionList;
    }

    public void setSubmission(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public boolean addContactDetail(ContactDetail cd) {
        return contactDetails.add(cd);
    }

    public boolean removeContactDetail(ContactDetail cd) {
        return contactDetails.remove(cd);
    }

    public void setContactDetails(List<ContactDetail> cds) {
        contactDetails = cds;
    }

    public List<MilestoneInstance> getMilestones() {
        return milestoneInstances;
    }

    public boolean addMilestone(MilestoneInstance mi) {
        return milestoneInstances.add(mi);
    }

    public boolean removeMilestone(MilestoneInstance mi) {
        return milestoneInstances.remove(mi);
    }

    public void setMilestones(List<MilestoneInstance> lmi) {
        milestoneInstances = lmi;
    }

    public boolean isUser() {
        return hasRole("User");
    }

    public boolean isAdmin() {
        return hasRole("Administrator");
//        return personStatus.isAdmin();
    }

    public boolean isStaff() {
        return hasRole("Staff") || isAdmin();
//        return personStatus.isStaff();
    }

    public boolean isStudent() {
        return hasRole("Student") || isAdmin();
//        return personStatus.isStudent();
    }

    public boolean isExternal() {
        return hasRole("External") || isAdmin();
//        return personStatus.isExternal();
    }

    public Finalproject getCurrentProject() {
        if (finalprojectList.size() < 1) {
            return null;
        } else {
            Finalproject fproj = new Finalproject();
            for (Finalproject fp : finalprojectList) {
                if (fp.getPlannedsubmissiondate().after(fproj.getPlannedsubmissiondate())) {
                    fproj = fp;
                }
            }
            return fproj;
        }
    }

    public Person getModerator() {
        if (isStudent()) {
            return getCurrentProject().getModerator();
        }
        return null;
    }

    public Person getSupervisor() {
        if (isStudent()) {
            return getCurrentProject().getSupervisor();
        }
        return null;
    }

    public UnitInstance getCurrentProjectUnit() {
        //TODO: Fix this to only select project units
        return UnitPersonInUtil.findCurrent(unitpersoninList);
    }

    public List<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleName> roles) {
        this.roles = roles;
    }

    public void addRole(RoleName role) {
        if (!getRoles().contains(role)) {
            this.getRoles().add(role);
            role.getPersonsHavingRole().add(this);
        }
    }

    public void removeRole(RoleName role) {
        getRoles().remove(role);
    }

    public List<MilestoneInstance> getMilestoneInstances() {
        return milestoneInstances;
    }

    public void setMilestoneInstances(List<MilestoneInstance> milestoneInstances) {
        this.milestoneInstances = milestoneInstances;
    }

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    public boolean hasRole(String rolename) {
        for (RoleName r : this.getRoles()) {
            if (r.matches(rolename)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(RoleName role) {
        for (RoleName r : this.getRoles()) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

    public String getRoleNames() {
        return StringFormatter.join(getRoles(), ", ");
    }

    public static String formatUsername(String username) {
        return username.toLowerCase();
    }

    public static String formatSurname(String s) {
        s = s.toLowerCase();
        if (s.charAt(0) == 'm' && s.charAt(1) == 'c' && s.length() > 3) {
            s = "Mc" + Character.toUpperCase(s.charAt(2)) + s.substring(3);
        } else if (s.charAt(0) == 'o' && s.charAt(1) == '\'' && s.length() > 3) {
            s = "O'" + Character.toUpperCase(s.charAt(2)) + s.substring(3);
        } else {
            s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
        return s;
    }

    public static String formatForename(String forename) {
        return Character.toUpperCase(forename.charAt(0)) + forename.substring(1).toLowerCase();
    }

    public InternetAddress getInternetAddress() throws BusinessException {
        try {
            return new InternetAddress(this.getEmail(), this.getFullname());
        } catch (UnsupportedEncodingException ex) {
            try {
                return new InternetAddress(this.getEmail());
            } catch (AddressException ex1) {
                Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex1);
                throw new BusinessException("Syntactically incorrect email address: " + this.getEmail(), ex1);
            }
        }
    }
    //TODO: Get Current Course Function
}
