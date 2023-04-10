package com.singh.employeemanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.singh.employeemanagement.dto.DepartmentDTO;
import com.singh.employeemanagement.enums.DepartmentField;
import com.singh.employeemanagement.exception.BadRequestException;
import com.singh.employeemanagement.model.Department;
import com.singh.employeemanagement.repository.DepartmentRepository;
import com.singh.employeemanagement.service.DepartmentService;
import com.singh.employeemanagement.util.DepartmentUtil;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Value(value = "${department.id.notFound}")
    private String departmentIdDoesNotExist;

    @Value(value = "${department.name.alreadyExists}")
    private String departmentNameAlreadyExist;

    @Override
    public Page<DepartmentDTO> getDepartments(int pageNo, int pageSize, DepartmentField sortField,
            Sort.Direction sortDirection, String searchQuery) {
        Sort sort = Sort.by(sortDirection, sortField.toString());
        Page<Department> departmentPage = departmentRepository.findByDepartmentNameContaining(searchQuery,
                PageRequest.of(pageNo - 1, pageSize, sort));
        return departmentPage.map(DepartmentUtil::entityToDTO);
    }

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) throws BadRequestException {
        Optional<Department> departmentOptional = departmentRepository
                .findByDepartmentName(departmentDTO.getDepartmentName());
        if (departmentOptional.isPresent())
            throw new BadRequestException(departmentNameAlreadyExist);
        Department department = departmentRepository.save(DepartmentUtil.DTOToEntity(departmentDTO));
        return DepartmentUtil.entityToDTO(department);
    }

    @Override
    public DepartmentDTO editDepartment(Long departmentId, DepartmentDTO departmentDTO) throws BadRequestException {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        Department department = departmentOptional.orElseThrow(() -> new BadRequestException(departmentIdDoesNotExist));
        departmentOptional = departmentRepository.findByDepartmentName(departmentDTO.getDepartmentName());
        if (departmentOptional.isPresent() && !departmentOptional.get().getDepartmentId().equals(departmentId))
            throw new BadRequestException(departmentNameAlreadyExist);
        department.setDepartmentName(departmentDTO.getDepartmentName());
        return DepartmentUtil.entityToDTO(departmentRepository.save(department));
    }

}
