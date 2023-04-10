package com.singh.employeemanagement.util;

import com.singh.employeemanagement.dto.DepartmentDTO;
import com.singh.employeemanagement.model.Department;

public class DepartmentUtil {
    private DepartmentUtil() {
    }

    public static DepartmentDTO entityToDTO(Department department) {
        return new DepartmentDTO(department.getDepartmentId(), department.getDepartmentName());
    }

    public static Department DTOToEntity(DepartmentDTO departmentDTO) {
        return new Department(null, departmentDTO.getDepartmentName());
    }
}
