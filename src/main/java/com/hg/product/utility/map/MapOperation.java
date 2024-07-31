package com.hg.product.utility.map;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapOperation {

    public record Transaction(long id, boolean isCompleted) {
    }

    public record Employee(long id, String name, BigDecimal salary) {
    }


    public static void main(String[] args) {
        MapOperation mo = new MapOperation();

        // list frequency by sorting order
        List<Integer> numbers = List.of(4, 4, 3, 4, 5, 5, 6, 3);
        Map<Integer, Long> frequenctyBySorted = mo.findFrequencyBySorted(numbers);
        System.out.println("frequencyBySorted = " + frequenctyBySorted);

        // find non completed transactions average
        List<Transaction> transactions = List.of(
                new Transaction(1, false),
                new Transaction(6, false),
                new Transaction(4, false),
                new Transaction(22, true)
        );
        double averageOfCompletedTransaction = mo.completedTransactionAvarage(transactions);
        System.out.println("averageOfCompletedTransaction = " + String.format("%.3f", averageOfCompletedTransaction));

        // get complete or non completed tranaactions
        Map<Boolean, List<Transaction>> listOfTransactionsToSituation = mo.getListOfTransactionsToSituation(transactions);
        System.out.println("listOfTransactionsToSituation = " + listOfTransactionsToSituation);
        System.out.println("non completed transactions = " + listOfTransactionsToSituation.get(false));

        // update employee salary * 1.20
        List<Employee> employees = List.of(
                new Employee(1, "tyler", new BigDecimal("12.5")),
                new Employee(2, "micheal", new BigDecimal("20")),
                new Employee(3, "eliz", new BigDecimal("4"))
        );
        int ratio = 20;
        Map<Long, Employee> updatedSalaries = mo.updateSalaries(employees, ratio);
        updatedSalaries.forEach((k, v) -> System.out.println("id:" + k + " value:" + v));


    }

    private Map<Long, Employee> updateSalaries(List<Employee> employees, int ratio) {
        return employees.stream()
                .collect(Collectors.toMap(Employee::id, s -> {
                    BigDecimal interest = new BigDecimal("" + ratio).divide(new BigDecimal("" + 100), 2, RoundingMode.CEILING);
                    BigDecimal diff = s.salary().multiply(new BigDecimal("" + interest));
                    BigDecimal totalSalary = s.salary().add(diff).setScale(0, RoundingMode.DOWN);
                    return new Employee(s.id(), s.name(), totalSalary);
                }));
    }

    private Map<Boolean, List<Transaction>> getListOfTransactionsToSituation(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.partitioningBy(Transaction::isCompleted));

    }

    private double completedTransactionAvarage(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Predicate.not(Transaction::isCompleted))
                .mapToDouble(Transaction::id)
                .average()
                .orElse(-1);
    }

    private Map<Integer, Long> findFrequencyBySorted(List<Integer> numbers) {
        Comparator<Map.Entry<Integer, Long>> comparator = (a, b) -> {
            if (Objects.equals(b.getValue(), a.getValue())) {
                return b.getKey().compareTo(a.getKey());
            } else {
                return b.getValue().compareTo(a.getValue());
            }
        };

        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

}
