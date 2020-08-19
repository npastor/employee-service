package com.takeaway.challenge.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Department")
public class DepartmentDto {

    @ApiModelProperty(value = "Department ID.", readOnly = true)
    private Integer id;

    @NotBlank()
    @ApiModelProperty(value = "Department name.", required = true)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentDto id(Integer id) {
        this.id = id;
        return this;
    }

    public DepartmentDto name(String name) {
        this.name = name;
        return this;
    }

    public void trim() {
        name = StringUtils.trimWhitespace(name);
    }

    @Override
    public String toString() {
        return "DepartmentDto [id=" + id + ", name=" + name + "]";
    }

}
