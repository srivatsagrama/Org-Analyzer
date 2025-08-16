package com.sample.analyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id", "firstName", "lastName" })
public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Double salary;
    private Integer managerId;
}