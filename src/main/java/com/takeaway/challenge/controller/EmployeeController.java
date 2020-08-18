package com.takeaway.challenge.controller;

import java.util.ArrayList;
import java.util.List;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ChallengeApi
@RestController
@RequestMapping(value = {"v1/employees"})
@Api(tags = {"employees"})
public class EmployeeController {

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Gets all employees",
                  response = EmployeeDto.class,
                  responseContainer = "List",
                  produces = "application/json")
    public List<EmployeeDto> getEmployees() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Gets employee by employee id",
                  response = EmployeeDto.class,
                  produces = "application/json")
    public EmployeeDto getEmployeeById(@ApiParam(value = "Employee ID") @PathVariable(value = "id") Integer id) {
        return new EmployeeDto();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Creates an employee",
                  consumes = "application/json")
    public void createEmployee(@ApiParam(value = "Employee data") @RequestBody EmployeeDto employee) {}

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Updates employee information",
                  consumes = "application/json")
    public void updateEmployee(@ApiParam(value = "Employee data") @RequestBody EmployeeDto employee,
                               @ApiParam(value = "Employee ID") @PathVariable(value = "id") Integer id) {}

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Removes an employee")
    public void removeEmployee(@ApiParam(value = "Employee ID") @PathVariable(value = "id") Integer id) {

    }
}
