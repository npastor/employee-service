package com.takeaway.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.takeaway.challenge.dto.EmployeeDto;
import com.takeaway.challenge.exception.ApplicationException;
import com.takeaway.challenge.persistence.model.Department;
import com.takeaway.challenge.persistence.model.Employee;
import com.takeaway.challenge.persistence.repository.EmployeeRepository;
import com.takeaway.challenge.service.impl.EmployeeServiceImpl;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Department department;

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeServiceImpl();
        ReflectionTestUtils.setField(employeeService, "employeeRepository", employeeRepository);
    }

    @Test
    void testGetEmployees_OK() {

        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(1, "abc@gmai.com", "abc", "xyz", LocalDate.of(1990, 12, 21), department));

        Mockito.when(employeeRepository.findAll())
               .thenReturn(expectedEmployees);
        List<EmployeeDto> actualEmployees = employeeService.getEmployees();
        assertNotNull(actualEmployees);
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertEquals(expectedEmployees.get(0).getId(), actualEmployees.get(0).getId());
        assertEquals(expectedEmployees.get(0).getEmail(), actualEmployees.get(0).getEmail());
        assertEquals(expectedEmployees.get(0).getFirstName(), actualEmployees.get(0).getFirstName());
        assertEquals(expectedEmployees.get(0).getLastName(), actualEmployees.get(0).getLastName());
        assertEquals(expectedEmployees.get(0).getBirthDate(), actualEmployees.get(0).getBirthDate());
        assertEquals(expectedEmployees.get(0).getDepartment().getId(), actualEmployees.get(0).getDepartmentId());
    }

    @Test
    void testGetEmployeeById_OK() {

        Employee expectedEmployee = new Employee(1, "abc@gmai.com", "abc", "xyz", LocalDate.of(1990, 12, 21), department);
        Mockito.when(employeeRepository.findById(Mockito.anyInt()))
               .thenReturn(Optional.of(expectedEmployee));
        EmployeeDto actualEmployee = employeeService.getEmployeeById(1);
        assertNotNull(actualEmployee);
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getEmail(), actualEmployee.getEmail());
        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getBirthDate(), actualEmployee.getBirthDate());
        assertEquals(expectedEmployee.getDepartment().getId(), actualEmployee.getDepartmentId());
    }

    @Test
    void testGetEmployeeById_BAD_REQUEST_DOES_NOT_EXISTS() {

        Mockito.when(employeeRepository.findById(Mockito.anyInt()))
               .thenReturn(Optional.empty());

        Assertions.assertThrows(ApplicationException.class, () -> {
            employeeService.getEmployeeById(1);
        });

    }

    @Test
    void testRemoveEmployeeById_BAD_REQUEST_DOES_NOT_EXISTS() {

        Mockito.when(employeeRepository.findById(Mockito.anyInt()))
               .thenReturn(Optional.empty());

        Assertions.assertThrows(ApplicationException.class, () -> {
            employeeService.removeEmployee(1);
        });

    }

    @Test
    void testUpdateEmployeeById_BAD_REQUEST_DOES_NOT_EXISTS() {

        Mockito.when(employeeRepository.findById(Mockito.anyInt()))
               .thenReturn(Optional.empty());

        Assertions.assertThrows(ApplicationException.class, () -> {
            employeeService.updateEmployee(new EmployeeDto(), 1);
        });

    }

    @Test
    void testCreateEmployee_EMAIL_ALREADY_EXISTS() {

        Mockito.when(employeeRepository.findByEmail(Mockito.anyString()))
               .thenReturn(Optional.of(new Employee()));

        Assertions.assertThrows(ApplicationException.class, () -> {
            employeeService.createEmployee(new EmployeeDto().email("abc@dss"));
        });

    }

}
