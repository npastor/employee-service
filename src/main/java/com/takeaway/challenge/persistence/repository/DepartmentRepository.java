package com.takeaway.challenge.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.persistence.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    public Optional<Department> findByName(String name);

}
