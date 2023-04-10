package com.singh.employeemanagement.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.employeemanagement.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public Page<Employee> findByEmployeeFirstNameContaining(String searchQuery, Pageable pageable);

	public Optional<Employee> findByEmployeeEmail(String employeeEmail);
    
}
