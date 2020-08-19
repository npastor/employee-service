package com.takeaway.challenge.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.persistence.model.Department;
import com.takeaway.challenge.persistence.repository.DepartmentRepository;
import com.takeaway.challenge.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getDepartments() {
        return Optional.ofNullable(departmentRepository.findAll())
                       .orElseGet(Collections::emptyList)
                       .stream()
                       .map(dept -> new DepartmentDto().name(dept.getName()).id(dept.getId()))
                       .collect(Collectors.toList());

    }

    @Override
    public void createDepartment(DepartmentDto departmentDTO) {
        boolean departmentExists = departmentRepository.findByName(departmentDTO.getName()).isPresent();
        if (departmentExists) {
            throw new RuntimeException();
        }

        Department department = new Department();
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }

}
