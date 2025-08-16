package com.sample.analyzer.utility;

import com.sample.analyzer.model.Employee;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class EmployeeUtility {

    public static Map<Employee,Integer> findLongReportingLines(Map<Employee,Integer> reportingLineMap){
        return reportingLineMap
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 3)
                .collect(Collectors.toMap(k -> k.getKey(),v -> v.getValue()));
    }

    public static Map<Employee,Double> findUnderPaidManagers(Map<Employee, List<Employee>> managerDirectSubordinatesMap){
        return managerSubordinateSalaryRatioMap(managerDirectSubordinatesMap)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() < 20)
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
    }

    public static Map<Employee,Double> findOverPaidManagers(Map<Employee, List<Employee>> managerDirectSubordinatesMap){
        return managerSubordinateSalaryRatioMap(managerDirectSubordinatesMap)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 50)
                .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
    }

    private static Map<Employee, Double> managerSubordinateSalaryRatioMap(Map<Employee, List<Employee>> managerDirectSubordinatesMap){
        Map<Employee, Double> managerToSubordinateSalaryRatio = new HashMap<>();
        for(Map.Entry<Employee,List<Employee>> entry: managerDirectSubordinatesMap.entrySet()){
            double subordinateAverageSalary = entry.getValue().stream().mapToDouble(s -> s.getSalary()).average().orElse(0.0);
            managerToSubordinateSalaryRatio.put(entry.getKey(), calculateManagerToSubordinateSalaryRatio(entry.getKey().getSalary(),subordinateAverageSalary));
        }
        return managerToSubordinateSalaryRatio;
    }

    private static Double calculateManagerToSubordinateSalaryRatio(Double managerSalary, Double avgSubordinateSalary){
        return (managerSalary - avgSubordinateSalary)/(avgSubordinateSalary) * 100;
    }
}
