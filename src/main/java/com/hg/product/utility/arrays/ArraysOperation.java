package com.hg.product.utility.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class ArraysOperation {
    public static void main(String[] args) {
        ArraysOperation ao = new ArraysOperation();
        int[] arr1 = IntStream.rangeClosed(0, 10).toArray();
        int[] arr2 = IntStream.rangeClosed(0, 10).toArray();

       // nth max element
        int max = ao.nthMaxElement(arr1, 2);
        System.out.println("max = " + max);

        // array equality
        boolean isEqual = ao.checkEquality(arr1, arr2);
        System.out.println("isEqual = " + isEqual);
    }

    private boolean checkEquality(int[] arr1, int[] arr2) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        boolean classicEquals = Arrays.equals(arr1, arr2);
        System.out.println("classicEquals = " + classicEquals);

        Integer[] arr1Big = Arrays.stream(arr1).boxed().toArray(Integer[]::new);
        Integer[] arr2Big = Arrays.stream(arr2).boxed().toArray(Integer[]::new);
        return Arrays.deepEquals(arr1Big, arr2Big);
    }

    private int nthMaxElement(int[] arr, int k) {
        Arrays.sort(arr);
        return arr[arr.length -k];
    }
}
