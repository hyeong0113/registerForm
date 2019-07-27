package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Volunteer.
 */
@Entity
@Table(name = "volunteer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Volunteer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "volunteer_type")
    private String volunteerType;

    @ManyToMany(mappedBy = "volunteers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<RegisterMember> registerMembers = new HashSet<>();

    @ManyToMany(mappedBy = "volunteers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<FamilyMember> familyMembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolunteerType() {
        return volunteerType;
    }

    public Volunteer volunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
        return this;
    }

    public void setVolunteerType(String volunteerType) {
        this.volunteerType = volunteerType;
    }

    public Set<RegisterMember> getRegisterMembers() {
        return registerMembers;
    }

    public Volunteer registerMembers(Set<RegisterMember> registerMembers) {
        this.registerMembers = registerMembers;
        return this;
    }

    public Volunteer addRegisterMember(RegisterMember registerMember) {
        this.registerMembers.add(registerMember);
        registerMember.getVolunteers().add(this);
        return this;
    }

    public Volunteer removeRegisterMember(RegisterMember registerMember) {
        this.registerMembers.remove(registerMember);
        registerMember.getVolunteers().remove(this);
        return this;
    }

    public void setRegisterMembers(Set<RegisterMember> registerMembers) {
        this.registerMembers = registerMembers;
    }

    public Set<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public Volunteer familyMembers(Set<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
        return this;
    }

    public Volunteer addFamilyMember(FamilyMember familyMember) {
        this.familyMembers.add(familyMember);
        familyMember.getVolunteers().add(this);
        return this;
    }

    public Volunteer removeFamilyMember(FamilyMember familyMember) {
        this.familyMembers.remove(familyMember);
        familyMember.getVolunteers().remove(this);
        return this;
    }

    public void setFamilyMembers(Set<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Volunteer)) {
            return false;
        }
        return id != null && id.equals(((Volunteer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
            "id=" + getId() +
            ", volunteerType='" + getVolunteerType() + "'" +
            "}";
    }
}
