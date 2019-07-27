package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Baptism.
 */
@Entity
@Table(name = "baptism")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Baptism implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "baptism_type")
    private String baptismType;

    @Column(name = "baptism_year")
    private String baptismYear;

    @Column(name = "baptism_church")
    private String baptismChurch;

    @ManyToOne
    @JsonIgnoreProperties("baptisms")
    private RegisterMember registerMember;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaptismType() {
        return baptismType;
    }

    public Baptism baptismType(String baptismType) {
        this.baptismType = baptismType;
        return this;
    }

    public void setBaptismType(String baptismType) {
        this.baptismType = baptismType;
    }

    public String getBaptismYear() {
        return baptismYear;
    }

    public Baptism baptismYear(String baptismYear) {
        this.baptismYear = baptismYear;
        return this;
    }

    public void setBaptismYear(String baptismYear) {
        this.baptismYear = baptismYear;
    }

    public String getBaptismChurch() {
        return baptismChurch;
    }

    public Baptism baptismChurch(String baptismChurch) {
        this.baptismChurch = baptismChurch;
        return this;
    }

    public void setBaptismChurch(String baptismChurch) {
        this.baptismChurch = baptismChurch;
    }

    public RegisterMember getRegisterMember() {
        return registerMember;
    }

    public Baptism registerMember(RegisterMember registerMember) {
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
        if (!(o instanceof Baptism)) {
            return false;
        }
        return id != null && id.equals(((Baptism) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Baptism{" +
            "id=" + getId() +
            ", baptismType='" + getBaptismType() + "'" +
            ", baptismYear='" + getBaptismYear() + "'" +
            ", baptismChurch='" + getBaptismChurch() + "'" +
            "}";
    }
}
