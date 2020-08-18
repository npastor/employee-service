package com.takeaway.challenge.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeDto {

    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Integer departmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public EmployeeDto id(Integer id) {
        this.id = id;
        return this;
    }

    public EmployeeDto email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeDto firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeDto lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeDto birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public EmployeeDto departmentId(Integer departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    @Override
    public String toString() {
        return "EmployeeDto [id=" + id
               + ", email="
               + email
               + ", firstName="
               + firstName
               + ", lastName="
               + lastName
               + ", birthDate="
               + birthDate
               + ", departmentId="
               + departmentId
               + "]";
    }

}
