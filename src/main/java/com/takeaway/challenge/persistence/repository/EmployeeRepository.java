package com.takeaway.challenge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.persistence.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
