package com.takeaway.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.ChallengeApi;
import com.takeaway.challenge.dto.EmployeeDto;
import com.takeaway.challenge.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ChallengeApi
@RestController
@RequestMapping(value = {"v1/employees"})
@Api(tags = {"employees"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Gets all employees",
                  response = EmployeeDto.class,
                  responseContainer = "List",
                  produces = "application/json")
    public List<EmployeeDto> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Gets employee by employee id",
                  response = EmployeeDto.class,
                  produces = "application/json")
    public EmployeeDto getEmployeeById(@ApiParam(value = "Employee ID", required = true) @PathVariable(value = "id") Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Creates an employee",
                  consumes = "application/json")
    public void createEmployee(@ApiParam(value = "Employee data") @Valid @RequestBody EmployeeDto employee) {
        employee.trim();
        employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Updates employee information",
                  consumes = "application/json")
    public void updateEmployee(@ApiParam(value = "Employee data") @Valid @RequestBody EmployeeDto employee,
                               @ApiParam(value = "Employee ID", required = true) @PathVariable(value = "id") Integer id) {
        employee.trim();
        employeeService.updateEmployee(employee, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Removes an employee")
    public void removeEmployee(@ApiParam(value = "Employee ID", required = true) @PathVariable(value = "id") Integer id) {
        employeeService.removeEmployee(id);
    }
}
