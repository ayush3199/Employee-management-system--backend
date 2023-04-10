package com.singh.employeemanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_Id")
    private Long employeeId;
    @Column(name = "employee_First_Name")
    private String employeeFirstName;
    @Column(name = "employee_Last_Name")
    private String employeeLastName;
    @Column(name = "employee_Email")
    private String employeeEmail;

    @ManyToOne
    @JoinColumn(name = "employee_Department_Id")
    private Department employeeDepartment;

}
