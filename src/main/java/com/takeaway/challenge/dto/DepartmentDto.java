package com.takeaway.challenge.dto;

public class DepartmentDto {

    private Integer id;

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

    @Override
    public String toString() {
        return "DepartmentDto [id=" + id + ", name=" + name + "]";
    }

}
