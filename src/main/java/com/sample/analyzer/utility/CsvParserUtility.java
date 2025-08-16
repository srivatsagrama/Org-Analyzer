package com.sample.analyzer.utility;

import com.sample.analyzer.model.Employee;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CsvParserUtility {

    public static List<Employee> parseCsvFile(Path path) throws IOException {
        return Files.readAllLines(path)
                .stream()
                .skip(1)
                .map(CsvParserUtility::toEmployee)
                .collect(Collectors.toList());
    }

    private static Employee toEmployee(String line) {
        String[] employeeDetails = line.split(",", -1);
        if (employeeDetails.length < 5) {
            throw new IllegalArgumentException("CSV line is invalid");
        }
        Employee e = new Employee();
        e.setId(Integer.parseInt(employeeDetails[0]));
        e.setFirstName(employeeDetails[1]);
        e.setLastName(employeeDetails[2]);
        e.setSalary(new Double(employeeDetails[3]));
        e.setManagerId(employeeDetails[4].isBlank() ? null : Integer.parseInt(employeeDetails[4]));
        return e;
    }



}
