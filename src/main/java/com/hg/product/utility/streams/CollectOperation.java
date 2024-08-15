package com.hg.product.utility.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hg.product.utility.streams.CollectOperation.EmployeeOfCollection.DEFAULT_EMPLOYEE;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;


public class CollectOperation {

    public record EmployeeOfCollection(long id, String name, String department) {
        public static final EmployeeOfCollection DEFAULT_EMPLOYEE = new EmployeeOfCollection(-1, "default", "default");
    }

    final List<EmployeeOfCollection> employeeOfCollections = List.of(
            new EmployeeOfCollection(1, "tyler", "it"),
            new EmployeeOfCollection(2, "mike", "hr"),
            new EmployeeOfCollection(1, "barney", "hr"),
            new EmployeeOfCollection(3, "vent", "account"),
            new EmployeeOfCollection(4, "mike", "it")
    );

    
    public static void main(String[] args) {
        CollectOperation co = new CollectOperation();
        List<Integer> toList = co.collectToList();
        System.out.println("toList = " + toList);

        LinkedHashMap<String, List<String>> collectedToGroup = co.collectToGroup();
        System.out.println("collectedToGroup = " + collectedToGroup);

        DoubleSummaryStatistics summarizing = co.summarizing();
        System.out.println("summarizing = " + summarizing);

        Map<String, EmployeeOfCollection> teeing = co.teeingEmployees();
        System.out.println("teeing = ✔   " + teeing);

        List<String> collectAndThen = co.collectAndThen();
        System.out.println("collectAndThen = " + collectAndThen);

        List<EmployeeOfCollection> filtering = co.filteringEmployees();
        System.out.println("filtering = " + filtering);

        Double averaging = co.averaging();
        System.out.println("averaging = " + averaging);

        Set<String> mappedEmployeeNames = co.mappingEmployeeNames();
        System.out.println("mappedEmployeeNames = " + mappedEmployeeNames);

        Set<String> flatMappingCharacters = co.flatMappingCharacters();
        System.out.println("flatMappingCharacters = " + flatMappingCharacters);

        LinkedHashMap<String, Long> createdMapNew = co.createMap();
        System.out.println("createdMapNew = " + createdMapNew);

        Map<Boolean, List<String>> partitionBy = co.partitionBy();
        System.out.println("partitionBy = " + partitionBy);

        int countOfWorkers = 1;
        List<String> departmentsWorkingMoreThanOneEmployee = co.workingMoreThanXEmployeesInADepartment(countOfWorkers);
        System.out.println("departmentsWorkingMoreThanOneEmployee = " + departmentsWorkingMoreThanOneEmployee);

    }

    private List<String> workingMoreThanXEmployeesInADepartment(int minWorkerCount) {
        return employeeOfCollections
                .stream()
                .collect(collectingAndThen(
                                groupingBy(EmployeeOfCollection::department), // Map<Dep, List<Employee>
                                extractNamesMoreThanWorkerCountFromMap(minWorkerCount)
                        )
                );
    }

    private static Function<Map<String, List<EmployeeOfCollection>>, List<String>> extractNamesMoreThanWorkerCountFromMap(int minWorkerCount) {
        return map -> map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > minWorkerCount)
                .flatMap(emp -> emp.getValue().stream())
                .map(emp -> "id:" + emp.id + " name:" + emp.name)
                .toList();
    }


    private Map<Boolean, List<String>> partitionBy() {
        return employeeOfCollections
                .stream()
                .collect(partitioningBy(
                        emp -> emp.name.length() > 4,
                        mapping(EmployeeOfCollection::name, toList())
                ));
    }

    private LinkedHashMap<String, Long> createMap() {
        return employeeOfCollections
                .stream()
                .collect(toMap(
                        EmployeeOfCollection::name,
                        EmployeeOfCollection::id,
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
    }

    private Set<String> flatMappingCharacters() {
        return employeeOfCollections
                .stream()
                .collect(flatMapping(
                        emp -> Arrays.stream(emp.name.split("")),
                        toSet()
                ));

    }

    private Set<String> mappingEmployeeNames() {
        return employeeOfCollections
                .stream()
                .collect(mapping(
                        EmployeeOfCollection::name,
                        toSet()
                ));
    }

    private Double averaging() {
        return employeeOfCollections
                .stream()
                .collect(averagingDouble(EmployeeOfCollection::id));
    }

    private List<EmployeeOfCollection> filteringEmployees() {
        return employeeOfCollections
                .stream()
                .collect(filtering(
                        emp -> emp.id > 2,
                        toList()
                ));
    }

    private List<String> collectAndThen() {
        EmployeeOfCollection maxIdEmployeeOfCollection = employeeOfCollections
                .stream()
                .collect(collectingAndThen(
                        maxBy(comparing(EmployeeOfCollection::id)),
                        emp -> emp.orElse(new EmployeeOfCollection(0, "non-emp", "non"))
                ));
        System.out.println("maxIdEmployee = " + maxIdEmployeeOfCollection);

        // #  find departments who are having max employees
        Map<String, Long> depWorkersMap = employeeOfCollections
                .stream()
                .collect(groupingBy(EmployeeOfCollection::department, counting()));// it 2, hr 2, xx 1

        Long maxWorkerCount = depWorkersMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(0L);

        return depWorkersMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(maxWorkerCount))
                .map(Map.Entry::getKey)
                .toList();
    }

    private Map<String, EmployeeOfCollection> teeingEmployees() {
        return employeeOfCollections
                .stream()
                .collect(teeing(
                        minBy(comparing(EmployeeOfCollection::id)),
                        maxBy(comparing(EmployeeOfCollection::id)),
                        (min, max) -> Map.of("min", min.orElse(DEFAULT_EMPLOYEE), "max", max.orElse(DEFAULT_EMPLOYEE))
                ));
    }

    private DoubleSummaryStatistics summarizing() {
        Double idSum = employeeOfCollections
                .stream()
                .collect(summingDouble(EmployeeOfCollection::id));
        System.out.println("idSum = " + idSum);

        return employeeOfCollections
                .stream()
                .collect(summarizingDouble(EmployeeOfCollection::id));
    }

    private LinkedHashMap<String, List<String>> collectToGroup() {
        Map<String, List<EmployeeOfCollection>> groupByDepartment = employeeOfCollections
                .stream()
                .collect(groupingBy(EmployeeOfCollection::department));
        System.out.println("groupByDepartment = " + groupByDepartment);

        Map<String, Long> groupingByDepCounter = employeeOfCollections
                .stream()
                .collect(groupingBy(EmployeeOfCollection::department, counting()));
        System.out.println("groupingByDepCounter = " + groupingByDepCounter);

        return employeeOfCollections
                .stream()
                .collect(groupingBy(
                                EmployeeOfCollection::department,
                                LinkedHashMap::new,
                                mapping(EmployeeOfCollection::name, toList())
                        ));
    }

    private List<Integer> collectToList() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        return numbers
                .stream()
                .filter(num -> num > 5)
                .collect(toList());
    }

}
