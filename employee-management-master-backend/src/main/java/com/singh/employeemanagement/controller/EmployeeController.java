package com.singh.employeemanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.singh.employeemanagement.dto.EmployeeDTO;
import com.singh.employeemanagement.enums.EmployeeField;
import com.singh.employeemanagement.exception.BadRequestException;
import com.singh.employeemanagement.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{pageNo}/{pageSize}")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@PathVariable int pageNo, @PathVariable int pageSize,
            @RequestParam(required = false, defaultValue = "employeeId") EmployeeField sortField,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "") String searchQuery) {
        Page<EmployeeDTO> employeeDTOPage = employeeService.getEmployees(pageNo, pageSize, sortField, sortDirection,
                searchQuery);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", Long.toString(employeeDTOPage.getTotalElements()));
        responseHeaders.set("Access-Control-Expose-Headers", "*");
        return new ResponseEntity<>(employeeDTOPage.getContent(), responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO)
            throws BadRequestException {
        return new ResponseEntity<>(employeeService.addEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{employeeId}")
    public ResponseEntity<EmployeeDTO> editEmployee(@PathVariable Long employeeId,
            @Valid @RequestBody EmployeeDTO employeeDTO)
            throws BadRequestException {
        return new ResponseEntity<>(employeeService.editEmployee(employeeId, employeeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId)
            throws BadRequestException {
        return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
    }

}
