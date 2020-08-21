package com.takeaway.challenge.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.dto.EmployeeDto;
import com.takeaway.challenge.exception.ApplicationException;
import com.takeaway.challenge.persistence.model.Department;
import com.takeaway.challenge.persistence.model.Employee;
import com.takeaway.challenge.persistence.repository.DepartmentRepository;
import com.takeaway.challenge.persistence.repository.EmployeeRepository;
import com.takeaway.challenge.producer.EventProducer;
import com.takeaway.challenge.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EventProducer producer;

    private static final String DEPARTMENT_NOT_FOUND = "Department with given dept id does not exists ";
    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id does not exists ";
    private static final String EMAIL_ALREADY_EXISTS = "Email address already exists ";

    @Override
    public List<EmployeeDto> getEmployees() {
        return Optional.ofNullable(employeeRepository.findAll())
                       .orElseGet(Collections::emptyList)
                       .stream()
                       .map(emp -> mapToEmployeeDto(emp))
                       .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                                              .orElseThrow(() -> new ApplicationException(EMPLOYEE_NOT_FOUND + id));
        return mapToEmployeeDto(employee);
    }

    @Override
    public void createEmployee(EmployeeDto employee) {
        boolean emailInUse = employeeRepository.findByEmail(employee.getEmail().toLowerCase()).isPresent();

        if (emailInUse) {
            throw new ApplicationException(EMAIL_ALREADY_EXISTS + employee.getEmail());
        }

        Department department = departmentRepository.findById(employee.getDepartmentId())
                                                    .orElseThrow(() -> new ApplicationException(DEPARTMENT_NOT_FOUND
                                                                                                + employee.getDepartmentId()));

        Employee emp = new Employee();
        emp.setBirthDate(employee.getBirthDate());
        emp.setDepartment(department);
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail().toLowerCase());
        Employee createdEmployee = employeeRepository.save(emp);

        producer.employeeCreatedLog(createdEmployee.getId());

    }

    @Override
    public void updateEmployee(EmployeeDto emp, Integer id) {
        Employee employee = employeeRepository.findById(id)
                                              .orElseThrow(() -> new ApplicationException(EMPLOYEE_NOT_FOUND + id));
        Department department =
                              departmentRepository.findById(emp.getDepartmentId())
                                                  .orElseThrow(() -> new ApplicationException(DEPARTMENT_NOT_FOUND
                                                                                              + emp.getDepartmentId()));

        employee.setBirthDate(emp.getBirthDate());
        employee.setDepartment(department);
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        employee.setEmail(emp.getEmail().toLowerCase());
        employeeRepository.save(employee);

        producer.employeeUpdatedLog(id);

    }

    @Override
    public void removeEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                                              .orElseThrow(() -> new ApplicationException(EMPLOYEE_NOT_FOUND + id));
        employeeRepository.delete(employee);

        producer.employeeDeletedLog(id);

    }

    private EmployeeDto mapToEmployeeDto(Employee emp) {
        return new EmployeeDto().firstName(emp.getFirstName())
                                .lastName(emp.getLastName())
                                .birthDate(emp.getBirthDate())
                                .email(emp.getEmail())
                                .departmentId(emp.getDepartment().getId())
                                .id(emp.getId());

    }

}
