package com.takeaway.challenge.service;

import java.util.List;

import com.takeaway.challenge.dto.DepartmentDto;

public interface DepartmentService {

    public List<DepartmentDto> getDepartments();

    public void createDepartment(DepartmentDto department);

}
