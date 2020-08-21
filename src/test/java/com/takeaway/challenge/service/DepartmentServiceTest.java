package com.takeaway.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.exception.ApplicationException;
import com.takeaway.challenge.persistence.model.Department;
import com.takeaway.challenge.persistence.repository.DepartmentRepository;
import com.takeaway.challenge.service.impl.DepartmentServiceImpl;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        departmentService = new DepartmentServiceImpl();
        ReflectionTestUtils.setField(departmentService, "departmentRepository", departmentRepository);
    }

    @Test
    void testGetDepartments_OK() {

        List<Department> expectedDepartments = new ArrayList<>();
        expectedDepartments.add(new Department(1, "HR"));

        Mockito.when(departmentRepository.findAll())
               .thenReturn(expectedDepartments);
        List<DepartmentDto> actualDepartments = departmentService.getDepartments();
        assertNotNull(actualDepartments);
        assertEquals(expectedDepartments.size(), actualDepartments.size());
        assertEquals(expectedDepartments.get(0).getId(), actualDepartments.get(0).getId());
        assertEquals(expectedDepartments.get(0).getName(), actualDepartments.get(0).getName());
    }

    @Test
    void testGetDepartments_EMPTY() {

        Mockito.when(departmentRepository.findAll())
               .thenReturn(Collections.EMPTY_LIST);
        List<DepartmentDto> actualDepartments = departmentService.getDepartments();
        assertNotNull(actualDepartments);
        assertEquals(0, actualDepartments.size());
    }

    @Test
    void testGetDepartments_NULL() {

        Mockito.when(departmentRepository.findAll())
               .thenReturn(null);
        List<DepartmentDto> actualDepartments = departmentService.getDepartments();
        assertNotNull(actualDepartments);
        assertEquals(0, actualDepartments.size());
    }

    @Test
    void testCreateDepartment_BAD_REQUEST_DEPT_NAME_EXISTS() {
        Mockito.when(departmentRepository.findByName(Mockito.anyString()))
               .thenReturn(Optional.of(new Department()));

        Assertions.assertThrows(ApplicationException.class, () -> {
            departmentService.createDepartment(new DepartmentDto().name("HR"));
        });

    }

    @Test
    void testCreateDepartment_OK() {
        departmentService.createDepartment(new DepartmentDto().name("HR"));
    }

}
