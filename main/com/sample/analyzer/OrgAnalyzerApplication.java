package com.sample.analyzer;

import com.sample.analyzer.model.Employee;
import com.sample.analyzer.service.EmployeeService;
import com.sample.analyzer.utility.CsvParserUtility;
import com.sample.analyzer.utility.EmployeeUtility;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class OrgAnalyzerApplication {
    public static void main(String[] args) throws Exception {
        System.out.println("Please enter the absolute path of csv file:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        List<Employee> employeeList = CsvParserUtility.parseCsvFile(Path.of(path));
        EmployeeService service = new EmployeeService(employeeList);
        EmployeeUtility.findLongReportingLines(service.employeesReportingLineCount())
                .entrySet()
                .forEach(e -> {
                    System.out.println("Employee name: "+e.getKey().getFirstName()+" "+e.getKey().getLastName()+" with id: "+e.getKey().getId()+" has "+e.getValue()+" reporting lines between them and CEO");
                });
        EmployeeUtility.findUnderPaidManagers(service.reportingSubordinatesUnderManager())
                .entrySet()
                .stream()
                .forEach(e -> {
                    System.out.println("Manager name: "+e.getKey().getFirstName()+" "+e.getKey().getLastName()+" with id: "+e.getKey().getId()+" is under paid and earning "+String.format("%.2f",e.getValue())+"% than average subordinates salary");
                });
        EmployeeUtility.findOverPaidManagers(service.reportingSubordinatesUnderManager())
                .entrySet()
                .stream()
                .forEach(e -> {
                    System.out.println("Manager name: "+e.getKey().getFirstName()+" "+e.getKey().getLastName()+" with id: "+e.getKey().getId()+" is over paid and earning "+String.format("%.2f",e.getValue())+"% than average subordinates salary");
                });
    }
}
