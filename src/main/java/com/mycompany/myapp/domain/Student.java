package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "student", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private Set<ExtraCourse> extraCourses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Student name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Student surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<ExtraCourse> getExtraCourses() {
        return extraCourses;
    }

    public Student extraCourses(Set<ExtraCourse> ExtraCourses) {
        this.extraCourses = ExtraCourses;
        return this;
    }

    public Student addExtraCourse(ExtraCourse ExtraCourse) {
        this.extraCourses.add(ExtraCourse);
        ExtraCourse.setStudent(this);
        return this;
    }

    public Student removeExtraCourse(ExtraCourse ExtraCourse) {
        this.extraCourses.remove(ExtraCourse);
        ExtraCourse.setStudent(null);
        return this;
    }

    public void setExtraCourses(Set<ExtraCourse> ExtraCourses) {
        this.extraCourses = ExtraCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}
