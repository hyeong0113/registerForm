package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A FamilyMember.
 */
@Entity
@Table(name = "family_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "relation_status", nullable = false)
    private String relationStatus;

    @NotNull
    @Column(name = "previous_register", nullable = false)
    private String previousRegister;

    @NotNull
    @Column(name = "kor_name", nullable = false)
    private String korName;

    @NotNull
    @Column(name = "eng_name", nullable = false)
    private String engName;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "profession")
    private String profession;

    @Column(name = "company_name")
    private String companyName;

    @NotNull
    @Column(name = "cell_phone", nullable = false)
    private String cellPhone;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "church_served")
    private String churchServed;

    @Column(name = "year_served")
    private String yearServed;

    @Column(name = "duty_status")
    private String dutyStatus;

    @Column(name = "prev_church")
    private String prevChurch;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "family_member_volunteer",
               joinColumns = @JoinColumn(name = "family_member_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "volunteer_id", referencedColumnName = "id"))
    private Set<Volunteer> volunteers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("familyMembers")
    private RegisterMember registerMember;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public FamilyMember relationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
        return this;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getPreviousRegister() {
        return previousRegister;
    }

    public FamilyMember previousRegister(String previousRegister) {
        this.previousRegister = previousRegister;
        return this;
    }

    public void setPreviousRegister(String previousRegister) {
        this.previousRegister = previousRegister;
    }

    public String getKorName() {
        return korName;
    }

    public FamilyMember korName(String korName) {
        this.korName = korName;
        return this;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public String getEngName() {
        return engName;
    }

    public FamilyMember engName(String engName) {
        this.engName = engName;
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public FamilyMember birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public FamilyMember gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public FamilyMember profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompanyName() {
        return companyName;
    }

    public FamilyMember companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public FamilyMember cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public FamilyMember emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getChurchServed() {
        return churchServed;
    }

    public FamilyMember churchServed(String churchServed) {
        this.churchServed = churchServed;
        return this;
    }

    public void setChurchServed(String churchServed) {
        this.churchServed = churchServed;
    }

    public String getYearServed() {
        return yearServed;
    }

    public FamilyMember yearServed(String yearServed) {
        this.yearServed = yearServed;
        return this;
    }

    public void setYearServed(String yearServed) {
        this.yearServed = yearServed;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public FamilyMember dutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
        return this;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getPrevChurch() {
        return prevChurch;
    }

    public FamilyMember prevChurch(String prevChurch) {
        this.prevChurch = prevChurch;
        return this;
    }

    public void setPrevChurch(String prevChurch) {
        this.prevChurch = prevChurch;
    }

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public FamilyMember volunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
        return this;
    }

    public FamilyMember addVolunteer(Volunteer volunteer) {
        this.volunteers.add(volunteer);
        volunteer.getFamilyMembers().add(this);
        return this;
    }

    public FamilyMember removeVolunteer(Volunteer volunteer) {
        this.volunteers.remove(volunteer);
        volunteer.getFamilyMembers().remove(this);
        return this;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public RegisterMember getRegisterMember() {
        return registerMember;
    }

    public FamilyMember registerMember(RegisterMember registerMember) {
        this.registerMember = registerMember;
        return this;
    }

    public void setRegisterMember(RegisterMember registerMember) {
        this.registerMember = registerMember;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyMember)) {
            return false;
        }
        return id != null && id.equals(((FamilyMember) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
            "id=" + getId() +
            ", relationStatus='" + getRelationStatus() + "'" +
            ", previousRegister='" + getPreviousRegister() + "'" +
            ", korName='" + getKorName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", gender='" + getGender() + "'" +
            ", profession='" + getProfession() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", churchServed='" + getChurchServed() + "'" +
            ", yearServed='" + getYearServed() + "'" +
            ", dutyStatus='" + getDutyStatus() + "'" +
            ", prevChurch='" + getPrevChurch() + "'" +
            "}";
    }
}
