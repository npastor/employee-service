package com.takeaway.challenge.dto;

import java.io.Serializable;

public class EmployeeEventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;
    private String action;

    public EmployeeEventDto() {}

    public EmployeeEventDto(Integer employeeId, String action) {
        super();
        this.employeeId = employeeId;
        this.action = action;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Event [employeeId=" + employeeId + ", action=" + action + "]";
    }

}
