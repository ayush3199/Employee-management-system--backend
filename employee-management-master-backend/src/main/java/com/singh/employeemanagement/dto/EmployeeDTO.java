package com.singh.employeemanagement.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {
    private Long employeeId;

    @NotNull(message = "{employee.firstname.missing}")
    @Size(min = 3, max = 10, message = "{employee.firstname.invalidSize}")
    @Pattern(regexp = "^[a-zA-Z]*", message = "{employee.firstname.invalidPattern}")
    private String employeeFirstName;

    @NotNull(message = "{employee.lastname.missing}")
    @Size(min = 3, max = 10, message = "{employee.lastname.invalidSize}")
    @Pattern(regexp = "^[a-zA-Z]*", message = "{employee.lastname.invalidPattern}")
    private String employeeLastName;

    @NotNull(message = "{employee.email.missing}")
    @Email(message = "{employee.email.invalid}")
    private String employeeEmail;

    @NotNull(message = "{employee.department.missing}")
    @Valid
    private DepartmentDTO employeeDepartment;

}
