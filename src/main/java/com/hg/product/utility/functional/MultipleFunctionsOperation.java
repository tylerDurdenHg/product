package com.hg.product.utility.functional;

import com.hg.product.utility.streams.CollectOperation;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class MultipleFunctionsOperation {
    public static void main(String[] args) {
        List<String> cities = List.of("istanbul", "antep", "adana", "maras", "tokat");
        MultipleFunctionsOperation mfo = new MultipleFunctionsOperation();
        Function<Long, Long> increaseOne = s -> s + 1;
        Function<Long, Long> doubleIt = s -> s * 2;
        Function<Long, Long> allFunctions = doubleIt.andThen(increaseOne);
        Predicate<Long> evenNumbers = s -> s % 2 == 0;
        int value = 10;
        long result = mfo.doMathJobs(value, evenNumbers, allFunctions);
        System.out.println("result = " + result);
    }

    private long doMathJobs(long value, Predicate<Long> predicate, Function<Long, Long> allFunctions) {
        if (predicate.test(value)) {
            return allFunctions.apply(value);
        }
        return -1L;
    }


}

