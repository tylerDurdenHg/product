package com.hg.product.utility.streams;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

;

public class CollectOperation {
    public record EmployeeOfCollection(long id, String name, String department) {
         public static final EmployeeOfCollection DEFAULT_EMPLOYEE_OF_COLLECTION = new EmployeeOfCollection(-1, "default", "default");
    }

    List<EmployeeOfCollection> employeeOfCollections = List.of(
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

        Map<String, EmployeeOfCollection> teeing = co.teeing();
        System.out.println("teeing = " + teeing);

        List<String> collectAndThen = co.collectAndThen();
        System.out.println("collectAndThen = " + collectAndThen);

        List<EmployeeOfCollection> filtering = co.filtering();
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
                .collect(
                        collectingAndThen(
                                groupingBy(EmployeeOfCollection::department), // Map<Dep, List<Employee>
                                map -> map.entrySet().stream()
                                        .filter(val -> val.getValue().size() > minWorkerCount)
                                        .flatMap(emps -> emps.getValue().stream())
                                        .map(emp -> "id:" + emp.id + " name:" + emp.name)
                                        .toList()
                        )
                );
    }


    private Map<Boolean, List<String>> partitionBy() {
        return employeeOfCollections
                .stream()
                .collect(partitioningBy(s -> s.name.length() > 4, Collectors.mapping(EmployeeOfCollection::name, toList())));
    }

    private LinkedHashMap<String, Long> createMap() {
        return employeeOfCollections
                .stream()
                .collect(toMap(
                        EmployeeOfCollection::name,
                        EmployeeOfCollection::id,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private Set<String> flatMappingCharacters() {
        return employeeOfCollections
                .stream()
                .collect(Collectors.flatMapping(s -> Arrays.stream(s.name.split("")), toSet()));
    }

    private Set<String> mappingEmployeeNames() {
        return employeeOfCollections
                .stream()
                .collect(Collectors.mapping(EmployeeOfCollection::name, toSet()));
    }

    private Double averaging() {
        return employeeOfCollections
                .stream()
                .collect(averagingDouble(EmployeeOfCollection::id));
    }

    private List<EmployeeOfCollection> filtering() {
        return employeeOfCollections
                .stream()
                .collect(Collectors.filtering(s -> s.id > 2, toList()));
    }

    private List<String> collectAndThen() {
        Collector<EmployeeOfCollection, ?, EmployeeOfCollection> collector =
                collectingAndThen(
                        maxBy(comparing(EmployeeOfCollection::id)),
                        emp -> emp.orElse(new EmployeeOfCollection(0, "non-emp", "non"))
                );

        EmployeeOfCollection maxIdEmployeeOfCollection = employeeOfCollections.stream()
                .collect(collector);
        System.out.println("maxIdEmployee = " + maxIdEmployeeOfCollection);
        // #  find departments who are having max employees
        Map<String, Long> depWorkersMap = employeeOfCollections.stream()
                .collect(groupingBy(EmployeeOfCollection::department, counting()));// it 2, hr 2, xx 1

        Long maxWorkerCount = depWorkersMap.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(0L);
        return depWorkersMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxWorkerCount))
                .map(Map.Entry::getKey)
                .toList();
    }

    private Map<String, EmployeeOfCollection> teeing() {
        return employeeOfCollections
                .stream()
                .collect(
                        Collectors.teeing(
                                minBy(comparing(EmployeeOfCollection::id)),
                                maxBy(comparing(EmployeeOfCollection::id)),
                                (min, max) -> Map.of("min", min.orElse(EmployeeOfCollection.DEFAULT_EMPLOYEE_OF_COLLECTION), "max", max.orElse(EmployeeOfCollection.DEFAULT_EMPLOYEE_OF_COLLECTION))
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
                .collect(
                        groupingBy(EmployeeOfCollection::department, LinkedHashMap::new,
                                Collectors.mapping(EmployeeOfCollection::name, toList())));
    }

    private List<Integer> collectToList() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        return numbers
                .stream()
                .filter(s -> s > 5)
                .collect(toList());
    }
}
