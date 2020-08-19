package com.takeaway.challenge.service;

import java.util.List;

import com.takeaway.challenge.dto.EmployeeDto;

public interface EmployeeService {

    public List<EmployeeDto> getEmployees();

    public EmployeeDto getEmployeeById(Integer id);

    public void createEmployee(EmployeeDto employee);

    public void updateEmployee(EmployeeDto employee, Integer id);

    public void removeEmployee(Integer id);

}
