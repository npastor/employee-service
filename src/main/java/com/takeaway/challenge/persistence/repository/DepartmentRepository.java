package com.takeaway.challenge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeaway.challenge.persistence.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
