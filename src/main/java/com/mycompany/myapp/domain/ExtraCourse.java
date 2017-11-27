package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A ExtraCourse.
 */
@Entity
@Table(name = "extra_course")
public class ExtraCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade", precision=10, scale=2)
    private BigDecimal grade;

    @ManyToOne
    @JsonIgnore
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ExtraCourse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public ExtraCourse grade(BigDecimal grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public ExtraCourse student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtraCourse extraCourse = (ExtraCourse) o;
        if (extraCourse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraCourse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraCourse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", grade='" + getGrade() + "'" +
            "}";
    }
}
