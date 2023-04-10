package com.singh.employeemanagement.repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.employeemanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long>{
    public Page<Department> findByDepartmentNameContaining(String searchQuery, Pageable pageable);
	
	public Optional<Department> findByDepartmentName(String departmentName);
}
