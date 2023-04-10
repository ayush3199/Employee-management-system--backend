package com.singh.employeemanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.singh.employeemanagement.dto.EmployeeDTO;
import com.singh.employeemanagement.enums.EmployeeField;
import com.singh.employeemanagement.exception.BadRequestException;
import com.singh.employeemanagement.model.Department;
import com.singh.employeemanagement.model.Employee;
import com.singh.employeemanagement.repository.DepartmentRepository;
import com.singh.employeemanagement.repository.EmployeeRepository;
import com.singh.employeemanagement.service.EmployeeService;
import com.singh.employeemanagement.util.DepartmentUtil;
import com.singh.employeemanagement.util.EmployeeUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Value(value = "${department.name.notFound}")
    private String departmentNameDoesNotExist;

    @Value(value = "${employee.email.alreadyExists}")
    private String employeeEmailAlreadyExists;

    @Value(value = "${employee.id.notFound}")
    private String employeeIdDoesNotExist;

    @Value(value = "${employee.delete.success}")
    private String employeeDeleteSuccess;

    @Override
    public Page<EmployeeDTO> getEmployees(int pageNo, int pageSize, EmployeeField sortField, Direction sortDirection,
            String searchQuery) {
        Sort sort = Sort.by(sortDirection, sortField.toString());
        Page<Employee> employeePage = employeeRepository.findByEmployeeFirstNameContaining(searchQuery,
                PageRequest.of(pageNo - 1, pageSize, sort));
        return employeePage.map(EmployeeUtil::entityToDTO);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws BadRequestException {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(employeeDTO.getEmployeeEmail());
        if (employeeOptional.isPresent())
            throw new BadRequestException(employeeEmailAlreadyExists);
        Optional<Department> departmentOptional = departmentRepository
                .findByDepartmentName(employeeDTO.getEmployeeDepartment().getDepartmentName());
        Department department = departmentOptional
                .orElseThrow(() -> new BadRequestException(departmentNameDoesNotExist));
        Employee employee = EmployeeUtil.DTOToEntity(employeeDTO);
        employee.setEmployeeDepartment(department);
        return EmployeeUtil.entityToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO editEmployee(Long employeeId, EmployeeDTO employeeDTO) throws BadRequestException {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(employeeDTO.getEmployeeEmail());
        if (employeeOptional.isPresent() && !employeeOptional.get().getEmployeeId().equals(employeeId))
            throw new BadRequestException(employeeEmailAlreadyExists);
        Optional<Department> departmentOptional = departmentRepository
                .findByDepartmentName(employeeDTO.getEmployeeDepartment().getDepartmentName());
        Department department = departmentOptional
                .orElseThrow(() -> new BadRequestException(departmentNameDoesNotExist));
        employeeOptional = employeeRepository.findById(employeeId);
        Employee employee = employeeOptional.orElseThrow(() -> new BadRequestException(employeeIdDoesNotExist));
        employee.setEmployeeFirstName(employeeDTO.getEmployeeFirstName());
        employee.setEmployeeLastName(employeeDTO.getEmployeeLastName());
        employee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
        employee.setEmployeeDepartment(department);
        return EmployeeUtil.entityToDTO(employeeRepository.save(employee));
    }

    @Override
    public String deleteEmployee(Long employeeId) throws BadRequestException {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        Employee employee = employeeOptional.orElseThrow(() -> new BadRequestException(employeeIdDoesNotExist));
        employeeRepository.delete(employee);
        return employeeDeleteSuccess;
    }
}
