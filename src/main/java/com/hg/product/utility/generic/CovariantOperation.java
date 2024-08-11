package com.hg.product.utility.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CovariantOperation {

    public static void main(String[] args) {
        CovariantOperation co = new CovariantOperation();
        co.isArraysCovariant();
        co.listWithGeneric();
        co.whatisSuperClass();
    }

    private void whatisSuperClass() {
        List<Integer> integers = List.of(1, 3, 2);
        List<Integer> arraysList = Arrays.asList(1, 3, 2);
        List<Integer> objectArrayList = new ArrayList<>();
        objectArrayList.add(1);
        String className = integers.getClass().getName();
        System.out.println(STR."class name is: \{className} and arraysList: \{arraysList.getClass().getName()}");
        System.out.println(STR."class name 2: \{objectArrayList.getClass().getName()}");
    }

    private void listWithGeneric() {
        List<Integer> integers = List.of(1, 2, 3);
        List<Double> doubles = List.of(3D, 2D);
        sumofAll(integers);
        sumofAll(doubles);
    }

    private <T extends Number>void sumofAll(List<T> list) {
        double sum = list.stream()
                .mapToDouble(Number::doubleValue)
                .sum();
        System.out.println("sum = " + sum);
    }

    private void isArraysCovariant() {
        Integer[] integers = IntStream.rangeClosed(1, 5).boxed().toArray(Integer[]::new);
        System.out.println(STR."Array is: \{Arrays.toString(integers)}");
        Number[] numbers = integers;
        numbers[1] = 10;
        System.out.println(STR."Array is: \{Arrays.toString(numbers)}");
    }

}
