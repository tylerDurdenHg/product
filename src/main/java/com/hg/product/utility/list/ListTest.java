package com.hg.product.utility.list;

import java.util.List;

public class ListTest {

    public static void main(String[] args) {
        ListTest test = new ListTest();

        List<String> names = List.of("tyler", " micheal", "ceh");
        test.sortNames(names);
    }

    private List<String> sortNames(List<String> names) {
        names.stream()
                .sorted((a, b) -> Integer.compare(a.length(), b.length()))
                .peek(System.out::println)
                .toList();

        return null;
    }
}
