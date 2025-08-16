package com.sample.analyzer.service;

import com.sample.analyzer.model.Employee;

import java.util.*;
import java.util.stream.Collectors;


public class EmployeeService {

    private List<Employee> employees;
    private Map<Integer,Employee> employeeMap;

    public EmployeeService(final List<Employee> employees) {
        this.employees = employees;
        this.employeeMap = employeeMap();
    }

    public Map<Employee,Integer> employeesReportingLineCount(){
        Map<Employee,Integer> map = new HashMap<>();
        for(Employee employee: employees){
            map.put(employee,reportingLinesForEmployee(employee,employeeMap));
        }
        return map;
    }

    public Map<Employee, List<Employee>> reportingSubordinatesUnderManager() {
        return employees.stream()
                .filter(e -> e.getManagerId() != null)
                .collect(Collectors.groupingBy(e -> employeeMap.get(e.getManagerId())));
    }


    private Map<Integer,Employee> employeeMap(){
        return employees.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(k -> k.getId(), v -> v));
    }

    private Integer reportingLinesForEmployee(Employee employee, Map<Integer,Employee> employeeMap){
        if(Objects.isNull(employee.getManagerId())){
            return 0;
        }
        Employee employeeManager = employeeMap.get(employee.getManagerId());
        int reportingCount = 0;
        while(employeeManager.getManagerId() != null){
            Employee employeeReporting = employeeMap.get(employeeManager.getManagerId());
            employeeManager = employeeReporting;
            reportingCount++;
        }
        return reportingCount;
    }

}
