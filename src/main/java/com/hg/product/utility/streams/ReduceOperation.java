package com.hg.product.utility.streams;

import java.util.List;
import java.util.stream.IntStream;

public class ReduceOperation {

    public static void main(String[] args) {
        ReduceOperation ro = new ReduceOperation();
        ro.reduceStream();
        ro.reduceWithSumMax();
        ro.findSumFirst100Numbers(10);
    }

    private void findSumFirst100Numbers(int i) {
        Integer sum = IntStream.rangeClosed(1, i)
                .boxed()
                .reduce(0, Integer::sum);
        System.out.println("sum of first n numbers: = " + i + " sum:" + sum);

    }

    private void reduceWithSumMax() {
        List<Integer> numbers = List.of(2, 4, 1);
        Integer max = numbers.stream()
                .reduce(Integer::max).get();
        Integer sum = numbers.stream()
                .reduce(Integer::sum).get();
        System.out.println("max = " + max);
        System.out.println("sum = " + sum);
    }

    private void reduceStream() {
        List<String> names = List.of("tyler", "me", "mike");
        String reducedOne = names.stream()
                .reduce("", (a, b) -> a.concat("\n -").concat(b));
        System.out.println(reducedOne);
    }
}
