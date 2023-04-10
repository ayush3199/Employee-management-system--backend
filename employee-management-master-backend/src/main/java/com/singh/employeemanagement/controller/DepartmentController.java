package com.singh.employeemanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.singh.employeemanagement.dto.DepartmentDTO;
import com.singh.employeemanagement.enums.DepartmentField;
import com.singh.employeemanagement.exception.BadRequestException;
import com.singh.employeemanagement.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{pageNo}/{pageSize}")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@PathVariable int pageNo, @PathVariable int pageSize,
            @RequestParam(required = false, defaultValue = "departmentId") DepartmentField sortField,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "") String searchQuery) {
        Page<DepartmentDTO> departmentDTOPage = departmentService.getDepartments(pageNo, pageSize, sortField,
                sortDirection, searchQuery);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", Long.toString(departmentDTOPage.getTotalElements()));
        responseHeaders.set("Access-Control-Expose-Headers", "*");
        return new ResponseEntity<>(departmentDTOPage.getContent(), responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody DepartmentDTO departmentDTO)
            throws BadRequestException {
        return new ResponseEntity<>(departmentService.addDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{departmentId}")
    public ResponseEntity<DepartmentDTO> editDepartment(@PathVariable Long departmentId,
            @Valid @RequestBody DepartmentDTO departmentDTO)
            throws BadRequestException {
        return new ResponseEntity<>(departmentService.editDepartment(departmentId, departmentDTO), HttpStatus.OK);
    }
}
