package com.hg.product.utility.interfaces;

import java.util.List;
import java.util.stream.Stream;

public interface InterfaceOparation {

    public static void main(String[] args) {

        List<String> cities = Stream.of("istanbul", "antep", "adana", "maras", "tokat")
                .map(String::toUpperCase)
                .toList();
        System.out.println("cities = " + cities);
/*
        List<String> copyList = List.copyOf(cities);
        copyList.remove(0);
        System.out.println("copyList = " + copyList);
*/
//        checkException();
        int before1 = "a".compareTo("b");
        int before2 = "a".compareTo("a");
        int before3 = "b".compareTo("a");
        System.out.println("before1 = " + before1);
        System.out.println("before1 = " + before2);
        System.out.println("before3 = " + before3);

    }

    private static String checkException() {
        throw returnException();
    }

    public  static RuntimeException returnException() {
        return new IllegalArgumentException("my exception");
    }
}
