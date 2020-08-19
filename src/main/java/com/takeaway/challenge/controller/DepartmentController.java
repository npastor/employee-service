package com.takeaway.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.challenge.ChallengeApi;
import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.service.DepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ChallengeApi
@RestController
@RequestMapping(value = {"v1/departments"})
@Api(tags = {"departments"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Gets all departments",
                  response = DepartmentDto.class,
                  responseContainer = "List",
                  produces = "application/json")
    public List<DepartmentDto> getDepartments() {
        return departmentService.getDepartments();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Creates a department", consumes = "application/json")
    public void createDepartment(@ApiParam(value = "Department data") @Valid @RequestBody DepartmentDto department) {
        department.trim();
        departmentService.createDepartment(department);

    }
}
