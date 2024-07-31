package com.hg.product.utility.arrays;

import java.util.Arrays;

public class ZeroToLeftOperation {

    public static void main(String[] args) {
        ZeroToLeftOperation zo = new ZeroToLeftOperation();

        int[] arr = {0, 1, 1, 0, 1, 0, 1};
//        int[] result1 = zo.zeroToLeft(arr);
//        System.out.println("result1 = " + Arrays.toString(result1));
        int[] result1 = zo.zeroToLeft2(arr);
        System.out.println("result1 = " + Arrays.toString(result1));


    }

    private int[] zeroToLeft2(int[] arr) {
        return Arrays.stream(Arrays.stream(arr)
                        .boxed()
                        .filter(s -> s == 0)
                        .toArray(s -> new Integer[arr.length]))
                .mapToInt(s -> s == null ? 1 : 0)
                .toArray();
    }

    private int[] zeroToLeft(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            while (arr[left] == 0) {
                left++;
            }
            while (arr[right] == 1) {
                right--;
            }
            if (arr[left] == 1 && arr[right] == 0) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        return arr;
    }

}
