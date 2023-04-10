package com.singh.employeemanagement.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDTO {
    private List<String> messages;

    private Integer status;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
}