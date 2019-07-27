package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A RegisterMember.
 */
@Entity
@Table(name = "register_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegisterMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    @Column(name = "first_street", nullable = false)
    private String firstStreet;

    @Column(name = "second_street")
    private String secondStreet;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "province", nullable = false)
    private String province;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "cell_phone", nullable = false)
    private String cellPhone;

    @Column(name = "residential_phone")
    private String residentialPhone;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "profession")
    private String profession;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "first_licenseplate")
    private String firstLicenseplate;

    @Column(name = "second_licenseplate")
    private String secondLicenseplate;

    @NotNull
    @Column(name = "residence_status", nullable = false)
    private String residenceStatus;

    @Column(name = "duty_status")
    private String dutyStatus;

    @Column(name = "prev_church")
    private String prevChurch;

    @Column(name = "instructor")
    private String instructor;

    @OneToMany(mappedBy = "registerMember")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FamilyMember> familyMembers = new HashSet<>();

    @OneToMany(mappedBy = "registerMember")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Baptism> baptisms = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "register_member_volunteer",
               joinColumns = @JoinColumn(name = "register_member_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "volunteer_id", referencedColumnName = "id"))
    private Set<Volunteer> volunteers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreviousRegister() {
        return previousRegister;
    }

    public RegisterMember previousRegister(String previousRegister) {
        this.previousRegister = previousRegister;
        return this;
    }

    public void setPreviousRegister(String previousRegister) {
        this.previousRegister = previousRegister;
    }

    public String getKorName() {
        return korName;
    }

    public RegisterMember korName(String korName) {
        this.korName = korName;
        return this;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public String getEngName() {
        return engName;
    }

    public RegisterMember engName(String engName) {
        this.engName = engName;
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public RegisterMember birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public RegisterMember gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstStreet() {
        return firstStreet;
    }

    public RegisterMember firstStreet(String firstStreet) {
        this.firstStreet = firstStreet;
        return this;
    }

    public void setFirstStreet(String firstStreet) {
        this.firstStreet = firstStreet;
    }

    public String getSecondStreet() {
        return secondStreet;
    }

    public RegisterMember secondStreet(String secondStreet) {
        this.secondStreet = secondStreet;
        return this;
    }

    public void setSecondStreet(String secondStreet) {
        this.secondStreet = secondStreet;
    }

    public String getCity() {
        return city;
    }

    public RegisterMember city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public RegisterMember province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public RegisterMember postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public RegisterMember cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getResidentialPhone() {
        return residentialPhone;
    }

    public RegisterMember residentialPhone(String residentialPhone) {
        this.residentialPhone = residentialPhone;
        return this;
    }

    public void setResidentialPhone(String residentialPhone) {
        this.residentialPhone = residentialPhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RegisterMember emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfession() {
        return profession;
    }

    public RegisterMember profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompanyName() {
        return companyName;
    }

    public RegisterMember companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstLicenseplate() {
        return firstLicenseplate;
    }

    public RegisterMember firstLicenseplate(String firstLicenseplate) {
        this.firstLicenseplate = firstLicenseplate;
        return this;
    }

    public void setFirstLicenseplate(String firstLicenseplate) {
        this.firstLicenseplate = firstLicenseplate;
    }

    public String getSecondLicenseplate() {
        return secondLicenseplate;
    }

    public RegisterMember secondLicenseplate(String secondLicenseplate) {
        this.secondLicenseplate = secondLicenseplate;
        return this;
    }

    public void setSecondLicenseplate(String secondLicenseplate) {
        this.secondLicenseplate = secondLicenseplate;
    }

    public String getResidenceStatus() {
        return residenceStatus;
    }

    public RegisterMember residenceStatus(String residenceStatus) {
        this.residenceStatus = residenceStatus;
        return this;
    }

    public void setResidenceStatus(String residenceStatus) {
        this.residenceStatus = residenceStatus;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public RegisterMember dutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
        return this;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getPrevChurch() {
        return prevChurch;
    }

    public RegisterMember prevChurch(String prevChurch) {
        this.prevChurch = prevChurch;
        return this;
    }

    public void setPrevChurch(String prevChurch) {
        this.prevChurch = prevChurch;
    }

    public String getInstructor() {
        return instructor;
    }

    public RegisterMember instructor(String instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Set<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public RegisterMember familyMembers(Set<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
        return this;
    }

    public RegisterMember addFamilyMember(FamilyMember familyMember) {
        this.familyMembers.add(familyMember);
        familyMember.setRegisterMember(this);
        return this;
    }

    public RegisterMember removeFamilyMember(FamilyMember familyMember) {
        this.familyMembers.remove(familyMember);
        familyMember.setRegisterMember(null);
        return this;
    }

    public void setFamilyMembers(Set<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public Set<Baptism> getBaptisms() {
        return baptisms;
    }

    public RegisterMember baptisms(Set<Baptism> baptisms) {
        this.baptisms = baptisms;
        return this;
    }

    public RegisterMember addBaptism(Baptism baptism) {
        this.baptisms.add(baptism);
        baptism.setRegisterMember(this);
        return this;
    }

    public RegisterMember removeBaptism(Baptism baptism) {
        this.baptisms.remove(baptism);
        baptism.setRegisterMember(null);
        return this;
    }

    public void setBaptisms(Set<Baptism> baptisms) {
        this.baptisms = baptisms;
    }

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public RegisterMember volunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
        return this;
    }

    public RegisterMember addVolunteer(Volunteer volunteer) {
        this.volunteers.add(volunteer);
        volunteer.getRegisterMembers().add(this);
        return this;
    }

    public RegisterMember removeVolunteer(Volunteer volunteer) {
        this.volunteers.remove(volunteer);
        volunteer.getRegisterMembers().remove(this);
        return this;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegisterMember)) {
            return false;
        }
        return id != null && id.equals(((RegisterMember) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegisterMember{" +
            "id=" + getId() +
            ", previousRegister='" + getPreviousRegister() + "'" +
            ", korName='" + getKorName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", gender='" + getGender() + "'" +
            ", firstStreet='" + getFirstStreet() + "'" +
            ", secondStreet='" + getSecondStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", residentialPhone='" + getResidentialPhone() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", profession='" + getProfession() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", firstLicenseplate='" + getFirstLicenseplate() + "'" +
            ", secondLicenseplate='" + getSecondLicenseplate() + "'" +
            ", residenceStatus='" + getResidenceStatus() + "'" +
            ", dutyStatus='" + getDutyStatus() + "'" +
            ", prevChurch='" + getPrevChurch() + "'" +
            ", instructor='" + getInstructor() + "'" +
            "}";
    }
}
