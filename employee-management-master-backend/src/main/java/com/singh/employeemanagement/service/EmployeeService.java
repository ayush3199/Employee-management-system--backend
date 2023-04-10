package com.singh.employeemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.singh.employeemanagement.dto.EmployeeDTO;
import com.singh.employeemanagement.enums.EmployeeField;
import com.singh.employeemanagement.exception.BadRequestException;

public interface EmployeeService {
    public Page<EmployeeDTO> getEmployees(int pageNo, int pageSize, EmployeeField sortField,
            Sort.Direction sortDirection, String searchQuery);

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws BadRequestException;

    public EmployeeDTO editEmployee(Long employeeId, EmployeeDTO employeeDTO) throws BadRequestException;

    public String deleteEmployee(Long employeeId) throws BadRequestException;
}
