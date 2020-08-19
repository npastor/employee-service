package com.takeaway.challenge.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.persistence.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public Optional<Employee> findByEmail(String email);

}
