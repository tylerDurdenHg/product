package com.hg.product.utility.streams;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamOperation {

    public static void main(String[] args) {
        StreamOperation so = new StreamOperation();
        Stream<Integer> stream1 = Stream.of(3, 4, 1, 3, 2, 5);
        Stream<Integer> stream2 = Stream.of(100, 200, 150, 143);
        List<Integer> list1 = List.of(3, 55, 1, 10);
        List<Integer> list2 = List.of(98, 77, 12);

        // stream concat
        List<Integer> result = so.flatStreams(stream1, stream2);
        System.out.println("streams concatted: = " + result);

        // list concat
        List<Integer> flattedLists = so.flatLists(list1, list2);
        System.out.println("flattedLists = " + flattedLists);

        // stream sum why Big ? because it can hold any number
        BigInteger sumAsBigInteger = so.sumOfEvenNumbers(list2);
        System.out.println("sumAsBigInteger = " + sumAsBigInteger);

        // stream sum first N numbers with BigInteger
        int k = 2;
        BigInteger sumnNumbers = so.sumOfnNumbers(list2, k);
        System.out.println("sumnNumbers = " + sumnNumbers);
    }

    private BigInteger sumOfnNumbers(List<Integer> list, int k) {
        return list.stream()
                .limit(k)
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private BigInteger sumOfEvenNumbers(List<Integer> list) {
        return list.stream()
                .filter(s -> (s % 2) == 0 )
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private List<Integer> flatLists(List<Integer> list1, List<Integer> list2) {
        return Stream.of(list1.stream(), list2.stream())
                .flatMap(Function.identity())
                .toList();
    }

    private List<Integer> flatStreams(Stream<Integer> stream1, Stream<Integer> stream2) {
        // or use Stream.concat(stream1, stream2)
        return Stream.of(stream1, stream2)
                .flatMap(Function.identity())
                .toList();
    }
}
