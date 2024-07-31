package com.hg.product.utility.list;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ListOperation {

    public static void main(String[] args) {
        ListOperation lo = new ListOperation();
        List<String> list1 = List.of("10", "20", "valu12", "014", "20");

        // convert to number
        List<Integer> convertedNumbers = lo.extractNumbers(list1);
        System.out.println("convertedNumbers = " + convertedNumbers);

        // sort list to number
        List<Integer> sortedReverse = lo.sortReverse(convertedNumbers);
        System.out.println("reverse sorted = " + sortedReverse);

        // sort list to string length
        List<String> sortedToLenght = lo.sortToLength(list1);
        System.out.println("sortedToLenght = " + sortedToLenght);

        // list distinct elements in a list
        List<String> distinctList = lo.distinctList(list1);
        System.out.println("distinctList = " + distinctList);

        // find non unique elements in the list

        List<String> nonUniqueElements = lo.nonUniqueElements(list1);
        System.out.println("nonUniqueElements = " + nonUniqueElements);

        // list to LinkedList
        LinkedList<String> linkedList = lo.toLinkedList(list1);
        System.out.println("linkedList  " + linkedList.getLast());

        // find max min, average in a list and print
        List<Integer> listApp = List.of(3, 2, 1, 8);
        List<Integer> minMaxAverage = lo.findMinMaxAverage(listApp);
        System.out.println("minMaxAverage = " + minMaxAverage);

        // find prime numbers in the range
        int maxRange = 10;
        List<Integer> primeNumbers = lo.findPrimeNumbers(maxRange);
        System.out.println("primeNumbers = " + primeNumbers);

        // group by name
        List<String> fruits = List.of("apple", "limon", "tomato", "onion", "apple", "limon");
        Map<String, List<String>> groupedFruits = lo.groupByName(fruits);
        System.out.println("groupedFruits = " + groupedFruits);

        // merge two list and sorted by name
        List<String> mergedLists = lo.mergeTwoList(list1, fruits);
        System.out.println("mergedLists = " + mergedLists);

    }

    private List<String> mergeTwoList(List<String> list1, List<String> fruits) {
        return Stream.of(list1, fruits)
                .flatMap(List::stream)
                .filter(s -> !(s.equalsIgnoreCase("limon") || s.equalsIgnoreCase("0")))
                .toList();
    }

    private Map<String, List<String>> groupByName(List<String> fruits) {
        return fruits.stream()
                .collect(Collectors.groupingBy(Function.identity()));
    }

    private List<Integer> findPrimeNumbers(int maxRange) {
        return IntStream.rangeClosed(1, maxRange)
                .filter(this::isPrime)
                .boxed()
                .toList();
    }

    private boolean isPrime(int num) {
        return IntStream.rangeClosed(2, num / 2)
                .noneMatch(cur -> num % cur == 0);
    }

    private List<String> nonUniqueElements(List<String> list) {
        return list.stream()
                .filter(s -> Collections.frequency(list, s) > 1)
                .distinct()
                .toList();
    }

    private List<Integer> findMinMaxAverage(List<Integer> list) {
/* this is old fashion second
        int min = list.stream()
                .mapToInt(Integer::valueOf)
                .min()
                .orElse(Integer.MIN_VALUE);

        int max = list.stream()
                .mapToInt(Integer::valueOf)
                .max()
                .orElse(Integer.MIN_VALUE);

        int sum = list.stream()
                .mapToInt(Integer::valueOf)
                .sum();

        double avg = list.stream()
                .mapToInt(Integer::valueOf)
                .average()
                .orElse(Integer.MIN_VALUE);
*/
        IntSummaryStatistics statistics = list.stream()
                .mapToInt(Integer::valueOf)
                .summaryStatistics();

        return List.of(statistics.getMax(), statistics.getMin(), (int) statistics.getSum(), (int) statistics.getAverage());
    }

    private LinkedList<String> toLinkedList(List<String> list) {
        return list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<String> distinctList(List<String> list) {
        return list.stream()
                .distinct()
                .toList();
    }

    private List<String> sortToLength(List<String> list) {
        return list.stream()
                .sorted(Comparator.comparing(String::length))
                .toList();
    }

    private List<Integer> sortReverse(List<Integer> list) {
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private List<Integer> extractNumbers(List<String> list) {
        String regex = "[^0-9]";
        return list.stream()
                .map(s -> s.replaceAll(regex, ""))
                .filter(Predicate.not(String::isBlank))
                .map(Integer::valueOf)
                .toList();
    }
}
