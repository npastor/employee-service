package com.takeaway.challenge.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Employee")
public class EmployeeDto {

    @ApiModelProperty(value = "Employee ID.", readOnly = true)
    private Integer id;

    @NotBlank
    @Email
    @ApiModelProperty(value = "Email address of the employee.", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "First name of the employee.", required = true)
    private String firstName;

    @NotBlank
    @ApiModelProperty(value = "Last name of the employee.", required = true)
    private String lastName;

    @NotNull
    @Past
    @ApiModelProperty(value = "Birth date of the employee in format yyyy-MM-dd.", required = true, example = "1990-12-21")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    @ApiModelProperty(value = "Department Id of the department this employee belongs to.", required = true)
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

    public void trim() {
        email = StringUtils.trimWhitespace(email);
        firstName = StringUtils.trimWhitespace(firstName);
        lastName = StringUtils.trimWhitespace(lastName);
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
